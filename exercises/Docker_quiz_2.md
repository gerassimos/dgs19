# Docker quiz

---

Q: A Docker image consists of:   

A. A txt file with linux commands.
B. The binaries of our application and all the necessary dependencies to run the application.
C. The linux kernel
D. None of the above

---

Q: Where Docker images can be stored? 
A. Are usually stored in registries (repositories). The default registry is Docker Hub but we can use a private registry  
B. Are volatile objects and so are only available in RAM memory 
C. Are only stores on FTP servers 
D. None of the above
 

---

Q: The command  "docker container run --publish 8080:80 --detach nginx"

A: Will create a container from the Docker image "nginx:latest" which can be accessed from the Docker host on port 8080.  
B: Will fail because we did not specify a name for the container.  
C: Will fail because we did not specify the application "CMD" to run inside the container
D. Will build an Docker image

---

Q: The command: "docker exec -it <container-name> bash" can be used: 
A. On a running container to get a bash terminal in the container  
B. On a Docker image to get a bash terminal in the image
C. To stop a container
D. To start a container 

---

Q: A linux container is intended to:   
A. Run a single "main" application / a single process with PID=1.
B. Run only backend applications 
C. Run only frontend applications 
D. Run multiple unrelated applications 

---

Q: The command: "docker container run --publish 80:80 -d nginx".

A. Will create a container that will share the same network interfaces as the host.  
B. Will create a container that will be connected on the default bridge network *docker0* and an IP address will be assign automatically.  
C. Will create a container that is completely isolated from the network point of view.  
D. None of the above

---

Q: For the network configuration of a container it is best practice to: 
A. Manually assign an IP address on the container    
B. Let the Docker engine automatically assign an IP address on the container
C. Setup a unix socket 
D. None of the above

---

Q: For the network communication between containers it is best practice to: 
A. Use the container's name as DNS name
B. Use the container's IP address
C. Use the inter-process communication (IPC) mechanism 
D. None of the above

---

Q: What best describe a Docker image?
A. A Docker image consists of RO layers each of which represents a Dockerfile instruction.  
B. A Docker image consists of RW layers each of which represents a Dockerfile instruction.  
C. A Docker image is single txt file. 
D. None of the above.
 

---

Q: The "docker-compose" tool: 
A. Is used to define a run multi-container applications. A yml file is used to define services, networks and volumes.
B. Is used to scan the Docker images.
C. Is used to monitor the host server.
D. None of the above
 

