package com.gmos.iotc.collector.service;

import com.github.gnmi.proto.Encoding;
import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.SubscribeRequest;
import com.github.gnmi.proto.SubscribeResponse;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.SubscriptionMode;
import com.github.gnmi.proto.gNMIGrpc;
import com.github.gnmi.proto.gNMIGrpc.gNMIStub;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GrpcClientChannelSubscriptions {

  private gNMIStub stub;
  private ManagedChannel channel;
  private String ne;
  private final Logger logger = LoggerFactory.getLogger(GrpcClientChannelSubscriptions.class);

  public GrpcClientChannelSubscriptions(String ne) {
    this.ne = ne;
    createNewChannelAndStub();
    notifyWhenStateChanged();
  }

  public boolean isConnected(){
    boolean result = false;
    ConnectivityState state = channel.getState(false);
    logger.info("{} ConnectivityState {}", ne, state.toString());
    if (state == ConnectivityState.READY ) {
      result = true;
    }
    return result;
  }

  public void reConnectReStartDataCollection(){
    channel.shutdown();
    createNewChannelAndStub();
    getDataOverGnmiSubscribeStream();
  }

  private void notifyWhenStateChanged(){
    //https://github.com/grpc/grpc-java/issues/3763
    Runnable notify = new Runnable() {
      @Override public void run() {
        ConnectivityState currentState = channel.getState(false);
        // handle currentState
        channel.notifyWhenStateChanged(currentState, this);
        logger.info("{}, Connection State Changed. New state: {}", ne, currentState);
      }
    };
    notify.run();
  }

  public void createNewChannelAndStub(){
    logger.info("{} - Creating new ChannelAndStub", ne);
    String[] parts = ne.split(":");
    String ip = parts[0];
    int port = Integer.parseInt(parts[1]) ;
    this.channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
    this.stub = gNMIGrpc.newStub(channel);
  }

  public void getDataOverGnmiSubscribeStream(){
    logger.info("{} - getDataOverGnmiSubscribeStream()",ne);
    // Latch is needed otherwise onNext is never reached
    CountDownLatch latch = new CountDownLatch(1);
    StreamObserver<SubscribeRequest> stream = stub.subscribe(new StreamObserver<SubscribeResponse>() {
       @Override
       public void onNext(SubscribeResponse response) {
         try {
           String responseVal0 =  response.getUpdate().getUpdateList().get(0).getVal().toString();
           logger.debug("{} - onNext() - responseVal0 {}", ne, responseVal0.trim());
         }catch (Exception e){
           logger.error("onNext Error: {}", e.getMessage());
         }
       }

       @Override
       public void onError(Throwable t) {
         logger.error("{} onError Error: {}",ne  ,t.getMessage());
       }

       @Override
       public void onCompleted() {
         logger.info("{} onCompleted() ",ne);
         latch.countDown();
       }
     }
    );

    SubscriptionList list = SubscriptionList.newBuilder()
            .addSubscription(getSubscriptionForOffsetFromMaster())
            .addSubscription(getSubscriptionForClockClass())
            .setEncoding(Encoding.JSON)
            .build();

    SubscribeRequest request =SubscribeRequest.newBuilder().setSubscribe(list).build();
    stream.onNext(request);
    stream.onCompleted();
    try {
      latch.await(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      logger.info("{} InterruptedException latch.await ",ne, e.getMessage());
    }

  }

  private Subscription getSubscriptionForOffsetFromMaster(){
    //    PathElem pathElem = PathElem.newBuilder()
//            .setName("ptp/instance-list/1/current-ds/offset-from-master")
//            .build();
    PathElem ptp = PathElem.newBuilder()
            .setName("ptp")
            .build();
    PathElem il = PathElem.newBuilder()
            .setName("instance-list")
            .build();
    PathElem one = PathElem.newBuilder()
            .setName("1")
            .build();
    PathElem cds = PathElem.newBuilder()
            .setName("current-ds")
            .build();
    PathElem ofm = PathElem.newBuilder()
            .setName("offset-from-master")
            .build();

    Path path = Path.newBuilder()
            .addElem(ptp)
            .addElem(il)
            .addElem(one)
            .addElem(cds)
            .addElem(ofm)
            .setOrigin("openconfig")
            .setTarget("ssync_OFM")
            .build();
    Subscription subscription = Subscription.newBuilder()
            .setPath(path)
            .setMode(SubscriptionMode.SAMPLE)
            .setSampleInterval(3000000000l) //ns 1000000000l => 1s
            .build();
    return subscription;
  }
  private Subscription getSubscriptionForClockClass(){
//    name = "ssync_ClockClass"
//    origin = "openconfig"
//    path = "/ptp/instance-list/1/default-ds/clock-quality/clock-class"
    PathElem ptp = PathElem.newBuilder()
            .setName("ptp")
            .build();
    PathElem il = PathElem.newBuilder()
            .setName("instance-list")
            .build();
    PathElem one = PathElem.newBuilder()
            .setName("1")
            .build();
    PathElem cds = PathElem.newBuilder()
            .setName("default-ds")
            .build();
    PathElem cq = PathElem.newBuilder()
            .setName("clock-quality")
            .build();
    PathElem cc = PathElem.newBuilder()
            .setName("clock-class")
            .build();

    Path path = Path.newBuilder()
            .addElem(ptp)
            .addElem(il)
            .addElem(one)
            .addElem(cds)
            .addElem(cq)
            .addElem(cc)
            .setOrigin("openconfig")
            .setTarget("ssync_ClockClass")
            .build();
    Subscription subscription = Subscription.newBuilder()
            .setPath(path)
            .setMode(SubscriptionMode.SAMPLE)
            .setSampleInterval(3000000000l) //ns 1000000000l => 1s
            .build();
    return subscription;
  }
}
