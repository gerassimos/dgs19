package com.gmos.iotc.collectorui.web;


import com.gmos.iotc.collectorui.service.DataHdrl;
import com.gmos.iotc.collectorui.service.DataHdrlGrpcClient;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
  private final DataHdrlGrpcClient dataHdrlGrpcClient;

  public DataRestController(DataHdrl dataHdrl, DataHdrlGrpcClient dataHdrlGrpcClient) {
    this.dataHdrl = dataHdrl;
    this.dataHdrlGrpcClient = dataHdrlGrpcClient;
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
    logHeaders(headers);
    result.put("helloMessage", helloMessage);
    return result;
  }

  @GetMapping("/getData")
  public List<PerformanceDataDTO> getData(@RequestHeader Map<String, String> headers) {
    logger.debug("Get Request getData");
    logHeaders(headers);
    long deviceId=1L;
    List<PerformanceDataDTO> result = dataHdrl.getData(deviceId);
    return result;
  }

  @GetMapping("/grpc-stream-perf-data")
  public void grpcStreamPerfData() {
    logger.debug("Get Request grpcStreamPerfData");
    long deviceId=1L;
    dataHdrlGrpcClient.grpcStreamPerfData(deviceId);
  }

  @GetMapping("/grpc-greet-mitge")
  public String grpcGreet() {
    logger.debug("Get Request grpcGreet");
    String result = dataHdrlGrpcClient.doGreet("mitge");
    return result;
  }

  private void logHeaders(Map<String, String> headers){
    headers.forEach((key, value) -> {
      logger.info(String.format("Header '%s' = %s", key, value));
    });
  }

}

