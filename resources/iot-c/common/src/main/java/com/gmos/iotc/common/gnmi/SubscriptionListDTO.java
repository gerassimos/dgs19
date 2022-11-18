package com.gmos.iotc.common.gnmi;

import java.util.List;

public class SubscriptionListDTO {

  private List<SubscriptionDTO> subscriptionList;

  private GnmiEnum.Encoding encoding;

  public List<SubscriptionDTO> getSubscriptionList() {
    return subscriptionList;
  }

  public void setSubscriptionList(List<SubscriptionDTO> subscriptionList) {
    this.subscriptionList = subscriptionList;
  }

  public GnmiEnum.Encoding getEncoding() {
    return encoding;
  }

  public void setEncoding(GnmiEnum.Encoding encoding) {
    this.encoding = encoding;
  }

  @Override
  public String toString() {
    return "SubscriptionListDTO{" +
            "subscriptionList=" + subscriptionList +
            ", encoding=" + encoding +
            '}';
  }
}
