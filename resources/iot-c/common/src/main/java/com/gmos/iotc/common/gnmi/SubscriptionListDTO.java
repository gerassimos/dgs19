package com.gmos.iotc.common.gnmi;

import java.util.List;

public class SubscriptionListDTO {

  private List<SubscriptionDTO> subscriptionList;


  //  enum Encoding {
//    JSON = 0;           // JSON encoded text.
//    BYTES = 1;          // Arbitrarily encoded bytes.
//    PROTO = 2;          // Encoded according to scalar values of TypedValue.
//    ASCII = 3;          // ASCII text of an out-of-band agreed format.
//    JSON_IETF = 4;      // JSON encoded text as per RFC7951.
//  }
  private int encoding;

  public List<SubscriptionDTO> getSubscriptionList() {
    return subscriptionList;
  }

  public void setSubscriptionList(List<SubscriptionDTO> subscriptionList) {
    this.subscriptionList = subscriptionList;
  }

  public int getEncoding() {
    return encoding;
  }

  public void setEncoding(int encoding) {
    this.encoding = encoding;
  }
}
