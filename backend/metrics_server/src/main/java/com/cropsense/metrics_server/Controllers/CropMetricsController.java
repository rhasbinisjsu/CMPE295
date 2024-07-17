package com.cropsense.metrics_server.Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.metrics_server.Logger.AppLogger;
import com.cropsense.metrics_server.Services.CropMetricsService;

@RestController
@RequestMapping("/CropSense/MetricsServer/CropMetricsController")
public class CropMetricsController {

    @Autowired 
    private CropMetricsService cmService;

    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * 
     * @param ownerId
     * @return
     * @throws Exception 
     */
    @GetMapping(
        path = "/getActiveCropCountForOwner",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getActiveCropCountForOwner(
        @RequestParam long ownerId
    ) throws IOException, URISyntaxException, InterruptedException {
        
        logger.logInfoMsg("[ ENTER ] Received request to count active crops for owner with ID: " + Long.toString(ownerId));
        int activeCropCount = 0;
        ResponseEntity<?> response;

        try {
            activeCropCount = cmService.getActiveCropCountForOwner(ownerId);
            response = new ResponseEntity<String>(String.valueOf(activeCropCount), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to count active crops for owner with ID: " + Long.toString(ownerId));
        return response;

    }


    /**
     * 
     * @param ownerId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping(
        path = "/getActiveCropSpeciesForOwner", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getActiveCropSpeciesForOwner(
        @RequestParam long ownerId
    ) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("[ ENTER ] Received request to find active crop species for owner with ID: " + Long.toString(ownerId));
        ResponseEntity<?> response;

        try {
            List<String> cropSpecies = cmService.getActiveCropSpeciesForOwner(ownerId);
            response = new ResponseEntity<List<String>>(cropSpecies, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to find active crop species for owner with ID: " + Long.toString(ownerId));
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
    @GetMapping(
        path = "/getCropYield",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getCropYield(
        @RequestParam long cropId
    ) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("[ ENTER ] Received request to calculate % yield for crop with ID: " + Long.toString(cropId));
        ResponseEntity<?> response;

        try {
            float yield = cmService.calculateCropYield(cropId);
            response = new ResponseEntity<String>(String.valueOf(yield), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to calculate % yield for crop with ID: " + Long.toString(cropId));
        return response;
    }


    /**
     * 
     * @param farmId
     * @param species
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping(
        path = "/analyzeFarmCropSpeciesYield",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> analyzeFarmCropSpeciesYield(
        @RequestParam long farmId,
        @RequestParam String species
    ) throws URISyntaxException, IOException, InterruptedException {

        logger.logInfoMsg("[ ENTER ] Received request to analyze " + species + " crop yields at farm with ID: " + Long.toString(farmId));
        ResponseEntity<?> response;

        try {
            float averageYield = cmService.analyzeCropSpeciesYieldByFarm(farmId, species);
            response = new ResponseEntity<String>(String.valueOf(averageYield), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to analyze " + species + " crop yields at farm with ID: " + Long.toString(farmId));
        return response;

    }
    
}
