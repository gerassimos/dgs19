package com.gmos.iotc.common.gnmi;

import java.util.List;
import java.util.Map;

public class SubscriptionCfgDTO {

  private Map<String, SubscriptionListDTO> subscriptionMap;
  private List<TargetDTO> targetList;

  public Map<String, SubscriptionListDTO> getSubscriptionMap() {
    return subscriptionMap;
  }

  public void setSubscriptionMap(Map<String, SubscriptionListDTO> subscriptionMap) {
    this.subscriptionMap = subscriptionMap;
  }

  public List<TargetDTO> getTargetList() {
    return targetList;
  }

  public void setTargetList(List<TargetDTO> targetList) {
    this.targetList = targetList;
  }

  @Override
  public String toString() {
    return "SubscriptionCfgDTO{" +
            "subscriptionMap=" + subscriptionMap +
            ", targetList=" + targetList +
            '}';
  }
}
