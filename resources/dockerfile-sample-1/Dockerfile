# In this example is to see how we can build our own image by extending an existing official image

FROM nginx:latest
# In the first instruction we specify the base image to start with
# The base image usually is a minimal Linux distribution like debian or alpine
# In this case the base image is ==> debian + nginx
# Note:
# It is highly recommended to use specific TAG (other than latest) for production builds

WORKDIR /usr/share/nginx/html
# This instruction is to change the working directory from within the container
# The "/usr/share/nginx/html" is the nginx default directory for html files

COPY index.html index.html
# The "COPY" instruction is used to copy files from the build context to the container's file system
# In this case we will copy the index.html from the "context" into the current working directory 
# This will override the default "index.html" of the nginx server

# We do NOT need to specify EXPOSE or CMD instructions because they are inherited "FROM" the base image 


