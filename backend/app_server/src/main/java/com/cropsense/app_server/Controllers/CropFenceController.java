package com.cropsense.app_server.Controllers;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.app_server.Entities.CropFence;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.CropFenceService;

@RestController
@RequestMapping("/CropSense/AppServer/CropFenceController")
public class CropFenceController {
    
    @Autowired
    private CropFenceService cfService;

    // Define and instantiate logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new crop fence
     * @param cropFence
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createCropFence", 
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createCropFence(
        @RequestBody CropFence cropFence
    ) throws SQLException {

        logger.logInfoMsg("POST [ ENTER ] - Received request to create a new crop fence for crop with ID: " + Long.toString(cropFence.getCropId()));
        ResponseEntity<?> response;
        
        CropFence createdCropFence;

        try {
            createdCropFence = cfService.createCropFence(cropFence);
            if (createdCropFence == null) {
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            else {
                long createdCfId = createdCropFence.getFenceId();
                response = new ResponseEntity<Long>(createdCfId, HttpStatus.CREATED);
            }
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to create a new crop fence");
        return response;

    }


    /**
     * Fetch persisted crop fence for crop
     * @param cropId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchCropFence",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchCropFence(
        @RequestParam long cropId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch fence for crop with ID: " + Long.toString(cropId));
        ResponseEntity<?> response;

        try {
            Optional<CropFence> ocf = cfService.fetchCropFenceForCrop(cropId);

            if (ocf.isPresent()) {
                CropFence cf = ocf.get();
                logger.logInfoMsg("Found crop fence");
                response = new ResponseEntity<CropFence>(cf, HttpStatus.OK);
            }
            else {
                logger.logWarnMsg("Crop fence is not found");
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to execute DB query");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch fence for crop");
        return response;

    }


    /**
     * Delete a persisted crop fence for crop
     * @param cropId
     * @return
     * @throws SQLException
     */
    @DeleteMapping(
        path = "/deleteCropFence"
    )
    public ResponseEntity<HttpStatus> deleteCropFence(
        @RequestParam long cropId
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete fence for crop with ID: " + Long.toString(cropId));
        ResponseEntity<HttpStatus> response;

        try {
            boolean deleted = false; 
            deleted = cfService.deleteCropFence(cropId);

            if (deleted) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to execute DB operation");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete fence for crop");
        return response;

    }


}
