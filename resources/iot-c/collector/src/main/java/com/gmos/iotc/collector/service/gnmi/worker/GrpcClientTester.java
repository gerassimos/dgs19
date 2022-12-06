package com.gmos.iotc.collector.service.gnmi.worker;

import com.github.gnmi.proto.Encoding;
import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.SubscriptionMode;
import com.gmos.iotc.common.gnmi.SubscribeConfigureDTO;
import com.gmos.iotc.common.gnmi.SubscriptionListDTO;
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

  private Map<String, SubscribeGrpcClient> neToGrpcWorkerMap;
  private final Logger logger = LoggerFactory.getLogger(GrpcClientTester.class);

  public GrpcClientTester() {
    this.neToGrpcWorkerMap = new HashMap();
  }


  public void getConnectionStatesFromAllGrpcClients() {
    for (Map.Entry<String, SubscribeGrpcClient> entry : neToGrpcWorkerMap.entrySet()){
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
//    result.add("192.168.56.102:20830");
//    result.add("192.168.56.105:20830");
//    result.add("192.168.56.106:20830");
    result.add("localhost:4567");
    result.add("localhost:4568");
//    result.add("localhost:4569");
    return result;
  }


  public void subscribe(final SubscribeConfigureDTO subscribeConfigureDTO) {
    List<TargetDTO> targetList = subscribeConfigureDTO.getTargetList();
      //TODO store subName name in the worker
      SubscriptionListDTO subscriptionListDTO = subscribeConfigureDTO.getSubscriptionListDTO();
      for (TargetDTO targetDTO: targetList){
        //TODO move logic to worker
        SubscribeGrpcClient grpcClientChannelSubscriptions =
                new SubscribeGrpcClient(targetDTO);
        neToGrpcWorkerMap.put(targetDTO.getAddress().getName()+":"+targetDTO.getAddress().getPort(),
                grpcClientChannelSubscriptions);
        SubscribeConfigureDTO subscribeConfigureDTOPerTarget = new SubscribeConfigureDTO();

        List<TargetDTO> singleTarget = new ArrayList<>(1);
        singleTarget.add(targetDTO);

        subscribeConfigureDTOPerTarget.setTargetList(singleTarget);
        subscribeConfigureDTOPerTarget.setName(subscribeConfigureDTO.getName());
        subscribeConfigureDTOPerTarget.setSubscribeAction(subscribeConfigureDTO.getSubscribeAction());
        subscribeConfigureDTOPerTarget.setTags(subscribeConfigureDTO.getTags());
        subscribeConfigureDTOPerTarget.setSubscriptionListDTO(subscribeConfigureDTO.getSubscriptionListDTO());

        grpcClientChannelSubscriptions.
                createStream(subscribeConfigureDTOPerTarget);

      }
  }
}
