package com.gmos.iotc.collectorui.service;


import com.gmos.iotc.collectorui.config.IoTConfig;
import com.gmos.iotc.proto.greeting.lib.GreetingRequest;
import com.gmos.iotc.proto.greeting.lib.GreetingResponse;
import com.gmos.iotc.proto.greeting.lib.GreetingServiceGrpc.GreetingServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DataHdrlGrpcClient {

  @GrpcClient("greeting-grpc-server")
  private GreetingServiceBlockingStub stub;

  private final IoTConfig ioTConfig;

  public DataHdrlGrpcClient(IoTConfig ioTConfig) {
    this.ioTConfig = ioTConfig;
  }
  public String doGreet(String name){
    System.out.println("Enter doGreet");
    GreetingResponse response = stub.greeting(GreetingRequest.newBuilder().setName(name).build());
    processGreetingResponse(response);
    return response.toString();
  }

  private static void processGreetingResponse(GreetingResponse response){
    Date date = new Date();
    System.out.println(date.toString() + " | Response: "+ response.getResult() );
  }
}
