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

import com.cropsense.app_server.Entities.FarmFence;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.FarmFenceService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/CropSense/AppServer/FarmFenceController")
public class FarmFenceController {
    
    @Autowired
    private FarmFenceService ffService;

    // define and create logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new farm fence
     * @param newFence
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createFarmFence",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createFarmFence(
        @RequestBody FarmFence newFence
    ) throws SQLException {

        logger.logInfoMsg("POST [ ENTER ] - Received request to create new farm fence for farm with ID: " + Long.toString(newFence.getFarmId()));

        // define response
        ResponseEntity<?> response;
        
        // check farm fence does not exist for farmId
        logger.logInfoMsg("Checking if farm fence already exists for farm");
        Optional<FarmFence> off = ffService.fetchFarmFenceForFarm(newFence.getFarmId());
        if (off.isPresent()) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
            logger.logErrorMsg("Failed to create the new farm fence since once already exists for farm with ID: " + Long.toString(newFence.getFarmId()));
        }
        else {
            FarmFence createdFarmFence = ffService.createFarmFence(newFence);
            long createdFFId = createdFarmFence.getFenceId();
            response = new ResponseEntity<Long>(createdFFId,HttpStatus.CREATED);
            logger.logInfoMsg("Successfully created farm fence for farm with ID: " + Long.toString(newFence.getFarmId()));
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to create new farm fence");
        return response;
    }


    /**
     * Fetch a persisted farm fence for a farm
     * @param farmId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchFarmFenceForFarm",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchFarmFence(@RequestParam long farmId) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch the farm fence for farm with ID: " + Long.toString(farmId));

        // define response
        ResponseEntity<?> response;

        // check for fence
        logger.logInfoMsg("Checking if farm fence exists for farm");
        Optional<FarmFence> off = ffService.fetchFarmFenceForFarm(farmId);

        if (off.isPresent()) {
            response = new ResponseEntity<FarmFence>(off.get(), HttpStatus.OK);
            logger.logInfoMsg("Found farm fence for farm with ID: " + Long.toString(farmId));
        }
        else {
            response = new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            logger.logWarnMsg("A farm fence does not exist for farm with ID: " + Long.toString(farmId));
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch farm fence");
        return response;

    }


    /**
     * Deletes a persisted farm fence
     * @param farmId
     * @return
     * @throws SQLException
     */
    @Transactional
    @DeleteMapping(
        path = "/deleteFarmFenceForFarm"
    )
    public ResponseEntity<HttpStatus> deleteFarmFence(@RequestParam long farmId) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete farm fence for farm with ID: " + Long.toString(farmId));
        ffService.deleteFarmFence(farmId);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete farm fence");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
