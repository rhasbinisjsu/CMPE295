package com.cropsense.app_server.Entities.Metrics;

import java.util.HashMap;

import com.cropsense.app_server.Entities.Embeddables.MetricCoordinate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "diseaseMetrics")
public class DiseaseMetric extends MetricBase {
    
    // coordinate
    @Column(nullable = true, unique = false)
    private MetricCoordinate coordinate;
    
    // disease type
    @Column(nullable = true, unique = false)
    private String diseaseType;

    // Setters & Getters
    public void setCoordinate(MetricCoordinate diseaseCoordinate) {
        this.coordinate = diseaseCoordinate;
    }

    public void setDiseaseType(String disease) {
        this.diseaseType = disease;
    }

    public HashMap<String,Float> getCoordinate() {
        return this.coordinate.getCoordinate();
    }

    public String getDiseaseType() {
        return this.diseaseType;
    }


}
