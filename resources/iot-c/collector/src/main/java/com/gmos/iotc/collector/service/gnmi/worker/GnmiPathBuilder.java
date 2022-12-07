package com.gmos.iotc.collector.service.gnmi.worker;


import com.github.gnmi.proto.Encoding;
import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.SubscriptionMode;
import com.gmos.iotc.common.gnmi.Address;
import com.gmos.iotc.common.gnmi.GnmiEnum;
import com.gmos.iotc.common.gnmi.PathDTO;
import com.gmos.iotc.common.gnmi.SubscribeConfigureDTO;
import com.gmos.iotc.common.gnmi.SubscriptionDTO;
import com.gmos.iotc.common.gnmi.SubscriptionListDTO;
import com.gmos.iotc.common.gnmi.TargetDTO;
import io.micrometer.core.instrument.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GnmiPathBuilder {
  private static String TOKEN_PARTTERN = "/";


  private static Path.Builder getPathBuilder(String gNMIPath){
    // Example path
    // path = "/ptp/instance-list/1/default-ds/clock-quality/clock-class"
    if (!StringUtils.isBlank(gNMIPath)) {
      String[] tokens = gNMIPath.split(TOKEN_PARTTERN);
      Path.Builder pathBuilder = Path.newBuilder();

      for (String token : tokens) {
        if(!StringUtils.isBlank(token)) {
          String elem = null;
          Map<String,String> map = new HashMap<String,String>();
          if(token.contains("[")){
            elem = token.substring(0, token.indexOf("["));
            //TODO maybe has multiply key [a=b][b=c]
            String keyPeers = token.substring(token.indexOf("[") + 1,token.indexOf("]"));
            String[] kpArr = keyPeers.split("=");
            map.put(kpArr[0], kpArr[1]);
          }else{
            elem = token;
          }
          PathElem.Builder elemBuilder = PathElem.newBuilder().setName(elem);
          if(!map.isEmpty()){
            elemBuilder.putKey(map.keySet().toArray()[0].toString(), map.values().toArray()[0].toString());
          }
          pathBuilder.addElem(elemBuilder.build());
        }
      }
      return pathBuilder;
    }
    return null;
  }

  private static Encoding getEncoding(GnmiEnum.Encoding gnmiEnumEncoding){
    return Encoding.forNumber(gnmiEnumEncoding.getNumber());
  }

  private static Subscription getSubscription(String gnmiPath,
                                              int subscriptionMode,
                                              long sampleIntervalNanoSeconds,
                                              String origin,
                                              String target){
//    name = "ssync_ClockClass"
//    origin = "openconfig"
//    path = "/ptp/instance-list/1/default-ds/clock-quality/clock-class"


    Path path = getPathBuilder(gnmiPath)
            .setOrigin(origin)
            .setTarget(target)
            .build();
    Subscription subscription = Subscription.newBuilder()
            .setPath(path)
            .setMode(SubscriptionMode.forNumber(subscriptionMode))
            .setSampleInterval(sampleIntervalNanoSeconds) //ns 1000000000l => 1s
            .build();
    return subscription;
  }
  private static Subscription getSubscriptionFromDTO(SubscriptionDTO subscriptionDTO){
    Subscription result;
    PathDTO pathDTO = subscriptionDTO.getPath();
    String gnmiPath = pathDTO.getPath();
    int subscriptionMode = subscriptionDTO.getSubscriptionMode().ordinal();
    long sampleInterval = subscriptionDTO.getSampleInterval(); //NanoSeconds
    String origin = pathDTO.getTarget();
    String target  = pathDTO.getTarget();
    result = getSubscription(gnmiPath,subscriptionMode,sampleInterval,origin,target);
    return result;
  }

  public static SubscriptionList getSubscriptionList(SubscriptionListDTO subscriptionListDTO){

    SubscriptionList.Builder subscriptionListBuilder = SubscriptionList.newBuilder();

    subscriptionListBuilder.setEncoding(getEncoding(subscriptionListDTO.getEncoding()));
    for (SubscriptionDTO subscriptionDTO : subscriptionListDTO.getSubscriptionList()){
      Subscription subscription = getSubscriptionFromDTO(subscriptionDTO);
      subscriptionListBuilder.addSubscription(subscription);
    }

    String pathPrefix = subscriptionListDTO.getPathPrefix();
    if ( pathPrefix!=null ){
      subscriptionListBuilder.setPrefix(getPathBuilder(pathPrefix));
    }

    //TODO subscriptionListBuilder.setQos();

    return subscriptionListBuilder.build();
  }

  public static SubscriptionListDTO buildExampleSubscriptionListDTO4OFM(){
    SubscriptionListDTO result = new SubscriptionListDTO();
    List<SubscriptionDTO> subscriptionList = new ArrayList<>();
    PathDTO pathDTO = new PathDTO();
    pathDTO.setPath("/ptp/instance-list/1/default-ds/clock-quality/clock-class");
    pathDTO.setOrigin("openconfig");
    pathDTO.setTarget("ssync_ClockClass");
    SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
    subscriptionDTO.setPath(pathDTO);
    subscriptionDTO.setSubscriptionMode(GnmiEnum.SubscriptionMode.sample);
    subscriptionDTO.setSampleInterval(5000000000l);
    subscriptionList.add(subscriptionDTO);
    result.setSubscriptionList(subscriptionList);
    result.setEncoding(GnmiEnum.Encoding.json);
    return result;
  }

  private static List<TargetDTO> getTargetListForLocalSim() {
    List<TargetDTO> result = new ArrayList<>();
    TargetDTO targetDTO = new TargetDTO();
    Map<String,String> tags = new HashMap<>();
    tags.put("key1", "val1");
    tags.put("key2", "val2");
    targetDTO.setTags(tags);
    Address address = new Address();
    address.setName("localhost");
    address.setPort(4567);
    targetDTO.setAddress(address);
    result.add(targetDTO);
    return result;
  }

  public static SubscribeConfigureDTO buildExampleSubscriptionOperationDTO4OFM() {
    SubscribeConfigureDTO result = new SubscribeConfigureDTO();
    SubscriptionListDTO subscriptionListDTO = buildExampleSubscriptionListDTO4OFM();
    result.setSubscriptionListDTO(subscriptionListDTO);
    result.setTargetList(getTargetListForLocalSim());
    result.setName("sub-name-01");
    result.setSubscribeAction(GnmiEnum.SubscribeAction.subscribe);
    return result;
  }



  public static void main(String[] args) {
    //    name = "ssync_ClockClass"
    //    origin = "openconfig"
    //    path = "/ptp/instance-list/1/default-ds/clock-quality/clock-class"

    //    name = "ssync_OFM"
    //    origin = "openconfig"
    //    path = "/ptp/instance-list/1/current-ds/offset-from-master"

// Other paths
//    path = /ptp/instance-list/1/current-ds/offset-from-master
//    path = /ptp/instance-list/1/current-ds/mean-path-delay
//    path = /ptp/instance-list/1/time-properties-ds/time-traceable
//    path = /ptp/instance-list/1/default-ds/clock-quality/clock-class


    SubscriptionListDTO subListDTO4OFM = buildExampleSubscriptionListDTO4OFM();
    SubscriptionList subscriptionListProto = getSubscriptionList(subListDTO4OFM);

    System.out.println("subscriptionList DTO "+subListDTO4OFM.toString());
    System.out.println("subscriptionListProto "+subscriptionListProto);
  }


}
