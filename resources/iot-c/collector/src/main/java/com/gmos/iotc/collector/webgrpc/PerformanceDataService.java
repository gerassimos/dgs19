package com.gmos.iotc.collector.webgrpc;

import com.gmos.iotc.proto.perfdata.lib.PerformanceDataDeviceId;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataMessage;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

@GrpcService
public class PerformanceDataService extends PerformanceDataServiceGrpc.PerformanceDataServiceImplBase {

  private Logger logger = LoggerFactory.getLogger(PerformanceDataService.class);

  @Override
  public void getPerformanceDataStreaming(PerformanceDataDeviceId request, StreamObserver<PerformanceDataMessage> responseObserver) {

    logger.info("Enter getPerformanceDataStreaming for DeviceId: {}", request.getDeviceId());
    long deviceId = request.getDeviceId();
    for (int i=0; i<10; i++){
      PerformanceDataMessage response = PerformanceDataMessage.newBuilder().setDeviceId(deviceId)
              .build();
      responseObserver.onNext(response);

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    responseObserver.onCompleted();
    logger.info("Exiting getPerformanceDataStreaming for DeviceId: {}", request.getDeviceId());
  }
}

