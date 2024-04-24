package com.cropsense.appmanager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropsense.appmanager.Entities.FenceCoordinate;

@Repository
public interface FenceCoordinateRepository extends JpaRepository <FenceCoordinate, Long> {
    
}
