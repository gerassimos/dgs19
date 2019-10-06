package com.gmos.iotc.common;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PerformanceDataDTO {
  private Long id;
  private Long deviceId;
  private Timestamp timestamp;
  private String timeStr;
  private Double temperature;
  private Double humidity;

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

  public Double getHumidity() {
    return humidity;
  }

  public void setHumidity(Double humidity) {
    this.humidity = humidity;
  }

  public String getTimeStr() {
    timeStr = new SimpleDateFormat("HH.mm.ss").format(new Date(timestamp.getTime()));
    return timeStr;
  }

  @Override
  public String toString() {
    return "PerformanceDataDTO{" +
            "id=" + id +
            ", deviceId=" + deviceId +
            ", timestamp=" + timestamp +
            ", temperature=" + temperature +
            ", humidity=" + humidity +
            '}';
  }
}
