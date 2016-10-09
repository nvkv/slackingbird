(ns slackingbird.slack.markup
  (:require [clojure.string :as str]))

(defn markdownize [string]
  (as-> string x
    (str/replace x "_" "\\_")
    (str/replace x #"<(\S+?)\|(.*?)>" "[$2]($1)")
    (str/replace x #"<(\S+?)>" "$1")))