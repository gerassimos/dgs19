package com.gmos.iotc.common.gnmi;

public class GnmiEnum {

  public enum Encoding {
    json(0),
    bytes(1),
    proto(2),
    json_ietf(3),
    unrecognized(-1);

    public final int getNumber() { return value; }
    private final int value;
    Encoding(int value) {
      this.value = value;
    }
  }

  public enum SubscriptionMode {
    targetDefined(0),
    onChange(1),
    sample(2),
    unrecognized(-1);
    public final int getNumber() { return value; }
    private final int value;
    SubscriptionMode(int value) {
      this.value = value;
    }
  }

  public enum SubscribeAction {
    subscribe(0),
    unsubscribe(1),
    unsubscribeAll(2);
    public final int getNumber() { return value; }
    private final int value;
    SubscribeAction(int value) {
      this.value = value;
    }
  }

  public enum SubscriptionStatus {
    requestCreate(0),
    requestCancel(1);
    public final int getNumber() { return value; }
    private final int value;
    SubscriptionStatus(int value) {this.value = value;
    }
  }
}
