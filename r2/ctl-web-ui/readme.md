# ctl-wev-ui overview

## init modules
```shell
mkdir ctl-web-ui
cd ctl-web-ui
go mod init gm-dev/ctl-web-ui
go get -u github.com/gin-gonic/gin
go get github.com/gorilla/websocket
```

## Dockerfile ref
https://docs.docker.com/language/golang/build-images/
- Note that we are using multi-stage builds, 
  Then with the `target: build-alpine` keyword in the `docker-compose.yml` we specify which image we want to build.

## Run the app
 - `cd ctl-web-ui`
 - `go run cmd/main.go`


## html template guide
 - [Grid system](https://getbootstrap.com/docs/5.3/layout/grid/)

## usefull links
 - [Showing command line output on a html page](https://stackoverflow.com/questions/53860093/showing-command-line-output-on-a-html-page)
 - [execute long running job and streaming the realtime output](https://gist.github.com/bamoo456/7e21773e8ef742a726c041f5f0019d2e)
 - [StdoutPipe with colors](https://stackoverflow.com/questions/61113201/io-multiwriter-removes-terminal-colors-from-output)

## TODO
add Makefile for build `docker compose build --no-cach`