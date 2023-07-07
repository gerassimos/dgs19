package com.gmos.iotc.collector.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeviceEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private String friendlyName;
  private String type;

  public DeviceEntity() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "DeviceEntity{" +
            "id=" + id +
            ", friendlyName='" + friendlyName + '\'' +
            ", type='" + type + '\'' +
            '}';
  }
}
