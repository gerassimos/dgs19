## iot-c spring boot multi-project
 - This is a spring boot multi-project **example** application named **iot-c**
 - The **iot-c** application is compose from 3 services:
    1. The **collector** service simulates the collection of performance data from IoT devices such as temperature/humidity sensors  
    2. The **collector-ui** service is the front end Web UI for the collector service.
    3. The **postgres-db** service is the DB where the performance data are stored
 - The 3 services are related to the following docker images:
    1. dgs19/iot-collector
    2. dgs19/iot-collector-ui
    3. postgres
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
- The Web application should be accessible on port 8093
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
    7. `GRPC_SERVER_PORT` : set the port for the grpc server
    8. `COLLECTOR_SCHEDULER_ENABLED`: boolean flag, if set to `false` the main collector scheduler will NOT start
    9. `spring_profiles_default`: set the default spring profile. Valid values are: *dev*
    10. `spring_profiles_active`: set the active spring profile. Valid values are: *dev*
    11. `GRPC_SERVER_SIMPLE_PORT`: set the port for the simple additional code created grpc server != grpc server created from grpc-server-spring-boot-starter  

 - **dgs19/iot-collector-ui**
   1. `LOG_LEVEL`: to set the log level. Valid values are: *INFO*, *DEBUG* and *TRACE*
   2. `COLLECTOR_HOST_NAME`: hostname of the collector (dgs19/iot-collector) server 
   3. `COLLECTOR_HOST_PORT`: TCP port of the collector (dgs19/iot-collector) server. Default value is 8092
   4. `COLLECTOR_GRPC_PORT`: set the grpc port for the greeting service implemented in the collector module 

## TCP ports
 - TCP port 8092 is used from the **dgs19/iot-collector** service to expose the REST Endpoints.  
 - TCP port 8093 is used from the **dgs19/iot-collector-ui** service to serve the index.html web page.
  
## REST Endpoints examples
 - **dgs19/iot-collector**
    - http://localhost:8092/devices
    - http://localhost:8092/device/performance?deviceId=1
    - http://localhost:8092/actuator/prometheus
    - http://localhost:8092/actuator/health
    
## Build Notes
 - Reminder note gradle properties are set with the "-P prop-name=prop-value" in the Arguments field of the related gradle task
 - set the gradle property "-P IOTC_TAG=0.0.1" for the jar version and docker tag
 - set the gradle property "-P DOCKER_USERNAME=user" for docker-hub account username
 
## Build targets 
 - ./gradlew build
 - ./gradlew docker
 - ./gradlew dockerTagTag1 -P DOCKER_USERNAME=dgs19 -P IOTC_TAG=0.0.1
 - ./gradlew dockerPushTag1 -P DOCKER_USERNAME=dgs19 -P IOTC_TAG=0.0.1
 - ./gradlew :common-grpc:generateProto
 - ./gradlew :collector:build

## Prometheus Integration
 - The default spring boot prometheus metrics are enabled 
 - prometheus metrics are available at the endpoint /actuator/prometheus
 - The following labels are added to all the prometheus metrics 
   - instanceHostName: ${HOSTNAME:localhost}
   - application: iot-c
   - service: iot-c-collector

## grpc Integration (common-grpc)
 - the common-grpc module has been added that contains the .proto files
 - from this module we can execute the `generateProto` gradle target to generate the java files needed
   ```shell 
   cd iot-c
   ./gradlew :common-grpc:generateProto
   ```
 - Sometimes the import of the generated java files looks broker from intellij
   Some workaround:
   - `File -> Invalidate Caches/Restart`
      ref: [intellij-cannot-resolve-symbol-on-import](https://stackoverflow.com/questions/26952078/intellij-cannot-resolve-symbol-on-import)
   - From the gradle tab right click on iot-c -> `Reload Gradle project`
 - ref helper sites:
 - https://yidongnan.github.io/grpc-spring-boot-starter/en/server/getting-started.html
 - https://github.com/yidongnan/grpc-spring-boot-starter

## grpc Integration (collector)
 - the env variable `GRPC_SERVER_PORT` is to control the grpc server port (collector module)
 - the dependencies added in the build.gradle file of the collector module are
   - implementation(project(":common-grpc"))
   - implementation("net.devh:grpc-server-spring-boot-starter:xxx")

## grpc Integration (collector-ui)
 - the env variables `COLLECTOR_HOST_NAME` and `COLLECTOR_GRPC_PORT` are used to the grpc host:port for the greeting service implemented in the collector
 - to test the grpc communication between the client (collector-ui) and the server (collector) use:
   - `curl http://localhost:8093/grpc-greet-mitge`
 - to test the server side grpc streaming. Will get a stream of PerformanceDataMessage 
   - `curl http://localhost:8093/grpc-stream-perf-data`

## grpcurl grpc cmd history
```shell
# install grpcurl
brew install grpcurl

# grpcurl list
grpcurl --plaintext localhost:9091 list
com.gmos.iotc.proto.greeting.GreetingService
grpc.health.v1.Health
grpc.reflection.v1alpha.ServerReflection

# grpcurl service
grpcurl --plaintext -d '{"name": "test222"}' localhost:9091 com.gmos.iotc.proto.greeting.GreetingService/greeting
{
  "result": "Hello test222"
}
```
## log format json
- To enable log format add the following to the application.yml
- This is a quick a "dirty" way to have json log, probably we need to use more clean approach
- [ref](https://stackoverflow.com/questions/53730449/is-there-a-recommended-way-to-get-spring-boot-to-json-format-logs-with-logback)
```yaml
logging:
  #https://stackoverflow.com/questions/53730449/is-there-a-recommended-way-to-get-spring-boot-to-json-format-logs-with-logback
  pattern:
    console: "{\"time\": \"%d\", \"level\": \"%p\", \"correlation-id\": \"%X{X-Correlation-Id}\", \"source\": \"%logger{63}:%L\", \"message\": \"%replace(%m%wEx{6}){'[\r\n]+', '\\n'}%nopex\"}%n"
```

## spring boot dev profile 
 - It is now possible to use the spring boot dev profile which will use the `application-dev.yml` file
 - There a number of ways to control which spring boot profile is active
 - For example to set the `dev` profile
 - Set the env variable `spring_profiles_active=dev`
 - Set the env variable `spring_profiles_default=dev`
 - update the application.yml as follows
   ```yaml
   spring:
     profiles:
       default: dev
   ``` 
 - Note that application.yml is meant to define the common setting among profiles
 - For simplicity's sake in this project we us application.yml for "production" deployment
 - [ref-1](https://www.baeldung.com/spring-profiles)
  
## H2 Database
 - The in memory `H2 Database` is configured for the `dev` spring boot profile
 - [console](http://localhost:8092/h2-console) 

## open-telemetry
 - The opentelemetry-javaagent has been added to the docker images
 - The `Dockerfile` updated to copy the opentelemetry-javaagent.jar
 - The `docker-compose-jaeger.yml` is updates to use opentelemetry-javaagent
 - The `start-jar.sh` added on each module to test locally the opentelemetry-javaagent.jar
 - The [collector] - `findByDeviceId()` method is updated to log all Headers (logHeaders)
 - The [collector-ui] - `getData()` method is updated to log all Headers (logHeaders)
 - The env `OTEL_TRACES_EXPORTER=none` and `OTEL_PROPAGATORS=tracecontext,baggage,b3multi` 
   can be used with the tracing capabilities of istio
   In this case the application will not send traces to jaeger but will propagate the trace context 

## collector-a-to-c
 - acting as a proxy between collector-ui and collector for one endpoint
 - Implemented to test tracing functionality when a request is passing through multiple services

## spring-boot 3.1
 - After updated to spring-boot 3.1 the grpc code is not working, the grpc `stub` object is null
 - most probably is not supported yet
 - consider replacing the `net.devh:grpc-server-spring-boot-starter:2.14.0.RELEASE` library
 - NOTE: gprc functionality not working 

## openapi
 - Ref: https://springdoc.org/
 - Add openapi lib (org.springdoc:springdoc-openapi-starter-webmvc-ui) to enable swagger and 
 - swagger ui:  http://localhost:8092/swagger-ui/index.html
 - openapi json: http://localhost:8092/v3/api-docs
 - openapi yaml: http://localhost:8092/v3/api-docs.yaml
 - test

## collector - gRPC server Simple 
 - A simple additional code created grpc server added 
 - This is different from the grpc server created from grpc-server-spring-boot-starter lib
 - The port is defined from `GRPC_SERVER_SIMPLE_PORT` env var

## collector-kafka-pg
 - Test kafka integration
 - To test sending and receiving messages from kafka:
   - `curl http://localhost:8096/test-kafka`
 - vs code setup 
   - file `.vscode/launch.json`
   - ref1: https://spring.io/guides/gs/guides-with-vscode
   - ref2: https://stackoverflow.com/questions/59687712/how-to-set-java-environment-variable-in-vs-code
 - Test vs code env var
   - `curl http://localhost:8096/test-env`
 - Test commit

## cloud-aws-pg
- It a playground `pg` service to test integration with [Spring Cloud AWS](https://awspring.io/)
- The `cloud-aws-pg` service is configured to use the aws ssm parameter store to get the `kafka.bs` string
- To work required access to aws via accessKeyId and secretAccessKey
   - more: https://docs.awspring.io/spring-cloud-aws/docs/3.2.0/reference/html/index.html#getting-started
- Test endpoints: 
   - `curl http://localhost:8098/test-aws-ssm`

## Binding From Environment Variables
- [ref1](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.relaxed-binding.environment-variables)
- [ref2](https://stackoverflow.com/questions/70267751/override-spring-boot-yaml-property-via-environment-variable)
- For example, the configuration property `spring.main.log-startup-info` 
- would be an environment variable named `SPRING_MAIN_LOGSTARTUPINFO`
- For example in use `CLOUDAWSPG_KEY1=xyz` to override the value `cloud-aws-pg.key1` in application.yml 

## run amd64 image on an arm64 based MAC os
- Use the `--platform linux/amd64` flag to run the amd64 image on arm64 MAC host
- Look also multi-platform build at:
- [ref1](https://docs.docker.com/build/building/multi-platform/?utm_source=chatgpt.com)
- 
```shell
docker run --platform linux/amd64 -e SPRING_PROFILES_ACTIVE=dev dgs19/iot-collector:latest
## The simple run command will also work on arm based MAC host
## will automatically detect that the only available image is for amd64
## WARNING: The requested image's platform (linux/amd64) does not match the detected host platform (linux/arm64/v8) and no specific platform was requested
docker run -e SPRING_PROFILES_ACTIVE=dev dgs19/iot-collector:latest
```
