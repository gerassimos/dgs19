package main

import (
	"fmt"
	"gnmi/common/proto/gnmi"
	"io"
	"log"
	"time"
)

//func (*Server) GreetManyTimes(in *pb.GreetRequest, stream pb.GreetService_GreetManyTimesServer) error {
//	log.Printf("GreetManyTimes was invoked with %v\n", in)
//
//	for i := 0; i < 10; i++ {
//		res := fmt.Sprintf("Hello %s, number %d", in.FirstName, i)
//
//		stream.Send(&pb.GreetResponse{
//			Result: res,
//		})
//	}
//
//	return nil
//}

func (*Server) Subscribe(stream gnmi.GNMI_SubscribeServer) error {
	//Only one message is expected from client
	subscribeRequest, err := stream.Recv()

	if err == io.EOF {
		log.Fatalf("Error unexpected EOF received: %v\n", err)
		return nil
	}

	if err != nil {
		log.Fatalf("Error while reading client stream: %v\n", err)
	}
	log.Printf("Subscribe was invoked with %v\n", subscribeRequest)
	for {
		subscriptionList := subscribeRequest.GetSubscribe()
		subscriptionArray := subscriptionList.GetSubscription()
		for _, subscription := range subscriptionArray {
			fmt.Println()
			subscription.GetPath()
			update := gnmi.Update{
				Path: subscription.GetPath(),
				Val: &gnmi.TypedValue{
					Value: &gnmi.TypedValue_IntVal{
						IntVal: 3,
					},
				},
			}
			log.Printf("update %v\n", update)
		}

		//response := &gnmi.SubscribeResponse{
		//	Response: &gnmi.SubscribeResponse_Update{
		//		Update: update,
		//	},
		//}
		//err = stream.Send(response)

		time.Sleep(3 * time.Second)
		log.Println("sleeping 3s...")
		if err != nil {
			log.Fatalf("Error while sending data to client: %v\n", err)
		}
	}
	return nil
}
