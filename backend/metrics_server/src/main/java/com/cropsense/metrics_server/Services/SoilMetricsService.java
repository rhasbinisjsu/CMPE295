package com.cropsense.metrics_server.Services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;
import com.cropsense.metrics_server.EntityEndpoints.SoilMetricEndpoints;
import com.cropsense.metrics_server.HttpTransporter.HttpTransporter;
import com.cropsense.metrics_server.Logger.AppLogger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SoilMetricsService {
    
    @Autowired
    private ApplicationServerProfile appServer;

    private HttpTransporter httpTransporter = new HttpTransporter();
    private final AppLogger logger = new AppLogger(getClass().toString());

    private final SoilMetricEndpoints smEndpoints = new SoilMetricEndpoints();


    // return individual metrics in hashmap for a crop - they must be sorted by date
    public HashMap<String,List<String>> getIndividualSoilMetricsForCrop(long cropId) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("Getting individual soul metrics for crop with ID: " + Long.toString(cropId));

        // get server connection
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // get the crop soil metrics
        String getCropSoilMetricsUrl = "http://" + connectionHost + ":" + connectionPort + smEndpoints.getBaseUrl() + smEndpoints.getSoilMetricsForCrop() + "?cropId=" + Long.toString(cropId);
        HttpRequest requestSoilMetrics = httpTransporter.buildRequest(getCropSoilMetricsUrl);
        HttpResponse<String> responseSoilMetrics = httpTransporter.sendRequest(requestSoilMetrics);
        JSONArray soilMetricsArr = new JSONArray(responseSoilMetrics.body());
        
        List<String> moistureArr = new ArrayList<>();
        List<String> phLevelArr = new ArrayList<>();
        List<String> nitorgenLevelArr = new ArrayList<>();
        List<String> phosphorousLevelArr = new ArrayList<>();
        List<String> potassiumLevelArr = new ArrayList<>();

        for (int i = 0; i < soilMetricsArr.length(); i++) {
            JSONObject dataPoint = soilMetricsArr.getJSONObject(i);
            
            String moisture = dataPoint.get("soilMoisture").toString();
            moistureArr.add(moisture);

            String ph = dataPoint.get("phLevel").toString();
            phLevelArr.add(ph);

            String nitrogen = dataPoint.get("nitrogenLevel").toString();
            nitorgenLevelArr.add(nitrogen);

            String phosphorous = dataPoint.get("phosphorousLevel").toString();
            phosphorousLevelArr.add(phosphorous);

            String potassium = dataPoint.get("potassiumLevel").toString();
            potassiumLevelArr.add(potassium);
        }

        // init the new response hashmap
        HashMap<String,List<String>> responseMap = new HashMap<>();
        responseMap.put("moisture", moistureArr);
        responseMap.put("ph", phLevelArr);
        responseMap.put("nitrogen", nitorgenLevelArr);
        responseMap.put("phosphorous", phosphorousLevelArr);
        responseMap.put("potassium", potassiumLevelArr);

        return responseMap;

    }

}
