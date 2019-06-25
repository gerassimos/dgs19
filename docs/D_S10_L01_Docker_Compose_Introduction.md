class: center, middle
# Section 10 - Docker Compose
## 1 Docker Compose - Introduction
---

## Overview of Docker Compose (1)
 - docker-compose is a tool for defining and running multiple containers on a Docker host.
 - A YAML file is used to configure the containers. 
 - With a single command `docker-compose up`, you create and start all the containers defined in your yml file.

---

## Overview of Docker Compose (2) 
 - YAML file is used to define:
   - containers
   - networks
   - volumes
 - docker-compose is mainly used for development purposes. 
 - The yml files can be used on a production environment with Docker Swarm. 
 - docker-compose.yml is the default filename but any file name can be used with `docker-compose -f`. 

---

## Documentation pages
### - https://docs.docker.com/compose/
### - https://docs.docker.com/compose/overview/
### - https://docs.docker.com/compose/compose-file/

---

## Docker Compose - Example (1a)
```yml
version: '3.6'
# same as 
# docker run -p 8080:80 --name nginx nginx

services:
  nginx:
    image: 
      nginx
    ports:
      - "8080:80"
```
---
## Docker Compose - Example (1b)

```console 
# cd resources/compose-sample-1
# docker-compose up

# docker container ls
CONTAINER ID   IMAGE   COMMAND       CREATED         STATUS         PORTS                  NAMES
94bd5ee203f4   nginx   "nginx -g…"   2 minutes ago   Up 2 minutes   0.0.0.0:8080->80/tcp   composesample1_nginx_1

$ docker network ls
NETWORK ID     NAME                     DRIVER    SCOPE
9f7a65b273f0   bridge                   bridge    local
8acc86631a77   composesample1_default   bridge    local
b0897e090893   host                     host      local
3163420f3967   none                     null      local
```

---

## Docker Compose - Example (2) 
 - docker-compose file for example 2:  
 - [docker-compose.yml](../resources/compose-sample-2/docker-compose.yml)

 
