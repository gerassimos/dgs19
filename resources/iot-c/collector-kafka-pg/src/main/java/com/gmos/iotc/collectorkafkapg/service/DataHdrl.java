package com.gmos.iotc.collectorkafkapg.service;

import com.gmos.iotc.collectorkafkapg.config.IoTConfig;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@Component
public class DataHdrl {

  private final RestTemplate restTemplate;
  private Logger logger = LoggerFactory.getLogger(DataHdrl.class);
  private final IoTConfig ioTConfig;
  private final KafkaTemplate<String, PerformanceDataDTO> kafkaTemplatePm;


  public DataHdrl(RestTemplateBuilder restTemplateBuilder, IoTConfig ioTConfig,
                  KafkaTemplate<String, PerformanceDataDTO> kafkaTemplatePm) {
    this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(10)).build();
    this.ioTConfig = ioTConfig;
    this.kafkaTemplatePm = kafkaTemplatePm;
  }

  public List<PerformanceDataDTO> getData(long deviceId){
    return null;
  }

  public void testKafka() {
    PerformanceDataDTO performanceDataDTO = new PerformanceDataDTO();
    performanceDataDTO.setDeviceId(1L);
    performanceDataDTO.setTemperature(69.0);
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    performanceDataDTO.setTimestamp(timestamp);
    kafkaTemplatePm.send("iotc-kafka-pg-pm-topic", performanceDataDTO);
  }

  public String testEnv()  {
    logger.info("iot-collector-kafka-pg.kafka-bootstap-servers: {}", ioTConfig.getKafkaBootstapServers());
    return ioTConfig.getKey1();
  }
}
