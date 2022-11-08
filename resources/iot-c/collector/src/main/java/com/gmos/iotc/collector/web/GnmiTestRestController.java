package com.gmos.iotc.collector.web;

import com.gmos.iotc.collector.service.gnmi.GrpcClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GnmiTestRestController {

  private Logger logger = LoggerFactory.getLogger(GnmiTestRestController.class);
  private final GrpcClientManager grpcClientManager;

  public GnmiTestRestController(GrpcClientManager grpcClientManager) {
    this.grpcClientManager = grpcClientManager;
  }

  @GetMapping("/gnmi/{action}")
  public String gnmi(@PathVariable("action") String action) {
    logger.debug("gnmi action: {}", action);
    switch (action){
      case "start":
        grpcClientManager.startCollectionOfDataFromNEs();
        break;
      case "status":
        grpcClientManager.getConnectionStatesFromAllGrpcClients();
        break;
      case "add-subscription":
        grpcClientManager.addSubscription();
        break;
      case "cancel-streaming":
        grpcClientManager.cancelStreaming();
        break;
      default:
        System.err.println("Action not supported");
    }

    String result = "gnmi action: " + action;
    return result;
  }
}
