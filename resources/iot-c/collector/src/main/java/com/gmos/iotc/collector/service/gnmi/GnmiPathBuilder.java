package com.gmos.iotc.collector.service.gnmi;


import com.github.gnmi.proto.Encoding;
import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.SubscriptionMode;
import com.gmos.iotc.common.gnmi.PathDTO;
import com.gmos.iotc.common.gnmi.SubscriptionDTO;
import com.gmos.iotc.common.gnmi.SubscriptionListDTO;
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

  public static SubscriptionList getSubscriptionList(SubscriptionDTO subscriptionDTO){

    SubscriptionList.Builder subscriptionListBuilder = SubscriptionList.newBuilder();
// TODO
//    for (String path :subscriptionDTO.getGnmiPathList()){
//      subscriptionListBuilder.addSubscription( getSubscription(
//              path,
//              subscriptionDTO.getSubscriptionMode(),
//              subscriptionDTO.getSampleIntervalNanoSeconds(),
//              subscriptionDTO.getOrigin(),
//              subscriptionDTO.getTarget() )
//      );
//    }
//    subscriptionListBuilder.setEncoding(Encoding.forNumber(subscriptionDTO.getEncoding()));

    return subscriptionListBuilder.build();
  }

  public static void main(String[] args) {
    //    name = "ssync_ClockClass"
    //    origin = "openconfig"
    //    path = "/ptp/instance-list/1/default-ds/clock-quality/clock-class"

    //    name = "ssync_OFM"
    //    origin = "openconfig"
    //    path = "/ptp/instance-list/1/current-ds/offset-from-master"


    List<String> gnmiPathList = new ArrayList<>();
    gnmiPathList.add("/ptp/instance-list/1/default-ds/clock-quality/clock-class");
    gnmiPathList.add("/ptp/instance-list/1/current-ds/offset-from-master");


    PathDTO pathDTO = new PathDTO();
    pathDTO.setPath("/ptp/instance-list/1/default-ds/clock-quality/clock-class");
    pathDTO.setOrigin("openconfig");
    pathDTO.setTarget("ssync_ClockClass");
    SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
    subscriptionDTO.setPath(pathDTO);
    subscriptionDTO.setMode(2); //SubscriptionMode.SAMPLE = 2
    subscriptionDTO.setSampleInterval(5000000000l);
    List<SubscriptionDTO> subscriptionDTOList = new ArrayList<>();


  }
}
