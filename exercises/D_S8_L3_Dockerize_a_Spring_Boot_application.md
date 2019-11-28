class: center, middle
# Section 8 - Build Images - The Dockerfile Basics
## 3 Dockerize a Spring Boot application - LAB

---


## Objective:
 - In this LAB you are going to build your own image from a **Dockerfile**.

## Key points:
 - Sometimes you may not know which is the language or the packages used to build an application but nevertheless you should find your way to "Dockerize" it.
 - In order to find the base image to start with, you need to search for official images or even not official images that fullfill your needs and read the Dockerfile to understand how this is working and then how you can build on it.
 - In this LAB you will take an existing Spring Boot (java) application and will "Dockerize" it. 
 - Note that you don't actually have to know anything about "Spring Boot (java)", other than it is a web application.
 - The goals are: 
    - Implement a **Dockerfile** to build an image. 
    - Test the image => create a container and verify that everything is working.
    - Upload `push` the image to the Docker Hub registry.
    - Delete the local image and run it again using the image available on Docker Hub.
 - Note that it is not common to get a correct Dockerfile on the first time. So, this will be an iterative process, i.e. build and test over and over, until you get the desired result. 
 - For this LAB use as starting point the `resources/dockerfile-assignment-2/java_app/Dockerfile` file from the GitHub repository. In this file you will find detailed instructions on how to build the image.
 - The base image must be a openjdk 11.x image based on debian linux (buster or stretch).
 - Note that `buster` and `stretch` are code names for releases of Debian
 - The expected result is that you're actually able to access the web application from your browser by using the IP address of the host running Docker (e.g. http://192.168.99.100).
 - The application inside the container is using TCP port 8090
 - The application must be accessible from the outside world on TCP port 80
 - Tag your image and push to your Docker Hub (free) account.
 - Remove your image from local cache, run it again from Docker Hub. This will actually re-download the image, so you can verify that everything is working as expected.

## [Optional] additional step 1
 - Improve the `docker build` process by creating dedicated image layers for the dependencies of the application such as  the gradle tool and the 3rd party java libraries 

## [Optional] additional step 2
 - Improve the `docker build` process by creating a Docker image which is: 
    - containing only the jre binary (not javac) 
    - containing only the "result" jar file (not the sources code)
    - java options such as XmX can be set from env variables    


## Reference for memory settings of Dockerized java applications 
 - [Java inside docker: What you must know to not FAIL](https://developers.redhat.com/blog/2017/03/14/java-inside-docker/)
 - [Java and Docker, the limitations](https://royvanrijn.com/blog/2018/05/java-and-docker-memory-limits/)
       
## Solution

### step 1
  - Implement the **Dockerfile**.
  - Detailed instructions are available in the `resources/dockerfile-assignment-2/java_app/Dockerfile` .
  
  - The Solution **Dockerfiles** are available at:
    0. `resources/dockerfile-assignment-2/java_app/Solution_v0.Dockerfile`
    1. `resources/dockerfile-assignment-2/java_app/Solution_v1.Dockerfile`
    2. `resources/dockerfile-assignment-2/java_app/Solution_v2.Dockerfile`

 - Ref:.
 - Official java Docker image:
 - https://hub.docker.com/_/openjdk
 - https://github.com/docker-library/docs/blob/master/openjdk/README.md#supported-tags-and-respective-dockerfile-links

### step 2
  - Build the image:
 ```terminal
 # docker build -t java_app -f Dockerfile .
 ```

### step 3a
 - Run the image: 
```terminal
# docker  container run --rm -p 80:8090 java_app
```

### step 3b
 - Verify that you can access the web application from your browser.
 - Note: Remember to use the IP address of your Docker Host (e.g 192.168.99.100)
![](../docs/images/D_S8_L3_LAB_java_web_app_screen.jpg)

### step 4
 - Re-tag the image for the upload `push` operation on Docker Hub.
 - Note that you need to use the username of your (free) Docker Hub account. 
 
```terminal
# docker tag java_app gerassimos/java_app
# docker image ls
REPOSITORY                          TAG                 IMAGE ID            CREATED             SIZE
gerassimos/java_app                 latest              959e1b0ed21a        9 minutes ago       66.6MB
java_app                            latest              959e1b0ed21a        9 minutes ago       66.6MB
...
``` 
### step 5
 - Push to Docker Hub:
```terminal
# docker push gerassimos/java_app
The push refers to repository [docker.io/gerassimos/java_app]
8f84acb939e7: Pushed
86cd623c4405: Pushing [================>]  10.41MB
...
``` 
### step 6 
 - Remove the local image:
```terminal
# docker image rm java_app:latest
``` 
### step 7
 - Run again using the online Docker Hub image:  
```terminal
# docker container run --rm -p 80:8090 gerassimos/java_app
``` 
