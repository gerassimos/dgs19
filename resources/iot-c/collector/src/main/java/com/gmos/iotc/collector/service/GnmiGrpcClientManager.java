package com.gmos.iotc.collector.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GnmiGrpcClientManager {

  public void startAllClients(){
    List<String> listNEs = new ArrayList<String>();
//    listNEs.add("192.168.56.102:20830");
//    listNEs.add("192.168.56.105:20830");
//    listNEs.add("192.168.56.106:20830");
    listNEs.add("localhost:4567");
    listNEs.add("localhost:4568");
    listNEs.add("localhost:4569");

    for (String ne : listNEs){
      GnmiGrpcClientWorker  gnmiGrpcClientWorker = new GnmiGrpcClientWorker(ne);
      gnmiGrpcClientWorker.getDataOverGnmiSubscribeStream();
    }
  }

}
