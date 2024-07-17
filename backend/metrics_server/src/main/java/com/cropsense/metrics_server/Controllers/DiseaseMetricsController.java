package com.cropsense.metrics_server.Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.metrics_server.DataStructures.Coordinate;
import com.cropsense.metrics_server.Logger.AppLogger;
import com.cropsense.metrics_server.Services.DiseaseMetricsService;

@RestController
@RequestMapping("/CropSense/MetricsServer/DiseaseMetricsController")
public class DiseaseMetricsController {
    
    @Autowired
    private DiseaseMetricsService dmService;

    private final AppLogger logger = new AppLogger(getClass().toString());

    
    /**
     * Get a list of the latest disease metrics and their coordinates for a crop 
     * @param cropId
     * @return
     * @throws Exception 
     */
    @GetMapping(
        path = "/getLatestDetectedDiseases",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getLatestDetectedDiseases(
        @RequestParam long cropId
    ) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] Finding latest disease entries and their coordinates for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            HashMap<String,List<Coordinate>> responseHash = dmService.getLatestDetectedDiseases(cropId);
            response = new ResponseEntity<HashMap<String,List<Coordinate>>>(responseHash, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to find latest disease entries and their coordinates for crop with ID: " + cropIdStr);
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
        path = "/calculateLatestDiseaseRate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> calculateLatestDiseaseRate(
        @RequestParam long cropId
    ) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] Received request to calculate latest disease rate for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            float diseaseRate = dmService.caluclateLatestCropDiseaseRate(cropId);
            if (diseaseRate == -1.0) {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                response = new ResponseEntity<Float>(diseaseRate, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to calculate latest disease rate for crop with ID: " + cropIdStr);
        return response;

    }


    /**
     * 
     * @param cropId
     * @return
     */
    @GetMapping(
        path = "/calculateDiseaseRateForAllDates",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> calculateDiseaseRateForAllDates(
        @RequestParam long cropId
    ) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] Received request to caluclate disease rates over time for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            HashMap<String,Float> responseHash = dmService.calculateDiseaseRateForAllDates(cropId);
            response = new ResponseEntity<HashMap<String,Float>>(responseHash, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to calculate disease rates over time for a crop with ID: " + cropIdStr);
        return response;

    }


    /**
     * Get latest date for crop disease entries
     * @param cropId
     * @return
     */
    @GetMapping(
        path = "/getLatestDiseaseEntryDate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getLatestDiseaseEntryDate(
        @RequestParam long cropId
    ) throws URISyntaxException, IOException, InterruptedException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] Received request to find latest date for disease entries/metrics for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            Date latestDate = dmService.getLatestEntryDate(cropId);
            response = new ResponseEntity<Date>(latestDate, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }

        return response;

    }

}
