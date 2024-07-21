package com.cropsense.app_server.Repositories.Metrics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.Metrics.SpeciesMetric;

public interface SpeciesMetricRepository extends JpaRepository<SpeciesMetric, Long> {

    List<SpeciesMetric> findByCropIdOrderByCollectionDateAsc(long cropId);
    
}
