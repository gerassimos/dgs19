package com.gmos.iotc.collector.web;

import com.gmos.iotc.collector.service.GnmiGrpcClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GnmiTestRestController {

  private Logger logger = LoggerFactory.getLogger(GnmiTestRestController.class);
  private final GnmiGrpcClientManager gnmiGrpcClientManager;

  public GnmiTestRestController(GnmiGrpcClientManager gnmiGrpcClientManager) {
    this.gnmiGrpcClientManager = gnmiGrpcClientManager;
  }

  @GetMapping("/gnmi/{action}")
  public String gnmi(@PathVariable("action") String action) {
    logger.debug("gnmi action: {}", action);
    switch (action){
      case "start":
        gnmiGrpcClientManager.startCollectionOfDataFromNEs();
        break;
      case "status":
        gnmiGrpcClientManager.getConnectionStatesFromAllGrpcClients();
        break;
      case "add-subscription":
        gnmiGrpcClientManager.addSubscription();
        break;
      case "cancel-streaming":
        gnmiGrpcClientManager.cancelStreaming();
        break;
      default:
        System.err.println("Action not supported");
    }

    String result = "gnmi action: " + action;
    return result;
  }
}
