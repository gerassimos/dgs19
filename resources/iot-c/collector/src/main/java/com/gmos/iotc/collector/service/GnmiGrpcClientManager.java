package com.gmos.iotc.collector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GnmiGrpcClientManager {

  private Map<String, GrpcClientChannelSubscriptions> neToGrpcWorkerMap;
  private final Logger logger = LoggerFactory.getLogger(GnmiGrpcClientManager.class);

  public GnmiGrpcClientManager() {
    this.neToGrpcWorkerMap = new HashMap();
  }

  public void startCollectionOfDataFromNEs(){
    List<String> listNEs = getAllNEs();
    for (String ne : listNEs){
      GrpcClientChannelSubscriptions gnmiGrpcClientWorker = new GrpcClientChannelSubscriptions(ne);
      neToGrpcWorkerMap.put(ne,gnmiGrpcClientWorker);
      gnmiGrpcClientWorker.getDataOverGnmiSubscribeStream();
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

  private List<String> getAllNEs(){
    List<String> result = new ArrayList<String>();
//    listNEs.add("192.168.56.102:20830");
//    listNEs.add("192.168.56.105:20830");
//    listNEs.add("192.168.56.106:20830");
    result.add("localhost:4567");
    result.add("localhost:4568");
    result.add("localhost:4569");
    return result;
  }



}
