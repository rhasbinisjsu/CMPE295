package com.cropsense.metrics_server.Services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.sql.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;
import com.cropsense.metrics_server.DataStructures.Coordinate;
import com.cropsense.metrics_server.EntityEndpoints.CropEndpoints;
import com.cropsense.metrics_server.EntityEndpoints.DiseaseMetricEndpoints;
import com.cropsense.metrics_server.HttpTransporter.HttpTransporter;
import com.cropsense.metrics_server.Logger.AppLogger;

@Service
public class DiseaseMetricsService {
    
    @Autowired 
    private ApplicationServerProfile appServer;

    private HttpTransporter httpTransporter = new HttpTransporter();
    private final AppLogger logger = new AppLogger(getClass().toString());
    private final DiseaseMetricEndpoints dmEndpoints = new DiseaseMetricEndpoints();
    private final CropEndpoints cropEndpoints = new CropEndpoints();


    /**
     * Get a list of the latest disease metrics and their coordinates
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public HashMap<String,List<Coordinate>> getLatestDetectedDiseases(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Finding detected diseases for crop with ID: " + cropIdStr);

        // request endpoints
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String requestEndpoint = "http://" + connectionHost + ":" + connectionPort + dmEndpoints.getAllDiseaseEntriesForCrop() + "?cropId=" + cropIdStr;

        // send request and retrieve response
        HttpRequest request = httpTransporter.buildRequest(requestEndpoint);
        HttpResponse<String> responseDiseaseEntries = httpTransporter.sendRequest(request);
        JSONArray diseaseEntriesArr = new JSONArray(responseDiseaseEntries.body());
        if (diseaseEntriesArr.length() == 0 ) {
            return null;
        }

        // collect the dates
        List<Date> datesList = new ArrayList<>();
        for (int i = 0; i < diseaseEntriesArr.length(); i++) {

            JSONObject currentEntry = diseaseEntriesArr.getJSONObject(i);
            Date collectedDate = Date.valueOf(currentEntry.get("collectionDate").toString());

            if (datesList.isEmpty()) {
                datesList.add(collectedDate);
            }
            else {
                if (!datesList.contains(collectedDate)) {
                    datesList.add(collectedDate);
                }
            }
        }

        // find the max date 
        Date latestDate = Collections.max(datesList);
        logger.logInfoMsg("Found latest collected entries date = " + latestDate.toString());

        HashMap<String,List<Coordinate>> responseHash = new LinkedHashMap<>();
        for (int i = 0; i < diseaseEntriesArr.length(); i++) {
            JSONObject currentEntry = diseaseEntriesArr.getJSONObject(i);
            String currentDate = currentEntry.get("collectionDate").toString();
            if (currentDate.equals(latestDate.toString())) {
                // get the disease
                String disease = currentEntry.get("diseaseType").toString();

                // get the coordinate
                JSONObject coordinateStr = currentEntry.getJSONObject("coordinate");
                float coordinateLat = Float.valueOf(coordinateStr.get("lat").toString());
                float coordinateLng = Float.valueOf(coordinateStr.get("lng").toString());
                Coordinate coordinate = new Coordinate();
                coordinate.setLat(coordinateLat);
                coordinate.setLng(coordinateLng);


                if (responseHash.containsKey(disease)) {
                    List<Coordinate> coordList = responseHash.get(disease);
                    coordList.add(coordinate);
                    responseHash.replace(disease, coordList);
                    logger.logInfoMsg("Adding disease entry for " + disease);
                }
                else {
                    List<Coordinate> coordList = new ArrayList<>();
                    coordList.add(coordinate);
                    responseHash.put(disease, coordList);
                    logger.logInfoMsg("Creating the HashMap entry and adding disease: " + disease);
                }
            }

        }

        return responseHash;

    }


    /**
     * 
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public float caluclateLatestCropDiseaseRate(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Finding detected diseases for crop with ID: " + cropIdStr);

        // request endpoints
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String requestEndpoint = "http://" + connectionHost + ":" + connectionPort + dmEndpoints.getAllDiseaseEntriesForCrop() + "?cropId=" + cropIdStr;

        // send request and retrieve response
        HttpRequest request = httpTransporter.buildRequest(requestEndpoint);
        HttpResponse<String> responseDiseaseEntries = httpTransporter.sendRequest(request);
        JSONArray diseaseEntriesArr = new JSONArray(responseDiseaseEntries.body());
        if (diseaseEntriesArr.length() == 0) {
            return -1;
        }

        // collect the dates
        List<Date> datesList = new ArrayList<>();
        for (int i = 0; i < diseaseEntriesArr.length(); i++) {

            JSONObject currentEntry = diseaseEntriesArr.getJSONObject(i);
            Date collectedDate = Date.valueOf(currentEntry.get("collectionDate").toString());

            if (datesList.isEmpty()) {
                datesList.add(collectedDate);
            }
            else {
                if (!datesList.contains(collectedDate)) {
                    datesList.add(collectedDate);
                }
            }
        }

        // find the max date 
        Date latestDate = Collections.max(datesList);
        logger.logInfoMsg("Found latest collected entries date = " + latestDate.toString());

        // calculate rate at latest
        float response = this.calculateDiseaseRateForCropOnDate(cropId, latestDate);

        return response;

    }


    /**
     * 
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public HashMap<String,Float> calculateDiseaseRateForAllDates(long cropId) throws URISyntaxException, IOException, InterruptedException {
        
        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Finding detected diseases for crop with ID: " + cropIdStr);

        // request endpoints
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String requestEndpoint = "http://" + connectionHost + ":" + connectionPort + dmEndpoints.getAllDiseaseEntriesForCrop() + "?cropId=" + cropIdStr;

        // send request and retrieve response
        HttpRequest request = httpTransporter.buildRequest(requestEndpoint);
        HttpResponse<String> responseDiseaseEntries = httpTransporter.sendRequest(request);
        JSONArray diseaseEntriesArr = new JSONArray(responseDiseaseEntries.body());
        if (diseaseEntriesArr.length() == 0) {
            return null;
        }

        // collect the dates
        List<Date> datesList = new ArrayList<>();
        for (int i = 0; i < diseaseEntriesArr.length(); i++) {

            JSONObject currentEntry = diseaseEntriesArr.getJSONObject(i);
            Date collectedDate = Date.valueOf(currentEntry.get("collectionDate").toString());

            if (datesList.isEmpty()) {
                datesList.add(collectedDate);
            }
            else {
                if (!datesList.contains(collectedDate)) {
                    datesList.add(collectedDate);
                }
            }
        }

        // calculate the rate at each date
        HashMap<String,Float> responseHash = new LinkedHashMap<>();
        for (int i = 0; i < datesList.size(); i++) {
            Date currentDate = datesList.get(i);
            float rate = this.calculateDiseaseRateForCropOnDate(cropId, currentDate);
            responseHash.put(currentDate.toString(), rate);
            logger.logInfoMsg("Appended hashmap with value: " + Float.toString(rate) + " at date: " + currentDate.toString());
        }

        logger.logInfoMsg("Generated final hashmap: " + responseHash.toString());
        return responseHash;
        
    }

    
    /**
     * Get the most current date for metrics
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public Date getLatestEntryDate(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Finding latest disease metric date for crop with ID: " + cropIdStr);

        // request endpoints
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String requestEndpoint = "http://" + connectionHost + ":" + connectionPort + dmEndpoints.getAllDiseaseEntriesForCrop() + "?cropId=" + cropIdStr;

        // send request and retrieve response
        HttpRequest request = httpTransporter.buildRequest(requestEndpoint);
        HttpResponse<String> responseDiseaseEntries = httpTransporter.sendRequest(request);
        JSONArray diseaseEntriesArr = new JSONArray(responseDiseaseEntries.body());
        if (diseaseEntriesArr.length() == 0) {
            return null;
        }

        // collect the dates
        List<Date> datesList = new ArrayList<>();
        for (int i = 0; i < diseaseEntriesArr.length(); i++) {

            JSONObject currentEntry = diseaseEntriesArr.getJSONObject(i);
            Date collectedDate = Date.valueOf(currentEntry.get("collectionDate").toString());

            if (datesList.isEmpty()) {
                datesList.add(collectedDate);
            }
            else {
                if (!datesList.contains(collectedDate)) {
                    datesList.add(collectedDate);
                }
            }
        }

        // find the max date 
        Date latestDate = Collections.max(datesList);
        logger.logInfoMsg("Found latest collected entries date = " + latestDate.toString());

        return latestDate;
    }


    /**
     * 
     * @param cropId
     * @param date
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private float calculateDiseaseRateForCropOnDate(long cropId, Date date) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        String selectedDate = date.toString();
        logger.logInfoMsg("Calculating the disease rate for crop with ID: " + cropIdStr + " at date: " + selectedDate);

        // Connection to host
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // Get crop amount
        String getCropUrl = "http://" + connectionHost + ":" + connectionPort + cropEndpoints.getBaseUrl() + cropEndpoints.getCropData() + "?cropId=" + cropIdStr;
        HttpRequest cropRequest = httpTransporter.buildRequest(getCropUrl);
        HttpResponse<String> cropData = httpTransporter.sendRequest(cropRequest);
        JSONObject cropDataJson = new JSONObject(cropData.body());
        logger.logInfoMsg("Fetched the data for crop with ID: " + cropIdStr);

        int transplantedCount = Integer.valueOf(cropDataJson.get("transplantAmount").toString());
        logger.logInfoMsg("collected crop transplant count " + Integer.toString(transplantedCount) + " for crop with ID: " + cropIdStr);

        // Get diseased crop amount
        String getDiseaseEntriesUrl = "http://" + connectionHost + ":" + connectionPort + dmEndpoints.getAllDiseaseEntriesForCrop() + "?cropId=" + cropIdStr;
        HttpRequest diseaseEntryRequest = httpTransporter.buildRequest(getDiseaseEntriesUrl);
        HttpResponse<String> diseaseEntryResponse = httpTransporter.sendRequest(diseaseEntryRequest);
        JSONArray diseaseEntryArr = new JSONArray(diseaseEntryResponse.body().toString());
        
        // find the metrics at date
        List<Coordinate> readlocation = new ArrayList<>();
        int diseaseCount = 0;
        for (int i = 0; i < diseaseEntryArr.length(); i++) {
            JSONObject entry = diseaseEntryArr.getJSONObject(i);
            String entryDate = entry.get("collectionDate").toString();

            if (selectedDate.equals(entryDate)) {
                JSONObject coordinateJson = new JSONObject(entry.get("coordinate").toString());
                // check coordinates
                float lat = Float.valueOf(coordinateJson.get("lat").toString());
                float lng = Float.valueOf(coordinateJson.get("lng"). toString());
                Coordinate metricCoordinate = new Coordinate();
                metricCoordinate.setLat(lat);
                metricCoordinate.setLng(lng);

                if (readlocation.isEmpty()) {
                    readlocation.add(metricCoordinate);
                    logger.logInfoMsg("Detected new disease location");
                    logger.logInfoMsg("Added disease metric entry at date: " + entryDate);
                }
                else {
                    for (int j = 0; j < readlocation.size(); j++) {
                        Coordinate c = readlocation.get(j);
                        if (!c.compareCoordinates(c, metricCoordinate)) {
                            if (j == readlocation.size() - 1) {
                                logger.logInfoMsg("Detected new disease location");
                                readlocation.add(metricCoordinate);
                                logger.logInfoMsg("Added disease metric entry at date: " + entryDate);
                            }
                        }
                        else {
                            logger.logInfoMsg("Current coordinate location is accounted for");
                        }
                    }
                }
            }
        }

        // calculate % diseased
        diseaseCount = readlocation.size();
        float value = (float) diseaseCount/transplantedCount;
        logger.logInfoMsg(Float.toString(value));
        value = value * 100;
        
        logger.logInfoMsg("Calculated disease rate for crop with ID: " + cropIdStr + " at date: " + selectedDate + " is " + Float.toString(value));
        return value;

    }

}
