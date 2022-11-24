package com.gmos.iotc.common.gnmi;

import java.util.Map;

public class TargetDTO {
  private Address address;
  private Credential credential;
  Map<String, String> metadata;

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

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  @Override
  public String toString() {
    return "TargetDTO{" +
            "address=" + address +
            ", credential=" + credential +
            ", metadata=" + metadata +
            '}';
  }
}
