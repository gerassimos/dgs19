package com.gmos.iotc.collectorui.web;


import com.gmos.iotc.collectorui.service.DataHdrl;
import com.gmos.iotc.common.ChartDataDTO;
import com.gmos.iotc.common.PerformanceDataDTO;
import com.gmos.iotc.common.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataRestController {

  private Logger logger = LoggerFactory.getLogger(DataRestController.class);
  private final DataHdrl dataHdrl;

  public DataRestController(DataHdrl dataHdrl) {
    this.dataHdrl = dataHdrl;
  }

  @GetMapping("/getData")
  public List<PerformanceDataDTO> getData() {
    logger.debug("Get Request getData");
    long deviceId=1L;
    List<PerformanceDataDTO> result = dataHdrl.getData(deviceId);
    return result;
  }
}

