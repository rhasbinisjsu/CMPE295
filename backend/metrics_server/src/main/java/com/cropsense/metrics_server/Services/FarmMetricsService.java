package com.cropsense.metrics_server.Services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;
import com.cropsense.metrics_server.EntityEndpoints.FarmEndpoints;
import com.cropsense.metrics_server.HttpTransporter.HttpTransporter;
import com.cropsense.metrics_server.Logger.AppLogger;

@Service
public class FarmMetricsService {
    
    @Autowired
    private ApplicationServerProfile appServer; 

    // Get the farm endpoints
    private final FarmEndpoints farmEndpoints = new FarmEndpoints();
    // declare and instantiate logger
    private final AppLogger logger = new AppLogger(getClass().toString());
    // declare and instantiate the Http Transporter
    private HttpTransporter httpTransporter = new HttpTransporter();


    /**
     * 
     * @param ownerId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public int getOwnerFarmCount(long ownerId) throws URISyntaxException, IOException, InterruptedException {
        
        logger.logInfoMsg("Counting farms serviced for owner with ID: " + Long.toString(ownerId));

        // declare and initialize count object
        int farmCount = 0;

        // get the server connection endpoints
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        
        // compiled URL endpoint 
        String endpointUrl = "http://" + connectionHost + ":" + connectionPort + farmEndpoints.getBaseUrl() + farmEndpoints.getFetchFarmsForOwnerUrl() + "?ownerId=" + Long.toString(ownerId);
        HttpRequest request =  httpTransporter.buildRequest(endpointUrl);
        HttpResponse<String> response = httpTransporter.sendRequest(request);

        // Get farm count from response body
        JSONArray jsonArr = new JSONArray(response.body());
        farmCount = jsonArr.length();
        logger.logInfoMsg("Counted " + String.valueOf(farmCount) + " farms for owner with ID: " + Long.toString(ownerId));

        return farmCount;

    }

}
