#!/bin/sh 

lein ring uberjar

docker build -t sdfgh153/slackingbird .
docker push sdfgh153/slackingbird