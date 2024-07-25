package com.cropsense.metrics_server.Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.metrics_server.Logger.AppLogger;
import com.cropsense.metrics_server.Services.SoilMetricsService;

@RestController
@RequestMapping("/CropSense/MetricsServer/SoilMetricsController")
public class SoilMetricsController {

    @Autowired
    private SoilMetricsService smService;

    private final AppLogger logger = new AppLogger(getClass().toString());

    
    /**
     * 
     * @param cropId
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping(
        path = "/getIndividualizedMetricsForCrop",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getIndividualizedMetricsForCrop(
        @RequestParam long cropId
    ) throws URISyntaxException, IOException, InterruptedException {
        
        logger.logInfoMsg("[ ENTER ] Receieved request to get individualized soil metrics for crop with ID: " + Long.toString(cropId));
        ResponseEntity<?> response;

        try {
            HashMap<String,HashMap<String,String>> responseHash = smService.getIndividualSoilMetricsForCrop(cropId);
            response = new ResponseEntity<HashMap<String,HashMap<String,String>>>(responseHash, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... Failed to fetch and generate individualized metrics \n Reason: " + e.getMessage());
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to get individualized soil metrics for crop with ID: " + Long.toString(cropId));
        return response;

    }


    /**
     * Gather the latest soil metric values - average values
     * @param cropId
     * @return
     * @throws Exception
     */
    @GetMapping(
        path = "/getLatestIndividualSoilMetrics",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getLatestIndividualSoilMetrics(
        @RequestParam long cropId
    ) throws Exception {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] Received request to find the latest soil metrics for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            HashMap<String,Float> responseHash = smService.getLatestIndividualSoilMetrics(cropId);
            if (responseHash == null) {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                response = new ResponseEntity<HashMap<String,Float>>(responseHash, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... Failed to fetch and generate individualized metrics \n Reason: " + e.getMessage());
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to find the latest soil metrics for crop with ID: " + cropIdStr);
        return response;

    }


    /**
     * Get the latest date metrics were collected
     * @param cropId
     * @return
     * @throws Exception
     */
    @GetMapping(
        path = "/getLatestMetricsDate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getLatestMetricsDate(
        @RequestParam long cropId
    ) throws Exception {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("[ ENTER ] Received request to find the latest soil metric collection date for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            String latestDate = smService.getLatestMetricsDate(cropId);

            if (latestDate == null) {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                response = new ResponseEntity<String>(latestDate, HttpStatus.OK);
            }

        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... \n Reason: " + e.getMessage());
            throw e;
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to find the latest soil metric collection date for crop with ID: " + cropIdStr);
        return response;
    }
    
}
