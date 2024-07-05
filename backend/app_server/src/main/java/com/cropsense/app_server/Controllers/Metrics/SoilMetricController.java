package com.cropsense.app_server.Controllers.Metrics;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.app_server.Entities.Metrics.SoilMetric;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.Metrics.SoilMetricService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/CropSense/AppServer/SoilMetricController")
public class SoilMetricController {
    
    @Autowired
    private SoilMetricService smService;

    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new soil metric for a crop
     * @param newSoilMetric
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/postSoilMetric",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> postSoilMetric(
        @RequestBody SoilMetric newSoilMetric
    ) throws SQLException {

        long cropId = newSoilMetric.getCropId();
        logger.logInfoMsg("POST [ ENTER ] - Received request to persist new soil metric for crop with ID: " + Long.toString(cropId));
        boolean posted = false;

        ResponseEntity<HttpStatus> response;
        logger.logInfoMsg("Coll date = " + newSoilMetric.getCollectionDate().toString());

        try {
            posted = smService.postSoilMetric(newSoilMetric);
            
            if (posted) {
                response = new ResponseEntity<>(HttpStatus.CREATED);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                logger.logWarnMsg("The new soil metric was not persisted! No Exception");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to post the new soil metric");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to persist the soil metric");
        return response;

    }


    /**
     * Fetch a soil metric by its ID
     * @param metricId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchSoilMetricById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchSoilMetricById(
        @RequestParam long metricId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch soil metric with ID: " + Long.toString(metricId));
        ResponseEntity<?> response;

        try {
            Optional<SoilMetric> osm = smService.fetchSoilMetricById(metricId);

            if (osm.isPresent()) {
                logger.logInfoMsg("Optional<SoilMetric> object is populated... Soil metric with ID: " + Long.toString(metricId) + " is found");
                SoilMetric sm = osm.get();
                response = new ResponseEntity<SoilMetric>(sm, HttpStatus.OK);
            }
            else {
                logger.logInfoMsg("Optional<SoilMetric> object is empty... Soil metric with ID: " + Long.toString(metricId) + " is not found");
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch soil metric with ID: " + Long.toString(metricId));
        return response; 

    }
    

    /**
     * Fetch persisted soil metrics for a crop
     * @param cropId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchSoilMetricsForCrop",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchSoilMetricsForCrop(
        @RequestParam long cropId
    ) throws SQLException{

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all soil metrics for crop with ID: " + Long.toString(cropId));
        ResponseEntity<?> response; 

        try {
            List<SoilMetric> smList = smService.fetchSoilMetricsForCrop(cropId);
            response = new ResponseEntity<List<SoilMetric>>(smList, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... " + e.getMessage());
            response = new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all soil metrics for crop with ID: " + Long.toString(cropId));
        return response;

    }


    /**
     * BULK OPERATION - Fetch soil metrics for a list of crops
     * @param cropIdList
     * @return
     * @throws SQLException
     */
    @Transactional
    @GetMapping(
        path = "/BulkOperation/FetchSoilMetricsForCrops",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?>  BulkFetchSoilMetricsForCrops(
        @RequestBody List<Long> cropIdList
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - (BULK OPERATION) - Received request to fetch soil metrics for crops with IDs: " + cropIdList.toString());
        ResponseEntity<?> response; 

        try {
            List<SoilMetric> smList = smService.bulkFetchSoilMetricsForCropsList(cropIdList);
            response = new ResponseEntity<List<SoilMetric>>(smList, HttpStatus.OK);
        }
        catch (Exception e) { 
            logger.logErrorMsg("Caught exception... " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - (BULK OPERATION) - Exiting request to fetch soil metrics for crops");
        return response;

    }

}
