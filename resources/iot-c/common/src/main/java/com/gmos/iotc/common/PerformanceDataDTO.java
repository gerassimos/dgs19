package com.gmos.iotc.common;

import java.sql.Timestamp;

public class PerformanceDataDTO {
  private Long id;
  private Long deviceId;
  private Timestamp timestamp;
  private Double temperature;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(Long deviceId) {
    this.deviceId = deviceId;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  @Override
  public String toString() {
    return "PerformanceDataDTO{" +
            "id=" + id +
            ", deviceId=" + deviceId +
            ", timestamp=" + timestamp +
            ", temperature=" + temperature +
            '}';
  }
}
