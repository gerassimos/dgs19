package com.gmos.iotc.collector.service;


import com.gmos.iotc.collector.domain.DeviceEntity;
import com.gmos.iotc.collector.domain.PerformanceDataEntity;
import com.gmos.iotc.collector.repository.DeviceRepository;
import com.gmos.iotc.collector.repository.PerformanceDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Component
public class CollectorScheduler {
  private Logger logger = LoggerFactory.getLogger(CollectorScheduler.class);
  private boolean isCollectionInProgress;
  private final DeviceRepository deviceRepository;
  private final PerformanceDataRepository performanceDataRepository;

  private static final String DEVICE_NAME = "TEMP-HUM sensor";

  public CollectorScheduler(DeviceRepository deviceRepository,
                            PerformanceDataRepository performanceDataRepository) {
    this.deviceRepository = deviceRepository;
    this.performanceDataRepository = performanceDataRepository;
    this.isCollectionInProgress = false;
    initDBWithDummyData();
//    testDBDummyData();

  }

  @Scheduled(cron = "*/10 * * * * *")
  private void scheduleCollectionTasks(){
    if(isCollectionInProgress){
      // Detect overlaps
      // we need to run the entire collection logic on an new thread in order to
      // detect overlap of schedule executions and raise and alarm
      // to detect overlaps:
      // we need to detect the case where the previous scheduled collection is still in progress
      // when the actual (current) scheduled collection starts
      logger.error("Collision detected. Avoid start of new collection");

    }
    else {
      new Thread(new Runnable() {
        @Override
        public void run() {
          runAllCollectorTasksAsync();
        }
      }).start();
    }
  }

  private void runAllCollectorTasksAsync(){
    logger.info("START all collection tasks");
    isCollectionInProgress = true;
    try {
      collectPerfDummy();
      deleteOldPerformanceDataForMaintenance();
    }
    catch (Exception e){
      logger.error("Failed to runAllCollectorTasksAsync " + e.getCause() + " "+ e.getMessage());
    }
    isCollectionInProgress = false;
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
    logger.debug("collecting performance data for "+deviceEntity.getFriendlyName() + " with id: " +deviceEntity.getId());
    long nowTimestampLong = System.currentTimeMillis();
    long scheduleStartTimeMillis = System.currentTimeMillis();
    long scheduleStartTimeMillisRoundedtoSec = 1000 * (scheduleStartTimeMillis  /1000); // rounded to seconds
    Timestamp timestamp = new Timestamp(scheduleStartTimeMillisRoundedtoSec );

    PerformanceDataEntity performanceData = new PerformanceDataEntity();
    performanceData.setDeviceId(deviceEntity.getId());
    performanceData.setTimestamp(timestamp);
    double randomDoubleTemp = 10.0 * Math.random();
    double randomDoubleHum = 15.0 * Math.random();
    performanceData.setTemperature(randomDoubleTemp);
    performanceData.setHumidity(randomDoubleHum);
    performanceDataRepository.save(performanceData);

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
//        long back1Min = now - 1000L *60L;
        long back2Min = now - 1000L *60L*2L;
        Timestamp timestamp = new Timestamp(back2Min);
        long recordDeleted = performanceDataRepository.deleteByDeviceIdAndTimestampIsLessThanEqual(deviceEntity.getId(), timestamp);
        logger.debug("Delete Performance Data for maintenance. Record Deleted:" + recordDeleted);
      }

    }catch (Exception e){
      logger.error("Failed to Delete Old Performance Data For Maintenance: " + e.getCause());
    }
  }

}
