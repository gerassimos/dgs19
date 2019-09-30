package com.gmos.iotc.collector.service;


import com.gmos.iotc.collector.domain.DeviceEntity;
import com.gmos.iotc.collector.domain.PerformanceDataEntity;
import com.gmos.iotc.collector.repository.DeviceRepository;
import com.gmos.iotc.collector.repository.PerformanceDataRepository;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;

@Component
public class CollectorScheduler {
  Logger logger = LoggerFactory.getLogger(CollectorScheduler.class);
  private final DeviceRepository deviceRepository;
  private final PerformanceDataRepository performanceDataRepository;

  public CollectorScheduler(DeviceRepository deviceRepository,
                            PerformanceDataRepository performanceDataRepository) {
    this.deviceRepository = deviceRepository;
    this.performanceDataRepository = performanceDataRepository;
    scheduleCollectionTasks();
  }

  private void scheduleCollectionTasks(){

    initDBWithDummyData();
    testDBDummyData();
    try {
      //TODO remove dummy code
      PerformanceDataDTO performanceDataDTO = new PerformanceDataDTO();
      performanceDataDTO.setTemp(69);

      while (true){
        logger.debug("scheduleCollectionTasks...tmp :" + performanceDataDTO.getTemp());
        Thread.sleep(3000);
      }
    }catch (Exception e){
      logger.error(e.getMessage() +" - "+ e.getCause() );
    }
  }

  private void testDBDummyData() {
   List<DeviceEntity> deviceEntityList = (List<DeviceEntity>) deviceRepository.findAll();
   for (DeviceEntity deviceEntity :deviceEntityList){
     logger.debug("testDBDummyData DeviceEntity: " + deviceEntity);
     List<PerformanceDataEntity>  performanceDataEntityList= performanceDataRepository.findByDeviceId(deviceEntity.getId());
     for (PerformanceDataEntity performanceDataEntity : performanceDataEntityList){
       logger.debug("testDBDummyData performanceDataEntity: " + performanceDataEntity);
     }
   }
  }

  // init DB with dummy data
  private void initDBWithDummyData(){
    DeviceEntity deviceEntity = new DeviceEntity();
    deviceEntity.setFriendlyName("device_name_test_1");
    deviceEntity.setType("temperature sensor");
    deviceRepository.save(deviceEntity);
    long nowTimestampLong = System.currentTimeMillis();
    // Add data back in time one hour
    for (int i=0; i < 60 ; i++){
      long timestampLong = nowTimestampLong - (i * 1000l);
      Timestamp timestamp = new Timestamp(timestampLong);
      PerformanceDataEntity performanceData = new PerformanceDataEntity();
      performanceData.setDeviceId(deviceEntity.getId());
      performanceData.setTimestamp(timestamp);
      double randomDouble = 10.0 * Math.random();
      performanceData.setTemperature(randomDouble);
      performanceDataRepository.save(performanceData);
    }

  }

  // collect Performance Data From Devices
  private void collectPerformanceDataFromDevices(){

  }

}
