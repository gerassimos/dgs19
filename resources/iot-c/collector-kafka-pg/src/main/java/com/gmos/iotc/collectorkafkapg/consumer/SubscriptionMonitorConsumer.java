package com.gmos.iotc.collectorkafkapg.consumer;

import com.gmos.iotc.common.PerformanceDataDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionMonitorConsumer {

  private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


  /**
   * single message listener
   * @param consumerRecords
   */
  @KafkaListener(topics = "iotc-kafka-pg-pm-topic",
          groupId = "collector-kafka-pg-pm-group-01",
          containerFactory = "kafkaListenerContainerFactoryPm")
  public void listen(List<ConsumerRecord<String, PerformanceDataDTO>> consumerRecords) {
    // log consumerRecord size
    logger.trace("Batch size: {}", consumerRecords.size());
    for (ConsumerRecord<?, ?> consumerRecord : consumerRecords) {
      logger.trace("Message - partition: {} key: {}", consumerRecord.partition(), consumerRecord.key());

      //log the value
      logger.info("value: {}", consumerRecord.value());
    }
  }
}
