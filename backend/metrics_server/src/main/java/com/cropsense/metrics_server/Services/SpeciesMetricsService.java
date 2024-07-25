package com.cropsense.metrics_server.Services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;
import com.cropsense.metrics_server.DataStructures.Coordinate;
import com.cropsense.metrics_server.EntityEndpoints.SpeciesMetricEndpoints;
import com.cropsense.metrics_server.HttpTransporter.HttpTransporter;
import com.cropsense.metrics_server.Logger.AppLogger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SpeciesMetricsService {
    
    @Autowired
    private ApplicationServerProfile appServer;

    private final SpeciesMetricEndpoints speciesMetricEndpoint = new SpeciesMetricEndpoints();
    private final AppLogger logger = new AppLogger(getClass().toString());
    private HttpTransporter httpTransporter = new HttpTransporter();


    /**
     * Get the latest crop species anomalies and their instance coordinates
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public HashMap<String,List<Coordinate>> compileLatestSpeciesAnomaliesForCrop(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Compiling the latest species anomalies detected for crop with ID: " + cropIdStr);

        // build request url
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String requestUrl = "http://" + connectionHost + ":" + connectionPort + speciesMetricEndpoint.getEntriesForCropUrl() + "?cropId=" + cropIdStr;

        // send request and receive response
        HttpRequest getCropSpeciesAnomalyReq = httpTransporter.buildRequest(requestUrl); 
        HttpResponse<String> cropSpeciesAnomalyRes = httpTransporter.sendRequest(getCropSpeciesAnomalyReq);
        JSONArray jsonAnomalies = new JSONArray(cropSpeciesAnomalyRes.body());

        // check for empty response
        if (jsonAnomalies.length() == 0) {
            return null;
        }

        // capture all dates
        List<Date> datesList = getEntryDates(jsonAnomalies);

        // find max date
        Date maxDate = Collections.max(datesList);
        String maxDateStr = maxDate.toString();
        logger.logInfoMsg("Found latest date for species metrics: " + maxDateStr + " for crop with ID: " + cropIdStr);

        // collect the anomalies at the date
        HashMap<String,List<Coordinate>> responseHash = new LinkedHashMap<>();
        for (int i = 0; i < jsonAnomalies.length(); i++) {
            
            JSONObject currentEntry = jsonAnomalies.getJSONObject(i);
            String currEntryDate = currentEntry.get("collectionDate").toString();

            if (currEntryDate.equals(maxDateStr)) {
                
                // collect anomaly detected
                String anomalyName = currentEntry.get("speciesDetected").toString();
                JSONObject anomalyCoordJson = currentEntry.getJSONObject("coordinate");
                String anomalyLat = anomalyCoordJson.get("lat").toString();
                String anomalyLng = anomalyCoordJson.get("lng").toString();

                // create coordinate object
                Coordinate anomalyCoord = new Coordinate();
                anomalyCoord.setLat(Float.valueOf(anomalyLat));
                anomalyCoord.setLng(Float.valueOf(anomalyLng));

                if (responseHash.containsKey(anomalyName)) {
                    List<Coordinate> coordsList = responseHash.get(anomalyName);
                    coordsList.add(anomalyCoord);
                    responseHash.replace(anomalyName, coordsList);
                }
                else {
                    List<Coordinate> coordsList = new ArrayList<>();
                    coordsList.add(anomalyCoord);
                    responseHash.put(anomalyName, coordsList);
                }
            }

        }

        logger.logInfoMsg("Returning final responseHash for latest anomalies: " + responseHash.toString());
        return responseHash;

    }

    
    /**
     * Calculate latest anomaly count for crop
     * @param cropId
     * @return
     * @throws URISyntaxException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    public int calculateLatestSpeciesAnomalyCountForCrop(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Calculating latest species anomaly count for crop with ID: " + cropIdStr);

        // get connection details and build url
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String getSpeciesEntriesUrl = "http://" + connectionHost + ":" + connectionPort + speciesMetricEndpoint.getEntriesForCropUrl() + "?cropId=" + cropIdStr;

        // build request and get response
        HttpRequest request = httpTransporter.buildRequest(getSpeciesEntriesUrl);
        HttpResponse<String> cropSpeciesAnomaliesRes = httpTransporter.sendRequest(request);
        JSONArray anomaliesArrayJson = new JSONArray(cropSpeciesAnomaliesRes.body());

        // if no anomaly entries
        if (anomaliesArrayJson.length() == 0) {
            return 0;
        }

        // get entry dates
        List<Date> entryDates = getEntryDates(anomaliesArrayJson);

        // find the max date
        Date maxDate = Collections.max(entryDates);
        String maxDateStr = maxDate.toString();

        // parse entries at max date and add to count
        int anomalyCount = calculateAnomalyCountAtDate(anomaliesArrayJson, maxDateStr);

        logger.logInfoMsg("Calculated anomaly count = " + String.valueOf(anomalyCount));
        return anomalyCount;

    }

    
    /**
     * Get anomaly count metrics over time
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public HashMap<String,Integer> calculateSpeciesAnomalyCountForCropOverTime(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Caluclating the species anomaly counts for crop with ID: " + cropIdStr + " over time");

        // setup connection details and build URL
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String getSpeciesAnomalyEntriesUrl = "http://" + connectionHost + ":" + connectionPort + speciesMetricEndpoint.getEntriesForCropUrl() + "?cropId=" + cropIdStr;

        // build request and get response
        HttpRequest request = httpTransporter.buildRequest(getSpeciesAnomalyEntriesUrl);
        HttpResponse<String> speciesAnomalyRes = httpTransporter.sendRequest(request);
        JSONArray anomaliesArrayJson = new JSONArray(speciesAnomalyRes.body());

        // if no anomalies
        if (anomaliesArrayJson.length() == 0) {
            return null;
        }

        // get the entry dates
        List<Date> entryDates = this.getEntryDates(anomaliesArrayJson);

        // loop through each date and get the count
        HashMap<String,Integer> responseHash = new LinkedHashMap<>();
        for (Date date : entryDates) {
            
            String dateStr = date.toString();
            int count = this.calculateAnomalyCountAtDate(anomaliesArrayJson, dateStr);
            responseHash.put(dateStr, count);
        }

        logger.logInfoMsg("Compiled counts for each date and returning - " + responseHash.toString());
        return responseHash;

    }


    /**
     * Get the latest data entry date
     * @param cropId
     * @return
     * @throws URISyntaxException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    public String getLatestEntryDate(long cropId) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Finding the date for latest anomaly species detection entries");

        // get connection details and build url
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        String getSpeciesEntriesUrl = "http://" + connectionHost + ":" + connectionPort + speciesMetricEndpoint.getEntriesForCropUrl() + "?cropId=" + cropIdStr;

        // build request and get response
        HttpRequest request = httpTransporter.buildRequest(getSpeciesEntriesUrl);
        HttpResponse<String> cropSpeciesAnomaliesRes = httpTransporter.sendRequest(request);
        JSONArray anomaliesArrayJson = new JSONArray(cropSpeciesAnomaliesRes.body());

        // if array is empty
        if (anomaliesArrayJson.length() == 0) {
            return null;
        }

        List<Date> foundDates = this.getEntryDates(anomaliesArrayJson);
        logger.logInfoMsg("Found dates: " + foundDates.toString());

        Date maxDate = Collections.max(foundDates);
        String maxDateStr = maxDate.toString();

        logger.logInfoMsg("Found the latest date to be: " + maxDateStr);

        return maxDateStr;

    }



    // Helper methods -------------------------------------------------------------------------------------------------

    /**
     * Get the dates for entry response
     * @param entriesArray
     * @return
     */
    private List<Date> getEntryDates(JSONArray entriesArray) {
        
        List<Date> entryDates = new ArrayList<>();
        for (int i = 0; i < entriesArray.length(); i++) {

            JSONObject currentEntry = entriesArray.getJSONObject(i);
            Date currEntryDate = Date.valueOf(currentEntry.get("collectionDate").toString());

            if (!entryDates.contains(currEntryDate)) {
                entryDates.add(currEntryDate);
            }

        }

        return entryDates;

    }

    /**
     * Count anomalies detected on a date
     * @param entriesArray
     * @param selectedDate
     * @return
     */
    private int calculateAnomalyCountAtDate(JSONArray entriesArray, String selectedDate) {

        int anomalyCount = 0;

        for (int i = 0; i < entriesArray.length(); i++) {
            
            JSONObject currentEntry = entriesArray.getJSONObject(i);
            String currEntryDate = currentEntry.get("collectionDate").toString();
            
            if (currEntryDate.equals(selectedDate)) {
                anomalyCount = anomalyCount + 1;
            }

        }
        
        return anomalyCount;

    }

}
