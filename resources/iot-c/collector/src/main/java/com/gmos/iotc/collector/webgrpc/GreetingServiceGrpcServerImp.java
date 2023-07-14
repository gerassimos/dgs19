package com.gmos.iotc.collector.webgrpc;

import com.gmos.iotc.proto.greeting.lib.GreetingRequest;
import com.gmos.iotc.proto.greeting.lib.GreetingResponse;
import com.gmos.iotc.proto.greeting.lib.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingServiceGrpcServerImp extends GreetingServiceGrpc.GreetingServiceImplBase{

  private Logger logger = LoggerFactory.getLogger(this.getClass());


  @Override
  public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
    logger.info("greeting for: "+request.getName() );
    responseObserver.onNext(GreetingResponse.newBuilder().setResult("Hello "+ request.getName()).build());
    responseObserver.onCompleted();
    logger.info("greeting completed");
  }

}
