class: center, middle
# Section 10 - Docker Compose
## 2 Docker Compose - Build

---

## Docker Compose - Build (1)
 - `Docker Compose` can be used also to **build** our custom Docker images
 - I have seen how to build a custom Docker image using a `Dockerfile` 
 - Now will see hot to use the `Docker compose` and the `Dockerfile` together to **build** and run our custom Docker images

---

## Docker Compose - Build (2)
 - In the following example we see the `build` section of a `docker-compose.yml` file: 
 ```console
 version: "3.7"
 services:
       webapp:
         build:
           context: ./dir
           dockerfile: Dockerfile-alternate
...
 ```

> Notes  
> - `context:` =>  Is the relative path of the Docker build context  
> - `dockerfile:` =>  Is the name of the Docker File to use for the build process (optional needed only if != default name )  
> - The `docker-compose.yml` file,  `Dockerfile` and the files related to the build context must be located on the same directory
 
---

## Docker Compose - Build (3)
 - Use the `docker-compose up` to **build** and **start** the services defined in the compose file 
 - Use the `docker-compose build` to **build** all the custom Docker images defined in the compose file
 

> Notes:  
> - The first time the build process will take place to create the custom Docker images.  
> - After that the custom Docker images will be available in the local cache.  
> - The build process will take place again only if the local cache is invalidated  

---

## Docker Compose - Build (4)
 - To force the **re-build** process to recreate the custom Docker image we must use the `docker-compose build` or `docker-compose up --build` command.

> Notes  
> - Documentation Reference available [here](https://docs.docker.com/compose/reference/up/)  
---

## Docker Compose vs other CM tools
 - With `Docker Compose` we have easier way to setup the development environment. The steps required to setup the development environment could be:
    1. Checkout the code `git clone <repo>`
    2. Use the `docker-compose up` to start all services required for the development

---
 
## LAB
 - Ref:
 - [D_S10_L02_Docker_Compose_Basic_Commands_LAB.md](https://github.com/gerassimos/dgs19/blob/master/exercises/D_S10_L02_Docker_Compose_Basic_Commands_LAB.md)  