package com.gmos.iotc.collector.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DeviceEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private String friendlyName;
  private String type;

  public DeviceEntity() {
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
