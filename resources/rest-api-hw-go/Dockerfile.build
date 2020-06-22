FROM golang:1.14.4 as builder

WORKDIR /src
# Copy go mod and sum files 
COPY go.mod go.sum ./

# Download all dependencies. Dependencies will be cached if the go.mod and the go.sum files are not changed 
RUN go mod download 

# Copy the source from the current directory to the working Directory inside the container 
COPY . .

# Build the Go app
# RUN go build -o app .
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app .


FROM scratch
# FROM alpine:latest
WORKDIR /root/
COPY --from=builder /src/app .
# COPY --from=builder /src/.env . 
# Expose port 8080 to the outside world

ENV GIN_MODE=release
EXPOSE 8080

# Command to run the executable
CMD ["./app"]