package com.gmos.iotc.collector.webgrpc;

import com.gmos.iotc.proto.perfdata.lib.PerformanceDataDeviceId;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataMessage;
import com.gmos.iotc.proto.perfdata.lib.PerformanceDataServiceGrpc;
import io.grpc.stub.StreamObserver;

public class PerformanceDataServiceGrpcServerImp extends
        PerformanceDataServiceGrpc.PerformanceDataServiceImplBase {

  private final PerformanceDataServiceCommon performanceDataServiceCommon;

  public PerformanceDataServiceGrpcServerImp(PerformanceDataServiceCommon performanceDataServiceCommon) {
    this.performanceDataServiceCommon = performanceDataServiceCommon;
  }

  @Override
//  public void greetingManyTimes(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {}
  public void getPerformanceDataStreaming(PerformanceDataDeviceId request,
                                          StreamObserver<PerformanceDataMessage> responseObserver) {
    performanceDataServiceCommon.getPerformanceDataStreaming(request,responseObserver);
  }
}
