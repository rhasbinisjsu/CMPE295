package com.cropsense.metrics_server.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "appserver.profile")
public class ApplicationServerProfile {
    
    private String serverIp;
    private String serverPort;

    // Setters

    public void setServerIp(String ip) {
        this.serverIp = ip;
    }

    public void setServerPort(String port) {
        this.serverPort = port;
    }

    // Getters

    public String getServerIp() {
        return this.serverIp;
    }

    public String getServerPort() {
        return this.serverPort;
    }

    @Override
    public String toString() {
        return "Application Server Profile:\n   Host = " + this.serverIp + "\n   Port = " + this.serverPort;
    }
}
