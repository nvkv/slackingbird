(ns slackingbird.telegram.bot
  (:require [clj-http.client :as http]
            [slackingbird.telegram.message :refer :all]))

(defn hook-url [bot-token] 
  (format "https://api.telegram.org/bot%s/sendMessage" bot-token))

(defn proxy-payload [bot-token chat-id payload]  
  (http/post (hook-url bot-token)
             {:content-type :json
              :form-params {"chat_id" chat-id
                            "text" (format-message payload)
                            "parse_mode" "Markdown"}}))
