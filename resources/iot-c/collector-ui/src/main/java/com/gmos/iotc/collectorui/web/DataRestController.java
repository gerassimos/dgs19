package com.gmos.iotc.collectorui.web;


import com.gmos.iotc.collectorui.service.DataHdrl;
import com.gmos.iotc.collectorui.service.DataHdrlGrpcClient;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataRestController {

  private Logger logger = LoggerFactory.getLogger(DataRestController.class);
  private final DataHdrl dataHdrl;
  private final DataHdrlGrpcClient dataHdrlGrpcClient;

  public DataRestController(DataHdrl dataHdrl, DataHdrlGrpcClient dataHdrlGrpcClient) {
    this.dataHdrl = dataHdrl;
    this.dataHdrlGrpcClient = dataHdrlGrpcClient;
  }

  @GetMapping("/getData")
  public List<PerformanceDataDTO> getData() {
    logger.debug("Get Request getData");
    long deviceId=1L;
    List<PerformanceDataDTO> result = dataHdrl.getData(deviceId);
    return result;
  }

  @GetMapping("/grpc-greet-mitge")
  public String grpcGreet() {
    logger.debug("Get Request grpcGreet");
    String result = dataHdrlGrpcClient.doGreet("mitge");
    return result;
  }
}

