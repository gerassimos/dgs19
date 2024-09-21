package com.gmos.iotc.collectorkafkapg.config;

import com.gmos.iotc.common.PerformanceDataDTO;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  private final IoTConfig ioTConfig;

  private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  public KafkaConsumerConfig(IoTConfig ioTConfig) {
    this.ioTConfig = ioTConfig;

  }

  private Map<String, Object> createCommonConfigProps(){
    Map<String, Object> commonProperties = new HashMap<>();
    commonProperties.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ioTConfig.getKafkaBootstapServers());
    commonProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    commonProperties.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    commonProperties.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    return commonProperties;
  }


  // =================================================
  // kafka Consumer setting for Pm
  // =================================================

  @Bean
  public ConsumerFactory<String, PerformanceDataDTO> consumerFactoryPm() {
    Map<String, Object> properties = createCommonConfigProps();
    properties.put( ConsumerConfig.GROUP_ID_CONFIG, "collector-kafka-pg-pm-group");

    properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
    properties.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
    properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
            "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
            ioTConfig.getKafkaUsername(),
            ioTConfig.getKafkaUsername()
    ));

    return new DefaultKafkaConsumerFactory<>(
            properties,
            new StringDeserializer(),
            new JsonDeserializer<>(PerformanceDataDTO.class)
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, PerformanceDataDTO> kafkaListenerContainerFactoryPm() {
    ConcurrentKafkaListenerContainerFactory<String, PerformanceDataDTO> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactoryPm());
    factory.setBatchListener(true);
    // Concurrency controls the number of threads that the KafkaListenerContainer uses to consume messages, \
    // But it does not directly affect the number of consumers in the consumer group
    // factory.setConcurrency(3);
    return factory;
  }



}

