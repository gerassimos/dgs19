package com.gmos.iotc.collector.service.gnmi;

import com.github.gnmi.proto.SubscribeRequest;
import com.github.gnmi.proto.SubscribeResponse;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.gNMIGrpc;
import com.github.gnmi.proto.gNMIGrpc.gNMIStub;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GrpcClientChannelSubscriptions {

  private Map<SubscriptionList, StreamObserver<SubscribeRequest> > streamMap;
  private gNMIStub stub;
  private ManagedChannel channel;
  private String ne;
  private final Logger logger = LoggerFactory.getLogger(GrpcClientChannelSubscriptions.class);

  public GrpcClientChannelSubscriptions(String ne) {
    this.ne = ne;
    streamMap = new HashMap<>();
    createNewChannelAndStub();
    notifyWhenStateChanged();
  }

  public boolean isConnected(){
    boolean result = false;
    ConnectivityState state = channel.getState(false);
    logger.debug("{} ConnectivityState {}", ne, state.toString());
    if (state == ConnectivityState.READY ) {
      result = true;
    }
    return result;
  }

  public void reConnectReStartDataCollection(){
    channel.shutdown();
    createNewChannelAndStub();
    for (SubscriptionList subscriptionList : streamMap.keySet()){
      createStreamForSubscriptionList(subscriptionList);
    }
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

  public void cancelStreamForSubscriptionList(SubscriptionList subscriptionList){
    StreamObserver<SubscribeRequest> stream = streamMap.get(subscriptionList);

    ClientCallStreamObserver clientCallStreamObserver = (ClientCallStreamObserver)stream;
    StatusRuntimeException e = Status.CANCELLED
            .withDescription("Received cancellation request for  " + subscriptionList.toString())
            .augmentDescription("Augment Description Not provided yet")
            .asRuntimeException();
    clientCallStreamObserver.cancel("Received cancellation request for  " + subscriptionList.toString(), e);
    streamMap.remove(subscriptionList);
  }

  public void createStreamForSubscriptionList(SubscriptionList subscriptionList){
    logger.info("{} - addSubscription()",ne);
    // Latch is needed otherwise onNext is never reached
    CountDownLatch latch = new CountDownLatch(1);
    StreamObserver<SubscribeRequest> stream = stub.subscribe(new StreamObserver<SubscribeResponse>() {
      @Override
      public void onNext(SubscribeResponse response) {
        try {
          logger.debug("{} - onNext() - ResponseCase {}", ne, response.getResponseCase());
          //TODO
          // Review with Ziv,
          // parse the response if needed and send to kafka
          // what is the best approach to parse the response
          // (handle multiple values that could come with the use of * in the path request)
          if ( logger.isTraceEnabled() ){
            logger.trace("{} - onNext() - response {}", ne, response);
          }
        }catch (Exception e){
          logger.error("onNext Error: {}", e.getMessage());
        }
      }

      @Override
      public void onError(Throwable t) {
        //Handle case of StatusRuntimeException
        if (t instanceof  io.grpc.StatusRuntimeException){
          //Safe cast
          Status status = ((io.grpc.StatusRuntimeException)t).getStatus();
          //Handle case of cancellation - log at info level
          if (status.getCode() == Status.Code.CANCELLED) {
            logger.info("{} StatusRuntimeException status.code: {} - msg: {}",ne, status.getCode().toString() ,t.getMessage());
          }else{
            logger.error("{} StatusRuntimeException status.code: {} - msg: {}",ne, status.getCode().toString() ,t.getMessage());
          }
        }else{
          logger.error("{} onError Error: {}",ne  ,t.getMessage());
        }
      }

      @Override
      public void onCompleted() {
        logger.info("{} onCompleted() ",ne);
        latch.countDown();
      }
    }
    );

    //TODO
    // Review with Ziv,
    // We use as key the SubscriptionList is it ok ?? dd
    // Should we use an other key ?
    streamMap.put(subscriptionList, stream);
    SubscribeRequest request =SubscribeRequest.newBuilder().setSubscribe(subscriptionList).build();
    stream.onNext(request);
    stream.onCompleted();
    try {
      latch.await(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      logger.info("{} InterruptedException latch.await ",ne, e.getMessage());
    }
  }
}
