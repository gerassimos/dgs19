## iot-c spring boot multi-project
 - This is a spring boot multi-project **example** application
 - The **dgs19/iot-collector** service simulates the collection of performance data from IoT devices such as temperature/humidity sensors  
 - The **dgs19/iot-collector-ui** service is the front end Web UI for the collector service.
 
##  Goals
 - The Goals of this example **iot-c** application are to see how to:
    1. Create a spring boot multi-project  
    2. Dockerizing a Spring Boot Application  
    3. others...

## environment variables 
 - **dgs19/iot-collector**
    1. `LOG_LEVEL`: to set the log level. Valid values are: *INFO*, *DEBUG* and *TRACE*
    2. `DB_HOST`: hostname of the database server. Default value is *192.168.99.100*
    3. `DB_PORT`: TCP port of the database server. Default value is *5432*
    4. `DB_NAME`: database name. Default value is *iotc* 
    5. `DB_USER`: username of the database server. Default value is *iotc*   
    6. `DB_PASSWORD`: password of the database server. Default value is *iotc*

 - **dgs19/iot-collector-ui**
   1. `LOG_LEVEL`: to set the log level. Valid values are: *INFO*, *DEBUG* and *TRACE*
   2. `COLLECTOR_HOST_NAME`: hostname of the collector (dgs19/iot-collector) server 
   3. `COLLECTOR_HOST_PORT`: TCP port of the collector (dgs19/iot-collector) server. Default value is 8092

## TCP ports
 - TCP port 8092 is used from the **dgs19/iot-collector** service to expose the REST Endpoints.  
 - TCP port 8093 is used from the **dgs19/iot-collector-ui** service to serve the index.html web page.
  
## REST Endpoints examples
 - **dgs19/iot-collector**
    - http://192.168.99.100:8092/devices
    - http://192.168.99.100:8092/device/performance?deviceId=1
    
## Build Notes
 - Reminder note gradle properties are set with the "-P prop-name=prop-value" in the Arguments field of the related gradle task
 - set the gradle property "-P IOTC_TAG=0.0.1" for the jar version and docker tag
 - set the gradle property "-P DOCKER_USERNAME=user" for docker-hub account username
 
## Build targets 
 - ./gradlew build
 - ./gradlew docker
 - ./gradlew dockerTagTag1 -P DOCKER_USERNAME=dgs19 -P IOTC_TAG=0.0.1
 - ./gradlew dockerPushTag1 -P DOCKER_USERNAME=dgs19 -P IOTC_TAG=0.0.1