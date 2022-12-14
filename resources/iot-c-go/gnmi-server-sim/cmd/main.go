package main

import (
	"fmt"
	"gnmi/common/dto"
	"gnmi/common/proto/calc"
	"gnmi/common/proto/gnmi"
	"gnmi/common/proto/greet"
	"gnmi/server-sim/config"
	"google.golang.org/grpc"
	"log"
	"net"
)

var addr string = "0.0.0.0:4567"

type Server struct {
	greet.GreetServiceServer
	calc.CalculatorServiceServer
	gnmi.GNMIServer
}

func main() {
	fmt.Println("Starting main...")
	config.PrintTestMsg()
	t := config.Test{}
	fmt.Println(t)
	t2 := dto.Test2{}
	fmt.Println(t2)

	lis, err := net.Listen("tcp", addr)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	greet.RegisterGreetServiceServer(s, &Server{})
	calc.RegisterCalculatorServiceServer(s, &Server{})
	gnmi.RegisterGNMIServer(s, &Server{})

	log.Printf("server listening at %v", lis.Addr())
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
