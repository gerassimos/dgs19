class: center, middle
# Section 5 - Containers lifecycle
## 3 - Container Shell Exercise 
---

### Exercise 1
Create a alpine container. Override the default command to be "ping 8.8.8.8". 
Press `<Ctrl-C>` to kill to foreground "ping" process. 

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
 2. Verify if the `curl` tool is available,  
    1. use the `apk` package manager to install it if needed,
 3. Use the `curl` to download the `www.google.com` web page
 4. Exit from the container shell back to the host shell and verify if the alpine container is still running.
---

### Exercise 2 Solution 
 1. Run an alpine container and get into the default shell.
```console
# docker container run -it --name my_alpine alpine
...
/ #
```

 2. Verify if the `curl` tool is available,  
    1. use the `apk` package manager to install it if needed,

```console
/ # which curl
/ #
/ # apk update
...
/ # apk add curl
...
OK: 8 MiB in 19 packages
/ #
/ # which curl
/usr/bin/curl
/ # 
```

 3. Use the `curl` to download the `www.google.com` web page 

```console
/ # curl www.google.com > google-home.html
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 13776    0 13776    0     0   269k      0 --:--:-- --:--:-- --:--:--  269k

cat google-home.html
<!doctype html><html...>
...
</body></html>
```

 4. Exit from the container shell back to the host shell and verify if the alpine container is still running.

```console 
$ docker container ls
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

$ docker container ls -a
CONTAINER ID   IMAGE     COMMAND     CREATED         STATUS                        PORTS     NAMES
2b5e008b9e8d   alpine    "/bin/sh"   9 minutes ago   Exited (0) 9 minutes ago                my_alpine
```

> Note that once we exit the alpine shell the container is terminated.
> The alpine default `CMD` is the `sh` shell, when we exit the container there is not any pseudo-Terminal attached to the container and so the `sh` process is terminated.

---

### Exercise 3
 1. Run a CentOS container and get into the default shell.
 2. Verify if the `nslookup` command is installed. If not, use the appropriate package manager to install the package required to run the `nslookup` command
 3. Use the `nslookup` command to resolve the IP address of *www.google.com*
 4. Use the `ip address` command to display all network interface. 
 5. Use the `ip route` command to display the routing table.
 6. Exit from the container shell back to the host shell and verify if the CentOS container is still running.

---

### Exercise 3 Solution 

1 - Run a CentOS container and get into the default shell:
```console 
# docker run -it --name centos centos
```

2 - Verify if the `ip` command is installed. If not, use the appropriate package manager to install the package required to run the ip command.
```console  
[root@8b44a91956e5 /]# nslookup
bash: nslookup: command not found

[root@8b44a91956e5 /]# yum provides nslookup
...    
bind-utils-32:9.11.20-5.el8_3.1.x86_64 : Utilities for querying DNS name servers
...

[root@8b44a91956e5 /]# yum install -y bind-utils
...
Complete!
```

3 - Use the `nslookup` command to resolve the IP address of *www.google.com*
```
[root@8b44a91956e5 /]# nslookup www.google.com
Server:         8.8.8.8
Address:        8.8.8.8#53

Non-authoritative answer:
Name:   www.google.com
Address: 142.251.33.196
Name:   www.google.com
Address: 2607:f8b0:4004:837::2004
```

4 - Use the "ip address" command to display all network interface: 
```console 
[root@8b44a91956e5 /]# ip address
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
7: eth0@if8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default 
    link/ether 02:42:ac:11:00:02 brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet 172.17.0.2/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
```

5 - Use the `ip route` command to display the routing table: 
```console 
root@8b44a91956e5 /]# ip route  
default via 172.17.0.1 dev eth0 
172.17.0.0/16 dev eth0 proto kernel scope link src 172.17.0.2
```

6 - Exit from the container shell back to the host shell and verify if the CentOS container is still running.
```console 
[root@8b44a91956e5 /]# exit
exit

# docker container ls 
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

# docker container ls -a 
CONTAINER ID   IMAGE     COMMAND       CREATED          STATUS                        PORTS     NAMES
8b44a91956e5   centos    "/bin/bash"   15 minutes ago   Exited (0) 4 minutes ago                centos
```

