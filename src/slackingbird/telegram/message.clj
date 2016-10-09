(ns slackingbird.telegram.message
  (:require [clojure.string :as str]
            [slackingbird.slack.webhook-payload :refer :all]
            [slackingbird.slack.markup :refer :all]))

(defn slack-color->emoji [color-string]
  (case color-string
    "good"    "✅"
    "warning" "⚠️"
    "danger"  "❌"
              "⭕"))

(defn format-field [field]  
  (if (nil? field)
    nil
    (format "▫ %s: %s" 
            (markdownize (:title field)) 
            (markdownize (:value field)))))

(defn format-attachment [attachment]
  (if (nil? attachment)
    nil
    (let [emoji (slack-color->emoji (:color attachment))
          text (markdownize (:text attachment))
          fields (str/join "\n" (map format-field (:fields attachment)))]
      (format "%s %s\n%s" emoji text fields))))
    
(defn format-message [payload]
  (if (nil? payload)
    nil
    (let [bot-name (:username payload)
          text (markdownize (:text payload))
          attachments (str/join "\n" (map format-attachment (:attachments payload)))]      
      (format "*%s*%s\n\n%s"
              (if-not (empty? bot-name) (str bot-name ": ") "")
              text
              attachments))))

