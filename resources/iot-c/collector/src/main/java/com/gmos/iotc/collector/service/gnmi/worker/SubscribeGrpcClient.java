package com.gmos.iotc.collector.service.gnmi.worker;

import com.github.gnmi.proto.SubscribeRequest;
import com.github.gnmi.proto.SubscribeResponse;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.gNMIGrpc;
import com.github.gnmi.proto.gNMIGrpc.gNMIStub;
import com.gmos.iotc.common.gnmi.SubscribeConfigureDTO;
import com.gmos.iotc.common.gnmi.TargetDTO;
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

public class SubscribeGrpcClient {

  private final Map<String, StreamObserver<SubscribeRequest> > streamMap;
  private final Map<String, SubscribeConfigureDTO> subscriptionMap;

  private gNMIStub stub;
  private ManagedChannel channel;
  private final TargetDTO targetDTO;
  private final String ne;
  private final Logger logger = LoggerFactory.getLogger(SubscribeGrpcClient.class);

  public SubscribeGrpcClient(TargetDTO targetDTO) {
    this.targetDTO = targetDTO;
    this.ne = targetDTO.getAddress().toConnectionString();
    this.streamMap = new HashMap<>();
    this.subscriptionMap = new HashMap<>();
    createNewChannelAndStub();
    notifyTargetStateChanged();
    logger.debug("{} - SubscribeGrpcClient created");
  }

  public boolean isTargetConnected(){
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
    for (SubscribeConfigureDTO subscribeConfigureDTO : subscriptionMap.values()){
      createStream(subscribeConfigureDTO);
    }
  }

  private void notifyTargetStateChanged(){
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
    logger.info("{} - Creating new ChannelAndStub", targetDTO.getAddress());
    this.channel = ManagedChannelBuilder.forAddress(
            targetDTO.getAddress().getName(),
            targetDTO.getAddress().getPort()).
            usePlaintext().build();
    this.stub = gNMIGrpc.newStub(channel);
  }

  public void cancelStream(String subscriptionRequestName){
    StreamObserver<SubscribeRequest> stream = streamMap.get(subscriptionRequestName);
    ClientCallStreamObserver clientCallStreamObserver = (ClientCallStreamObserver)stream;
    StatusRuntimeException e = Status.CANCELLED
            .withDescription("Received cancellation request for  " + subscriptionRequestName)
            .augmentDescription("Augment Description Not provided yet")
            .asRuntimeException();
    clientCallStreamObserver.cancel("Received cancellation request for  " + subscriptionRequestName, e);
    streamMap.remove(subscriptionRequestName);
    subscriptionMap.remove(subscriptionRequestName);
  }

  public void createStream(SubscribeConfigureDTO subscribeConfigureDTO){
    logger.info("{} - addSubscription()",ne);
    // Latch is needed otherwise onNext is never reached
    CountDownLatch latch = new CountDownLatch(1);
    StreamObserver<SubscribeRequest> stream = stub.subscribe(new StreamObserver<SubscribeResponse>() {
      @Override
      public void onNext(SubscribeResponse response) {
        try {
          Map<String, String> totalTagsToApply = new HashMap<>();
          Map<String, String> globalTags = subscribeConfigureDTO.getTags();
          // NOTE
          // At this point is safe to assume that subscribeConfigureDTO.targetList will always contain on target
          Map<String, String> targetLevelTagsPerSubscribeRequest = subscribeConfigureDTO.getTargetList().get(0).getTags();
          if ( globalTags != null ){ totalTagsToApply.putAll( globalTags ); }
          if ( targetLevelTagsPerSubscribeRequest != null) { totalTagsToApply.putAll( targetLevelTagsPerSubscribeRequest ); }

          logger.debug("{} - onNext() - ResponseCase {} tags: {}" , ne, response.getResponseCase() , totalTagsToApply);
          //TODO
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

    String subscriptionRequestName = subscribeConfigureDTO.getName();
    streamMap.put(subscriptionRequestName, stream);
    subscriptionMap.put(subscriptionRequestName, subscribeConfigureDTO);
    SubscriptionList subscriptionList = GnmiPathBuilder.getSubscriptionList(subscribeConfigureDTO.getSubscriptionListDTO()) ;
    SubscribeRequest request =SubscribeRequest.newBuilder().setSubscribe(subscriptionList).build();
    stream.onNext(request);
    stream.onCompleted();

    try {
      latch.await(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      logger.info("{} InterruptedException latch.await ",ne, e.getMessage());
    }
  }

  public void disconect() {
    channel.shutdown();
  }

  public boolean isAnyStream() {
    return streamMap.size() > 0;
  }

  public boolean streamExist(String name) {
    return streamMap.get(name) !=null;
  }

  public void validateSubscriptionRequest(SubscribeConfigureDTO subscribeConfigureDTOPerTarget) {
    //TODO
    // check if there is any other Subscription with the same path
  }
}
