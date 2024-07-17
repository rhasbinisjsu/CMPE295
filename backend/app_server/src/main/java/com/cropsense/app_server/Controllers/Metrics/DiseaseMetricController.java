package com.cropsense.app_server.Controllers.Metrics;

import java.sql.SQLException;
import java.util.List;

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

import com.cropsense.app_server.Entities.Metrics.DiseaseMetric;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.Metrics.DiseaseMetricService;

@RestController
@RequestMapping("/CropSense/AppServer/DiseaseMetricController")
public class DiseaseMetricController {
    
    @Autowired
    private DiseaseMetricService dmService;

    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new crop disease entry... Datasource (Drone or satellite) will use this
     * @param newDiseaseMetric
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createDiseaseMetric",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> createDiseaseMetrics(
        @RequestBody DiseaseMetric newDiseaseMetric
    ) throws SQLException {

        String cropIdStr = String.valueOf(newDiseaseMetric.getCropId());
        logger.logInfoMsg("POST [ ENTER ] - Recieved request to persist a new disease entry for crop with ID: " + cropIdStr);

        ResponseEntity<HttpStatus> response;

        try {
            dmService.createDiseaseMetric(newDiseaseMetric);
            response = new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... " + e.getMessage());
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to persist a new disease entry for crop with ID: " + cropIdStr);
        return response;

    }


    /**
     * Fetch all diease entries for a crop
     * @param cropId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchDiseaseEntriesForCrop",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchDiseaseEntriesForCrop(
        @RequestParam long cropId
    ) throws SQLException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch disease entries for crop with ID: " + cropIdStr);

        ResponseEntity<?> response; 

        try {
            List<DiseaseMetric> dmList = dmService.fetchDiseaseMetricsForCrop(cropId);
            response = new ResponseEntity<List<DiseaseMetric>>(dmList, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... " + e.getMessage());
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch diease entries for crop with ID: " + cropIdStr);
        return response;
        
    }

}
