FROM alpine:3.12.0

WORKDIR /root/
COPY app .

#GIN web framwork switch to "release" mode in production.
ENV GIN_MODE=release

# Expose port 8080 to the outside world
EXPOSE 8080

#Command to run the executable
CMD ["./app"]