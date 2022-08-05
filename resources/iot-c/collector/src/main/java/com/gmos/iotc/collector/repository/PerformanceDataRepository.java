package com.gmos.iotc.collector.repository;

import com.gmos.iotc.collector.domain.PerformanceDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface PerformanceDataRepository extends CrudRepository<PerformanceDataEntity, Long> {

  PerformanceDataEntity findById(long id);
//  List<PerformanceDataEntity> findByTimestampBetweenAndDeviceEntity(Timestamp aTimestamp, Timestamp zTimestamp, DeviceEntity deviceEntity);
  List<PerformanceDataEntity> findByDeviceId(Long deviceId);
  List<PerformanceDataEntity> findByTimestampBetweenAndDeviceId(Timestamp startTimestamp, Timestamp endTimestamp, Long deviceId);

  long deleteByDeviceIdAndTimestampIsLessThanEqual(Long deviceId, Timestamp timestamp);


}
