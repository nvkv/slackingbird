(defproject slackingbird "0.1.0-SNAPSHOT"
  :description "Slackingbird: telegram proxy"
  :url "https://github.com/semka/slackingbird"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.391"]
                 [compojure "1.5.1"]
                 [cheshire "5.6.3"]
                 [ring/ring-defaults "0.2.1"]                 
                 [clj-http "2.3.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler slackingbird.handler/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.0"]]
                   :resource-paths ["test/resources"]}})
