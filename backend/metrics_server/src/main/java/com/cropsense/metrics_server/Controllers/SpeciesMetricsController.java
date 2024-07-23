package com.cropsense.metrics_server.Controllers;

import java.util.HashMap;
import java.util.List;

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
import com.cropsense.metrics_server.Services.SpeciesMetricsService;

@RestController
@RequestMapping("/CropSense/MetricsServer/SpeciesMetricsController")
public class SpeciesMetricsController {
    
    @Autowired
    private SpeciesMetricsService speciesMetricSvc;

    private final AppLogger logger = new AppLogger(getClass().toString());

    
    /**
     * Get the latest crop species anomalies and their instance coordinates
     * @param cropId
     * @return
     * @throws Exception 
     */
    @GetMapping(
        path = "/compileLatestSpeciesAnomaliesForCrop",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> compileLatestSpeciesAnomaliesForCrop(
        @RequestParam long cropId
    ) throws Exception {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] - Received request to compile latest anomaly species for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            HashMap<String,List<Coordinate>> responseHash = speciesMetricSvc.compileLatestSpeciesAnomaliesForCrop(cropId);
            
            if (responseHash == null) {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                logger.logInfoMsg("Empty response - no entries found");
            }
            else {
                response = new ResponseEntity<HashMap<String,List<Coordinate>>>(responseHash, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... \n Reason: " + e.getMessage());
            throw e;
        }   

        logger.logInfoMsg("[ EXIT ] - Exiting request to compile latest anomaly species for crop with ID: " + cropIdStr);
        return response;
    }


    /**
     * Calculate latest anomaly count for crop
     * @param cropId
     * @return
     * @throws Exception
     */
    @GetMapping(
        path = "/countLatestSpeciesAnomaliesForCrop",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> countLatestSpeciesAnomaliesForCrop(
        @RequestParam long cropId
    ) throws Exception {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] - Received reauest to count species anolmalies for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            int anomalyCount = speciesMetricSvc.calculateLatestSpeciesAnomalyCountForCrop(cropId);
            response = new ResponseEntity<Integer>(anomalyCount, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... \n Reason: " + e.getMessage());
            throw e;
        }
        
        logger.logInfoMsg("[ EXIT ] - Exiting request to count species anomalies for crop with ID: " + cropIdStr);
        return response;

    }


    /**
     * Get anomaly count metrics over time
     * @param cropId
     * @return
     * @throws Exception
     */
    @GetMapping(
        path = "/calculateSpeciesAnomalyCountForCropOverTime",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> calculateSpeciesAnomalyCountForCropOverTime(
        @RequestParam long cropId
    ) throws Exception {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] - Received request to count anomalies detected over time for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            HashMap<String,Integer> responseHash = speciesMetricSvc.calculateSpeciesAnomalyCountForCropOverTime(cropId);
            if (responseHash == null) {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                response = new ResponseEntity<HashMap<String,Integer>>(responseHash, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... \n Reason: " + e.getMessage());
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] - Exiting request to count anomalies detected over time for crop with ID: " + cropIdStr);
        return response;

    }

}
