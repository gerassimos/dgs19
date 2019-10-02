package com.gmos.iotc.collector.service;


import com.gmos.iotc.collector.domain.DeviceEntity;
import com.gmos.iotc.collector.domain.PerformanceDataEntity;
import com.gmos.iotc.collector.repository.DeviceRepository;
import com.gmos.iotc.collector.repository.PerformanceDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Component
public class CollectorScheduler {
  private Logger logger = LoggerFactory.getLogger(CollectorScheduler.class);
  private final DeviceRepository deviceRepository;
  private final PerformanceDataRepository performanceDataRepository;

  private static final String DEVICE_NAME = "test device 01";

  public CollectorScheduler(DeviceRepository deviceRepository,
                            PerformanceDataRepository performanceDataRepository) {
    this.deviceRepository = deviceRepository;
    this.performanceDataRepository = performanceDataRepository;
    initDBWithDummyData();
//    testDBDummyData();
    scheduleCollectionTasks();
  }

  private void scheduleCollectionTasks(){

    new Thread(() -> {
      try {
        // START loop
        while (true){
          logger.debug("collecting performance data... :" );
          collectPerfDummy();
          deleteOldPerformanceDataForMaintenance();
          Thread.sleep(60000); // 1 min
        }}catch (Exception e){
        logger.error(e.getMessage() +" - "+ e.getCause() );
      }
    }).start();
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
  @Transactional
  protected void initDBWithDummyData(){
    DeviceEntity deviceEntity ;
    List<DeviceEntity> deviceEntityList = deviceRepository.findByFriendlyName(DEVICE_NAME);
    if (deviceEntityList.size() == 0){
      deviceEntity = new DeviceEntity();
      deviceEntity.setFriendlyName(DEVICE_NAME);
      deviceEntity.setType("temperature sensor");
      deviceRepository.save(deviceEntity);
    }

  }

  private void collectPerfDummy(){
    DeviceEntity deviceEntity = deviceRepository.findByFriendlyName(DEVICE_NAME).get(0);
    long nowTimestampLong = System.currentTimeMillis();
    // Add data back in time one hour
    for (int i=0; i < 60 ; i++){
      long timestampLong = nowTimestampLong - (i * 1000L);
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

  // delete Performance Data for maintenance
  // Data retention
  @Transactional
  protected void deleteOldPerformanceDataForMaintenance(){
    try{
      List<DeviceEntity> deviceEntityList = deviceRepository.findByFriendlyName(DEVICE_NAME);

      if (deviceEntityList.size() > 0){
        DeviceEntity deviceEntity = deviceEntityList.get(0);
        long now = System.currentTimeMillis();
        long back1Min = now - 1000L *60L;
        Timestamp timestamp = new Timestamp(back1Min);
        long recordDeleted = performanceDataRepository.deleteByDeviceIdAndTimestampIsLessThanEqual(deviceEntity.getId(), timestamp);
        logger.debug("Delete Performance Data for maintenance. Record Deleted:" + recordDeleted);
      }

    }catch (Exception e){
      logger.error("Failed to Delete Old Performance Data For Maintenance: " + e.getCause());
    }
  }

}
