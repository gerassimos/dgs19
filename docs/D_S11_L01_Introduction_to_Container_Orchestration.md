class: center, middle
# Section 11 - 
## 1 Introduction to Container Orchestration

---

## Overview 
 - Computer clusters
 - Container Orchestration
 - Access the cluster - DNS Load Balancing - Virtual IP    
 - Kubernetes vs Docker Swarm 
 - Docker Swarm 
 - 
 
---

## Computer cluster (1)
 - A computer **cluster** is a set of connected computers **(nodes)** that work together so that, in many respects, they can be viewed as a single system 
 - The components of a cluster are usually connected to each other through **fast local area networks** 
 - In some circumstances, all the nodes of a cluster use the same hardware and the same operating system

---  

## Computer cluster (2)
 - Clusters are usually deployed to improve **performance** and **availability** over that of a single computer
 - TODO 
 horizontal scale => load distribution 
HA High availability => fault tolerance  
Ref: [wiki](https://en.wikipedia.org/wiki/Computer_cluster)

---

## Computer cluster (3)
TODO
 - Software component which define which nodes are part of the cluster (eg corosync)
 - Quorum 
 - Service availability 
 
## List of cluster management software
 - Docker Swarm
 - Kubernetes, founded by Google Inc, from the Cloud Native Computing Foundation
- Apache Mesos, from the Apache Software Foundation
Red Hat cluster suite
Heartbeat, from Linux-HA
Nomad, from HashiCorp
Service Fabric, from Microsoft

Ref: [wiki](https://en.wikipedia.org/wiki/List_of_cluster_management_software)

---
## Red Hat cluster suite
https://en.wikipedia.org/wiki/Red_Hat_cluster_suite 

---
https://www.altaro.com/msp-dojo/container-orchestrator/
https://containerjournal.com/features/container-orchestrator-anyway/

---
## Container Orchestrator
 - A Container Orchestrator is a clustering solution
 - It has a set of tools that are designed to easily **manage** complex **container deployments** across **multiple nodes** from one central location. 
 - This includes: 
      - The containers themselves 
      - The hosts 
      - The virtual networking
      - The storage 
      - etc…
 - Well known container orchestrators that are on the market today are:
   - Kubernetes 
   - Docker Swarm 
   - Mesos/Marathon 
   - ...

 - The most popular Container Orchestration solutions are *Kubernetes* and *Docker Swarm*  

>  
>  [Ref:](https://www.altaro.com/msp-dojo/container-orchestrator/)
 


