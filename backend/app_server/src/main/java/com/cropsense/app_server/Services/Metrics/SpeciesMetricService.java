package com.cropsense.app_server.Services.Metrics;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.Metrics.SpeciesMetric;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.Metrics.SpeciesMetricRepository;

@Service
public class SpeciesMetricService {
    
    @Autowired
    private SpeciesMetricRepository speciesMetricRepo;

    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * 
     * @param newMetric
     * @return
     * @throws SQLException
     */
    public boolean createSpeciesMetricEntryForCrop(SpeciesMetric newMetric) throws SQLException {

        String cropIdStr = Long.toString(newMetric.getCropId());
        logger.logInfoMsg("Persisitng new species metric entry for crop with ID: " + cropIdStr + " in the database");
        boolean created = false;

        try {
            speciesMetricRepo.save(newMetric);
            created = true;
            logger.logInfoMsg("Successfully persisted the new species metric entry in the database for crop with ID: " + cropIdStr);
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to persist the new species metric entry for crop with ID: " + cropIdStr + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return created;
    }


    /**
     * 
     * @param cropId
     * @return
     * @throws SQLException
     */
    public List<SpeciesMetric> fetchAllSpeciesMetricEntriesForCrop(long cropId) throws SQLException {

        String cropIdStr = Long.toString(cropId);
        logger.logInfoMsg("Fetching all species metrci entries for crop with ID: " + cropIdStr);
        
        try {
            List<SpeciesMetric> responseList = speciesMetricRepo.findByCropIdOrderByCollectionDateAsc(cropId);
            logger.logInfoMsg("Successfully fetched all species metric entries for crop with ID: " + cropIdStr);
            return responseList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to fetch species metric entries... \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }

}
