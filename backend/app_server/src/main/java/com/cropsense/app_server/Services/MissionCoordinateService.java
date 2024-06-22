package com.cropsense.app_server.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.MissionCoordinate;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.MissionCoordinateRepository;

@Service
public class MissionCoordinateService {
    
    @Autowired
    private MissionCoordinateRepository mcRepo;

    // define and create logger object instance
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new mission coordinate
     * @param newMc
     * @return
     * @throws SQLException
     */
    public MissionCoordinate createMissionCoordinate(MissionCoordinate newMc) throws SQLException {
        
        logger.logInfoMsg("Persisting a new mission coordinate for mission with ID: " + Long.toString(newMc.getMissionId()));
        
        try {
            MissionCoordinate mc = mcRepo.save(newMc);
            logger.logInfoMsg("Successfully persisted a new mission coordinate");
            return mc;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to persist a new coordinate \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }


    /**
     * Fetch mission coordinates for a mission
     * @param missionId
     * @return
     * @throws SQLException
     */
    public List<MissionCoordinate> fetchCoordinatesForMission(long missionId) throws SQLException {

        logger.logInfoMsg("Fetching cooridnates for mission with ID: " + Long.toString(missionId));

        try {
            List<MissionCoordinate> mcList = mcRepo.findByMissionId(missionId);
            logger.logInfoMsg("Successfully fetched mission coordinates for mission with ID: " + Long.toString(missionId));
            return mcList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch coordinates for mission with ID: " + Long.toString(missionId));
            throw new SQLException();
        }

    }

    

    /**
     * Fetch mission coordinate by ID
     * @param mcId
     * @return
     * @throws SQLException
     */
    public Optional<MissionCoordinate> fetchMissionCoordinate(long mcId) throws SQLException {
        
        logger.logInfoMsg("Fetching mission coordinate with ID: " + Long.toString(mcId));

        try {
            Optional<MissionCoordinate> omc = mcRepo.findById(mcId);
            if (omc.isPresent()) {
                logger.logInfoMsg("Found & fetched mission coordinated with ID: " + Long.toString(mcId));
            }
            else {
                logger.logWarnMsg("The mission coordinate with ID: " + Long.toString(mcId) + " not found - this could be an error!");
            }
            return omc;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission coordinate with ID: " + Long.toString(mcId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Delete a persisted mission coordinate by its ID
     * @param mcId
     * @throws SQLException
     */
    public void deleteMissionCoordinate(long mcId) throws SQLException {

        logger.logInfoMsg("Deleting mission coordinate with ID: " + Long.toString(mcId));

        try {
            mcRepo.deleteById(mcId);
            logger.logInfoMsg("Mission coordinate with ID: " + Long.toString(mcId) + " has been deleted");
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to delete the mission coordinate: " + Long.toString(mcId));
            throw new SQLException();
        }

    }


    /**
     * Delete persisted coordinates for a mission
     * @param missionId
     * @throws SQLException
     */
    public void deleteCoordinatesForMission(long missionId) throws SQLException {

        logger.logInfoMsg("Deleting coordinates for mission with ID: " + Long.toString(missionId));

        try {
            mcRepo.deleteByMissionId(missionId);
            logger.logInfoMsg("Deleted coordinates for mission with ID: " + Long.toString(missionId));
        }
        catch (Exception e) {
            logger.logInfoMsg("Failed to delete coordinates for mission with ID: " + Long.toString(missionId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    } 

}
