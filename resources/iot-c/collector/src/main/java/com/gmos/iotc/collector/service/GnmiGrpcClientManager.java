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

  private Map<String, GnmiGrpcClientWorker> neToGrpcWorkerMap;
  private Logger logger;

  public GnmiGrpcClientManager() {
    this.neToGrpcWorkerMap = new HashMap<String, GnmiGrpcClientWorker>();
    this.logger = LoggerFactory.getLogger(GnmiGrpcClientManager.class);
  }

  public void startCollectionOfDataFromNEs(){
    List<String> listNEs = getAllNEs();
    for (String ne : listNEs){
      GnmiGrpcClientWorker  gnmiGrpcClientWorker = new GnmiGrpcClientWorker(ne);
      neToGrpcWorkerMap.put(ne,gnmiGrpcClientWorker);
      gnmiGrpcClientWorker.getDataOverGnmiSubscribeStream();
    }
  }

  public void getConnectionStatusFromAllGrpcClients() {
    for (String ne : getAllNEs()){
      boolean isTerminated = neToGrpcWorkerMap.get(ne).isTerminated();
      if (isTerminated){
        logger.info("{} is NOT connected", ne);
      }else {
        logger.info("{} is connected", ne);
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
