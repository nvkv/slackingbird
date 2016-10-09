# slackingbird

Everything in the world can be integrated with ~~Slack~~ Telegram.

Slackingbird provides webhooks compatible with Slack webhooks so anything you integrate into the Slack with 
webhooks now can be integrated into the Telegram.

## Prerequisites

- [A bot](https://core.telegram.org/bots#3-how-do-i-create-a-bot) and its 
token
- Publically accessible server
- If you want to build Slackingbird from source, you will need [Leiningen](https://github.com/technomancy/leiningen) 2.0.0 or above installed.

## Installing

Slackingbird is configured via environment variables:

- `SB_TELEGRAM_BOT_TOKEN` — token for you bot
- `SB_BASE_URL` — base domain name of the server Slackingbird deployed to. Slackingbird using this string 
to respond to the `/hook` command only

## Run in docker

```
$ docker run -d -p 3000:3000 -e "SB_TELEGRAM_BOT_TOKEN=<token>" -e "SB_BASE_URL=https://<base-url>" sdfgh153/slackingbird
```

## Running from source

```
$ git clone https://github.com/semka/slackingbird.git
$ cd slackingbird
$ export SB_SB_TELEGRAM_BOT_TOKEN=<token>
$ export SB_BASE_URL=https://<base-url>
$ lein ring server
```

## Start Using

Add your bot to the group and fire `/hook` command. Bot will reply with webhook URL.
