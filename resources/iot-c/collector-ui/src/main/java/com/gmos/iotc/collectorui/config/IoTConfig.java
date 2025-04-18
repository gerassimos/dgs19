package com.gmos.iotc.collectorui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "iot-collector-ui")
public class IoTConfig {
  private String collectorHostName;
  private int collectorHostPort;
  private String collectoratocHostName;
  private int collectoratocHostPort;

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

  public String getCollectoratocHostName() {
    return collectoratocHostName;
  }

  public void setCollectoratocHostName(String collectoratocHostName) {
    this.collectoratocHostName = collectoratocHostName;
  }

  public int getCollectoratocHostPort() {
    return collectoratocHostPort;
  }

  public void setCollectoratocHostPort(int collectoratocHostPort) {
    this.collectoratocHostPort = collectoratocHostPort;
  }
}
