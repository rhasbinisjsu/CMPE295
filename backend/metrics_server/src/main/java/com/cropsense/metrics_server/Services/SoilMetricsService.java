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
import java.util.HashMap;
import java.util.LinkedHashMap;

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
    public HashMap<String,HashMap<String,String>> getIndividualSoilMetricsForCrop(
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

        HashMap<String,String> moistureHash = new LinkedHashMap<>();
        HashMap<String,String> phHash = new LinkedHashMap<>();
        HashMap<String,String> nitrogenHash = new LinkedHashMap<>();
        HashMap<String,String> phosphorousHash = new LinkedHashMap<>();
        HashMap<String,String> potassiumHash = new LinkedHashMap<>();


        for (int i = 0; i < soilMetricsArr.length(); i++) {

            // get the datapoint
            JSONObject datapoint = soilMetricsArr.getJSONObject(i);

            // get the date
            String date = datapoint.get("collectionDate").toString();

            // moisture metric
            String moisture = datapoint.get("soilMoisture").toString();
            if (moistureHash.containsKey(date)) {
                float storedMoisture = Float.valueOf(moistureHash.get(date));
                float currentMoisture = Float.valueOf(moisture);

                float averageMoist = (float) (storedMoisture + currentMoisture) / 2;
                moistureHash.replace(date, String.valueOf(averageMoist));
            }
            else {
                moistureHash.put(date, moisture);
            }

            // ph metric
            String pH = datapoint.get("phLevel").toString();
            if (phHash.containsKey(date)) {
                float storedPh = Float.valueOf(phHash.get(date));
                float currentpH = Float.valueOf(pH);

                float averagePh = (float) (storedPh + currentpH) / 2;
                phHash.replace(date, String.valueOf(averagePh));
            }
            else {
                phHash.put(date, pH);
            }

            // nitrogen metric
            String nitrogen = datapoint.get("nitrogenLevel").toString();
            if (nitrogenHash.containsKey(date)) {
                float storedNitro = Float.valueOf(nitrogenHash.get(date));
                float currentNitro = Float.valueOf(nitrogen);

                float averageNitro = (float) (storedNitro + currentNitro) / 2;
                nitrogenHash.replace(date, String.valueOf(averageNitro));
            }
            else {
                nitrogenHash.put(date, nitrogen);
            }

            // phophorous metric
            String phosphorous = datapoint.get("phosphorousLevel").toString();
            if (phosphorousHash.containsKey(date)) {
                float storedPhos = Float.valueOf(phosphorousHash.get(date));
                float currentPhos = Float.valueOf(phosphorous);

                float averagePhos = (float) (storedPhos + currentPhos) / 2;
                phosphorousHash.replace(date, String.valueOf(averagePhos));
            }
            else {
                phosphorousHash.put(date, phosphorous);
            }

            // potassium metric
            String potassium = datapoint.get("potassiumLevel").toString();
            if (potassiumHash.containsKey(date)) {
                float storedPot = Float.valueOf(potassiumHash.get(date));
                float currentPot = Float.valueOf(potassium);

                float averagePot = (float) (storedPot + currentPot) / 2;
                potassiumHash.replace(date, String.valueOf(averagePot));
            }
            else {
                potassiumHash.put(date, potassium);
            }


        }

        // build response hash
        HashMap<String,HashMap<String,String>> responseHash = new LinkedHashMap<>();
        responseHash.put("moisture", moistureHash);
        responseHash.put("ph", phHash);
        responseHash.put("nitrogen", nitrogenHash);
        responseHash.put("phosphorous", phosphorousHash);
        responseHash.put("potassium", potassiumHash);

        return responseHash;

    }


}
