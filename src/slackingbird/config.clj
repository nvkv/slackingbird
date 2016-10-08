(ns slackingbird.config)

(defn bot-token []
  (System/getenv "TELEGRAM_BOT_TOKEN"))