package main

import (
	"fmt"
	"gnmi/server-sim/config"
	"gnmi/server-sim/greet"
	"google.golang.org/grpc"
	"log"
	"net"
)

var addr string = "0.0.0.0:50051"

type server struct {
	greet.GreetServiceServer
}

func main() {
	fmt.Println("Starting main...")
	config.PrintTestMsg()
	t := config.Test{}
	fmt.Println(t)

	lis, err := net.Listen("tcp", addr)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	greet.RegisterGreetServiceServer(s, &server{})
	log.Printf("server listening at %v", lis.Addr())
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
