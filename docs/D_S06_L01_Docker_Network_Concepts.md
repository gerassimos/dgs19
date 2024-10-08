class: center, middle
# Section 6 - Docker Networking Basics
## 1 - Docker Network Concepts

---

## Overview
 - Learn the concepts around Docker virtual networks. 
 - Inspect a container IP address and see diagrams on how traffic goes in and out.

---

## Networking Concepts
 - TCP/IP model
 - OSI model
 - IPv4 address
  
---
  
## TCP/IP model - 4 layer network model 
 - Layer 4 Application (https, ssh, smtp)
 - Layer 3 Transport (TCP/port and UDP/port) 
 - Layer 2 Network layer (IP address, ICMP control protocol `ping`)
 - Layer 1 Physical layer (wired Ethernet 802.3, wireless WLAN 802.11, MAC address)
 
---
  
## OSI model - 7 layer network model 
 - Layer 7  Application layer
 - Layer 6  Presentation layer
 - Layer 5  Session layer
 - Layer 4  Transport layer
 - Layer 3  Network layer
 - Layer 2  Data link layer
 - Layer 1  Physical layer
  
---
## Switching
![img_width_100](images/D_S6_L1_network_topology_switch.png)
---

## IPv4 address (1)
  - host part
  - network part (subnet)  
  
  
<img src="images/D_S6_L1_IPv4_Networking.PNG"  style="height:350px;"/> 
  
---
  
## IPv4 address (1)
<img src="images/D_S6_L1_Networking_Concepts_02_Calculating_the_network_address_solution.PNG"  style="height:450px;"/>

---

## IPv4 address (3)
<img src="images/D_S6_L1_IPv4_Network_example.PNG"  style="height:450px;"/>

---
## Docker Networks Defaults (1)
 - All networking concepts that are applicable for a physical server are also applicable for a Docker container.
 - Inside each Docker container will be a virtual network interface `eth0` that is connected to a virtual switch ("docker0" o "bridge")
 - On each container an **IP address** will be automatically assign via a **DHCP** server 
 - There is not an actual dedicated DHCP server but the Docker daemon is acting as an DHCP server
 - What has been describe above is **not** always the case there are multiple other valid network setups that can be used.
 
---
## Docker Networks Defaults (2)
 - It is common to create **multiple** virtual networks (virtual switches), and attach a container to one or more virtual networks
 - From the host point of view each a virtual network is just a "regular" **network interface** of type `bridge`
 - The `bridge` network interface on the host behaves like a **network switch** from the container point of view
 - The containers connected on the same virtual network can reference each other using the container name **(DNS name)**
 - Docker has a build in **DNS server**
 
---
## Docker Networks Defaults (3) 
 - There many ways to setup the virtual networks used by the Docker containers 
 - Docker's networking subsystem is pluggable, using **drivers**. 
 - Several drivers exist and provide core networking functionality.
 - The default network driver is `bridge`. 
 - If we don't specify a driver, the `bridge` is used when a virtual network is created.

---
## Docker Networks Defaults (4)
 - From the container’s point of view, it has: 
   - a **network interface** with an **IP address** 
   - a **gateway**
   - a **routing table** 
   - **DNS services**, 
   - and other networking details

> Ref: [Container networking](https://docs.docker.com/config/containers/container-networking/)  
>
---

## Network topology (1) - bridge network - diagram
<p style="text-align: center;">
<img src="images/D_S6_L1_network_topology_01_bridge.png" alt="img_width_90">
</p>
  
---
  
## Network topology (1) - bridge network
 -  From the above diagram we can see that the physical network interface of the Docker host system is connected to the NAT firewall which by default will block all incoming traffic.
 - When a container is started, such as the Container #1 in the above diagram, it is by default connected to the `docker0` network which is connected to the host network interface through the NAT firewall.
 - Container #1 can access the internet (outside world) through the NAT firewall. Outbound traffic goes through a firewall MASQUERADE rule.
 - Any other container, such as Container #2 in the above diagram, connected on the `docker0` virtual network can communicate directly with the other containers connected on this virtual network.
 - There is no need to explicitly expose a port to access the service of a container from an other container on the same virtual network.
 - The `docker0` virtual network behaves like a layer 2 network switch.
  
---

## Network topology (2) - port forwarding - diagram
<p style="text-align: center;">
<img src="images/D_S6_L1_network_topology_02_port_forwarding.png" alt="img_width_90">
</p>

---

## Network topology (2) - port forwarding

 - Example: 

```terminal
docker container run --publish 80:80 --name web_server -d nginx
```
 - In this example the nginx container is started using the `--publish 80:80` option.
 - This will open port 80 on the physical network interface of the Docker host and will forward any traffic received on this port to the container virtual interface on port 80
 - Remember that the `--publish 80:80` option corresponds to `--publish HOST_PORT:CONTAINER_PORT`
 - Traffic incoming to the **HOST_PORT** `80` is forwarded into the **CONTAINER_PORT** `80`.


---

## Network topology (3a) - multiple virtual networks
<p style="text-align: center;">
<img src="images/D_S6_L1_network_topology_03_net2.png" alt="img_width_90">
</p>
---

## Network topology (3b) - multiple virtual networks

 - Multiple virtual networks (bridge type) can be created. 
 - Each virtual network will have its own unique name.
 - For example, in the above diagram a new virtual private network `net2` is used for a second application composed from an Apache "httpd" container and a MySQL container.
 - The Apache "httpd" container can access the MySQL container on port 3306 because they are connected on the same virtual network.
 - MySQL container is listening on port 3306 but this port is not exposed to the outside world
 - No other containers from other virtual networks or other clients outside the docker host can access the MySQL container
 

---

## Network topology (3c) - multiple virtual networks
 - In this example the Apache "httpd" container is started using the `--publish 8080:80` option. This will open port 8080 on the "physical" network interface of the Docker host and will forward any traffic request receive on port 8080 to the container virtual interface on port 80.
 - The container #1 from docker0 can communicate with the Apache "httpd" container through port 8080 of the physical network interface.
 - On the physical network it is not possible to use a port for more than one service.
 - In this example port 80 is already occupied from the port forwarding rule defined for the nginx container. So, the Apache "httpd" container cannot use this port and it uses port 8080 instead.
 - It is the best practice to create a dedicated virtual network to connect containers related to a specific application or service. 

---

## Container connected to multiple virtual networks (a)

<p style="text-align: center;">
<img src="images/D_S6_L1_network_topology_04_c_to_multiple_nets.png" alt="img_width_90">
</p>

---
## Container connected to multiple virtual networks (b)

 - One container can be also connected to two (or more) virtual networks. 
 - In this example 
   - front-end container WEB UI logic => connected to the *front-end network*
   - back-end container business logic => connected to the *front-end network* and the *back-end network*
   - db container => connected to the *back-end network*
 
> Note:    
> This network topology is like in the physical world, where we can have two physical network interfaces on a real computer connected to two different networks.  

---

## The built-in Network drivers 
Docker networking subsystem is pluggable using **drivers**. Remember that batteries are included but removable. Several drivers exist by default and provide core networking functionality:
 - bridge 
 - host
 - none
 - overlay

---

## Network driver - bridge 
 - The default network driver. 
 - If you don’t specify a driver when you create a network then the `bridge` driver is used by default.
---

## Network driver - host (1)
 - Host network `--network=host`
 - It is also possible for a container to be connected directly to the physical network of the host with use of the `--network host` option.
 - In this case, the virtual network is **not used** at all and there is **no isolation** between the host machine and the container.     
 - In general, it is **NOT recommended** to use this type of network driver for security reasons, since in this case there is no network isolation.
 - For instance, if we run a container that runs a web server on port 80 using host networking, the web server is available on port 80 of the host machine.

---

 ## Network driver - host (2)
 - Example:
    - This could be the case of a special application such as a **redis cluster** where the containers that are part of the cluster require access to the physical network interface to work properly.

---

## Network driver - none  
 - Network disable `--network=none`
 - We can also **completely isolate** a running container by disabling the networking stack with use of the `--network none` option 
 - Example:
   - This could be the case of a container executing operations only on the local file system, where there is no need for network access.

---

## Network driver - overlay (1)

<p style="text-align: center;">
<img src="images/D_S6_L1_Overlay_network.PNG" alt="img_width_90">
</p>

---

## Network driver - overlay (2)
- Enable containers to communicate with each other over virtual networks that are **spanning across multiple hosts** in a Swarm cluster. 

---

## docker container port (1)
 - Use the `docker container port <container id|name>` to list port mappings (port forwarding rules): 

```terminal
# docker container run -p 8080:80 --name web_server -d nginx

# docker container port web_server
80/tcp -> 0.0.0.0:8080
```


 - `80/tcp -> 0.0.0.0:8080` => [container-port]/tcp -> [host-ip]:[host-port]
 - 0.0.0.0 => means all network interfaces 

---
## docker container port (2)

 - Remember that the `docker container ls` command will list the running container as well as the port mapping.

```terminal
# docker ps
CONTAINER ID  IMAGE  COMMAND                CREATED              STATUS         PORTS               NAMES
feaf47c89978  nginx  "nginx -g 'daemon of…" About a minute ago   5 minutes ago  0.0.0.0:8080->80/tcp  web_server
```  

---

## Container's IP address (1)
 - By default when a container is created is connected to the default `docker0` network of type `bridge`.
 - Sometimes the default `docker0` virtual network is named **bridge** instead.
 - A virtual network interface `eth0` is created inside the container and an IP address is assigned to it **automatically** through an internal **DHCP service** running on the Docker daemon.


---

## Container's IP address (2)
 - It is possible to display the IP address assigned to a container using the `docker inspect` command and format the output of the command by using the `--format` option.
 - The `--format` option will format the output using the given Go template [Ref](https://docs.docker.com/engine/reference/commandline/inspect/)
```terminal
# docker container inspect --format '{{.NetworkSettings.IPAddress}}' web_server
172.17.0.2
```
> Note:  
> It is also possible to get into the container terminal and use the `ifconfig` or `ip a` command to display the IP address(es) of the container.


