package com.gmos.iotc.common.gnmi;

public class GnmiEnum {

  public enum Encoding {
    JSON(0),
    BYTES(1),
    PROTO(2),
    JSON_IETF(3),
    UNRECOGNIZED(-1);

    public final int getNumber() { return value; }
    private final int value;
    Encoding(int value) {
      this.value = value;
    }
  }

  public enum SubscriptionMode {
    TARGET_DEFINED(0),
    ON_CHANGE(1),
    SAMPLE(2),
    UNRECOGNIZED(-1);
    public final int getNumber() { return value; }
    private final int value;
    SubscriptionMode(int value) {
      this.value = value;
    }
  }
}
