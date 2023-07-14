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
  private final PerformanceDataServiceCommon performanceDataServiceCommon;

  public PerformanceDataService(PerformanceDataServiceCommon performanceDataServiceCommon) {
    this.performanceDataServiceCommon = performanceDataServiceCommon;
  }

  @Override
  public void getPerformanceDataStreaming(PerformanceDataDeviceId request, StreamObserver<PerformanceDataMessage> responseObserver) {
    performanceDataServiceCommon.getPerformanceDataStreaming(request,responseObserver);
  }

}

