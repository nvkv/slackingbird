(ns slackingbird.telegram.bot
  (:require [clojure.core.async :as async]
            [clj-http.client :as http]
            [cheshire.core :as json]
            [slackingbird.config :as config]
            [slackingbird.telegram.message :refer :all]))

(defn base-url [bot-token]
  (format "https://api.telegram.org/bot%s" bot-token))

(defn hook-url [bot-token]
  (format "%s/sendMessage" (base-url bot-token)))

(defn updates-url [bot-token offset]
  (format "%s/getUpdates?offset=%s" (base-url bot-token) offset))

(defn send-message [bot-token chat-id message]
  (http/post (hook-url bot-token)
             {:content-type :json
              :form-params {"chat_id" chat-id
                            "text" message
                            "disable_web_page_preview" true
                            "parse_mode" "Markdown"}}))

(defn proxy-payload [bot-token chat-id payload]
  (println (format-message payload))
  (if-not (nil? payload)
    (send-message bot-token chat-id (format-message payload))))

(defn process-updates [bot-token updates]
  (doseq [update updates]
    (let [text (-> update :message :text)
          chat-id (-> update :message :chat :id)
          sender (-> update :message :from :first_name)
          hook-url (format "%s/slack/%d" (config/base-url) chat-id)]
      (case text
        "/hook" (send-message bot-token
                              chat-id
                              (format "%s, hook for this conversation is:\n*%s*" sender hook-url))
        nil)
      (println (str "Ok, processing update: " text)))))

(defn start-bot [bot-token]
  (def offset (atom 0))
  (async/thread
    (while true
      (try
        (let [result (json/parse-string (:body (http/get (updates-url bot-token @offset))) true)
              updates (:result result)
              last-update-id (:update_id (last updates))]
          (process-updates bot-token updates)
          (if-not (nil? last-update-id)
            (swap! offset (fn [_] (inc last-update-id))))
          (Thread/sleep 1000))
        (catch Exception e
          (println (str "Bot running exception: " e)))))))
