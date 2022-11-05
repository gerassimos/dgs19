package com.gmos.iotc.collector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "iot-collector")
public class IotcConfig {
  private boolean collectorSchedulerEnabled;

  public boolean isCollectorSchedulerEnabled() {
    return collectorSchedulerEnabled;
  }

  public void setCollectorSchedulerEnabled(boolean collectorSchedulerEnabled) {
    this.collectorSchedulerEnabled = collectorSchedulerEnabled;
  }
}
