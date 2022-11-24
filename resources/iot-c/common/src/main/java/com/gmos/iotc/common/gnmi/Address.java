package com.gmos.iotc.common.gnmi;

public class Address {
  private String name; //IP address or name
  int port;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "Address{" +
            "name='" + name + '\'' +
            ", port=" + port +
            '}';
  }
}