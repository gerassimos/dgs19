class: center, middle
# Section 7 - Container Images - Docker Hub Registry
## 3 Docker images - Local Cache
---

## Image layers (1)
 - We can see the image layers from the output of the `docker pull` command 
 - When we use `docker pull`, we can see from the output of the command that the image is not a single big blob of data. 
```terminal
# docker pull mysql
Using default tag: latest
latest: Pulling from library/mysql
27833a3ba0a5: Already exists
864c283b3c4b: Already exists
5479aaef3d30: Already exists
9667974ee097: Pull complete
4ebb5e7ad6ac: Downloading [=============>   ] 17.14MB/88.99MB
021bd5074e22: Downloading [======>          ] 33.14MB/69.99MB
cce70737c123: Waiting
544ff12e028f: Waiting
```
  
---

## Image layers (2)

```terminal
# docker pull mysql
Using default tag: latest
latest: Pulling from library/mysql
27833a3ba0a5: Already exists
864c283b3c4b: Already exists
5479aaef3d30: Already exists
9667974ee097: Pull complete
4ebb5e7ad6ac: Downloading [=============>   ] 17.14MB/88.99MB
021bd5074e22: Downloading [======>          ] 33.14MB/69.99MB
cce70737c123: Waiting
544ff12e028f: Waiting
```

> - The image is composed from smaller pieces of data => **layers**.
> - Some of the layer "Already exists" on the local **cache** => No need to download this part
> - The images are designed using the **union file system** which concept is to make layers for a set of changes

---

## docker image history (1)
 - Use the `docker image history` command to show the **build** history of an image.
 - Show **layers** of changes made on the image.
 ```terminal
 # docker image history nginx
IMAGE          CREATED       CREATED BY                                      SIZE  
27a188018e18   9 days ago    /bin/sh -c #(nop)  CMD ["nginx" "-g" "daemon…   0B
<missing>      9 days ago    /bin/sh -c #(nop)  STOPSIGNAL SIGTERM           0B
<missing>      9 days ago    /bin/sh -c #(nop)  EXPOSE 80                    0B
<missing>      9 days ago    /bin/sh -c ln -sf /dev/stdout /var/log/nginx…   22B
<missing>      9 days ago    /bin/sh -c set -x  && apt-get update  && apt…   54.1MB
<missing>      9 days ago    /bin/sh -c #(nop)  ENV NJS_VERSION=1.15.12.0…   0B
<missing>      9 days ago    /bin/sh -c #(nop)  ENV NGINX_VERSION=1.15.12…   0B
<missing>      4 weeks ago   /bin/sh -c #(nop)  LABEL maintainer=NGINX Do…   0B
<missing>      4 weeks ago   /bin/sh -c #(nop)  CMD ["bash"]                 0B
<missing>      4 weeks ago   /bin/sh -c #(nop) ADD file:4fc310c0cb879c876…   55.3MB
 ```
 
---

## docker image history (2) 

 - Every set of changes on the image file system is another layer.
 - Some layers may not change in terms of the **file size** => metadata (e.g EXPOSE 80 )
 - Every layer has a unique SHA number that identify the changes made.

---

## docker image inspect (1)
 - Use the `docker image inspect` command to display detailed information of an image.
 - Information included: ImageId, RepoTags, ExposePorts, Environment variables, CMD , Architecture, GraphDriver, etc...
  
---
 
## docker image inspect (2)
 
```terminal
# docker image inspect nginx
[
    {
        "Id": "sha256:27a188018e1847b312022b02146bb7ac3da54e96fab838b7db9f102c8c3dd778",
        "RepoTags": [
            "nginx:1.15",
            "nginx:1.15.12",
            "nginx:latest"
        ],
        ...
        "GraphDriver": {  ...   "Name": "overlay2" },
        ...
        "RootFS": {
            "Type": "layers",
            "Layers": [
                "sha256:5dacd731af1b0386ead06c8b1feff9f65d9e0bdfec032d2cd0bc03690698feda",
                "sha256:912ed487215b213aaad80bedb31484cab0b060de73d49bd1cfd9a550b7c2f11c",
                "sha256:fc4c9f8e7dacd81078d56e811c55ce1920688a91748bfbb2b98a5a9c316ff66c"
...
```


