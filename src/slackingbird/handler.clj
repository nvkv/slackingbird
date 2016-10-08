(ns slackingbird.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]
            [slackingbird.config :as config]
            [slackingbird.slack.webhook-payload :as slack]
            [slackingbird.telegram.bot :as tg]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes app-routes
  (POST "/hooker/:chat-id/hook" [chat-id] 
    (fn [{body :body}]
      (let [payload (slack/json->payload (slurp body))]        
        (tg/proxy-payload (config/bot-token)
                          chat-id 
                          payload))))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes api-defaults))
