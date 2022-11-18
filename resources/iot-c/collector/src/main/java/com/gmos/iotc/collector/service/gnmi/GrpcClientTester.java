package com.gmos.iotc.collector.service.gnmi;

import com.github.gnmi.proto.Encoding;
import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.SubscriptionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// NOTE
//
@Component
public class GrpcClientTester {

  private Map<String, GrpcClientChannelSubscriptions> neToGrpcWorkerMap;
  private final Logger logger = LoggerFactory.getLogger(GrpcClientTester.class);

  public GrpcClientTester() {
    this.neToGrpcWorkerMap = new HashMap();
  }

  public void startCollectionOfDataFromNEs(){
    List<String> listNEs = getAllNEs();
    for (String ne : listNEs){
      GrpcClientChannelSubscriptions gnmiGrpcClientWorker = new GrpcClientChannelSubscriptions(ne);
      neToGrpcWorkerMap.put(ne,gnmiGrpcClientWorker);
      //TODO
//      gnmiGrpcClientWorker.getDataOverGnmiSubscribeStream();
    }
  }

  public void getConnectionStatesFromAllGrpcClients() {
    for (Map.Entry<String, GrpcClientChannelSubscriptions> entry : neToGrpcWorkerMap.entrySet()){
      String ne = entry.getKey();
      GrpcClientChannelSubscriptions grpcClientWorker = entry.getValue();

      boolean isConnected = grpcClientWorker.isConnected();
      if (isConnected){
//        logger.info("{} is connected", ne);
      }else {
        logger.info("{} is NOT connected. try to reconnect and restart data collection", ne);
        //Try to reconnect
        grpcClientWorker.reConnectReStartDataCollection();
      }
    }
  }

  public void addSubscription(){
    for (Map.Entry<String, GrpcClientChannelSubscriptions> entry : neToGrpcWorkerMap.entrySet()){
      String ne = entry.getKey();
      GrpcClientChannelSubscriptions grpcClient = entry.getValue();
      SubscriptionList list = getSubscriptionListForTest();
      try{ grpcClient.createStreamForSubscriptionList(list); }
      catch (Exception e ){ logger.error("Failed to add subscription {}",e.getMessage());}
    }
  }

  public void cancelStreaming(){
    for (Map.Entry<String, GrpcClientChannelSubscriptions> entry : neToGrpcWorkerMap.entrySet()){
      String ne = entry.getKey();
      GrpcClientChannelSubscriptions grpcClient = entry.getValue();
      SubscriptionList subscriptionList = getSubscriptionListForTest();
      try{ grpcClient.cancelStreamForSubscriptionList(subscriptionList); }
      catch (Exception e ){ logger.error("Failed to cancelStreaming {}",e.getMessage());}
    }
  }

  private SubscriptionList getSubscriptionListForTest(){
    return SubscriptionList.newBuilder()
            .addSubscription(getSubscriptionForOffsetFromMaster())
            .addSubscription(getSubscriptionForClockClass())
            .setEncoding(Encoding.JSON)
            .build();
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

  private List<String> getAllNEs(){
    List<String> result = new ArrayList<String>();
    result.add("192.168.56.102:20830");
//    result.add("192.168.56.105:20830");
//    result.add("192.168.56.106:20830");
//    result.add("localhost:4567");
//    result.add("localhost:4568");
//    result.add("localhost:4569");
    return result;
  }



}
