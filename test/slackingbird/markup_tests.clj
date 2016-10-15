(ns slackingbird.markup-tests
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.java.io :as io]
            [slackingbird.slack.markup :refer :all]))

(deftest test-links-transformation
  (testing "Slack links format should be transformed to markdown"
    (let [markdown (markdownize "<http://zombo.com|Best Website Ever>")]
      (is (= markdown "[Best Website Ever](http://zombo.com)")))))


(deftest test-links-transformation
  (testing "Multiple occurrences of the slack links should be handled"
    (let [markdown (markdownize "<http://zombo.com|Best Website Ever> and <http://ya.ru|Shortest link I know>")]
      (is (= markdown "[Best Website Ever](http://zombo.com) and [Shortest link I know](http://ya.ru)")))))

(deftest test-raw-links-transformation
  (testing "Raw links in angle brackets format should be transformed to raw link"
    (let [markdown (markdownize "<http://zombo.com>")]
      (is (= markdown "http://zombo.com")))))

(deftest test-standalone-angle-brackets
  (testing "Standalone angle brackets should stay intact"
    (let [source "<) Fourty two is certainly > than any other number"
          markdown (markdownize source)]
      (is (= markdown source)))))