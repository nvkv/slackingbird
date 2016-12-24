(ns slackingbird.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]
            [slackingbird.config :as config]
            [slackingbird.slack.webhook-payload :as slack]
            [slackingbird.telegram.bot :as tg]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(tg/start-bot (config/bot-token))

(defroutes app-routes
  (POST "/slack/:chat-id" [chat-id]
    (fn [req]
      (let [body (:body req)
            json (or (-> req :params :payload) (slurp body))
            payload (slack/json->payload json)]
        (println (str "DEBUG: " json))
        (println (str "DEBUG: " (into {} payload)))
        (tg/proxy-payload (config/bot-token)
                          chat-id
                          payload))))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes api-defaults))
