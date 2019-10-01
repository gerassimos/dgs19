package com.gmos.iotc.collector.repository;

import com.gmos.iotc.collector.domain.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface  DeviceRepository extends CrudRepository<DeviceEntity, Long> {
  List<DeviceEntity> findByFriendlyName(String friendlyName);
  DeviceEntity findById(long id);

}
