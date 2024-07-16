package com.cropsense.app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.MissionPlan;
import java.util.List;
import java.sql.Date;



public interface MissionPlanRepository extends JpaRepository<MissionPlan, Long> {
    
    List<MissionPlan> findByOwnerId(long ownerId);
    List<MissionPlan> findByOwnerIdAndCropId(long ownerId, long cropId);
    List<MissionPlan> findByCropIdAndStatus(long cropId, String status);
    List<MissionPlan> findByOwnerIdAndStatus(long ownerId, String status);
    List<MissionPlan> findByOwnerIdAndMissionType(long ownerId, String missionType);
    List<MissionPlan> findByCropIdAndMissionType(long cropId, String missionType);
    List<MissionPlan> findByOwnerIdAndMissionDate(long ownerId, Date missionDate);
    List<MissionPlan> findByCropIdAndMissionDate(long cropId, Date missionDate);
    List<MissionPlan> findByOwnerIdAndMissionDateBetween(long ownerId, Date startDate, Date endDate);
    List<MissionPlan> findByCropIdAndMissionDateBetween(long cropId, Date startDate, Date endDate);

}
