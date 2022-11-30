package com.gmos.iotc.collectoratoc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "iot-collector-a-to-c")
public class IoTConfig {
  private String collectorHostName;
  private int collectorHostPort;

  public String getCollectorHostName() {
    return collectorHostName;
  }

  public void setCollectorHostName(String collectorHostName) {
    this.collectorHostName = collectorHostName;
  }

  public int getCollectorHostPort() {
    return collectorHostPort;
  }

  public void setCollectorHostPort(int collectorHostPort) {
    this.collectorHostPort = collectorHostPort;
  }
}
