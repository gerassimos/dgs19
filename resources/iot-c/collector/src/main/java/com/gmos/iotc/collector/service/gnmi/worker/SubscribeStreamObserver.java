package com.gmos.iotc.collector.service.gnmi.worker;

import com.github.gnmi.proto.SubscribeResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscribeStreamObserver implements StreamObserver<SubscribeResponse>{

  private final Logger logger = LoggerFactory.getLogger(SubscribeStreamObserver.class);

  public SubscribeStreamObserver() {
  }

  @Override
  public void onNext(SubscribeResponse value) {
    //TODO
  }

  @Override
  public void onError(Throwable t) {
    //TODO
  }

  @Override
  public void onCompleted() {
    //TODO
  }
}