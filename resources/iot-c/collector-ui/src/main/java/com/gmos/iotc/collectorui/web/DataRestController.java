package com.gmos.iotc.collectorui.web;


import com.gmos.iotc.collectorui.service.DataHdrl;
import com.gmos.iotc.collectorui.service.DataHdrlGrpcClient;
import com.gmos.iotc.collectorui.service.GnmiHdrlGrpcClient;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
public class DataRestController {

  private Logger logger = LoggerFactory.getLogger(DataRestController.class);
  private final DataHdrl dataHdrl;
  private final DataHdrlGrpcClient dataHdrlGrpcClient;
  private final GnmiHdrlGrpcClient gnmiHdrlGrpcClient;

  public DataRestController(DataHdrl dataHdrl, DataHdrlGrpcClient dataHdrlGrpcClient,
                            GnmiHdrlGrpcClient gnmiHdrlGrpcClient) {
    this.dataHdrl = dataHdrl;
    this.dataHdrlGrpcClient = dataHdrlGrpcClient;
    this.gnmiHdrlGrpcClient = gnmiHdrlGrpcClient;
  }

  @GetMapping("/hello")
  public String hello() {
    String host = "UnknownHost";
    try{
      host = InetAddress.getLocalHost().getHostName();
    }
    catch (UnknownHostException e ){
      logger.error("Failed to get hostname: {}", e.getMessage());
    }
    logger.info("Hello from {}", host);
    return "Hello from " + host;
  }

  @GetMapping("/getData")
  public List<PerformanceDataDTO> getData() {
    logger.debug("Get Request getData");
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

  @GetMapping("/gnmi")
  public String gnmi() {
    logger.debug("Get Request gnmi");
    String result = gnmiHdrlGrpcClient.doGnmi();
    return result;
  }
}

