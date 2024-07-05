package com.cropsense.metrics_server.Services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> getActiveCropSpeciesForOwner(long ownerId) throws URISyntaxException, IOException, InterruptedException {

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

        List<String> cropTypes = new ArrayList<>();

        // Find crops
        for (long captured : farmIds) {
            String cropsUrl = "http://" + connectionHost + ":" + connectionPort + cropEndpoints.getBaseUrl() + cropEndpoints.getActiveCropsForFarm() + "?farmId=" + Long.toString(captured);
            HttpRequest requestActiveCrops = httpTransporter.buildRequest(cropsUrl);
            HttpResponse<String> responseActiveCrops = httpTransporter.sendRequest(requestActiveCrops);
            JSONArray cropArr = new JSONArray(responseActiveCrops.body());

            // search for species
            for (int i = 0; i < cropArr.length(); i++) {
                JSONObject cropJson = cropArr.getJSONObject(i);
                String cropTypeStr = cropJson.get("cropSpecies").toString();
                logger.logInfoMsg("Found crop species: " + cropTypeStr);

                if (!cropTypes.contains(cropTypeStr)) {
                    logger.logInfoMsg("Found new species... appending");
                    cropTypes.add(cropTypeStr);
                }
                else {
                    logger.logInfoMsg("Already account for " + cropTypeStr + " not appending & moving on");
                }

            }
        }

        logger.logInfoMsg("Collected the active crop species: " + cropTypes.toString());
        return cropTypes;

    }
    

    // Caluclate % yeild for crop


    // helper


    // Http request


}
