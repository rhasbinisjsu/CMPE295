package com.cropsense.app_server.Repositories.Metrics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.Metrics.SoilMetric;

public interface SoilMetricRepository extends JpaRepository<SoilMetric, Long> {
    
    List<SoilMetric> findByCropIdOrderByCollectionDateAsc(long cropId);

}
