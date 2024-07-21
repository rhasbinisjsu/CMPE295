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

import com.cropsense.app_server.Entities.Metrics.SpeciesMetric;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.Metrics.SpeciesMetricService;

@RestController
@RequestMapping("/CropSense/AppServer/SpeciesMetricController")
public class SpeciesMetricController {

    @Autowired
    private SpeciesMetricService speciesMetricSvc;

    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new species detection metric entry
     * @param newSME
     * @return
     */
    @PostMapping(
        path = "/createSpeciesMetricEntryForCrop",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> createSpeciesMetricEntry(
        @RequestBody SpeciesMetric newSME
    ) throws SQLException {
        
        String cropIdStr = Long.toString(newSME.getCropId());
        logger.logInfoMsg("POST [ ENTER ] - Received request to persist a new species metric entry for crop with ID: " + cropIdStr);
        boolean created = false;
        ResponseEntity<HttpStatus> response;

        try {
            created = speciesMetricSvc.createSpeciesMetricEntryForCrop(newSME);
            
            if (created) {
                response = new ResponseEntity<>(HttpStatus.CREATED);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... Failed to persist the new metric");
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to persist a new species metric entry for crop with ID: " + cropIdStr);
        return response;

    }


    /**
     * 
     * @param cropId
     * @return
     */
    @GetMapping(
        path = "/fetchSpeciesMetricEntriesForCrop",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchSpeciesMetricEntriesForCrop(
        @RequestParam long cropId
    ) throws SQLException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all species metric entries for crop with ID: " + cropIdStr);
        ResponseEntity<?> response;

        try {
            List<SpeciesMetric> speciesMetricList = speciesMetricSvc.fetchAllSpeciesMetricEntriesForCrop(cropId);
            response = new ResponseEntity<List<SpeciesMetric>>(speciesMetricList, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Caught exception... " + e.getMessage());
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all species metric entries for crop with ID: " + cropIdStr);
        return response;

    }
    
}
