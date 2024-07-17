package com.cropsense.app_server.Repositories.Metrics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.Metrics.DiseaseMetric;

public interface DiseaseMetricRepository extends JpaRepository<DiseaseMetric, Long> {
    
    List<DiseaseMetric> findByCropIdOrderByCollectionDateAsc(long cropId);

}
