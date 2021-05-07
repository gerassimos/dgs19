class: center, middle
# Section 5 - Containers lifecycle
## 3 - Container Shell Exercise 
---

### Exercise 1
Create a alpine container. Override the default command to be "ping 8.8.8.8". 
Press `<Ctrl-C>` to kill to forground "ping" process. 

### Exercise 1  Solution 
```console
# docker run alpine ping 8.8.8.8
PING 8.8.8.8 (8.8.8.8): 56 data bytes
64 bytes from 8.8.8.8: seq=0 ttl=120 time=28.365 ms
64 bytes from 8.8.8.8: seq=1 ttl=120 time=28.778 ms
64 bytes from 8.8.8.8: seq=2 ttl=120 time=28.653 ms
<Ctrl-C>
#
```

---
### Exercise 2 
 1. Run an alpine container and get into the default shell.
 2. Use the "apk" package manager to install the bash shell (search the web to find how to install bash shell on the alpine image).
 3. Exit the alpine container => This will stop the alpine container.
 4. Start the alpine container. 
 5. Get a bash shell prompt in the alpine container (run a new bash process in the alpine container).
 6. Exit from the container shell back to the host shell.
---

### Exercise 2 Solution 
 1. Run an alpine container and get into the default shell:
```console
# docker container run -it --name my_alpine alpine
```

 2. Use the "apk" package manager to install the bash shell (search the web to find how to install bash shell on the alpine image). Verify that bash is not already installed before using the "apk add" command.

```console
# which bash
# apk update
# apk add bash
Verify that bash is actually installed after the "apk add bash" command
# which bash
/bin/bash
/ #
```

 3. Exit the alpine container: 

```console
# exit
[root@desktop1 ~]#
```
 3. [Verify the status of the "my_alpine" container]:

```console 
# docker container ls -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                      PORTS                 NAMES
a25bb29deffc        alpine              "/bin/sh"                7 minutes ago       Exited (0) 58 seconds ago                         my_alpine
06f8bab2e18e        alpine              "sh"                     2 hours ago         Up 2 hours                                        hopeful_spence
515d82f84f9e        mysql               "docker-entrypoint.s…"   4 hours ago         Up 4 hours                  3306/tcp, 33060/tcp   mysql
1b76d91c5f77        nginx               "nginx -g 'daemon of…"   4 hours ago         Up 4 hours                  80/tcp                nginx
```

 4. Start the alpine container (do not use the -ai option):

```console 
# docker container start my_alpine
my_alpine
```

 5. Get a bash shell prompt in the alpine container (run a new bash process in the alpine container):
```console 
# docker container exec -it my_alpine bash
bash-4.4#
```

 6. Exit from the container shell back to the host shell:
```console 
bash-4.4# exit
exit
#
```
---

### Exercise 3
 1. Run a CentOS container and get into the default shell.
 2. Verify if the "ip" command is installed. If not, use the appropriate package manager to install the package required to run the ip command.
 3. Use the "ip address" command to display all network interface. 
 4. Use the "ip route" command to display the routing table.
 5. Exit from the container shell back to the host shell

---

### Exercise 3 Solution 

 1. Run a CentOS container and get into the default shell:
```console 
# docker run -it --name centos centos
```

 2. Verify if the "ip" command is installed. If not, use the appropriate package manager to install the package required to run the ip command.
```console  
[root@79ea7cf6ce74 /]# ip --help
bash: ip: command not found

root@79ea7cf6ce74 /]# yum provides ip
...
iproute-4.11.0-14.el7.x86_64 : Advanced IP routing and network device configuration tools
Repo        : @base
Matched from:
Filename    : /usr/sbin/ip

[root@79ea7cf6ce74 /]# yum install -y iproute
...
[root@79ea7cf6ce74 /]# ip --help
Usage: ip [ OPTIONS ] OBJECT { COMMAND | help }
...
```

 3. Use the "ip address" command to display all network interface: 
```console 
[root@79ea7cf6ce74 /]# ip address
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
20: eth0@if21: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default
    link/ether 02:42:ac:11:00:02 brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet 172.17.0.2/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
```

 4. Use the "ip route" command to display the routing table: 

```console 
[root@79ea7cf6ce74 /]# ip route
default via 172.17.0.1 dev eth0
172.17.0.0/16 dev eth0 proto kernel scope link src 172.17.0.2
```

 5. Exit from the container shell back to the host shell:
```console 
[root@79ea7cf6ce74 /]# exit
exit
#
```

---

## cleanup 
 - Finally, delete all the containers created during the process of this exercise.

```console
# docker container ls -a
...
# docker container rm -f centos my_alpine ... 
```
---

