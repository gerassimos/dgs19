package com.gmos.iotc.collector.web;

import com.gmos.iotc.collector.repository.DeviceHdrl;
import com.gmos.iotc.common.DeviceDTO;
import com.gmos.iotc.common.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeviceRestController {

  private final DeviceHdrl deviceHdrl;
  Logger logger = LoggerFactory.getLogger(DeviceRestController.class);

  public DeviceRestController(DeviceHdrl deviceHdrl) {
    this.deviceHdrl = deviceHdrl;
  }

//  @GetMapping(RestPath.DEVICE_ALL)
//  public List<DeviceDTO> deviceAll() {
//    logger.info("Get Request" + RestPath.DEVICE_ALL);
//    return deviceHdrl.deviceAll();
//  }

  @RequestMapping("/devices")
  public List<DeviceDTO> deviceAll() {
    logger.info("Get Request" + RestPath.DEVICE_ALL);
    return deviceHdrl.deviceAll();
  }
}
