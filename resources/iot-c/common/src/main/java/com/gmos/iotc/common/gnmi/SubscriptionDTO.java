package com.gmos.iotc.common.gnmi;

public class SubscriptionDTO {

  private PathDTO path;
  private GnmiEnum.SubscriptionMode subscriptionMode;
  private long sampleInterval; //NanoSeconds

  public PathDTO getPath() {
    return path;
  }

  public void setPath(PathDTO path) {
    this.path = path;
  }

  public GnmiEnum.SubscriptionMode getSubscriptionMode() {
    return subscriptionMode;
  }

  public void setSubscriptionMode(GnmiEnum.SubscriptionMode subscriptionMode) {
    this.subscriptionMode = subscriptionMode;
  }

  public long getSampleInterval() {
    return sampleInterval;
  }

  public void setSampleInterval(long sampleInterval) {
    this.sampleInterval = sampleInterval;
  }

  @Override
  public String toString() {
    return "SubscriptionDTO{" +
            "path=" + path +
            ", subscriptionMode=" + subscriptionMode +
            ", sampleInterval=" + sampleInterval +
            '}';
  }
}
