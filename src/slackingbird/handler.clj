(ns slackingbird.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/hook" []
    (generate-string
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body "Ололошеньки"}))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
