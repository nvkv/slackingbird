(ns slackingbird.slack.webhook-payload
  (:require [cheshire.core :as json]))

(defrecord WebhookPayload
  [text
   username
   channel
   attachments])

(defrecord MessageAttachment
  [fallback
   color
   pretext
   author_name
   author_link
   author_icon
   title
   title_link
   text
   fields
   image_url
   footer])

(defn json->payload [json-string]
  (try
    (as-> json-string x
          (json/parse-string x true)
          (map->WebhookPayload x)
          (assoc x
                 :attachments
                 (map #(map->MessageAttachment %) (:attachments x))))
  (catch Exception e
    (println e))))
