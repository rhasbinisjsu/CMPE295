package com.cropsense.app_server.Controllers;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cropsense.app_server.Entities.Farm;
import com.cropsense.app_server.Entities.Embeddables.Address;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.FarmService;


@RestController
@RequestMapping("/CropSense/AppServer/FarmController")
public class FarmController {

    @Autowired
    private FarmService fService;

    // Define and create logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new farm
     * @param newFarm
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createFarm",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createFarm(
        @RequestBody Farm newFarm
    ) throws SQLException{

        logger.logInfoMsg("POST [ ENTER ] - Received request to create new farm for user with ID " + Long.toString(newFarm.getOwnerId()));
        
        // define response
        ResponseEntity<?> response;
        
        // check for name
        logger.logInfoMsg("Checking that name is valid");
        Optional<Farm> of = fService.fetchFarmByNameForOwner(newFarm.getOwnerId(), newFarm.getName());
        if (of.isPresent()) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.logErrorMsg("Name is not available for new farm creation");;
        }
        else {
            logger.logInfoMsg("Name is available for the new farm creation");
            Farm createdFarm = fService.createFarm(newFarm);
            long createdFarmId = createdFarm.getId();
            response = new ResponseEntity<Long>(createdFarmId,HttpStatus.CREATED);
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to create a new farm");

        return response;
        
    }


    /**
     * Fetch a list of persisted farms for a user
     * @param ownerId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchFarmsForOwner",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Farm>> fetchFarmsForOwner(@RequestParam long ownerId) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch farms for owner with ID: " + Long.toString(ownerId));
        List<Farm> fList = fService.fetchFarmsByOwner(ownerId);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch farms for owner");
        return new ResponseEntity<List<Farm>>(fList, HttpStatus.OK);

    }


    /**
     * Fetch a persisted farm by its ID
     * @param farmId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchFarmById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Farm> fetchFarmById(@RequestParam long farmId) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch farm with ID: " + Long.toString(farmId));
        Farm f = fService.fetchFarmById(farmId).get();
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch farm with ID: " + Long.toString(farmId));
        return new ResponseEntity<Farm>(f, HttpStatus.OK);

    }


    /**
     * Fetch a persisted farm by name for owner
     * @param ownerId
     * @param farmName
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchFarmByOwnerAndName",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Farm> fetchFarmByOwnerAndName(
        @RequestParam long ownerId,
        @RequestParam String farmName
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch farm with name: " + farmName + " for owner: " + Long.toString(ownerId));
        Farm f = fService.fetchFarmByNameForOwner(ownerId, farmName).get();
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch farm with name: " + farmName + " for owner: " + Long.toString(ownerId));
        return new ResponseEntity<Farm>(f, HttpStatus.OK);

    }


    /**
     * Change a persisted farm's name
     * @param farmId
     * @param newName
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/changeFarmName",
        consumes = MediaType.TEXT_PLAIN_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> changeFarmName(
        @RequestParam long farmId,
        @RequestParam long ownerId,
        @RequestBody String newName
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to change farm name for farm with ID: " + Long.toString(ownerId));

        // define response
        ResponseEntity<HttpStatus> response;

        Optional<Farm> of = fService.fetchFarmByNameForOwner(ownerId, newName);
        if (of.isPresent()) {
            logger.logErrorMsg("Unable to update farm name - Farm with name '" + newName + "' already exists for this owner");
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            logger.logInfoMsg("Farm name is available -> changing name");
            fService.changeFarmName(farmId, newName);
            response = new ResponseEntity<>(HttpStatus.OK);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to change farm name");

        return response;

    }


    /**
     * Change a persisted farm's address
     * @param farmId
     * @param newAddress
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/changeFarmAddress",
        consumes = MediaType.TEXT_PLAIN_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> changeFarmAddress(
        @RequestParam long farmId,
        @RequestBody Address newAddress
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to change farm address for farm with ID: " + Long.toString(farmId));
        fService.changeFarmAddress(farmId, newAddress);
        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to change farm address");
        return new ResponseEntity<>(HttpStatus.OK);

    }


    /**
     * Delete a persisted farm by its ID
     * @param farmId
     * @return
     * @throws SQLException
     */
    @DeleteMapping(
        path = "/deleteFarm",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> deleteFarm(@RequestParam long farmId) throws SQLException {
        
        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete farm with ID: " + Long.toString(farmId));
        fService.deleteFarm(farmId);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete farm");
        return new ResponseEntity<>(HttpStatus.OK);

    }
    
}
