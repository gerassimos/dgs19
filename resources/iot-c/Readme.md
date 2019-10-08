## iot-c spring boot multi-project
 - This is a spring boot multi-project **example** application named **iot-c**
 - The **iot-c** application is compose from 3 services:
    1. The **collector** service simulates the collection of performance data from IoT devices such as temperature/humidity sensors  
    2. The **collector-ui** service is the front end Web UI for the collector service.
    3. The **postgres_db** service is the DB where the performance data are stored
 - The 3 services are related to the following docker images:
    1. dgs19/iot-collector:0.0.1
    2. dgs19/iot-collector-ui:0.0.1
    3. postgres:10
 - The source code is available [here](https://github.com/gerassimos/dgs19/tree/master/resources/iot-c)    
     
##  Goals
 - The Goals of this example **iot-c** application are to see how to:
    1. Create a spring boot multi-project  
    2. Dockerizing a Spring Boot Application [Ref](https://spring.io/guides/gs/spring-boot-docker/) 
    3. Accessing Data with JPA [Ref](https://spring.io/guides/gs/accessing-data-jpa/)
    4. Build a RESTful Web Service [Ref](https://spring.io/guides/gs/rest-service/)
    5. others..
## Usage
 - Start the **iot-c** application using the [docker-compose.yml](https://github.com/gerassimos/dgs19/blob/master/resources/iot-c/docker-compose.yml) as follow:
```console
git clone https://github.com/gerassimos/dgs19.git
cd dgs19/resources/iot-c/
docker-compose up
```
- The Web application should be accessible on port 80
   ![](../../docs/images/D_S10_L02_collector-ui_web_page.png)

## Development environment   
 - In development environment we need to start only the the DB service using the [docker-compose-dev.yml](https://github.com/gerassimos/dgs19/blob/master/resources/iot-c/docker-compose-dev.yml)
 ```console
git clone https://github.com/gerassimos/dgs19.git
cd dgs19/resources/iot-c/
docker-compose -f docker-compose-dev.yml up
```  
 - In development environment the [settings.gradle](https://github.com/gerassimos/dgs19/blob/master/resources/iot-c/settings.gradle) file can be imported from an IDE such as IDEA to setup the spring-boot multi-project
   
 
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