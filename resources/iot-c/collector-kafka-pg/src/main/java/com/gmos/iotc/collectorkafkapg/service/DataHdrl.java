package com.gmos.iotc.collectorkafkapg.service;

import com.gmos.iotc.collectorkafkapg.config.IoTConfig;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Component
public class DataHdrl {

  private final RestTemplate restTemplate;
  private Logger logger = LoggerFactory.getLogger(DataHdrl.class);
  private final IoTConfig ioTConfig;

  public DataHdrl(RestTemplateBuilder restTemplateBuilder,IoTConfig ioTConfig) {
    this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(10)).build();
    this.ioTConfig = ioTConfig;
  }

  public List<PerformanceDataDTO> getData(long deviceId){
    return null;
  }
}
