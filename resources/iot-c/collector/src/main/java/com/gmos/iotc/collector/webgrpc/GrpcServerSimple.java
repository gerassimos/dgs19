package com.gmos.iotc.collector.webgrpc;

import com.gmos.iotc.collector.config.IotcConfig;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GrpcServerSimple {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private final PerformanceDataServiceCommon performanceDataServiceCommon;
  private final IotcConfig iotcConfig;

  public GrpcServerSimple(PerformanceDataServiceCommon performanceDataServiceCommon, IotcConfig iotcConfig) {
    this.performanceDataServiceCommon = performanceDataServiceCommon;
    this.iotcConfig = iotcConfig;

    Thread thread = new Thread(){
      public void run(){
        System.out.println("Thread Running");
        startSimpleGrpcServer();
      }
    };
    thread.start();
  }

  private void startSimpleGrpcServer(){
    int port = iotcConfig.getGrpcServerSimplePort();
    try{
      logger.info("Starting simple gRPC server...");
      // plaintext server
      Server server = ServerBuilder
              .forPort(port)
              .addService(new PerformanceDataServiceGrpcServerImp(performanceDataServiceCommon))
              .addService(new GreetingServiceGrpcServerImp())
              .build();
      server.start();
      logger.info("gRPC server started on port: {}", port);

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        logger.info("Received Shutdown Request");

        server.shutdown();
        logger.info("Successfully stopped the server");
      }));

      server.awaitTermination();
    }catch (Exception e){
      logger.error("Fail to start grpc server on port {}  - error: {}", port, e.getMessage());
    }
  }
}


