package com.cropsense.app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.MissionPlan;
import java.util.List;
import java.sql.Date;



public interface MissionPlanRepository extends JpaRepository<MissionPlan, Long> {
    
    List<MissionPlan> findByOwnerId(long ownerId);
    List<MissionPlan> findByOwnerIdAndFarmId(long ownerId, long farmId);
    List<MissionPlan> findByFarmIdAndStatus(long farmId, String status);
    List<MissionPlan> findByOwnerIdAndStatus(long ownerId, String status);
    List<MissionPlan> findByOwnerIdAndMissionType(long ownerId, String missionType);
    List<MissionPlan> findByFarmIdAndMissionType(long famrId, String missionType);
    List<MissionPlan> findByOwnerIdAndMissionDate(long ownerId, Date missionDate);
    List<MissionPlan> findByFarmIdAndMissionDate(long farmId, Date missionDate);
    List<MissionPlan> findByOwnerIdAndMissionDateBetween(long ownerId, Date startDate, Date endDate);
    List<MissionPlan> findByFarmIdAndMissionDateBetween(long farmId, Date startDate, Date endDate);

}
