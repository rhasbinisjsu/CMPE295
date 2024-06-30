package com.cropsense.app_server.Services;

import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.FenceCoordinate;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.FenceCoordinateRepository;

@Service
public class FenceCoordinateService {
    
    @Autowired
    private FenceCoordinateRepository fcRepo;

    // define and create logger object
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new fence coordinate
     * @param newFc
     * @return
     * @throws SQLException
     */
    public FenceCoordinate createFenceCoordinate(FenceCoordinate newFc) throws SQLException {

        logger.logInfoMsg("Persisting new fence coordinate");

        try {
            return fcRepo.save(newFc);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to create a new fence coordinate \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }


    /**
     * Fetch persisted coordinates for a farm fence
     * @param fenceId
     * @return
     * @throws SQLException
     */
    public List<FenceCoordinate> fetchCoordinatesForFence(long fenceId, String fenceType) throws SQLException {

        logger.logInfoMsg("Fetching coordinates for " + fenceType + " fence with ID: " + Long.toString(fenceId));

        try {
            List<FenceCoordinate> fcList = fcRepo.findByFenceIdAndFenceType(fenceId, fenceType);
            logger.logInfoMsg("Fetched coordinates for " + fenceType + " fence with ID: " + Long.toString(fenceId));
            return fcList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to fetch coordinates for " + fenceType + " fence with ID: " + Long.toString(fenceId) + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Delete persisted coordinates for a fence
     * @param fenceId
     * @throws SQLException
     */
    public void deleteCoordinatesForFence(long fenceId, String fenceType) throws SQLException {

        logger.logInfoMsg("Deleteing fence coordinates for " + fenceType + " fence with ID: " + Long.toString(fenceId));

        try {
            fcRepo.deleteByFenceIdAndFenceType(fenceId, fenceType);
            logger.logInfoMsg("Successfully deleted fence coordinates for " + fenceType + " fence with ID: " + Long.toString(fenceId));
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to delete fence coordinates for " + fenceType + " fence with ID: " + Long.toString(fenceId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }

}
