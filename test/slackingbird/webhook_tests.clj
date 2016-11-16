(ns slackingbird.webhook-tests
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.java.io :as io]
            [cheshire.core :refer :all]
            [slackingbird.slack.webhook-payload :refer :all]))

(deftest test-parsing
  (testing "json->payload should be able to create proper WebhookPayload record from the valid JSON"
    (let [json (slurp (-> "test-payload.json" io/resource io/file))
          payload (json->payload json)]
      (is (= (:text payload) "Welcome to <http://zombo.com|Zombo Com>!"))
      (is (= (:username payload) "ololobot"))
      (is (= (count (:attachments payload)) 1))
      (let [attach (first (:attachments payload))]
        (is (= (:color attach) "danger"))
        (is (= (:text attach) "Warning All The Things Are Broken!"))
        (is (= (count (:fields attach)) 1))))))

(deftest test-malformed-json-parsing
  (testing "json->payload should return nil in case of invalid JSON"
    (let [json (slurp (-> "test-invalid.json" io/resource io/file))
          payload (json->payload json)]
      (is (nil? payload)))))
