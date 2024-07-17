package com.cropsense.metrics_server.ScheduledJobs;

import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;
import com.cropsense.metrics_server.HttpTransporter.HttpTransporter;
import com.cropsense.metrics_server.Logger.AppLogger;

@Component
public class ValidateServerConnection {

    @Autowired
    private ApplicationServerProfile appServer;

    @Scheduled(fixedRate = 10000)
    public void checkApplicationServerConnection() throws URISyntaxException {

        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String urlPath = "http://" + connectionHost + ":" + connectionPort + "/actuator/health";
        AppLogger logger = new AppLogger(getClass().toString());
        HttpTransporter httpTransporter = new HttpTransporter();

        try {
            HttpRequest pingRequest = httpTransporter.buildRequest(urlPath);
            httpTransporter.sendRequest(pingRequest);
            logger.logInfoMsg("CropSense-Application-Server is up");
        }
        catch (Exception e) {
            logger.logErrorMsg("CropSense-Application-Server at " + urlPath + " is not reachable");
        }

    }

}
