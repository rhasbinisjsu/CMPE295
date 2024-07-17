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
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SoilMetricsService {
    
    @Autowired
    private ApplicationServerProfile appServer;

    private HttpTransporter httpTransporter = new HttpTransporter();
    private final AppLogger logger = new AppLogger(getClass().toString());
    private final SoilMetricEndpoints smEndpoints = new SoilMetricEndpoints();


    /**
     * Get indivualized soil metrics for a crop by its ID
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public HashMap<String,List<HashMap<String,String>>> getIndividualSoilMetricsForCrop(
        long cropId
    ) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("Getting individual soul metrics for crop with ID: " + Long.toString(cropId));

        // get server connection
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // get the crop soil metrics
        String getCropSoilMetricsUrl = "http://" + connectionHost + ":" + connectionPort + smEndpoints.getBaseUrl() + smEndpoints.getSoilMetricsForCrop() + "?cropId=" + Long.toString(cropId);
        HttpRequest requestSoilMetrics = httpTransporter.buildRequest(getCropSoilMetricsUrl);
        HttpResponse<String> responseSoilMetrics = httpTransporter.sendRequest(requestSoilMetrics);
        JSONArray soilMetricsArr = new JSONArray(responseSoilMetrics.body());
        
        List<HashMap<String,String>> moistureArr = new ArrayList<>();
        List<HashMap<String,String>> phLevelArr = new ArrayList<>();
        List<HashMap<String,String>> nitorgenLevelArr = new ArrayList<>();
        List<HashMap<String,String>> phosphorousLevelArr = new ArrayList<>();
        List<HashMap<String,String>> potassiumLevelArr = new ArrayList<>();

        for (int i = 0; i < soilMetricsArr.length(); i++) {
            JSONObject dataPoint = soilMetricsArr.getJSONObject(i);

            String date = dataPoint.get("collectionDate").toString();
            
            String moisture = dataPoint.get("soilMoisture").toString();
            HashMap<String,String> moistureHash = new LinkedHashMap<>();
            moistureHash.put(date, moisture);
            moistureArr.add(moistureHash);

            String ph = dataPoint.get("phLevel").toString();
            HashMap<String,String> phHash = new LinkedHashMap<>();
            phHash.put(date, ph); 
            phLevelArr.add(phHash);

            String nitrogen = dataPoint.get("nitrogenLevel").toString();
            HashMap<String,String> nitrogenHash = new LinkedHashMap<>();
            nitrogenHash.put(date, nitrogen);
            nitorgenLevelArr.add(nitrogenHash);

            String phosphorous = dataPoint.get("phosphorousLevel").toString();
            HashMap<String,String> phosphorousHash = new LinkedHashMap<>();
            phosphorousHash.put(date,phosphorous);
            phosphorousLevelArr.add(phosphorousHash);

            String potassium = dataPoint.get("potassiumLevel").toString();
            HashMap<String,String> potassiumHash = new LinkedHashMap<>();
            potassiumHash.put(date, potassium);
            potassiumLevelArr.add(potassiumHash);
        }

        // init the new response hashmap
        HashMap<String,List<HashMap<String,String>>> responseMap = new LinkedHashMap<>();
        responseMap.put("moisture", moistureArr);
        responseMap.put("ph", phLevelArr);
        responseMap.put("nitrogen", nitorgenLevelArr);
        responseMap.put("phosphorous", phosphorousLevelArr);
        responseMap.put("potassium", potassiumLevelArr);

        logger.logInfoMsg("Generated HashMap of individualized metrics");
        return responseMap;

    }

}
