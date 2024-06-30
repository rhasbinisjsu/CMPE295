package com.cropsense.app_server.Services;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.Farm;
import com.cropsense.app_server.Entities.Embeddables.Address;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.FarmRepository;

@Service
public class FarmService {
    
    @Autowired
    private FarmRepository fRepo;

    // define and create logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new farm
     * @param newFarm
     * @return
     * @throws SQLException
     */
    public Farm createFarm(Farm newFarm) throws SQLException {
        
        logger.logInfoMsg("Persisting the new farm");

        try {
            return fRepo.save(newFarm);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to persis the new farm" + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }


    /**
     * Fetch persisted farms for an owner
     * @param ownerId
     * @return
     * @throws SQLException
     */
    public List<Farm> fetchFarmsByOwner(long ownerId) throws SQLException {
        
        logger.logInfoMsg("Fetching farms for owner with ID: " + Long.toString(ownerId));
        
        try {
            List<Farm> fList = fRepo.findByOwnerId(ownerId);
            return fList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch Farms for owner with ID: " + Long.toString(ownerId) + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Fetch a peristed farm by its ID
     * @param farmId
     * @return
     * @throws SQLException
     */
    public Optional<Farm> fetchFarmById(long farmId) throws SQLException {
        
        logger.logInfoMsg("Fetching farm with ID: " + Long.toString(farmId));

        try {
            return fRepo.findById(farmId);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch farm with ID: " + Long.toString(farmId) + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }

    
    /**
     * Fetch a persisted farm for an owner by its name
     * @param ownerId
     * @param name
     * @return
     */
    public Optional<Farm> fetchFarmByNameForOwner(long ownerId, String name) throws SQLException {

        logger.logInfoMsg("Fetching farm with name: " + name + " for owner with ID: " + Long.toString(ownerId));

        try {
            Optional<Farm> of = fRepo.findByOwnerIdAndName(ownerId, name);
            return of;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch farm with name: " + name + " for owner with ID: " + Long.toString(ownerId) + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Update a Persisted farm's name
     * @param farmId
     * @param newName
     * @return
     * @throws SQLException
     */
    public Farm changeFarmName(long farmId, String newName) throws SQLException {

        logger.logInfoMsg("Changing name for farm with ID: " + Long.toString(farmId));

        try {
            Farm f = fRepo.findById(farmId).get();
            f.setName(newName);
            fRepo.save(f);
            logger.logInfoMsg("Updated name farm name successfully");
            return f;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to change name for farm with ID: " + Long.toString(farmId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }

    
    /**
     * Update a persisted farm's address
     * @param farmId
     * @param newAddress
     * @return
     * @throws SQLException
     */
    public Farm changeFarmAddress(long farmId, Address newAddress) throws SQLException {
        
        logger.logInfoMsg("Changing address for farm with ID: " + Long.toString(farmId));
        
        try {
            Farm f = fRepo.findById(farmId).get();
            f.setAddress(newAddress);
            fRepo.save(f);
            logger.logInfoMsg("Updated address for farm with ID: " + Long.toString(farmId));
            return f;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to change address for farm with ID: " + Long.toString(farmId) + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }

    /**
     * Delete a persisted farm by its ID
     * @param farmId
     * @throws SQLException
     */
    public void deleteFarm(long farmId) throws SQLException {
        
        logger.logInfoMsg("Deleting farm with ID: " + Long.toString(farmId) );

        try {
            fRepo.deleteById(farmId);
            logger.logInfoMsg("Successfully deleted farm with ID: " + Long.toString(farmId));
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to delete farm with ID: " + Long.toString(farmId) + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }



}
