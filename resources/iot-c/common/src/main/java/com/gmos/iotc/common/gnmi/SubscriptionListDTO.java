package com.gmos.iotc.common.gnmi;

import java.util.List;

public class SubscriptionListDTO {

  private List<SubscriptionDTO> subscriptionList;

  private GnmiEnum.Encoding encoding;
  private String pathPrefix;

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

  public String getPathPrefix() {
    return pathPrefix;
  }

  public void setPathPrefix(String pathPrefix) {
    this.pathPrefix = pathPrefix;
  }

  @Override
  public String toString() {
    return "SubscriptionListDTO{" +
            "subscriptionList=" + subscriptionList +
            ", encoding=" + encoding +
            ", pathPrefix='" + pathPrefix + '\'' +
            '}';
  }
}
