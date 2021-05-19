class: center, middle
# Section 10 - Docker Compose
## 1 Docker Compose - Introduction
---

## Docker Compose - Intro (1)
 - Most modern application are made of multiple smaller *services* that interact with each other to form an **application**  
 - Example:
   - front-end (html - javascript)
   - back-end (business logic - Rest endpoints)
   - DB
 - Deploy and manage multiple services (multiple containers) can be difficult. This is where **docker-compose** comes in to play
 - Instead of executing a separate `docker run` commands for each service of the application we can use a single `docker-compose up` command deploy the entire application.  

---

## Overview of Docker Compose (1)
 - There are 2 separate components required to use  **docker-compose**
   1. The **docker-compose.yml** (YAML) file used to define: 
      - **services** 
      - **networks**
      - **volumes**
   2. The CLI tool **docker-compose** used in conjunction with the *yml* file

---

## Overview of Docker Compose (2) 
 - docker-compose is mainly used for local development purposes. 
 - The yml files can be used on a production environment with **Docker Swarm**. 
 - **docker-compose.yml** is the **default** filename but any file name can be used with `docker-compose -f`. 

---
## Docker Compose - Installation

 - On desktop systems like Docker Desktop for Mac and Windows, Docker Compose is **included** as part of those desktop installs
 - Docker Compose is **NOT included** in the Docker Linux installation packages
 - Docker Compose installation instructions for Linux are available [here](https://docs.docker.com/compose/install/)

> Notes:  
> - *Docker Compose* relies on **Docker Engine** to work, so make sure you have Docker Engine installed before you start using the *Docker Compose* tool.  
> - *Docker Compose* is **not** a production-grade tool but is ideal for local development and test

---

## Docker Compose - Documentation pages
 - [Overview](https://docs.docker.com/compose/)
 - [Install](https://docs.docker.com/compose/install/)
 - [Reference](https://docs.docker.com/compose/compose-file/)

---

## Docker Compose - Example (1a)
 - Use `docker-compose up` to start the services defined in a **docker-compose.yml** file  
 
```console
version: '3.6'
# same as 
# docker run -p 8080:80 --name nginx nginx:1.19
   
services:
  nginx:
    image:nginx:1.19
    ports:
    - "8080:80"
```
> The [docker-compose.yml](https://github.com/gerassimos/dgs19/blob/master/resources/compose-sample-1/docker-compose.yml) of this example is available in the `resources` directory 

---

## Docker Compose - Example (1b)

```console 
# git clone https://github.com/gerassimos/dgs19.git
# cd dgs19/resources/compose-sample-1
# docker-compose up -d

# docker container ls
CONTAINER ID   IMAGE       COMMAND       CREATED         STATUS         PORTS                  NAMES
94bd5ee203f4   nginx:1.19  "nginx -g…"   2 minutes ago   Up 2 minutes   0.0.0.0:8080->80/tcp   composesample1_nginx_1

$ docker network ls
NETWORK ID     NAME                     DRIVER    SCOPE
9f7a65b273f0   bridge                   bridge    local
8acc86631a77   composesample1_default   bridge    local
b0897e090893   host                     host      local
3163420f3967   none                     null      local
```

---

## Compose file - schema (1)
 - The *version* is first top level key attribute of the `docker-compose.yml` file 
 - It ~~is~~ was mandatory and we should normally use the latest version 
 - The *version* value defines the format (basically the API)
 - Every single key value option that can be used in the `docker-compose.yml` file is described on the [documentation page](https://docs.docker.com/compose/compose-file/)  
 - More information [here](https://docs.docker.com/compose/compose-file/compose-versioning/) and [here](https://github.com/compose-spec/compose-spec/blob/master/spec.md#version-top-level-element)

---
 
## Compose file - schema (2)
 
 - The default compose file name is `docker-compose.yml`, in this case we do not need to specify the yml file when we execute the `docker-compose` cli commands, e.g:

 ```console
 # docker-compose up
 ```
 - Use `-f` to specify the name and path of a custom yml compose file, e.g:
 ```console
 # docker-compose -f docker-compose-custom.yml up
 ```

---

## Docker Compose - Example 2 notes 
 - In this example we will see how to use **Docker compose** to replace all the commands used in the [D_S9_L3_Persistent_Data_LAB](https://github.com/gerassimos/dgs19/blob/master/exercises/D_S9_L3_Persistent_Data_LAB.md)

 - The [docker-compose.yml](https://github.com/gerassimos/dgs19/blob/master/resources/compose-sample-2/docker-compose.yml) of this example is available in the `resources` directory.
  
---

## Docker Compose - Example 2 part1 
 - Example 2 part1
 
    ```console
    version: '3.6'
    
    services:
      postgres10:
        image: postgres:10
        environment:
          POSTGRES_DB: "db-test1"
          POSTGRES_USER: "db-user1"
          POSTGRES_PASSWORD: "db-pw1"
        volumes:
          - ./init.sql:/docker-entrypoint-initdb.d/init.sql:ro
          - db-data:/var/lib/postgresql/data      
        networks:
          - net-db
    ...
    ```
---

## Docker Compose - Example 2 part2
  - Example 2 part2
  
    ```console
      pgadmin4:
        image: dpage/pgadmin4:4.6
        environment:
          PGADMIN_DEFAULT_EMAIL: "pgadmin"
          PGADMIN_DEFAULT_PASSWORD: "pgadmin"
        networks:
          - net-db
        ports:
          - "8080:80"      
    
    volumes:
      db-data:
    
    networks:
      net-db:
    ```
---

## Compose file structure (1)
 - The **`<key>: <value>`** format is used to define single components such as  the image name 
```yml
...
    image: postgres:10
...    
``` 

---

## Compose file structure (2)

 - The value of some attributes keys such as the **volumes** and **ports** (note plural) is an array. The " ** - ** " symbol is used to define an element of an array 

```yml
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql:ro
      - db-data:/var/lib/postgresql/data      
``` 
 - Note also that we can use the **(.)** symbol to define the current working directory in the **volumes** section


---

## Docker Compose - Example - Start the Services (1)
 - Use the `docker-compose up` to start the services defined in the compose file available in the  `resources/compose-sample-2/` directory

```console
# git clone https://github.com/gerassimos/dgs19.git
# cd dgs19/resources/compose-sample-2
# docker-compose up 
Creating network "compose-sample-2_net-db" with the default driver
Creating volume "compose-sample-2_db-data" with default driver
Creating compose-sample-2_postgres10_1 ... done
Creating compose-sample-2_pgadmin4_1   ... done
Attaching to compose-sample-2_postgres10_1, compose-sample-2_pgadmin4_1
...
postgres10_1  | 2019-09-30 08:57:05.364 UTC [1] LOG:  database system is ready to accept connections
pgadmin4_1    | NOTE: Configuring authentication for SERVER mode.
...
```

---

## Docker Compose - Example - Start the Services (2)
 - From the output of the `docker-compose up` command we can see the networks, the volumes and the containers that are created
 - From a web browser we can access the `<DOCKER_HOST>:8080` to verify the the pgAdmin web application is running and that we can connect to te postgres DB.
 - After the initial phase the logs of all the containers defined in the `docker-compose.yml` file are displayed
 
---

## Docker Compose - Example - Start the Services (2)  
 - The command is running in the foreground. We can press `Ctrl-C` to stop all the containers and regain control of the shell prompt 

```console
...
postgres10_1  | 2019-09-30 08:57:05.364 UTC [1] LOG:  database system is ready to accept connections
pgadmin4_1    | NOTE: Configuring authentication for SERVER mode.
...
pgadmin4_1    | [2019-09-30 08:59:12 +0000] [79] [INFO] Booting worker with pid: 79
Gracefully stopping... (press Ctrl+C again to force)
Stopping compose-sample-2_postgres10_1 ... done
Stopping compose-sample-2_pgadmin4_1   ... done
...
```

---

## Docker Compose - Example - Detached mode
 - Use the `docker-compose up --detach` or `docker-compose up -d` to start the services in the background 

```console
# docker-compose up -d
Starting compose-sample-2_postgres10_1 ... done
Starting compose-sample-2_pgadmin4_1   ... done
#
```

---

## Docker Compose - Example - logs
 - The `docker-compose logs -f` to display the logs from the containers

```console
# docker-compose logs -f
...
postgres10_1  | 2019-09-30 09:23:06.825 UTC [1] LOG:  database system is ready to accept connections
...
pgadmin4_1    | [2019-09-30 09:23:08 +0000] [76] [INFO] Booting worker with pid: 76
...
```
> Notes:  
> In this example we have specified the `-f`, `--follow` option to continuously tail the log messages
> Press <ctrl-C> to stop displaying the log messages

---

## Docker Compose - Example - Help
 - Many of the commands used from the Docker CLI can be also used with the `Docker Compose` tool.
 - Use the `docker-compose --help` to display all commands that can be used 

```console
# docker-compose --help
...
Commands:
  build              Build or rebuild services
  bundle             Generate a Docker bundle from the Compose file
  config             Validate and view the Compose file
  create             Create services
  down               Stop and remove containers, networks, images, and volumes
...
```

---

## Docker Compose - Example - common commands 
 - Some of the most common commands used with `Docker Compose` are:
    1. `docker-compose ps` => List containers
    2. `docker-compose top` => Display the running processes  
    
```console
# docker-compose ps
Name           Command                      State      Ports
------------------------------------------------------------------------------------
pgadmin4_1     /entrypoint.sh                  Up      443/tcp, 0.0.0.0:8080->80/tcp
postgres10_1   docker-entrypoint.sh postgres   Up      5432/tcp

...
# docker-compose top
...
```


---

## Docker Compose - Example - Project name 
 - Compose uses the current directory name as the project name.
 - The **project name** is used as **prefix name** for all containers, networks and volumes created from the `docker-compose.yml` file

```console
# docker container ls
... IMAGE                ... NAMES
... postgres:10          ... compose-sample-2_postgres10_1
... dpage/pgadmin4:4.6   ... compose-sample-2_pgadmin4_1

# docker network ls
NETWORK ID     NAME                       DRIVER   SCOPE
...
91e9ac3cccf0   compose-sample-2_net-db    bridge   local
...
# docker volume ls
DRIVER   VOLUME NAME
local    compose-sample-2_db-data
``` 

---

## Docker Compose - Example -  down
 - Use the `docker-compose down` command to stop and remove containers, networks and volumes created by `up`

```console
# docker-compose down
Stopping compose-sample-2_postgres10_1 ... done
Stopping compose-sample-2_pgadmin4_1   ... done
Removing compose-sample-2_postgres10_1 ... done
Removing compose-sample-2_pgadmin4_1   ... done
Removing network compose-sample-2_net-db
```
> Note
> By default volumes are not removed 

---

## Docker Compose - Example -  down --volumes
 - Use the `docker-compose down --volumes` to remove also **named volumes** declared in the `volumes` section of the Compose file and **anonymous volumes** attached to containers.

```console
# docker-compose down -v
...
Removing volume compose-sample-2_db-data
...
```

---

## ## Docker Compose - Example [cmd summary] 
```console
# git clone https://github.com/gerassimos/dgs19.git
# cd dgs19/resources/compose-sample-2
# docker-compose up
* access the <DOCKER_HOST>:8080
* Ctrl-C
# docker-compose up -d
# docker-compose logs -f
# docker-compose --help
# docker-compose ps
# docker-compose top
# docker container ls
# docker network ls
# docker volume ls
# docker-compose down -- Note By default volumes are not removed
# docker-compose down -v 
```

---

## Docker Compose - Common commands 

 - The `docker-compose up` => create volumes/networks and start all containers
 - The `docker-compose down` => stop and remove all containers, remove all networks
 - The `docker-compose logs` => display logs of all the services defined in the yml file  

> Notes: 
> - Volumes are **NOT** deleted with the `docker-compose down` command  
> - To delete the volumes use the `docker-compose down --volumes` command



