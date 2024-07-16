package com.cropsense.app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.MissionCoordinate;
import java.util.List;


public interface MissionCoordinateRepository extends JpaRepository<MissionCoordinate, Long> {
    
    List<MissionCoordinate> findByMissionId(long missionId);
    void deleteByMissionId(long missionId);

}
