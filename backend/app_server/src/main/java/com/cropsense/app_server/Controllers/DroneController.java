package com.cropsense.app_server.Controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.app_server.Entities.Drone;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.DroneService;

@RestController
@RequestMapping("/CropSense/AppServer/DroneController")
public class DroneController {

    @Autowired
    private DroneService dService;

    // declare & create logger object instance
    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new drone
     * @param newDrone
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createDrone",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> createDrone(
        @RequestBody Drone newDrone
    ) throws SQLException {

        // caputure data
        long ownerId = newDrone.getOwnerId();
        String newDroneName = newDrone.getDroneName();

        logger.logInfoMsg("POST [ ENTER ] - Received request to create a new drone for user with ID: " + Long.toString(ownerId));

        // declare response object
        ResponseEntity<HttpStatus> response;

        // check if the name is available
        Optional<Drone> op = dService.fetchDroneByName(ownerId, newDroneName);
        if (op.isPresent()) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
            logger.logWarnMsg("Drone name: " + newDroneName + " already exists for user with ID: " + Long.toString(ownerId));
        }
        else {
            logger.logInfoMsg("Drone name is available for user... persisting the new drone");
            dService.createDrone(newDrone);
            response = new ResponseEntity<>(HttpStatus.CREATED);
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to create a new drone");
        return response;

    }


    /**
     * Fetch persisted drones for a user
     * @param ownerId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchDronesForUser",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Drone>> fetchDronesForUser(
        @RequestParam long ownerId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch drones for user with ID: " + Long.toString(ownerId));
        List<Drone> dList = dService.fetchDronesForOwner(ownerId);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch drones for user with ID: " + Long.toString(ownerId));
        return new ResponseEntity<List<Drone>>(dList, HttpStatus.OK);

    }


    /**
     * Fetch a persisted drone by its ID
     * @param droneId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchDroneById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchDroneById(
        @RequestParam long droneId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch drone with ID: " + Long.toString(droneId));

        // declare response object
        ResponseEntity<?> response;
        
        Optional<Drone> od = dService.fetchDroneById(droneId);

        if (od.isPresent()) {
            logger.logInfoMsg("Found drone with ID: " + Long.toString(droneId));
            Drone d = od.get();
            response = new ResponseEntity<Drone>(d, HttpStatus.OK);
        }
        else {
            logger.logWarnMsg("Could not fine drone with ID: " + Long.toString(droneId));
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch drone with ID: " + Long.toString(droneId));
        return response;

    }

    /**
     * Update a persisted drone's battery level
     * @param droneId
     * @param batteryLevel
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateDroneBatteryLevel",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateDroneBatteryLevel(
        @RequestParam long droneId,
        @RequestParam int batteryLevel
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update battery level for drone with ID: " + Long.toString(droneId));

        ResponseEntity<HttpStatus> response;

        boolean updated = dService.updateBatteryLevel(droneId, batteryLevel);
        if (updated == true) {
            logger.logInfoMsg("Battery level updated successfully");
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            logger.logWarnMsg("Battery level was not updated");
            response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update battery level for drone with ID: " + Long.toString(droneId));
        return response; 

    }


    /**
     * Update a persisted drone's docking status
     * @param droneId
     * @param docked
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateDockingFlag",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateDockingFlag(
        @RequestParam long droneId,
        @RequestParam boolean docked
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update docking status to: " + String.valueOf(docked) + " for drone with ID: " + Long.toString(droneId));

        ResponseEntity<HttpStatus> response;

        boolean updated = dService.updateDockedFlag(droneId, docked);
        if (updated == true) {
            logger.logInfoMsg("Drone docking flag has been successfully updated");
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            logger.logWarnMsg("Docking status was not updated");
            response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to updating docking status for drone with ID: " + Long.toString(droneId));
        return response;
    }



    /**
     * Update in-service flag for a persisted drone
     * @param droneId
     * @param InService
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateInServiceFlag",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateInServiceFlag(
        @RequestParam long droneId,
        @RequestParam boolean InService
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update In-Service flag to: " + String.valueOf(InService) + " for drone with ID: " + Long.toString(droneId));

        ResponseEntity<HttpStatus> response;

        boolean inServiceUpdated = dService.updateInServiceFlag(droneId, InService);
        if (inServiceUpdated == true) {
            logger.logInfoMsg("Drone In-service flag has been successfully updated");
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            logger.logWarnMsg("In-service flag was not updated");
            response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update In-Service flag for drone with ID: " + Long.toString(droneId));
        return response;
    }
    

    /**
     * Add equipment to a persisted drone
     * @param droneId
     * @param equipment
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/addEquipmentToDrone",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> addEquipmentToDrone(
        @RequestParam long droneId,
        @RequestParam String equipment
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to add new drone equipment: " + equipment + " for drone with ID: " + Long.toString(droneId));

        ResponseEntity<HttpStatus> response;

        boolean updated = dService.addEquipmentToDrone(droneId, equipment);
        if (updated == true) {
            logger.logInfoMsg("Equipment added for drone successfully");
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            logger.logWarnMsg("Equipment has not been updated for drone");
            response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to add new equipment for drone with ID: " + Long.toString(droneId));
        return response;

    }


    /**
     * Remove equipment from a persisted drone
     * @param droneId
     * @param equipment
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/removeEquipmentForDrone",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> removeEquipmentForDrone(
        @RequestParam long droneId,
        @RequestParam String equipment
    ) throws SQLException {
     
        logger.logInfoMsg("PUT [ ENTER ] - Received request to remove equipment: " + equipment + " from drone with ID: " + Long.toString(droneId));

        ResponseEntity<HttpStatus> response;

        boolean updated = dService.removeEquipmentFromDrone(droneId, equipment);
        if (updated == true) {
            logger.logInfoMsg("Successfully removed equipment for drone");
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            logger.logWarnMsg("Equipment has not been updated for drone");
            response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to remove equipment from drone with ID: " + Long.toString(droneId));
        return response;
        
    }


    /**
     * Delete a persisted drone
     * @param droneId
     * @return
     * @throws SQLException
     */
    @DeleteMapping(
        path = "/deleteDrone"
    )
    public ResponseEntity<HttpStatus> deleteDrone(
        @RequestParam long droneId
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete drone with ID: " + Long.toString(droneId));
        dService.deleteDrone(droneId);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete drone");
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
