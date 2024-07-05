package com.cropsense.app_server.Entities;

import java.sql.Date;
import java.util.List;

import com.cropsense.app_server.Entities.Metrics.SoilMetric;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Crops")
public class Crop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cropId;

    @Column(nullable = true, unique = false)
    private long farmId;

    @Column(nullable = false, unique = false)
    private String cropSpecies;

    @Column(nullable = false, unique = false)
    private int transplantAmount;

    @Column(nullable = true, unique = false)
    private int cultivatedAmount;

    @Column(nullable = false, unique = false)
    private boolean activeFlag;

    @Column(nullable = false, unique = false)
    private String status;

    @Column(nullable = false, unique  = false)
    private Date startDate;

    @Column(nullable = false, unique = false)
    private Date endDate;

    // foreign key to crop land/soil metrics
    @OneToMany(targetEntity = SoilMetric.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cropId", referencedColumnName = "cropId")
    private List<SoilMetric> soilMetrics;

    // Setters
    public void setFarmId(long farmId) {
        this.farmId = farmId;
    }

    public void setCropSpecies(String species) {
        this.cropSpecies = species;
    }

    public void setTransplantAmount(int tpAmount) {
        this.transplantAmount = tpAmount;
    }

    public void setCultivatedAmount(int cultAmount) {
        this.cultivatedAmount = cultAmount;
    }

    public void setActiveFlag(boolean isActive) {
        this.activeFlag = isActive;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    // Getters
    public long getCropId() {
        return this.cropId;
    }

    public long getFarmId() {
        return this.farmId;
    }

    public String getCropSpecies() {
        return this.cropSpecies;
    }

    public int getTransplantAmount() {
        return this.transplantAmount;
    }

    public int getCultivatedAmount() {
        return this.cultivatedAmount;
    }

    public boolean getActiveFlag() {
        return this.activeFlag;
    }

    public String getStatus() {
        return this.status;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

}
