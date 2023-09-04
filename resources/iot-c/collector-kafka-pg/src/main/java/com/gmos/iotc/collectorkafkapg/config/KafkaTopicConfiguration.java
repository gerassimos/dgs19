package com.gmos.iotc.collectorkafkapg.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

  // configuration for kafka topic kafka-subscribe-request-topic
  @Bean
  public NewTopic kafkaSubscribeRequestTopic() {
    return TopicBuilder.name("iotc-kafka-pg-pm-topic")
            .partitions(3)
            .replicas(1)
            .build();
  }

}
