package com.cropsense.app_server.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.Drone;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.DroneRepository;

@Service
public class DroneService {
    
    @Autowired
    private DroneRepository dRepo;

    // Declare and create logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new drone
     * @param newDrone
     * @return
     * @throws SQLException
     */
    public Drone createDrone(Drone newDrone) throws SQLException {

        logger.logInfoMsg("Persisting the new drone");

        try {
            Drone d = dRepo.save(newDrone);
            logger.logInfoMsg("Successfully added the new drone");
            return d;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to persist drone \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch a persisted drone by its name for owner
     * @param ownerId
     * @param dName
     * @return
     * @throws SQLException
     */
    public Optional<Drone> fetchDroneByName(long ownerId, String dName) throws SQLException {

        logger.logInfoMsg("Fetching drone with name: " + dName + " for owner with ID: " + Long.toString(ownerId));

        try {
            Optional<Drone> od = dRepo.findByOwnerIdAndDroneName(ownerId, dName);
            logger.logInfoMsg("Generated optional drone object for drone with name: " + dName + " and owner with ID: " + Long.toString(ownerId));
            return od;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to query for drone with name: " + dName + " for owner with ID: " + Long.toString(ownerId));
            throw new SQLException();
        }

    }


    /**
     * Fetch a persisted drone by its ID
     * @param droneId
     * @return
     * @throws SQLException
     */
    public Optional<Drone> fetchDroneById(long droneId) throws SQLException {
        
        logger.logInfoMsg("Fetching drone with ID: " + Long.toString(droneId));

        try {
            Optional<Drone> od = dRepo.findById(droneId);
            logger.logInfoMsg("Generated optional drone object for drone with ID: " + Long.toString(droneId));
            return od;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to query for drone with ID:" + Long.toString(droneId));
            throw new SQLException();
        }
    }


    /**
     * Fetch all persisted drones for owner
     * @param ownerId
     * @return
     * @throws SQLException
     */
    public List<Drone> fetchDronesForOwner(long ownerId) throws SQLException {

        logger.logInfoMsg("Fetching drones for owner with ID: " + Long.toString(ownerId));

        try {
            List<Drone> dList = dRepo.findByOwnerId(ownerId);
            logger.logInfoMsg("Successfully fetched drones for owner with ID: " + Long.toString(ownerId));
            return dList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to fetch drones for owner with ID: " + Long.toString(ownerId));
            throw new SQLException();
        }
    }


    /**
     * Update a drone's battery level
     * @param droneId
     * @param batteryLevel
     * @return
     * @throws SQLException
     */
    public boolean updateBatteryLevel(long droneId, int batteryLevel) throws SQLException {

        logger.logInfoMsg("Updating battery status for drone with ID: " + Long.toString(droneId));

        boolean updated = false;

        try {
            // fetch the drone
            Optional<Drone> od = dRepo.findById(droneId);
            
            // drone found
            if (od.isPresent()) {
                logger.logInfoMsg("Found drone with ID: " + Long.toString(droneId) + " ... proceeding to update battery level");
                Drone d = od.get();
                d.setBatteryLevel(batteryLevel);
                dRepo.save(d);
                logger.logInfoMsg("Battery level for drone with ID: " + Long.toString(droneId) + " has been updated to: " + String.valueOf(batteryLevel));
                updated = true;
            }
            else {
                logger.logWarnMsg("Drone with ID: " + Long.toString(droneId) + " is not found... No drone will be updated");
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Update a drone's docked status
     * @param droneId
     * @param bool
     * @return
     * @throws SQLException
     */
    public boolean updateDockedFlag(long droneId, boolean bool) throws SQLException {

        logger.logInfoMsg("Updating docking status for drone with ID: " + Long.toString(droneId) + " to: " + String.valueOf(bool));

        boolean updated = false;

        try {
            // fetch the drone
            Optional<Drone> od = dRepo.findById(droneId);
            
            // drone found
            if (od.isPresent()) {
                logger.logInfoMsg("Found drone with ID: " + Long.toString(droneId) + " ... proceeding to update");
                Drone d = od.get();
                d.setDocked(bool);
                dRepo.save(d);
                logger.logInfoMsg("Docking status for drone with ID: " + Long.toString(droneId) + " has been updated to: " + String.valueOf(bool));
                updated = true;
            }
            else { // drone not found
                logger.logWarnMsg("Drone with ID: " + Long.toString(droneId) + " is not found... No drone docking status will be updated");
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }

    
    /**
     * Update a drone's InService flag
     * @param droneId
     * @param bool
     * @return
     * @throws SQLException
     */
    public boolean updateInServiceFlag(long droneId, boolean bool) throws SQLException {

        logger.logInfoMsg("Updating InService flag for drone with ID: " + Long.toString(droneId) + " to: " + String.valueOf(bool));

        boolean updated = false;

        try {
            Optional<Drone> od = dRepo.findById(droneId);
            
            if (od.isPresent()) {
                logger.logInfoMsg("Found drone with ID: " + Long.toString(droneId) + " ... proceeding to update");
                Drone d = od.get();
                d.setInService(bool);
                dRepo.save(d);
                updated = true;
                logger.logInfoMsg("InService flag for drone with ID: " + Long.toString(droneId) + " has been updated to: " + String.valueOf(bool));
            }
            else {
                logger.logWarnMsg("Drone with ID: " + Long.toString(droneId) + " is not found... No drone will be updated");
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Add equipment to a persisted drone
     * @param droneId
     * @param equipment
     * @return
     * @throws SQLException
     */
    public boolean addEquipmentToDrone(long droneId, String equipment) throws SQLException {

        logger.logInfoMsg("Updating equipment list for drone with ID: " + Long.toString(droneId));

        boolean updated = false;

        try {
            Optional<Drone> od = dRepo.findById(droneId);

            if (od.isPresent()) {
                logger.logInfoMsg("Found drone with ID: " + Long.toString(droneId) + " ... Adding equipment: " + equipment);
                Drone d = od.get();
                d.addEquipment(equipment);
                dRepo.save(d);
                updated = true;
                logger.logInfoMsg("Added equipment: " + equipment + " to drone with ID: " + Long.toString(droneId));
            }
            else {
                logger.logWarnMsg("Drone with ID: " + Long.toString(droneId) + " is not found... No drone will be updated");
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;
    }


    /**
     * Remove equipment from a persisted drone
     * @param droneId
     * @param equipment
     * @return
     * @throws SQLException
     */
    public boolean removeEquipmentFromDrone(long droneId, String equipment) throws SQLException {
        
        boolean updated = false;

        try {
            Optional<Drone> od = dRepo.findById(droneId);
            
            if (od.isPresent()) {
                logger.logInfoMsg("Found drone with ID: " + Long.toString(droneId) + " ... Removing equipment: " + equipment);
                Drone d = od.get();
                d.removeEquipment(equipment);
                dRepo.save(d);
                updated = true;
                logger.logInfoMsg("Removed equipment: " + equipment + " from drone with ID: " + Long.toString(droneId));
            }
            else {
                logger.logWarnMsg("Drone with ID: " + Long.toString(droneId) + " is not found... No drone will be updated");
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Delete a persisted drone
     * @param droneId
     * @throws SQLException
     */
    public void deleteDrone(long droneId) throws SQLException {

        logger.logInfoMsg("Deleting drone with ID: " + Long.toString(droneId));

        try {
            dRepo.deleteById(droneId);
            logger.logInfoMsg("Deleted drone with ID: " + Long.toString(droneId));
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }

}
