(ns slackingbird.slack.markup
  (:require [clojure.string :as str]
            [clojure.core.reducers :as r]))

(defn markdownize
  "This function is a nightmare"
  [string]
  (if (nil? string)
    ""
    (let [str (as-> string x (str/replace x "_" "\\_")
                             (str/replace x #"<([^|\s]+?)>" "$1")
                             (str/replace x #"<(\S+?)\|(.*?)>" "[$2]($1)"))
          links (map first (re-seq #"(\[.*?\]\(.*?\))" str))
          replaced-links (map #(str/replace % "\\_" "_") links)
          zipped (map vector links replaced-links)]
      (r/reduce (fn [acc pair] (str/replace acc (first pair) (last pair)))
                str
                zipped))))
