package com.cropsense.app_server.Services;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.FarmFence;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.FarmFenceRepository;

@Service
public class FarmFenceService {
    
    @Autowired
    private FarmFenceRepository ffRepo;

    // define and create logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new farm fence
     * @param newFence
     * @return
     * @throws SQLException
     */
    public FarmFence createFarmFence(FarmFence newFence) throws SQLException {
        
        logger.logInfoMsg("Persisting a new farm fence for farm with ID: " + Long.toString(newFence.getFarmId()));

        try {
            return ffRepo.save(newFence);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to create a new farm fence");
            throw new SQLException();
        }
    }


    /**
     * Fetch a persisted farm fence for a farm 
     * @param farmId
     * @return
     * @throws SQLException
     */
    public Optional<FarmFence> fetchFarmFenceForFarm(long farmId) throws SQLException {
        
        logger.logInfoMsg("Fetching farm fence for farm with ID: " + Long.toString(farmId));
        
        try {
            return ffRepo.findByFarmId(farmId);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch farm fence for farm with ID: " + Long.toString(farmId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Delete a persisted farm fence by farmId
     * @param farmId
     * @throws SQLException 
     */
    public void deleteFarmFence(long farmId) throws SQLException {
        
        logger.logInfoMsg("Deleting farm fence for farm with ID: " + Long.toString(farmId));
        
        try {
            ffRepo.deleteByFarmId(farmId);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to delete farm fence for farm with ID: " + Long.toString(farmId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }   

}
