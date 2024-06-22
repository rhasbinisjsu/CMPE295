package com.cropsense.app_server.Entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private boolean active;

    @Column(nullable = false, unique = false)
    private String status;

    @Column(nullable = false, unique  = false)
    private Date startDate;

    @Column(nullable = false, unique = false)
    private Date endDate;

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


    // Getters

}
