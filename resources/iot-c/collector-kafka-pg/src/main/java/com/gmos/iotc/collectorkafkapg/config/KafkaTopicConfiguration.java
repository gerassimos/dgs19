package com.gmos.iotc.collectorkafkapg.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

  private final IoTConfig ioTConfig;

  public KafkaTopicConfiguration(IoTConfig ioTConfig) {
    this.ioTConfig = ioTConfig;
  }

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, ioTConfig.getKafkaBootstapServers());
    return new KafkaAdmin(configs);
  }

  // configuration for kafka topic kafka-subscribe-request-topic
  @Bean
  public NewTopic kafkaSubscribeRequestTopic() {
    return TopicBuilder.name("iotc-kafka-pg-pm-topic")
            .partitions(3)
            .replicas(1)
            .build();
  }

}
