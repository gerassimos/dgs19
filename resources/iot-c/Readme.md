## iot-c spring boot multi-project
 - This is an example spring boot multi-project
 - Goal are to see examples on how to:
    1. Build a Docker image 
    2. Implement HTTP Rest endpoints
    3. Access a DB
    
## Build Notes
 - Reminder note gradle properties are set with the "-P prop-name=prop-value" in the Arguments field of the related gradle task
 - set the gradle property "-P IOTC_TAG=0.0.1" for the jar version and docker tag
 - set the gradle property "-P DOCKER_USERNAME=user" for docker-hub account username
 