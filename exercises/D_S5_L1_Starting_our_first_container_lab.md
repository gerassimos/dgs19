# Section 5 - Containers lifecycle
## 2 - Lab: Manage Multiple Containers 

---

## Objective:  
 - To startup a typical three container application with nginx, mysql, and apache. 
 - To learn how to get help from both the cli command `--help` and the on line official Docker documentation.

---

## Key points:
 - Find how to set an environment variable when we create a container with `docker run`, 
   - Use help pages form cli `docker container run --help` and/or 
   - Use help from the on line documentation [docs.docker.com](https://docs.docker.com/reference/)  
 - Start a three-containers application composed from *nginx*, *mysql* and a *httpd*  
 - Run all of them in detach mode `--detach` (or -d), name them with --name
 - Set custom names `--name` for each container, nginx->proxy,  httpd->webserver, mysql->db  
 - *nginx* should listen on 80:80, *httpd* on 8080:80, *mysql* on 3306:3306
 - When running mysql, use the --env option (or -e) to pass in MYSQL_RANDOM_ROOT_PASSWORD=yes
 - Use docker container logs on mysql to find the random password created on startup
 - Clean up all with `docker container stop` and `docker container rm` (both can accept multiple names or ID's)
 - Use docker container ls to ensure everything is correct before and after cleanup

---
## Solution

### Step 1
Find how to get useful information about the options that can be used with the "docker container run" command.
In particular, find how to set an environment variable in the container. 
Use both:  
  a) the offline documentation available from the docker CLI (--help)  
  b) the official online documentation (docs.docker.com).

### Step 1 case a (on-line help)
```terminal
# docker container run --help
Usage:  docker container run [OPTIONS] IMAGE [COMMAND] [ARG...]

Run a command in a new container

Options:
...
-e, --env list                       Set environment variables
      --env-file list                  Read in a file of environment variables
...      
                                       
---
```

### Step 1 case b (on-line help)
Ref:  
 - https://docs.docker.com/engine/reference/run/  
 - https://docs.docker.com/engine/reference/run/#env-environment-variables 

From the online documentation we can see that we can set any environment variable in the container by using one or more -e flags.
Example:

```console
$ export today=Wednesday
$ docker run -e "deep=purple" -e today --rm alpine env
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
HOSTNAME=d2219b854598
deep=purple
today=Wednesday
HOME=/root
```

See also:  
https://hub.docker.com/_/mysql

---

### Step2
Start a three-containers application composed from *nginx*, *mysql* and a *httpd*. 
Note for this exercise these containers are not actually connected, it is just a simulation. 
Start all containers in detached mode (-d).

Set the following port mapping and custom names:

|Docker Image|host port   |container port|name      |
|------------|------------|--------------|----------|
|nginx       |80          |80            |proxy     |
|httpd       |8080        |80            |webserver |
|mysql       |3306        |3306          |db        | 
 
Use the "--env" option to the environment variable MYSQL_RANDOM_ROOT_PASSWORD=yes in the mysql container. 

```console
# docker container run -d --name proxy -p 80:80 nginx
# docker container run -d --name webserver -p 8080:80 httpd
# docker container run -d -p 3306:3306 --name db -e MYSQL_RANDOM_ROOT_PASSWORD=yes mysql
```
Q: why do we set different host ports for each container?  
A: We need to expose different ports for each container because we cannot have 2 processes on the same host opening the same TCP port.

Q: What is the MYSQL_RANDOM_ROOT_PASSWORD environment variable actually doing?  
A: The mysql image has an option for setting a random root password on bootup  
Ref:  
https://hub.docker.com/_/mysql  
> `MYSQL_RANDOM_ROOT_PASSWORD`    
> This is an optional variable. Set yes to generate a random initial password for the root user (using pwgen). The generated root password will be printed to stdout. 

### Step3 
Verify that the containers are running:  
```console
# docker ps
CONTAINER ID   IMAGE   COMMAND                  CREATED         STATUS         PORTS                               NAMES
3a4a2960ec08   mysql   "docker-entrypoint.s…"   2 minutes ago   Up 2 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp   db
18beccb07c1f   httpd   "httpd-foreground"       7 minutes ago   Up 7 minutes   0.0.0.0:8080->80/tcp                webserver
4db904668f02   nginx   "nginx -g 'daemon of…"   8 minutes ago   Up 8 minutes   0.0.0.0:80->80/tcp                  proxy
```
---

### Step4
Search the mysql password automatically generated from the log of the container. 

```console
# docker container logs db
...
GENERATED ROOT PASSWORD: ahChooXuR3ahpeew3eg5jou2Ae9aiXie
...
```
The following command uses also the `grep` to filter out the search result.  
Note also that we used the "2>&1" to redirect the STDERROR to the STDOUT.  
```console
# docker container logs db 2>&1 | grep -i "password"
GENERATED ROOT PASSWORD: ahChooXuR3ahpeew3eg5jou2Ae9aiXie
```
---
### Step5
Stop and remove all the containers created for this exercise.
```console
# docker rm -f db webserver proxy
db
webserver
proxy
```

### Step6
Verify that there is not any container running or stopped. 
```console
$ docker ps -a
CONTAINER ID   IMAGE   COMMAND   CREATED   STATUS   PORTS   NAMES
```

 
