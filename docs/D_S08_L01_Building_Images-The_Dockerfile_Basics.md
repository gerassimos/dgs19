background-image: url(images/containers_legacy_app.jpg)
background-size: 100% 100%

<h3 style="margin-top: 500px;">
<span style="color:white">
Section 8 - Build Images - The Dockerfile Basics
</span>
</h3>
---

class: center, middle
# Section 8 - Build Images - The Dockerfile Basics
## 1 Building Images - The Dockerfile Basics
---
## Dockerfile
 - The **Dockerfile** is a recipe for creating a Docker image.
 - It contains the **instructions** on how to build a Docker image.  
 - All official images on Docker Hub are created from Dockerfiles.
 - It is similar to a shell script.
 - The default file name is **Dockerfile** but you can use custom name as well by specifying the `--file` option.
 
---


## Dockerfile example
 - The [Dockerfile](https://github.com/gerassimos/dgs19/blob/master/resources/dockerfile-sample-1/Dockerfile) of this example is available in the `resources` directory:  
 (`resources/dockerfile-sample-1/Dockerfile`)
 
---
 
## package manager
 - The base linux distribution defined in the **FROM** section of the *Dockerfile* determines the **package manager** that we can use to install additional software.
 - For example: 
   - **apt-get** is used from *Ubuntu* or *Debian* base images
   - **yum** is used from *CenOS* or *Fedora* base images
   - *apk* is used from *Alpine* base image

> The base images usually have a minimal set of packages installed, many tools such as `curl` could be missing.

---

## Dockerfile - On line documentation

### [docker docs/Dockerfile reference](https://docs.docker.com/engine/reference/builder/)
