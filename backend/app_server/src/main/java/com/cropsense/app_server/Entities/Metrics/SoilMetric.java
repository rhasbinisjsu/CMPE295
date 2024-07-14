package com.cropsense.app_server.Entities.Metrics;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "soilMetrics")
public class SoilMetric extends MetricBase {

    @Column(nullable = true, unique = false)
    private float soilMoisture;

    @Column(nullable = true, unique = false)
    private float phLevel;

    @Column(nullable = true, unique = false)
    private float nitrogenLevel;

    @Column(nullable = true, unique = false)
    private float phosphorousLevel;

    @Column(nullable = true, unique = false)
    private float potassiumLevel;

    // setters

    public void setSoilMoisture(float sm) {
        this.soilMoisture = sm;
    }

    public void setPhLevel(float phl) {
        this.phLevel = phl;
    }

    public void setNitrogenLevel(float nl) {
        this.nitrogenLevel = nl;
    }

    public void setPhosphorousLevel(float pl) {
        this.phosphorousLevel = pl;
    }

    public void setPotassiumLevel(float potL) {
        this.potassiumLevel = potL;
    }

    // Getters

    public float getSoilMoisture() {
        return this.soilMoisture;
    }

    public float getPhLevel() {
        return this.phLevel;
    }

    public float getNitrogenLevel() {
        return this.nitrogenLevel;
    }

    public float getPhosphorousLevel() {
        return this.phosphorousLevel;
    }

    public float getPotassiumLevel() {
        return this.potassiumLevel;
    }
    
}
