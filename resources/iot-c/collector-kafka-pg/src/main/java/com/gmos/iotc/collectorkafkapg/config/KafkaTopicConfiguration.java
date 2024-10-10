package com.gmos.iotc.collectorkafkapg.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.config.SaslConfigs;
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
    String kafkaAuthentication = ioTConfig.getKafkaAuthentication();
    if ( kafkaAuthentication != "" || kafkaAuthentication != " "  ){
      //TODO refactor move logic to common method
      configs.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaAuthentication);
      configs.put(SaslConfigs.SASL_MECHANISM, ioTConfig.getKafkaSaslMechanism());
      configs.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
              "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
              ioTConfig.getKafkaUsername(),
              ioTConfig.getKafkaPassword()
      ));
    }

    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, ioTConfig.getKafkaBootstapServers());
    return new KafkaAdmin(configs);
  }

  // configuration for kafka topic kafka-subscribe-request-topic
  @Bean
  public KafkaAdmin.NewTopics createTopics() {
    return new KafkaAdmin.NewTopics(
            TopicBuilder.name("iotc-kafka-pg-pm-topic")
                    .partitions(ioTConfig.getKafkaTopicPartition())
                    .replicas(ioTConfig.getKafkaTopicReplica())
                    .build()
//                ,
//                TopicBuilder.name(multilingualGreetingsTopic)
//                        .partitions(defaultPartitions)
//                        .replicas(defaultReplicas)
//                        .build()
    );
  }


}
