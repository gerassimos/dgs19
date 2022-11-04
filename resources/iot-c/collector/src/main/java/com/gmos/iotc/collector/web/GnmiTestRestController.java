package com.gmos.iotc.collector.web;

import com.gmos.iotc.collector.service.GnmiGrpcClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GnmiTestRestController {

  private Logger logger = LoggerFactory.getLogger(GnmiTestRestController.class);
  private final GnmiGrpcClientManager gnmiGrpcClientManager;

  public GnmiTestRestController(GnmiGrpcClientManager gnmiGrpcClientManager) {
    this.gnmiGrpcClientManager = gnmiGrpcClientManager;
  }

  @GetMapping("/gnmi")
  public String gnmi() {
    logger.debug("Get Request gnmi");
    gnmiGrpcClientManager.startAllClients();
    String result = "gnmi";
    return result;
  }
}
