package com.gmos.iotc.collector.service.gnmi.worker;

import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionMode;
import com.gmos.iotc.common.gnmi.GnmiEnum;
import com.gmos.iotc.common.gnmi.SubscribeConfigureDTO;
import com.gmos.iotc.common.gnmi.TargetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// NOTE
// ONLY FOR DEV
@ConditionalOnExpression("${iot-collector.dev-mode-enabled:true}")
@Component
public class GrpcClientTester {

  private final Map<String, SubscribeGrpcClient> targetToGrpcClientMap;
  private final Logger logger = LoggerFactory.getLogger(GrpcClientTester.class);

  public GrpcClientTester() {
    this.targetToGrpcClientMap = new HashMap<>();
  }


  public void getConnectionStatesFromAllGrpcClients() {
    for (Map.Entry<String, SubscribeGrpcClient> entry : targetToGrpcClientMap.entrySet()){
      String ne = entry.getKey();
      SubscribeGrpcClient grpcClientWorker = entry.getValue();

      boolean isConnected = grpcClientWorker.isTargetConnected();
      if (isConnected){
//        logger.info("{} is connected", ne);
      }else {
        logger.info("{} is NOT connected. try to reconnect and restart data collection", ne);
        //Try to reconnect
        grpcClientWorker.reConnectReStartDataCollection();
      }
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
            .setSampleInterval(5000000000l) //ns 1000000000l => 1s
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
            .setSampleInterval(5000000000l) //ns 1000000000l => 1s
            .build();
    return subscription;
  }


  public void subscribeConfigure(final SubscribeConfigureDTO subscribeConfigureDTO) {
    GnmiEnum.SubscribeAction subscribeAction = subscribeConfigureDTO.getSubscribeAction();

    switch (subscribeAction){
      case subscribe: {
        subscribe(subscribeConfigureDTO);
        break;
      }
      case unsubscribe: {
        unsubscribe(subscribeConfigureDTO);
        break;
      }
      case unsubscribeAll: {
        unsubscribeAll(subscribeConfigureDTO);
        break;
      }
      default:{
        logger.info("No action found for {}", subscribeAction);
      }
    }

  }

  private void unsubscribe(SubscribeConfigureDTO subscribeConfigureDTO) {
    List<TargetDTO> targetList = subscribeConfigureDTO.getTargetList();
    for (TargetDTO targetDTO: targetList){
      String targetAddressStr =  targetDTO.getAddress().toConnectionString();
      SubscribeGrpcClient subscribeGrpcClient = targetToGrpcClientMap.get(targetAddressStr);
      if ( subscribeGrpcClient == null ){
        logger.warn("{} - Subscribe Grpc Client not found", targetAddressStr);
        return;
      }
      subscribeGrpcClient.cancelStream(subscribeConfigureDTO.getName());
      if ( !subscribeGrpcClient.isAnyStream() ) {
        //Here if there is not any stream in progress
        logger.info("{} - No other streams exist - disconnect and delete client", targetAddressStr);
        subscribeGrpcClient.disconect();
        targetToGrpcClientMap.remove(targetAddressStr);
      };
    }
  }

  private void unsubscribeAll(SubscribeConfigureDTO subscribeConfigureDTO) {
  }

  private void subscribe(SubscribeConfigureDTO subscribeConfigureDTO) {
    String name = subscribeConfigureDTO.getName();
    List<TargetDTO> targetList = subscribeConfigureDTO.getTargetList();
    for (TargetDTO targetDTO: targetList){
      String targetAddressStr =  targetDTO.getAddress().toConnectionString();
      SubscribeGrpcClient subscribeGrpcClient = targetToGrpcClientMap.get(targetAddressStr);
      //create grpc client if does not already exist
      if ( subscribeGrpcClient != null ){
        logger.debug("{} - Using existing SubscribeGrpcClient", targetAddressStr);
      }else {
        subscribeGrpcClient = new SubscribeGrpcClient(targetDTO);
        targetToGrpcClientMap.put(targetAddressStr, subscribeGrpcClient);
      }

      //Ignore creation of stream if already exist
      if ( subscribeGrpcClient.streamExist(name) ) {
        logger.warn("{} - {} stream already exists", targetAddressStr, name);
        return;
      }

      //create stream for Subscription Request
      SubscribeConfigureDTO subscribeConfigureDTOPerTarget = new SubscribeConfigureDTO();
      List<TargetDTO> singleTarget = new ArrayList<>(1);
      singleTarget.add(targetDTO);
      subscribeConfigureDTOPerTarget.setTargetList(singleTarget);
      subscribeConfigureDTOPerTarget.setName(name);
      subscribeConfigureDTOPerTarget.setTags(subscribeConfigureDTO.getTags());
      subscribeConfigureDTOPerTarget.setSubscriptionListDTO(subscribeConfigureDTO.getSubscriptionListDTO());
      subscribeGrpcClient.validateSubscriptionRequest(subscribeConfigureDTOPerTarget);
      subscribeGrpcClient.createStream(subscribeConfigureDTOPerTarget);
    }
  }


}
