package com.gmos.iotc.collectorui.service;


import com.gmos.iotc.collectorui.config.IoTConfig;
import com.gmos.iotc.proto.greeting.lib.GreetingRequest;
import com.gmos.iotc.proto.greeting.lib.GreetingResponse;
import com.gmos.iotc.proto.greeting.lib.GreetingServiceGrpc.GreetingServiceBlockingStub;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataDeviceId;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataMessage;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataServiceGrpc.PerformanceDataServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;

@Service
public class DataHdrlGrpcClient {

  @GrpcClient("collector-grpc-server")
  private GreetingServiceBlockingStub stub;

  @GrpcClient("collector-grpc-server")
  private PerformanceDataServiceBlockingStub perfDatastub;

  private final IoTConfig ioTConfig;

  private Logger logger = LoggerFactory.getLogger(DataHdrlGrpcClient.class);

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


  public void grpcStreamPerfData(long deviceId) {
    PerformanceDataDeviceId request = PerformanceDataDeviceId.newBuilder()
            .setDeviceId(deviceId)
            .build();
    Iterator<PerformanceDataMessage> it = perfDatastub.getPerformanceDataStreaming(request);
    while(it.hasNext()) {
      PerformanceDataMessage message = it.next();
      logger.debug("PM: {}", message.toString());
    }
  }

}
