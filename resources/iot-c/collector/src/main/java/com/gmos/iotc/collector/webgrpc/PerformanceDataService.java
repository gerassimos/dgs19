package com.gmos.iotc.collector.webgrpc;

import com.gmos.iotc.collector.domain.PerformanceDataEntity;
import com.gmos.iotc.collector.repository.DeviceHdrl;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataDeviceId;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataMessage;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@GrpcService
public class PerformanceDataService extends PerformanceDataServiceGrpc.PerformanceDataServiceImplBase {

  private Logger logger = LoggerFactory.getLogger(PerformanceDataService.class);
  private final DeviceHdrl deviceHdrl;

  public PerformanceDataService(DeviceHdrl deviceHdrl) {
    this.deviceHdrl = deviceHdrl;
  }

  @Override
  public void getPerformanceDataStreaming(PerformanceDataDeviceId request, StreamObserver<PerformanceDataMessage> responseObserver) {

    logger.info("Enter getPerformanceDataStreaming for DeviceId: {}", request.getDeviceId());
    long deviceId = request.getDeviceId();
    Timestamp endTimestamp = new Timestamp( System.currentTimeMillis()) ;
    Timestamp startTimestamp = new Timestamp( endTimestamp.getTime() - 10000l);
    int waitingPeriod = 10000;
    long timeShift = waitingPeriod;
    for (int i=0; i<10; i++){
      List<PerformanceDataEntity> performanceDataEntityList = deviceHdrl.findByTimestampBetweenAndDeviceEntity(startTimestamp,endTimestamp,deviceId);
      endTimestamp = new Timestamp( endTimestamp.getTime() + timeShift) ;
      startTimestamp = new Timestamp( startTimestamp.getTime() + timeShift);
      for(PerformanceDataEntity performanceDataEntity : performanceDataEntityList){
        PerformanceDataMessage response = toPerformanceDataMessage(performanceDataEntity);
        responseObserver.onNext(response);
      }
      try {
        Thread.sleep(waitingPeriod);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    responseObserver.onCompleted();
    logger.info("Exiting getPerformanceDataStreaming for DeviceId: {}", request.getDeviceId());
  }

  private PerformanceDataMessage toPerformanceDataMessage(PerformanceDataEntity performanceDataEntity) {
    PerformanceDataMessage result = PerformanceDataMessage.newBuilder()
            .setHumidity(performanceDataEntity.getHumidity())
            .setTemperature(performanceDataEntity.getTemperature())
            .setTimestamp(performanceDataEntity.getTimestamp().getTime())
            .setDeviceId(performanceDataEntity.getDeviceId())
            .build();
    return result;
  }
}

