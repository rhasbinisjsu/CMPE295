package com.cropsense.app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.FenceCoordinate;
import java.util.List;


public interface FenceCoordinateRepository extends JpaRepository<FenceCoordinate, Long> {
    
    List<FenceCoordinate> findByFenceIdAndFenceType(long fenceId, String fenceType);
    void deleteByFenceIdAndFenceType(long fenceId, String fenceType);

}
