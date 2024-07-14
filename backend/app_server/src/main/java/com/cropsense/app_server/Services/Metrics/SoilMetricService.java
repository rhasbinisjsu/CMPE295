package com.cropsense.app_server.Services.Metrics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.Metrics.SoilMetric;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.Metrics.SoilMetricRepository;

import jakarta.transaction.Transactional;

@Service
public class SoilMetricService {
    
    @Autowired
    private SoilMetricRepository smRepo;

    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new soil metric
     * @param newSoilMetric
     * @return
     * @throws SQLException
     */
    public boolean postSoilMetric(SoilMetric newSoilMetric) throws SQLException {

        long cropId = newSoilMetric.getCropId();

        logger.logInfoMsg("Persisting a new soil metric for crop with ID: " + Long.toString(cropId));
        boolean posted = false;

        try {
            smRepo.save(newSoilMetric);
            posted = true;
            logger.logInfoMsg("New soil metric has been persisted successfully");
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to persist the new soil metric... throwing exception");
            throw new SQLException();
        }

        return posted;

    }


    /**
     * Fetch a persisted soil metric by its ID
     * @param smId
     * @return
     * @throws SQLException
     */
    public Optional<SoilMetric> fetchSoilMetricById(long smId) throws SQLException {

        logger.logInfoMsg("Fetching the soil metric with ID: " + Long.toString(smId));

        try {
            Optional<SoilMetric> osm = smRepo.findById(smId);
            logger.logInfoMsg("Generated the Optional<SoilMetric> object for metric with ID: " + Long.toString(smId));
            return osm;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute GET code block... throwing exception \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch all persisted soil metrics for a crop
     * @param cropId
     * @return
     * @throws SQLException
     */
    public List<SoilMetric> fetchSoilMetricsForCrop(long cropId) throws SQLException {

        logger.logInfoMsg("Fetching all soil metrics for a crop with ID: " + Long.toString(cropId));

        try {
            List<SoilMetric> smList = smRepo.findByCropId(cropId);
            logger.logInfoMsg("Successfully fetched soil metric list for crop with ID: " + Long.toString(cropId));
            return smList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute GET code block... throwing exception \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * BULK OPERATION to fetch soil metrics for a list of crops
     * @param cropIdList
     * @return
     * @throws SQLException
     */
    @Transactional
    public List<SoilMetric> bulkFetchSoilMetricsForCropsList(List<Long> cropIdList) throws SQLException {

        logger.logInfoMsg("(BULK OPERATION) - Fetching all soil metrics for passed crop IDs");

        try {
            List<SoilMetric> smList = new ArrayList<>();

            for (long cropId : cropIdList) {
                List<SoilMetric> smListPerCrop = new ArrayList<>();
                logger.logInfoMsg("Fetching soil metrics for crop with ID: " + Long.toString(cropId));
                smListPerCrop = smRepo.findByCropId(cropId);
                logger.logInfoMsg("Fetched " + String.valueOf(smListPerCrop.size()) + " database entries for crop with ID: " + Long.toString(cropId));

                logger.logInfoMsg("Appending the soil metrics for crop with ID: " + Long.toString(cropId) + " to the return list");
                smList.addAll(smListPerCrop);
            }

            logger.logInfoMsg("Fetched all soil metrics for the passed crop IDs");
            return smList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to execute (BULK OPERATION) GET code block \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


}
