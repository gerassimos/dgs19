package com.gmos.iotc.collector.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class PerformanceDataEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private Long deviceId;
  private Timestamp timestamp;
  private Double temperature;
  private Double humidity;

  public PerformanceDataEntity() {
  }

  public Long getId() {
    return id;
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

  @Override
  public String toString() {
    return "PerformanceDataEntity{" +
            "id=" + id +
            ", deviceId=" + deviceId +
            ", timestamp=" + timestamp +
            ", temperature=" + temperature +
            ", humidity=" + humidity +
            '}';
  }
}
