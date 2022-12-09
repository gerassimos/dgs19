package main

import (
	"context"
	"log"

	"gnmi/common/proto/greet"
)

func doGreet(c greet.GreetServiceClient) {
	log.Println("doGreet was invoked")
	r, err := c.Greet(context.Background(), &greet.GreetRequest{FirstName: "Clement"})

	if err != nil {
		log.Fatalf("Could not greet: %v\n", err)
	}

	log.Printf("Greeting: %s\n", r.Result)
}
