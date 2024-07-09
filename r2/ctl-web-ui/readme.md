# ctl-wev-ui overview

## init modules
```shell
mkdir ctl-web-ui
cd ctl-web-ui
go mod init gm-dev/ctl-web-ui
go get -u github.com/gin-gonic/gin
```

## Dockerfile ref
https://docs.docker.com/language/golang/build-images/

## TODO
add Makefile for build `docker compose build --no-cach`

## html template guide
 - [Grid system](https://getbootstrap.com/docs/5.3/layout/grid/)