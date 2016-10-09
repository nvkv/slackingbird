(ns slackingbird.config)

(defn bot-token []
  (System/getenv "SB_TELEGRAM_BOT_TOKEN"))

(defn base-url []
  (System/getenv "SB_BASE_URL"))