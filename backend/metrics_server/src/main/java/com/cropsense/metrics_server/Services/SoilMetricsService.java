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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.sql.Date;

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


    /**
     * Gather the latest soil metric values - average values
     * @param cropId
     * @return
     * @throws URISyntaxException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    public HashMap<String,Float> getLatestIndividualSoilMetrics(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Gathering the latest soil metric values for crop with ID: " + cropIdStr);

        // get server connection
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // get the crop soil metrics
        String getCropSoilMetricsUrl = "http://" + connectionHost + ":" + connectionPort + smEndpoints.getBaseUrl() + smEndpoints.getSoilMetricsForCrop() + "?cropId=" + cropIdStr;
        HttpRequest requestSoilMetrics = httpTransporter.buildRequest(getCropSoilMetricsUrl);
        HttpResponse<String> responseSoilMetrics = httpTransporter.sendRequest(requestSoilMetrics);
        JSONArray soilMetricsArr = new JSONArray(responseSoilMetrics.body());

        // exit if empty array
        if (soilMetricsArr.length() == 0) {
            return null;
        }

        // get the latest date
        String latestDateStr = this.getLatestMetricsDateHelper(soilMetricsArr);
        Date latestDate = Date.valueOf(latestDateStr);

        HashMap<String,Float> responseHash = new LinkedHashMap<>();

        for (int i = 0; i < soilMetricsArr.length(); i++) {
            JSONObject currentEntry = soilMetricsArr.getJSONObject(i);
            Date currEntryDate = Date.valueOf(currentEntry.get("collectionDate").toString());
            
            if (currEntryDate.equals(latestDate)){

                // collect moisture value
                float currMoistureVal = Float.valueOf(currentEntry.get("soilMoisture").toString());
                if (!responseHash.containsKey("moisture")) {
                    responseHash.put("moisture", currMoistureVal);
                }
                else {
                    float storedVal = responseHash.get("moisture");
                    float averageVal = (float) (storedVal + currMoistureVal) / 2;
                    responseHash.replace("moisture", averageVal);
                }

                // collect ph value
                float currpHVal = Float.valueOf(currentEntry.get("phLevel").toString());
                if (!responseHash.containsKey("ph")){
                    responseHash.put("ph", currpHVal);
                }
                else {
                    float storedVal = responseHash.get("ph");
                    float averageVal = (float) (storedVal + currpHVal) / 2;
                    responseHash.replace("ph", averageVal);
                }

                // collect nitrogen value
                float currNitroVal = Float.valueOf(currentEntry.get("nitrogenLevel").toString());
                if (!responseHash.containsKey("nitrogen")) {
                    responseHash.put("nitrogen", currNitroVal);
                }
                else {
                    float storedVal = responseHash.get("nitrogen");
                    float averageVal = (float) (storedVal + currNitroVal) / 2;
                    responseHash.replace("nitrogen", averageVal);
                }

                // collect phosphorous value
                float currPhosVal = Float.valueOf(currentEntry.get("phosphorousLevel").toString());
                if (!responseHash.containsKey("phosphorous")) {
                    responseHash.put("phosphorous", currPhosVal);
                }
                else {
                    float storedVal = responseHash.get("phosphorous");
                    float averageVal = (float) (storedVal + currPhosVal) / 2;
                    responseHash.replace("phosphorous", averageVal);
                }

                // collect potassium value
                float currPotVal = Float.valueOf(currentEntry.get("potassiumLevel").toString());
                if (!responseHash.containsKey("potassium")) {
                    responseHash.put("potassium", currPotVal);
                }
                else {
                    float storedVal = responseHash.get("potassium");
                    float averageVal = (float) (storedVal + currPotVal) / 2;
                    responseHash.replace("potassium", averageVal);
                }

            }
        }

        logger.logInfoMsg("Compiled response HashMap: " + responseHash.toString());

        return responseHash;
    }


    /**
     * Get the latest date for soil metric collection for a crop
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public String getLatestMetricsDate(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Finding the latest date for collected soil metrics for crop with ID: " + cropIdStr);

        // get server connection
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // get the crop soil metrics
        String getCropSoilMetricsUrl = "http://" + connectionHost + ":" + connectionPort + smEndpoints.getBaseUrl() + smEndpoints.getSoilMetricsForCrop() + "?cropId=" + cropIdStr;
        HttpRequest requestSoilMetrics = httpTransporter.buildRequest(getCropSoilMetricsUrl);
        HttpResponse<String> responseSoilMetrics = httpTransporter.sendRequest(requestSoilMetrics);
        JSONArray soilMetricsArr = new JSONArray(responseSoilMetrics.body());

        // exit if empty array
        if (soilMetricsArr.length() == 0) {
            return null;
        }

        // List to hold the dates
        List<Date> foundDates = new ArrayList<>();

        for (int i = 0; i < soilMetricsArr.length(); i++) {
            JSONObject currentEntry = soilMetricsArr.getJSONObject(i);
            Date currentEntryDate = Date.valueOf(currentEntry.get("collectionDate").toString());

            logger.logInfoMsg("Found date: " + currentEntryDate.toString());
            
            if (!foundDates.contains(currentEntryDate)){
                foundDates.add(currentEntryDate);
                logger.logInfoMsg("Found new date... Adding it");
            }

            else {
                logger.logInfoMsg("Date is accounted for... moving on");
            }
        }

        logger.logInfoMsg("Found dates: " + foundDates.toString());

        String latestDate = Collections.max(foundDates).toString();
        logger.logInfoMsg("Found the latest date in the collection: " + latestDate);

        return latestDate;

    }


    /**
     * [INTERNAL METHOD]
     * Returns the latest date for crop metris
     * @param metricsList
     * @return
     */
    private String getLatestMetricsDateHelper(JSONArray metricsList) {

        logger.logInfoMsg("Finding the latest soil metrics collection date");

        // List to hold the dates
        List<Date> foundDates = new ArrayList<>();

        for (int i = 0; i < metricsList.length(); i++) {
            JSONObject currentEntry = metricsList.getJSONObject(i);
            Date currentEntryDate = Date.valueOf(currentEntry.get("collectionDate").toString());

            logger.logInfoMsg("Found date: " + currentEntryDate.toString());
            
            if (!foundDates.contains(currentEntryDate)){
                foundDates.add(currentEntryDate);
                logger.logInfoMsg("Found new date... Adding it");
            }

            else {
                logger.logInfoMsg("Date is accounted for... moving on");
            }
        }

        logger.logInfoMsg("Found dates: " + foundDates.toString());

        String latestDate = Collections.max(foundDates).toString();
        logger.logInfoMsg("Found the latest date in the collection: " + latestDate);

        return latestDate;

    }


}
