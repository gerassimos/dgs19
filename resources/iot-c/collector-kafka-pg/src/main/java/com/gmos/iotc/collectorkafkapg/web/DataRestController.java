package com.gmos.iotc.collectorkafkapg.web;

import com.gmos.iotc.collectorkafkapg.service.DataHdrl;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataRestController {

  private Logger logger = LoggerFactory.getLogger(DataRestController.class);
  private final DataHdrl dataHdrl;


  public DataRestController(DataHdrl dataHdrl) {
    this.dataHdrl = dataHdrl;
  }

  @GetMapping("/hello")
  public Map<String, String> hello(@RequestHeader Map<String, String> headers) {
    Map<String, String> result = new HashMap<>();
    String host = "UnknownHost";
    try{
      host = InetAddress.getLocalHost().getHostName();
    }
    catch (UnknownHostException e ){
      logger.error("Failed to get hostname: {}", e.getMessage());
    }
    String helloMessage = "Hello from "+host;
    logger.info(helloMessage);
    result.put("helloMessage", helloMessage);
    return result;
  }


}

