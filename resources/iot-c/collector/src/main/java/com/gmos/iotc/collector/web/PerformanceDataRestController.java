package com.gmos.iotc.collector.web;

import com.gmos.iotc.collector.repository.DeviceHdrl;
import com.gmos.iotc.common.PerformanceDataDTO;
import com.gmos.iotc.common.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PerformanceDataRestController {
  private final DeviceHdrl deviceHdrl;
  private Logger logger = LoggerFactory.getLogger(DeviceRestController.class);

  public PerformanceDataRestController(DeviceHdrl deviceHdrl) {
    this.deviceHdrl = deviceHdrl;
  }

  @GetMapping(RestPath.DEVICE_PERFORMANCE)
  public List<PerformanceDataDTO> findByDeviceId(@RequestParam long deviceId) {
    logger.info("Get Request" + RestPath.DEVICE_PERFORMANCE + " ");
    return deviceHdrl.findByDeviceId(deviceId);
  }
}
