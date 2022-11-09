package com.gmos.iotc.common.gnmi;

import java.util.List;

public class SubscriptionDTO {

  private PathDTO path;
  //  enum SubscriptionMode {
  //    TARGET_DEFINED = 0;  // The target selects the relevant mode for each element.
  //    ON_CHANGE      = 1;  // The target sends an update on element value change.
  //    SAMPLE         = 2;  // The target samples values according to the interval.
  //  }
  private int mode;
  private long sampleInterval; //NanoSeconds

  public PathDTO getPath() {
    return path;
  }

  public void setPath(PathDTO path) {
    this.path = path;
  }

  public int getMode() {
    return mode;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

  public long getSampleInterval() {
    return sampleInterval;
  }

  public void setSampleInterval(long sampleInterval) {
    this.sampleInterval = sampleInterval;
  }
}
