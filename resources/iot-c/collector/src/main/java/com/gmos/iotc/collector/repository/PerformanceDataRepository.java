package com.gmos.iotc.collector.repository;

import com.gmos.iotc.collector.domain.DeviceEntity;
import com.gmos.iotc.collector.domain.PerformanceDataEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface PerformanceDataRepository extends CrudRepository<PerformanceDataEntity, Long> {

  PerformanceDataEntity findById(long id);
//  List<PerformanceDataEntity> findByTimestampBetweenAndDeviceEntity(Timestamp aTimestamp, Timestamp zTimestamp, DeviceEntity deviceEntity);
  List<PerformanceDataEntity> findByDeviceId(Long deviceId);


}
