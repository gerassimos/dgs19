package com.gmos.iotc.collectorkafkapg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "iot-collector-kafka-pg")
public class IoTConfig {
  private String key1;
  private String kafkaBootstapServers;
  private String kafkaUsername;
  private String kafkaPassword;
  private int kafkaTopicReplica;
  private int kafkaTopicPartition;

  public String getKafkaBootstapServers() {
    return kafkaBootstapServers;
  }

  public void setKafkaBootstapServers(String kafkaBootstapServers) {
    this.kafkaBootstapServers = kafkaBootstapServers;
  }

  public String getKafkaUsername() {
    return kafkaUsername;
  }

  public void setKafkaUsername(String kafkaUsername) {
    this.kafkaUsername = kafkaUsername;
  }

  public String getKafkaPassword() {
    return kafkaPassword;
  }

  public void setKafkaPassword(String kafkaPassword) {
    this.kafkaPassword = kafkaPassword;
  }

  public int getKafkaTopicReplica() {
    return kafkaTopicReplica;
  }

  public void setKafkaTopicReplica(int kafkaTopicReplica) {
    this.kafkaTopicReplica = kafkaTopicReplica;
  }

  public int getKafkaTopicPartition() {
    return kafkaTopicPartition;
  }

  public void setKafkaTopicPartition(int kafkaTopicPartition) {
    this.kafkaTopicPartition = kafkaTopicPartition;
  }

  public String getKey1() {
    return key1;
  }

  public void setKey1(String key1) {
    this.key1 = key1;
  }
}
