# jsim - java simulator  

---

## Overview
 - This is a simple java web application (single java file) that can be used to simulate cpu/memory loads on a system
 - By default the web server is listening on port 4567 (not configurable yet)
 - So for example after the application is started use `http://localhost:4567/details` to display details about the request
 - The related GitHub repo is available [here](https://github.com/gerassimos/dgs19/tree/master/resources/jsim) 
 
## Endpoints
    GET "/"             =>  Display the server hosname and client IP
    GET "/cpu"          => Generare CPU load by running Fibonacci sequence
    GET "/ram"          => Generare Memory load and display memory info
    GET "/ram/info      => Display memory info
    GET "/ram/clean     => Clean Memory - Undo "Memory load" 
    GET "/health        => Display Healthy state http 200 => Healthy!  -- http 401 Not healthy
    GET "/health/flip   => Flip the healthy state (Healthy=>Not healthy=>Healthy)
    GET "/details       => Display details about the request

## How to deploy
 - With Docker: `docker run -p 4567:4567 dgs19/jsim:latest` 
    

