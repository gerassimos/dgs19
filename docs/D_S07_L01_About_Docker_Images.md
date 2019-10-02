background-image: url(images/containers_02.jpg)
background-size: 100% 100%

<h3 style="margin-top: 500px;">
<span style="color:white">
Section 7 - Container Images
</span>
</h3>
---

class: center, middle
# Section 7 - Container Images 
## 1 - About Docker Images 
---

## What is in an Image (1)
 - Application binaries and dependencies (e.g app.jar + JRE:8)
 - Metadata about the image data (such as ports, volumes etc...)
 - Official Definition: Docker images are the basis of containers. An Image is an ordered collection of root filesystem changes and the corresponding execution parameters for use within a container runtime. 
 - Ref: https://docs.docker.com/glossary/?term=image
 
> - A container is created by running a Docker image. 
> - An image is used to create a container as a java class is used to create an object instance
  
---

## What is in an Image (2) 
 - Inside the image there is not a complete O.S. (No kernel, No kernel modules such as drivers etc)
 - The host provides the **kernel**. The host kernel is "shared" across all the containers.  
 - Inside the image are just the binaries and dependencies required to run our application.
 - The main difference between a container and a VM is that container is more like a **process**.
 - Images size can be:
   - Small as one file (your app binary), as a java jar binary
   - Big as a full Ubuntu distribution with Apache, PHP and more ...
   - Very big including also the desktop environment 
