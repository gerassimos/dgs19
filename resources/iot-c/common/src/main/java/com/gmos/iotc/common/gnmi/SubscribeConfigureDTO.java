package com.gmos.iotc.common.gnmi;

import java.util.List;
import java.util.Map;

public class SubscribeConfigureDTO {

  private String name;
  private SubscriptionListDTO SubscriptionListDTO;
  private List<TargetDTO> targetList;
  private Map<String, String> tags;
  private GnmiEnum.SubscribeAction subscribeAction;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public com.gmos.iotc.common.gnmi.SubscriptionListDTO getSubscriptionListDTO() {
    return SubscriptionListDTO;
  }

  public void setSubscriptionListDTO(com.gmos.iotc.common.gnmi.SubscriptionListDTO subscriptionListDTO) {
    SubscriptionListDTO = subscriptionListDTO;
  }

  public List<TargetDTO> getTargetList() {
    return targetList;
  }

  public void setTargetList(List<TargetDTO> targetList) {
    this.targetList = targetList;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public GnmiEnum.SubscribeAction getSubscribeAction() {
    return subscribeAction;
  }

  public void setSubscribeAction(GnmiEnum.SubscribeAction subscribeAction) {
    this.subscribeAction = subscribeAction;
  }

  @Override
  public String toString() {
    return "SubscribeConfigureDTO{" +
            "name='" + name + '\'' +
            ", SubscriptionListDTO=" + SubscriptionListDTO +
            ", targetList=" + targetList +
            ", tags=" + tags +
            ", subscribeAction=" + subscribeAction +
            '}';
  }
}
