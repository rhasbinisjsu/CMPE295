package com.cropsense.app_server.Services.Metrics;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.Metrics.DiseaseMetric;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.Metrics.DiseaseMetricRepository;

@Service
public class DiseaseMetricService {
    
    @Autowired
    private DiseaseMetricRepository dmRepo;

    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new disease entries for crop (Data source (drone) will add these datapoints...)
     * @param newDiseaseMetric
     * @return
     * @throws SQLException
     */
    public boolean createDiseaseMetric(DiseaseMetric newDiseaseMetric) throws SQLException {

        String cropIdStr = String.valueOf(newDiseaseMetric.getCropId());
        logger.logInfoMsg("Persisting a new disease entry for crop with ID: " + cropIdStr);

        boolean created = false;

        try {
            dmRepo.save(newDiseaseMetric);
            created = true;
            logger.logInfoMsg("Successfully persisted a new disease entry for crop with ID: " + cropIdStr);
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to persist the new disease entry \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return created;

    }


    /**
     * Fetch all disease entries for crop 
     * @param cropId
     * @return
     * @throws SQLException
     */
    public List<DiseaseMetric> fetchDiseaseMetricsForCrop(long cropId) throws SQLException {
        
        String cropIdStr = String.valueOf(cropId);
        logger.logInfoMsg("Fetching all disease entries for crop with ID: " + cropIdStr);

        try {
            List<DiseaseMetric> dmList = dmRepo.findByCropIdOrderByCollectionDateAsc(cropId);
            logger.logInfoMsg("Successfully fetched all disease entries for crop with ID: " + cropIdStr);
            return dmList;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to fetch disease entries for crop with ID: " + cropIdStr);
            throw new SQLException();
        }

    }

}
