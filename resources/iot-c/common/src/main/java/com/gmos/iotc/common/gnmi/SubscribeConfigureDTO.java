package com.gmos.iotc.common.gnmi;

import java.util.List;
import java.util.Map;

public class SubscribeConfigureDTO {

//  private Map<String, SubscriptionListDTO> subscriptionMap;
  private String name;
  private SubscriptionListDTO SubscriptionListDTO;
  private List<TargetDTO> targetList;
  private Map<String, String> tags;
  //TODO private enum SubscribeAction;
  // subscribe
  // unsubscribe
  // unsubscribeAll (=> all the subscriptions of the specified targets)



}
