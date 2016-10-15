(ns slackingbird.telegram_message_tests
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.java.io :as io]
            [slackingbird.telegram.message :as tg]
            [slackingbird.telegram.bot :as tg-bot]
            [slackingbird.slack.webhook-payload :as slack]))

(def payload
  (-> "test-payload.json"
      io/resource
      io/file
      slurp
      slack/json->payload))

(deftest test-color->emoji
  (testing "Default slack 'colors' should be convertable to emojis"
    (is (= (tg/slack-color->emoji "danger")   "❌"))
    (is (= (tg/slack-color->emoji "warning")  "⚠️"))
    (is (= (tg/slack-color->emoji "good")     "✅"))
    (is (= (tg/slack-color->emoji "whatever") "⭕"))))

(deftest test-telegram-fields-formatting
  (is (nil? (tg/format-field nil)))
  (testing "format-field should properly transform attachment's field to string"
    (let [field (first (:fields (first (:attachments payload))))
          tg-string (tg/format-field field)]
      (is (= tg-string "▫ Warning: Ololo incoming!")))))

(deftest test-telegram-attachment-formatting
  (testing "format-attachment should properly transform attachment record to string"
    (is (nil? (tg/format-attachment nil)))
    (let [attachment (first (:attachments payload))
          tg-string (tg/format-attachment attachment)]
      (is (= tg-string "❌ Warning All The Things Are Broken!\n▫ Warning: Ololo incoming!")))))

(deftest test-telegram-message-formatting
  (testing "format-message should properly transform Webhook Payload record to string"
    (is (nil? (tg/format-message nil)))
    (let [tg-message (tg/format-message payload)]
      (is (not (nil? tg-message)))
      (is (= tg-message
             "*ololobot: *Welcome to [Zombo Com](http://zombo.com)!\n\n❌ Warning All The Things Are Broken!\n▫ Warning: Ololo incoming!")))))

