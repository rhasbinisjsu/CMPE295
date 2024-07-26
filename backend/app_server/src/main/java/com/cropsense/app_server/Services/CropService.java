package com.cropsense.app_server.Services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.Crop;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.CropRepository;

@Service
public class CropService {
    
    @Autowired
    private CropRepository cropRepo;

    // define and instantiation new logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new crop
     * @param newCrop
     * @return
     * @throws SQLException
     */
    public Crop createCrop(Crop newCrop) throws SQLException {

        long farmId = newCrop.getFarmId();
        logger.logInfoMsg("Persisiting new crop for farm with ID: " + Long.toString(farmId));

        Crop createdCrop;
        try {
            createdCrop = cropRepo.save(newCrop);
            logger.logInfoMsg("Successfully persisted the new crop");
        }
        catch(Exception e) {
            logger.logErrorMsg("Failed to execute persist code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return createdCrop;

    }


    /**
     * Fetch all crops for a farm
     * @param farmId
     * @return
     * @throws SQLException
     */
    public List<Crop> fetchCropsForFarm(long farmId) throws SQLException {
        
        logger.logInfoMsg("Fetching all persisted crops for farm with ID: " + Long.toString(farmId));

        try {
            List<Crop> cropList = cropRepo.findByFarmId(farmId);
            logger.logInfoMsg("Successfully fetched all crops for farm with ID: " + Long.toString(farmId));
            return cropList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute fetch code block for crops \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch all active crops for farm
     * @param farmId
     * @return
     * @throws SQLException
     */
    public List<Crop> fetchActiveCropsForFarm(long farmId) throws SQLException {

        logger.logInfoMsg("Fetching all active crops for farm with ID: " + Long.toString(farmId));

        try {
            List<Crop> cropList = cropRepo.findByFarmIdAndActiveFlag(farmId, true);
            logger.logInfoMsg("Successfully fetched all active crops for farm with ID: " + Long.toString(farmId));
            return cropList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute fetch code block for crops \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch persisted crops by status
     * @param farmId
     * @param status
     * @return
     * @throws SQLException
     */
    public List<Crop> fetchCropsForFarmByStatus(long farmId, String status) throws SQLException {

        logger.logInfoMsg("Fetching crops with status: " + status + " for farm with ID: " + Long.toString(farmId));

        try {
            List<Crop> cropList = cropRepo.findByFarmIdAndStatus(farmId, status);
            logger.logInfoMsg("Successfully fetched crops with status: " + status + " for farm with ID: " + Long.toString(farmId));
            return cropList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to exceute query block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch persisted crop for farm by species
     * @param farmId
     * @param species
     * @return
     * @throws SQLException
     */
    public List<Crop> fetchCropForFarmBySpecies(long farmId, String species) throws SQLException {
        
        logger.logInfoMsg("Fetching crops for farm with ID: " + Long.toString(farmId) + " of species: " + species);

        try {
            List<Crop> cropList = cropRepo.findByFarmIdAndCropSpecies(farmId, species);
            logger.logInfoMsg("Successfully fetched crops of species: " + species + " for farm with ID: " + Long.toString(farmId));
            return cropList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute query block \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }


    /**
     * Fetch persisted crops for a farm by year
     * @param farmId
     * @param year
     * @return
     * @throws SQLException
     */
    public List<Crop> fetchCropsForFarmByYear(long farmId, String year) throws SQLException {

        logger.logInfoMsg("Fetching crops for farm with ID: " + Long.toString(farmId) + " where year: " + year);

        try {
            // declare start date
            String startDate = year + "-01-01";
            // declare end date
            String endDate = year + "-12-31";

            // convert strings to java.sql.date
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);

            List<Crop> cropList = cropRepo.findByFarmIdAndStartDateBetween(farmId, sqlStartDate, sqlEndDate);
            logger.logInfoMsg("Successfully fetched crops for seasons in year: " + year + " for farm with ID: " + Long.toString(farmId));
            return cropList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to exceute query block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch a persisted crop by its ID
     * @param cropId
     * @return
     * @throws SQLException
     */
    public Optional<Crop> fetchCropById(long cropId) throws SQLException {

        logger.logInfoMsg("Fetching crop with ID: " + Long.toString(cropId));

        try {
            Optional<Crop> oc = cropRepo.findById(cropId);
            logger.logInfoMsg("Generated optional crop object for crop with ID: " + Long.toString(cropId));
            return oc;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to excecute query code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Update a persisted crop's transplanted amount
     * @param cropId
     * @param transAm
     * @return
     * @throws SQLException
     */
    public boolean updateCropTransplantAmount(long cropId, int transAm) throws SQLException {

        logger.logInfoMsg("Updating transplant amount for crop with ID: " + Long.toString(cropId) + " to: " + String.valueOf(transAm));
        boolean updated = false;

        try {

            Optional<Crop> oc = cropRepo.findById(cropId);
            
            if (oc.isPresent()){
                logger.logInfoMsg("Found crop with ID: " + Long.toString(cropId) + " ... proceeding");
                Crop c = oc.get();
                c.setTransplantAmount(transAm);
                cropRepo.save(c);
                logger.logInfoMsg("Updated transplant amount for crop with ID: " + Long.toString(cropId));
                updated = true;
            }
            else {
                logger.logWarnMsg("Crop with ID: " + Long.toString(cropId) + " is not found... Nothing to update");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Updated a persisted crop's cultivated amount
     * @param cropId
     * @param cultAm
     * @return
     * @throws SQLException
     */
    public boolean updateCropCultivatedAmount(long cropId, int cultAm) throws SQLException {

        logger.logInfoMsg("Updating cultivated amount for crop with ID: " + Long.toString(cropId) + " to: " + String.valueOf(cultAm));
        boolean updated = false; 

        try {

            Optional<Crop> oc = cropRepo.findById(cropId);

            if (oc.isPresent()) {
                logger.logInfoMsg("Found crop with ID: " + Long.toString(cropId) + " ... proceeding");
                Crop c = oc.get();
                c.setCultivatedAmount(cultAm);
                cropRepo.save(c);
                logger.logInfoMsg("Updated cultivated amount for crop with ID: " + Long.toString(cropId));
                updated = true;
            }
            else {
                logger.logWarnMsg("Crop with ID: " + Long.toString(cropId) + " is not found... Nothing to update");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Update a peristsed crop's active flag
     * @param cropId
     * @param flag
     * @return
     * @throws SQLException
     */
    public boolean updateCropIsActiveFlag(long cropId, boolean flag) throws SQLException {

        logger.logInfoMsg("Updating isActive flag for crop with ID: " + Long.toString(cropId) + " to: " + String.valueOf(flag));
        boolean updated = false;

        try {

            Optional<Crop> oc = cropRepo.findById(cropId);

            if (oc.isPresent()) {
                logger.logInfoMsg("Found crop with ID: " + Long.toString(cropId) + " ... proceeding");
                Crop c = oc.get();
                c.setActiveFlag(flag);
                cropRepo.save(c);
                logger.logInfoMsg("Updated isActive flag for crop with ID: " + Long.toString(cropId));
                updated = true;
            }
            else {
                logger.logWarnMsg("Crop with ID: " + Long.toString(cropId) + " is not found... Nothing to update");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Update a persisted crop's status
     * @param cropId
     * @param newStatus
     * @return
     * @throws SQLException
     */
    public boolean updateCropStatus(long cropId, String newStatus) throws SQLException {

        logger.logInfoMsg("Updating status for crop with ID: " + Long.toString(cropId) + " to: " + newStatus);
        boolean updated = false;

        try {

            Optional<Crop> oc = cropRepo.findById(cropId);

            if (oc.isPresent()) {
                logger.logInfoMsg("Found crop with ID: " + Long.toString(cropId) + " ... proceeding");
                Crop c = oc.get();
                c.setStatus(newStatus);
                cropRepo.save(c);
                logger.logInfoMsg("Updated status for crop with ID: " + Long.toString(cropId));
                updated = true;
            }
            else {
                logger.logWarnMsg("Crop with ID: " + Long.toString(cropId) + " is not found... Nothing to update");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Update a persisted crop's start date
     * @param cropId
     * @param startDate
     * @return
     * @throws SQLException
     */
    public boolean updateStartDate(long cropId, Date startDate) throws SQLException {

        logger.logInfoMsg("Updating start date to: " + startDate.toString() + " for crop with ID: " + Long.toString(cropId));
        boolean updated = false;

        try {

            Optional<Crop> oc = cropRepo.findById(cropId);

            if (oc.isPresent()) {
                logger.logInfoMsg("Found crop with ID: " + Long.toString(cropId) + " ... proceeding");
                Crop c = oc.get();
                c.setStartDate(startDate);
                cropRepo.save(c);
                logger.logInfoMsg("Updated start date for crop with ID: " + Long.toString(cropId));
                updated = true;
            }
            else {
                logger.logWarnMsg("Crop with ID: " + Long.toString(cropId) + " is not found... Nothing to update");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Update a persisted crop's end date
     * @param cropId
     * @param endDate
     * @return
     * @throws SQLException
     */
    public boolean updateEndDate(long cropId, Date endDate) throws SQLException {

        logger.logInfoMsg("Updating end date to: " + endDate.toString() + " for crop with ID: " + Long.toString(cropId));
        boolean updated = false;

        try {

            Optional<Crop> oc = cropRepo.findById(cropId);

            if (oc.isPresent()) {
                logger.logInfoMsg("Found crop with ID: " + Long.toString(cropId) + " ... proceeding");
                Crop c = oc.get();
                c.setEndDate(endDate);
                cropRepo.save(c);
                logger.logInfoMsg("Updated end date for crop with ID: " + Long.toString(cropId));
                updated = true;
            }
            else {
                logger.logWarnMsg("Crop with ID: " + Long.toString(cropId) + " is not found... Nothing to update");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute update code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Delete a persisted crop by its ID
     * @param cropId
     * @return
     * @throws SQLException
     */
    public boolean deleteCrop(long cropId) throws SQLException {

        logger.logInfoMsg("Deleteing crop with ID: " + Long.toString(cropId));
        boolean deleted = false;

        try {
            cropRepo.deleteById(cropId);
            deleted = true;
            logger.logErrorMsg("Deleted crop with ID: " + Long.toString(cropId));
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute delete code block \n Reason: " + e.getMessage());
        }

        return deleted;
    }

}
