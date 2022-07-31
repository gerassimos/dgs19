package com.gmos.iotc.collector.webgrpc;

import com.gmos.iotc.proto.greeting.lib.GreetingResponse;
import com.gmos.iotc.proto.greeting.lib.GreetingRequest;
import com.gmos.iotc.proto.greeting.lib.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
//import io.grpc.stub.StreamObserver;

@GrpcService
public class GreetingServerImp extends GreetingServiceGrpc.GreetingServiceImplBase {

  @Override
  public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
    System.out.println("greeting for: "+request.getName() );
    responseObserver.onNext(GreetingResponse.newBuilder().setResult("Hello "+ request.getName()).build());
    responseObserver.onCompleted();
    System.out.println("greeting completed");
  }

  private void sleepNoException(int periodInMsec){
    try { Thread.sleep(periodInMsec); }
    catch(InterruptedException ex) {
      System.out.println("Sleep Thread for got interrupted. Bye bye");
    }
  }


}
