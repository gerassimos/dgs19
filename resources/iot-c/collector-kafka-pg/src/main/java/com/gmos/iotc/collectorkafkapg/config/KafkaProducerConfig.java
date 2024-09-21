package com.gmos.iotc.collectorkafkapg.config;

import com.gmos.iotc.common.PerformanceDataDTO;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

  private final IoTConfig ioTConfig;

  public KafkaProducerConfig(IoTConfig ioTConfig) {
    this.ioTConfig = ioTConfig;
  }

  private Map<String, Object> createCommonConfigProps(){
    Map<String, Object> configProps = new HashMap<>();

    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ioTConfig.getKafkaBootstapServers());

    // serializers
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return configProps;
  }

  private Map<String, Object> createHighThroughputConfigProps(){
    Map<String, Object> configProps = createCommonConfigProps();
    // high throughput producer (at the expense of a bit of latency and CPU usage)
    configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
    configProps.put(ProducerConfig.LINGER_MS_CONFIG,20);
    configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768); // 32 KB batch size
    return configProps;
  }

  // ================================================================
  // kafka Template for SubscribeConfigureSingleTargetDTO messages
  // ================================================================
  @Bean
  public ProducerFactory<String, PerformanceDataDTO> producerFactoryPm() {
    Map<String, Object> properties = createCommonConfigProps();
    properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
    properties.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
    properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
            "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
            ioTConfig.getKafkaUsername(),
            ioTConfig.getKafkaPassword()
    ));
    return new DefaultKafkaProducerFactory<>(properties);
  }

  @Bean
  public KafkaTemplate<String, PerformanceDataDTO> kafkaTemplatePm() {
    return new KafkaTemplate<>(producerFactoryPm());
  }

}
