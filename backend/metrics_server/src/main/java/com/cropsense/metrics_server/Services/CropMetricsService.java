package com.cropsense.metrics_server.Services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;
import com.cropsense.metrics_server.EntityEndpoints.CropEndpoints;
import com.cropsense.metrics_server.EntityEndpoints.FarmEndpoints;
import com.cropsense.metrics_server.HttpTransporter.HttpTransporter;
import com.cropsense.metrics_server.Logger.AppLogger;

@Service
public class CropMetricsService {
    
    @Autowired
    private ApplicationServerProfile appServer;
    
    // declare and instantiate logger
    private final AppLogger logger = new AppLogger(getClass().toString());
    // declare and instantiate Http Transporter
    private HttpTransporter httpTransporter = new HttpTransporter();

    // Get crop endpoints
    private final CropEndpoints cropEndpoints = new CropEndpoints();
    // Get farm endpoints
    private final FarmEndpoints farmEndpoints = new FarmEndpoints();
    
    
    /**
     * Count active crops for owner
     * @param ownerId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public int getActiveCropCountForOwner(long ownerId) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("Counting active crops for owner with ID: " + Long.toString(ownerId));

        // get server connection profile
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // farm Id list 
        List<Long> farmIds = new ArrayList<>();

        // Get farms
        String getFarmsUrl = "http://" + connectionHost + ":" + connectionPort + farmEndpoints.getBaseUrl() + farmEndpoints.getFetchFarmsForOwnerUrl() + "?ownerId=" + Long.toString(ownerId);
        HttpRequest requestFarms = httpTransporter.buildRequest(getFarmsUrl);
        HttpResponse<String> resopnseFarms = httpTransporter.sendRequest(requestFarms);
        JSONArray farmsArr = new JSONArray(resopnseFarms.body());
        for (int i = 0; i < farmsArr.length(); i++) {
            JSONObject farm = farmsArr.getJSONObject(i);
            String farmIdString = farm.get("id").toString();
            long farmId = Long.valueOf(farmIdString);
            farmIds.add(farmId);
        }

        logger.logInfoMsg("Captured farm IDs: " + farmIds.toString() + " for owner with ID: " + Long.toString(ownerId));

        // query for active crop in each farm
        int trueCount = 0;
        for (long captured : farmIds) {
            String cropsUrl = "http://" + connectionHost + ":" + connectionPort + cropEndpoints.getBaseUrl() + cropEndpoints.getActiveCropsForFarm() + "?farmId=" + Long.toString(captured);
            HttpRequest requestActiveCrops = httpTransporter.buildRequest(cropsUrl);
            HttpResponse<String> responseActiveCrops = httpTransporter.sendRequest(requestActiveCrops);
            JSONArray cropArr = new JSONArray(responseActiveCrops.body());
            // count entries - array length
            int count = cropArr.length();
            trueCount = trueCount + count;
        }

        logger.logInfoMsg("Found " + String.valueOf(trueCount) + " active crops");
        return trueCount;

    }


    /**
     * 
     * @param ownerId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    //public List<String> getActiveCropSpeciesForOwner(long ownerId) throws URISyntaxException, IOException, InterruptedException {
    public HashMap<String,Integer> getActiveCropSpeciesForOwner(long ownerId) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("Finding active crop species for owner with ID: " + Long.toString(ownerId));

        // get server connection
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // farm Id list 
        List<Long> farmIds = new ArrayList<>();

        // Get farms
        String getFarmsUrl = "http://" + connectionHost + ":" + connectionPort + farmEndpoints.getBaseUrl() + farmEndpoints.getFetchFarmsForOwnerUrl() + "?ownerId=" + Long.toString(ownerId);
        HttpRequest requestFarms = httpTransporter.buildRequest(getFarmsUrl);
        HttpResponse<String> resopnseFarms = httpTransporter.sendRequest(requestFarms);
        JSONArray farmsArr = new JSONArray(resopnseFarms.body());
        for (int i = 0; i < farmsArr.length(); i++) {
            JSONObject farm = farmsArr.getJSONObject(i);
            String farmIdString = farm.get("id").toString();
            long farmId = Long.valueOf(farmIdString);
            farmIds.add(farmId);
        }

        logger.logInfoMsg("Captured farm IDs: " + farmIds.toString() + " for owner with ID: " + Long.toString(ownerId));

        HashMap<String,Integer> cropTypes = new HashMap<>();

        // Find crops
        int totalCount = 0;
        for (long captured : farmIds) {
            String cropsUrl = "http://" + connectionHost + ":" + connectionPort + cropEndpoints.getBaseUrl() + cropEndpoints.getActiveCropsForFarm() + "?farmId=" + Long.toString(captured);
            HttpRequest requestActiveCrops = httpTransporter.buildRequest(cropsUrl);
            HttpResponse<String> responseActiveCrops = httpTransporter.sendRequest(requestActiveCrops);
            JSONArray cropArr = new JSONArray(responseActiveCrops.body());

            // search for species
            for (int i = 0; i < cropArr.length(); i++) {
                JSONObject cropJson = cropArr.getJSONObject(i);
                String cropTypeStr = cropJson.get("cropSpecies").toString();
                Integer transplantedCrop = Integer.valueOf(cropJson.get("transplantAmount").toString());
                logger.logInfoMsg("Found crop species: " + cropTypeStr);
                totalCount = totalCount + transplantedCrop;

                if (!cropTypes.containsKey(cropTypeStr)) {
                    logger.logInfoMsg("Found new species... appending");
                    cropTypes.put(cropTypeStr, transplantedCrop);
                }
                else {
                    logger.logInfoMsg("Already account for " + cropTypeStr + " adding new count to total");
                    int storedCount = cropTypes.get(cropTypeStr);
                    int newCount = storedCount + transplantedCrop;
                    cropTypes.replace(cropTypeStr, newCount);
                }

            }
        }

        logger.logInfoMsg("Total plant count: " + Integer.toString(totalCount));

        for (Map.Entry<String,Integer> entry : cropTypes.entrySet()) {
            String currentKey = entry.getKey();
            int rawCount = entry.getValue();
            logger.logInfoMsg("raw count " + Integer.toString(rawCount));

            float percentage = (float) rawCount / totalCount ;
            percentage = percentage * 100;
            int percentageInt = Math.round(percentage);

            cropTypes.replace(currentKey, percentageInt);
        }

        logger.logInfoMsg("Collected the active crop species: " + cropTypes.toString());
        return cropTypes;

    }
    

    /**
     * Caluclate % yield for crop
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public float calculateCropYield(long cropId) throws URISyntaxException, IOException, InterruptedException {
     
        logger.logInfoMsg("Calculating season yield for crop with ID: " + Long.toString(cropId));

        // collect connection details
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();

        // get the crop data
        String cropUrl = "http://" + connectionHost + ":" + connectionPort + cropEndpoints.getBaseUrl() + cropEndpoints.getCropData() + "?cropId=" + Long.toString(cropId);
        HttpRequest cropDataRequest = httpTransporter.buildRequest(cropUrl);
        HttpResponse<String> cropDataResponse = httpTransporter.sendRequest(cropDataRequest);

        JSONObject cropJson = new JSONObject(cropDataResponse.body());
        int plantedCount = Integer.valueOf(cropJson.get("transplantAmount").toString());
        int cultivatedCount = Integer.valueOf(cropJson.get("cultivatedAmount").toString());
  
        // declare and initialize yield
        float yield = (float) cultivatedCount/plantedCount;
        yield = yield * 100;

        logger.logInfoMsg("Calculated yield: " + String.valueOf(yield) + "%");

        return yield;

    }


    /**
     * Calculated average crop yield for a species on a farm
     * @param farmId
     * @param species
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public float analyzeCropSpeciesYieldByFarm(long farmId, String species) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("Analyzing " + species + " grows for farm with ID: " + Long.toString(farmId));

        // initialize connection details
        String connectionHost = appServer.getServerIp();
        String connectionPort = appServer.getServerPort();
        
        // Collect crop species planted on farm historically
        String cropUrl = "http://" + connectionHost + ":" + connectionPort + cropEndpoints.getBaseUrl() + cropEndpoints.getCropBySpeciesForFarm() + "?farmId=" + Long.toString(farmId) + "&species=" + species;
        HttpRequest cropDataRequest = httpTransporter.buildRequest(cropUrl);
        HttpResponse<String> cropDataResponse = httpTransporter.sendRequest(cropDataRequest);
        
        // collect all cultivated grows
        JSONArray cropDataArr = new JSONArray(cropDataResponse.body());
        List<JSONObject> finalizedArr = new ArrayList<>();
        for (int i = 0; i < cropDataArr.length(); i++) {
            JSONObject jsonCrop = cropDataArr.getJSONObject(i);
            String cropStatus = jsonCrop.getString("status");
            if (cropStatus.equals("cultivated")) {
                finalizedArr.add(jsonCrop);
            }
        }

        logger.logInfoMsg("Cultivated crops collected: " + finalizedArr.toString());

        List<Float> cropYields = new ArrayList<>();
        for (int i = 0; i < finalizedArr.size(); i++) {

            JSONObject instance = finalizedArr.get(i);
            String transplantedAmountString = instance.get("transplantAmount").toString();
            String cultivatedAmountString = instance.get("cultivatedAmount").toString();

            int transplanted = Integer.valueOf(transplantedAmountString);
            int cultivated =  Integer.valueOf(cultivatedAmountString);

            float instanceYield = (float) cultivated / transplanted;
            instanceYield = instanceYield * 100;

            cropYields.add(instanceYield);
        }

        // calculate average yield
        int arrSize = cropYields.size();
        float numerator = 0;
        for (int i = 0; i < arrSize; i++) {
            numerator = numerator + cropYields.get(i);
        }

        return numerator / arrSize;

    }


}
