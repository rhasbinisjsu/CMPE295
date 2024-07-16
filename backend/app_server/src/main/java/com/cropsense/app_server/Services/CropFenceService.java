package com.cropsense.app_server.Services;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.CropFence;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.CropFenceRepository;

@Service
public class CropFenceService {
    
    @Autowired
    private CropFenceRepository cfRepo;

    // define and instantiate logger object
    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new crop fence
     * @param newCropFence
     * @return
     * @throws SQLException
     */
    public boolean createCropFence(CropFence newCropFence) throws SQLException {
     
        logger.logInfoMsg("Persisting a new crop fence for crop with ID: " + Long.toString(newCropFence.getCropId()));
        boolean created = false;

        try {
            // check the fence can be created
            Optional<CropFence> ocf = cfRepo.findByCropId(newCropFence.getCropId());
            
            if (ocf.isEmpty()) {
                cfRepo.save(newCropFence);
                created = true;
                logger.logInfoMsg("Successfully persisted the new crop fence");
            }
            else {
                logger.logWarnMsg("Crop fence already exits... Not persisting new crop fence");
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute POST code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return created;

    }



    /**
     * Fetch a persisted crop fence for a crop
     * @param cropId
     * @return
     * @throws SQLException
     */
    public Optional<CropFence> fetchCropFenceForCrop(long cropId) throws SQLException {

        logger.logInfoMsg("Fetching crop fence for crop with ID: " + Long.toString(cropId));

        try {
            Optional<CropFence> ocf = cfRepo.findByCropId(cropId);
            logger.logInfoMsg("Successfully fetched the crop fence optional object for crop with ID: " + Long.toString(cropId));
            return ocf;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute GET code block and generate the Optional<CropFence> object \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Delete a persisted crop fence
     * @param cropId
     * @return
     * @throws SQLException
     */
    public boolean deleteCropFence(long cropId) throws SQLException {

        logger.logInfoMsg("Deleting crop fence for crop with ID: " + Long.toString(cropId));
        boolean deleted = false;

        try {
            cfRepo.deleteByCropId(cropId);
            deleted = true;
            logger.logInfoMsg("Successfully deleted the crop fence for crop with ID: " + Long.toString(cropId));
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute DELETE code block \n Reason: " + e.getMessage());
        }

        return deleted;

    }


}
