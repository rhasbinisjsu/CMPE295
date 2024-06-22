package com.cropsense.app_server.Services;

import java.util.List;
import java.util.Optional;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.MissionPlan;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.MissionPlanRepository;

@Service
public class MissionPlanService {
    
    @Autowired
    private MissionPlanRepository mpRepo;

    // define & create logger instance
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new mission plan
     * @param mp
     * @return
     * @throws SQLException
     */
    public MissionPlan createMissionPlan(MissionPlan mp) throws SQLException {
        
        logger.logInfoMsg("Persisting mission plan");

        try {
            mpRepo.save(mp);
            logger.logInfoMsg("Successfully persisted a new mission plan");
            return mp;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to persist the new mission plan \n Reason: " + e.getMessage());
            throw new SQLException(); 
        }
        
    }


    /**
     * Fetch all persisted mission plans for owner
     * @param ownerId
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansForOwner(long ownerId) throws SQLException {
        
        logger.logInfoMsg("Fetching mission plans for owner with ID: " + Long.toString(ownerId));

        try {
            List<MissionPlan> mpList = mpRepo.findByOwnerId(ownerId);
            logger.logInfoMsg("Fetched mission plans for owner with ID: " + Long.toString(ownerId));
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans for owner with ID: " + Long.toString(ownerId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Fetch all persisted mission plans for owner by farm
     * @param ownerId
     * @param farmId
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansByFarm(long ownerId, long farmId) throws SQLException {
        
        logger.logInfoMsg("Fetching mission plans for farm with ID: " + Long.toString(farmId) + " and owner with ID: " + Long.toString(ownerId));
        
        try {
            List<MissionPlan> mpList = mpRepo.findByOwnerIdAndFarmId(ownerId, farmId);
            logger.logInfoMsg("Fetched mission plans for farm with ID: " + Long.toString(farmId));
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans for farm with ID: " + Long.toString(farmId) + " and owner with ID: " + Long.toString(ownerId) + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Fetch all persisted mission plans for owner by status
     * @param ownerId
     * @param status
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansByStatus(long ownerId, String status) throws SQLException {
        
        logger.logInfoMsg("Fetching mission plans by status for owner with ID: " + Long.toString(ownerId));

        try {
            List<MissionPlan> mpList = mpRepo.findByOwnerIdAndStatus(ownerId, status);
            logger.logInfoMsg("Fetched mission plans by status for owner with ID: " + Long.toString(ownerId));
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch Mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Fetch all persisted mission plans for a farm by status
     * @param farmId
     * @param status
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansByFarmAndStatus(long farmId, String status) throws SQLException {

        logger.logInfoMsg("Fetching mission plans for farm with ID: " + Long.toString(farmId) + " and status: " + status);

        try {
            List<MissionPlan> mpList = mpRepo.findByFarmIdAndStatus(farmId, status);
            logger.logInfoMsg("Fetcehd mission plans for farm with ID: " + Long.toString(farmId) + " with status: " + status);
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch persisted mission plans for owner by type
     * @param ownerId
     * @param missionType
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansByMissionType(long ownerId, String missionType) throws SQLException {

        logger.logInfoMsg("Fetching mission plans for owner with ID: " + Long.toString(ownerId) + " where mission type: " + missionType);

        try {
            List<MissionPlan> mpList = mpRepo.findByOwnerIdAndMissionType(ownerId, missionType);
            logger.logInfoMsg("Fetched mission plans for owner with ID: " + Long.toString(ownerId) + " where mission type: " + missionType);
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch persisted mission plans for a farm by type
     * @param farmId
     * @param missionType
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansByFarmAndMissionType(long farmId, String missionType) throws SQLException {
        
        logger.logInfoMsg("Fetching mission plans for farm with ID: " + Long.toString(farmId) + " where mission type: " + missionType);

        try {
            List<MissionPlan> mpList = mpRepo.findByFarmIdAndMissionType(farmId, missionType);
            logger.logInfoMsg("Fetched mission plans for farm with ID: " + Long.toString(farmId) + " where mission type: " + missionType);
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch persisted mission plans for owner on date
     * @param ownerId
     * @param d
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansByOwnerIdAndDate(long ownerId, Date d) throws SQLException {
        
        logger.logInfoMsg("Fetching mission plans for owner with ID: " + Long.toString(ownerId) + " on date: " + d.toString());

        try {
            List<MissionPlan> mpList = mpRepo.findByOwnerIdAndMissionDate(ownerId, d);
            logger.logInfoMsg("Fetched mission plans for owner with ID: " + Long.toString(ownerId) + " on date: " + d.toString());
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    } 


    /**
     * Fetch persisted mission plans for farm on date
     * @param farmId
     * @param d
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansForFarmAndDate(long farmId, Date d) throws SQLException {

        logger.logInfoMsg("Fetching mission plans for farm with ID: " + Long.toString(farmId) + " on date: " + d.toString());

        try {
            List<MissionPlan> mpList = mpRepo.findByFarmIdAndMissionDate(farmId, d);
            logger.logInfoMsg("Fetched mission plans for farm with ID: " + Long.toString(farmId) + " on date: " + d.toString());
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch persisted mission plans for owner between dates
     * @param ownerId
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansForOwnerBetweenDates(long ownerId, Date startDate, Date endDate) throws SQLException {

        logger.logInfoMsg("Fetching mission plans for owner with ID: " + Long.toString(ownerId) + " between dates: " + startDate.toString() + " - " + endDate.toString());

        try {
            List<MissionPlan> mpList = mpRepo.findByOwnerIdAndMissionDateBetween(ownerId, startDate, endDate);
            logger.logInfoMsg("Fetched mission plans for owner with ID: " + Long.toString(ownerId) + " between dates: " + startDate.toString() + " - " + endDate.toString());
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }

    
    /**
     * Fetch mission plans for a farm between dates
     * @param farmId
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public List<MissionPlan> fetchMissionPlansForFarmBetweenDates(long farmId, Date startDate, Date endDate) throws SQLException {

        logger.logInfoMsg("Fetching mission plans for farm with ID: " + Long.toString(farmId) + " betweek dates: " + startDate.toString() + " - " + endDate.toString());

        try {
            List<MissionPlan> mpList = mpRepo.findByFarmIdAndMissionDateBetween(farmId, startDate, endDate);
            logger.logInfoMsg("Fetched mission plans for farm with ID: " + Long.toString(farmId) + " betweek dates: " + startDate.toString() + " - " + endDate.toString());
            return mpList;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch mission plans \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }


    /**
     * Fetch a single mission plan with its ID
     * @param mpId
     * @return
     * @throws SQLException
     */
    public Optional<MissionPlan> fetchMissionPlanById(long mpId) throws SQLException {

        logger.logInfoMsg("Fectching mission plan with ID: " + Long.toString(mpId));

        try {
            Optional<MissionPlan> omp = mpRepo.findById(mpId);
            
            if (omp.isPresent()) {
                logger.logInfoMsg("Found mission plan with ID: " + Long.toString(mpId));
            }
            else { 
                logger.logWarnMsg("Mission plan with ID: " + Long.toString(mpId) + " not found");
            }
            
            return omp;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to fetch mission plan \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    // Get mission status


    /**
     * Update a mission plan's description by its ID
     * @param mpId
     * @param desc
     * @return
     * @throws SQLException
     */
    public boolean updateMissionDescription(long mpId, String desc) throws SQLException {

        logger.logInfoMsg("Updating mission plan description for plan with ID: " + Long.toString(mpId));
        boolean updated = false;

        try {
            MissionPlan mp = mpRepo.findById(mpId).get();
            logger.logInfoMsg("Found mission plan with ID: " + Long.toString(mpId));

            mp.setMissionDesc(desc);
            mpRepo.save(mp);

            updated = true;
            logger.logInfoMsg("Updated mission plan description");
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to update the mission plan description \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Update a mission date for mission plan by ID
     * @param mpId
     * @param newDate
     * @return
     * @throws SQLException
     */
    public boolean updateMissionDate(long mpId, Date newDate) throws SQLException {

        logger.logInfoMsg("Updating mission date for plan with ID: " + Long.toString(mpId));
        boolean updated = false; 

        try {
            MissionPlan mp = mpRepo.findById(mpId).get();
            logger.logInfoMsg("Found mission plan with ID: " + Long.toString(mpId));

            if (mp.getStatus().equals("Scheduled")) {
                mp.setMissionDate(newDate);
                mpRepo.save(mp);
                logger.logInfoMsg("Updated mission date");
                updated = true;
            }
            else {
                logger.logInfoMsg("Cannot update mission plan date - mission status is not 'Scheduled'");
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to update the mission date \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;

    }


    /**
     * Update a persisted mission's time
     * @param mpId
     * @param newTime
     * @return
     * @throws SQLException
     */
    public boolean updateMissionTime(long mpId, Time newTime) throws SQLException {

        logger.logInfoMsg("Updating mission time for plan with ID: " + Long.toString(mpId));
        boolean updated = false;

        try {
            MissionPlan mp = mpRepo.findById(mpId).get();
            logger.logInfoMsg("Found mission plan with ID: " + Long.toString(mpId));

            String missionStatus = mp.getStatus();
            if (missionStatus.equals("Scheduled")) {
                mp.setMissionTime(newTime);
                mpRepo.save(mp);
                updated = true;
                logger.logInfoMsg("Updated mission time");
            }
            else {
                logger.logInfoMsg("Cannot update mission plan time - mission status is not 'Scheduled'");
            }
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to update the mission time \n Reason: " + e.getMessage());
            throw new SQLException();
        }

        return updated;
    }


    /**
     * Update a persisted mission's status
     * @param mpId
     * @param newStatus
     * @return
     * @throws SQLException
     */
    public MissionPlan updateMissionStatus(long mpId, String newStatus) throws SQLException {
        
        logger.logInfoMsg("Updating mission status for plan with ID: " + Long.toString(mpId));

        try {
            MissionPlan mp = mpRepo.findById(mpId).get();
            logger.logInfoMsg("Found mission plan with ID: " + Long.toString(mpId));

            mp.setMissionStatus(newStatus);
            mpRepo.save(mp);
            logger.logInfoMsg("Updated mission status");

            return mp;
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to update the mission status \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }


    /**
     * Delete a persisted mission plan
     * @param mpId
     * @throws SQLException
     */
    public void deleteMissionPlan(long mpId) throws SQLException {

        logger.logInfoMsg("Deleting mission for plan with ID: " + Long.toString(mpId));

        try {
            mpRepo.deleteById(mpId);
            logger.logInfoMsg("Successfully delete mission plan with ID: " + Long.toString(mpId));
        }
        catch (Exception e) {
            logger.logErrorMsg("Failed to delete mission plan \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }
}
