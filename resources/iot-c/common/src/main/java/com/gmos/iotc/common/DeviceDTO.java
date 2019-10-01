package com.gmos.iotc.common;

public class DeviceDTO {

  private Long id;
  private String friendlyName;
  private String type;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return "DeviceDTO{" +
            "id=" + id +
            ", friendlyName='" + friendlyName + '\'' +
            ", type='" + type + '\'' +
            '}';
  }
}
