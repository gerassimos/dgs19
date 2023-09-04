package com.gmos.iotc.collectorkafkapg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "iot-collector-kafka-pg")
public class IoTConfig {
  private String kafkaBootstapServers;

  public String getKafkaBootstapServers() {
    return kafkaBootstapServers;
  }

  public void setKafkaBootstapServers(String kafkaBootstapServers) {
    this.kafkaBootstapServers = kafkaBootstapServers;
  }
}
