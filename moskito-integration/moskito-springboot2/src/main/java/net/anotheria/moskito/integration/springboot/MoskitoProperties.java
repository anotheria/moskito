package net.anotheria.moskito.integration.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "moskito")
public class MoskitoProperties {

  private int remotePort;

  public int getRemotePort() {
    return remotePort;
  }

  public void setRemotePort(int remotePort) {
    this.remotePort = remotePort;
  }
}
