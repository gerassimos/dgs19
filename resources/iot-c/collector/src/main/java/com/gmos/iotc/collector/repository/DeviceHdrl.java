package com.gmos.iotc.collector.repository;

import com.gmos.iotc.collector.domain.DeviceEntity;
import com.gmos.iotc.collector.domain.PerformanceDataEntity;
import com.gmos.iotc.common.DeviceDTO;
import com.gmos.iotc.common.PerformanceDataDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceHdrl {

  private final DeviceRepository deviceRepository;
  private final PerformanceDataRepository performanceDataRepository;

  public DeviceHdrl(DeviceRepository deviceRepository,
                    PerformanceDataRepository performanceDataRepository) {
    this.deviceRepository = deviceRepository;
    this.performanceDataRepository = performanceDataRepository;
  }

  public List<DeviceDTO> deviceAll(){
    List<DeviceDTO> result = new ArrayList<>();
    List<DeviceEntity> deviceEntityList = (List<DeviceEntity>) deviceRepository.findAll();
    for (DeviceEntity deviceEntity : deviceEntityList){
      DeviceDTO deviceDTO = new DeviceDTO();
      deviceDTO.setId(deviceEntity.getId());
      deviceDTO.setFriendlyName(deviceEntity.getFriendlyName());
      deviceDTO.setType(deviceEntity.getType());
      result.add(deviceDTO);
    }
    return result;
  }

  public List<PerformanceDataDTO> findByDeviceId(long deviceId){
    List<PerformanceDataDTO> result = new ArrayList<>();
    List<PerformanceDataEntity> PerformanceDataEntityList = performanceDataRepository.findByDeviceId(deviceId);
    for (PerformanceDataEntity performanceDataEntity :PerformanceDataEntityList){
      PerformanceDataDTO performanceDataDTO = new PerformanceDataDTO();
      performanceDataDTO.setId(performanceDataEntity.getId());
      performanceDataDTO.setTimestamp(performanceDataEntity.getTimestamp());
      performanceDataDTO.setTemperature(performanceDataEntity.getTemperature());
      performanceDataDTO.setDeviceId(performanceDataEntity.getDeviceId());
      result.add(performanceDataDTO);
    }
    return result;
  }

}
