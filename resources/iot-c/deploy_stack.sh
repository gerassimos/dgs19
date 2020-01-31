#!/bin/bash

docker network create --driver=overlay traefik-public
#docker stack deploy -c docker-compose-rp-https-le.yml
docker stack deploy -c docker-compose-rp-http.yml

