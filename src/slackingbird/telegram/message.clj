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

(defn format-attachment-title [attachment]
  (if (empty? (:title attachment))
    ""
    ; HACK: Thank you, Pasha, for shitty markdown implementation
    (let [title (as-> (:title attachment) x (str/replace x "[" "(")
                                            (str/replace x "]" ")"))
          link  (:title_link attachment)]
      (if-not (nil? link)
        (format " [%s](%s)" title link)
        (format " *%s*" title)))))

(defn format-attachment [attachment]
  (if (nil? attachment)
    nil
    (let [emoji (slack-color->emoji (:color attachment))
          text (markdownize (or (:text attachment) "Attachments:"))
          fields (str/join "\n" (map format-field (:fields attachment)))]
      (format "%s %s %s\n%s"
        emoji
        (format-attachment-title attachment)
        text
        fields))))

(defn format-message [payload]
  (if (nil? payload)
    nil
    (let [bot-name (:username payload)
          title (:title payload)
          text (markdownize (:text payload))
          attachments (str/join "\n" (map format-attachment (:attachments payload)))]
        (format "*%s*%s%s\n\n%s"
              (if-not (empty? bot-name) (str bot-name ": ") "")
              (if-not (empty? title) (str title "\n") "")
              text
              attachments))))
