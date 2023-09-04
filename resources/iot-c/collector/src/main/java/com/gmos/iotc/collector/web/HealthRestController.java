package com.gmos.iotc.collector.web;

import com.gmos.iotc.collector.repository.DeviceHdrl;
import com.gmos.iotc.common.DeviceDTO;
import com.gmos.iotc.common.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HealthRestController {

  private final DeviceHdrl deviceHdrl;
  private Logger logger = LoggerFactory.getLogger(HealthRestController.class);

  public HealthRestController(DeviceHdrl deviceHdrl) {
    this.deviceHdrl = deviceHdrl;
  }

  @GetMapping(RestPath.HEALTHZ)
  public String deviceAll() {
    logger.debug("Get Request" + RestPath.HEALTHZ);
    return "OK";
  }


}
