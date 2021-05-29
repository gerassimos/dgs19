# Docker quiz

---

Q: A Docker image consists of the binaries of our application and all the necessary dependencies to run the application  

A. True ✔️  
B. False  

---

Q: Docker images are stored registries (repositories). The default registry is Docker Hub but we can setup a private registry  

A. True ✔️  
B. False  

---

Q: `docker container run --publish 8080:80 --detach nginx`  

A: Will create a container from the Docker image `nginx:latest` which can be accessed from the Docker host on port 8080. ✔️ ⌛  
B: Will fail because we did not specify a name for the container.  
C: Will fail because we did not specify the application `CMD` to run inside the container

---

Q: The command `docker exec -it <container-name> bash` can be used to get a bash shell in the container  

A. True ✔️  
B. False  

---

Q: Docker containers are intended to run only one single process. The process ID (or PID) of this process is 1 by definition.  

A. True ✔️  
B. False  

---

Q: `docker container run --publish 80:80 -d nginx`  

A. Will create a container that will share the same network interfaces as the host  
B. Will create a container that will be connected on the default bridge network *docker0* and an IP address will be assign automatically ✔️ ⌛  
C. Will create a container that is completely isolated from the network point of view  

---

Q: It is best practice to manually assign the IP address on a container  

A. True  
B. False ✔️ ⌛  

---

Q: In a multi-container application it is best practice to use the IP addresses of the containers for the network communication  

A. True  
B. False ✔️  

---

Q: A Docker image consists of read-only layers each of which represents a Dockerfile instruction  

A. True ✔️  
B. False  

---

Q: The `docker-compose` tool is used to define a run multi-container applications. We can use a YAML file to define services, networks and volumes.

A. True ✔️  
B. False  

---