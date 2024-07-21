package com.cropsense.app_server.Entities.Metrics;

import com.cropsense.app_server.Entities.Embeddables.MetricCoordinate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "speciesMetric")
public class SpeciesMetric extends MetricBase {

    @Column(nullable = false, unique = false)
    private String speciesDetected;

    @Column(nullable = false, unique = false)
    private MetricCoordinate coordinate;

    // Setters and Getters

    public void setSpeciesDetected(String species) {
        this.speciesDetected = species;
    }

    public void setCoordinate(MetricCoordinate coord) {
        this.coordinate = coord;
    }

    public String getSpeciesDetected() {
        return this.speciesDetected;
    }

    public MetricCoordinate getCoordinate() {
        return this.coordinate;
    }
    
}
