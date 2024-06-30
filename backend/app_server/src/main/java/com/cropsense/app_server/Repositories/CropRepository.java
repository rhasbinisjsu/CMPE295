package com.cropsense.app_server.Repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.Crop;

public interface CropRepository extends JpaRepository<Crop, Long> {
    
    List<Crop> findByFarmId(long farmId);
    List<Crop> findByFarmIdAndActiveFlag(long farmId, boolean isActive);
    List<Crop> findByFarmIdAndStatus(long farmId, String status);
    List<Crop> findByFarmIdAndCropSpecies(long farmId, String species);
    List<Crop> findByFarmIdAndStartDateBetween(long farmId, Date startDate, Date endDate);

}
