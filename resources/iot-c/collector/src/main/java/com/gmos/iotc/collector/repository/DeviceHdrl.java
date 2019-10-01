package com.gmos.iotc.collector.repository;

import com.gmos.iotc.collector.domain.DeviceEntity;
import com.gmos.iotc.common.DeviceDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceHdrl {

  private final DeviceRepository deviceRepository;

  public DeviceHdrl(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  public List<DeviceDTO> deviceAll(){
    List<DeviceDTO> result = new ArrayList();
    List<DeviceEntity> deviceEntityList = (List<DeviceEntity>) deviceRepository.findAll();
    for (DeviceEntity deviceEntity : deviceEntityList){
      DeviceDTO deviceDTO = new DeviceDTO();
      deviceDTO.setId(deviceEntity.getId());
      deviceDTO.setFriendlyName(deviceEntity.getFriendlyName());
      deviceDTO.setType(deviceEntity.getType());
    }
    return result;
  }

}
