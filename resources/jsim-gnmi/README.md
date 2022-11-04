# jsim-gnmi - java simulator for gnmi/gRPC server  
---
## Overview
 - Dummy gnmi/gRPC server to simulate subscribe gRPC call 
 - implements `subscribe` gRPC/gNMI call

## env variables
 - `GRPC_SERVER_PORT` = The grpc server port default 4567 

## build
 - To build the Docker image `docker-compose build`
 - To compile the proto files `./gradlew :generateProto`

## How to deploy
 - With Docker: `docker run -p 4567:4567 dgs19/jsim-gnmi:latest` 


