package com.gmos.iotc.common.gnmi;

import java.util.Map;

public class TargetDTO {
  private Address address;
  private Credential credential;
  // NOTE:
  // The tags at this point are not at target level
  // They are at subscription level per target
  private Map<String, String> tags;

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Credential getCredential() {
    return credential;
  }

  public void setCredential(Credential credential) {
    this.credential = credential;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "TargetDTO{" +
            "address=" + address +
            ", credential=" + credential +
            ", tags=" + tags +
            '}';
  }
}
