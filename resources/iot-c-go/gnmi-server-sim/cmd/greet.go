package main

import (
	"context"
	"gnmi/common/proto/greet"
	"log"
)

func (*Server) Greet(ctx context.Context, in *greet.GreetRequest) (*greet.GreetResponse, error) {
	log.Printf("Greet was invoked with %v\n", in)
	return &greet.GreetResponse{Result: "Hello " + in.FirstName}, nil
}
