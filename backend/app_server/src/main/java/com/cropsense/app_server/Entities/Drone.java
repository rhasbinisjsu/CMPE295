package com.cropsense.app_server.Entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Drones")
public class Drone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = false)
    private long ownerId;

    @Column(nullable = false, unique = false)
    private String droneName;

    @Column(nullable = false, unique = false)
    private String droneModel;

    @Column(nullable = false, unique = false)
    private int batteryLevel;

    @Column(nullable = false, unique = false)
    private boolean docked;

    @Column(nullable = false, unique = false)
    private boolean inService;

    @Column(nullable = true, unique = false)
    private List<String> equipment;

    // Setters

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setDroneName(String dName) {
        this.droneName = dName;
    }

    public void setDroneModel(String model) {
        this.droneModel = model;
    }

    public void setBatteryLevel(int level) {
        this.batteryLevel = level;
    }

    public void setDocked(boolean bool) {
        this.docked = bool;
    }

    public void setInService(boolean bool) {
        this.inService = bool;
    }

    public void addEquipment(String equipName) {
        this.equipment.add(equipName);
    }

    public void removeEquipment(String equipName) {
        
        for (int i = 0; i < equipment.size(); i++) {
            if (equipment.get(i) == equipName) {
                equipment.remove(i);
                break;
            }
        }

    }

    // Getters

    public long getId() {
        return this.id;
    }

    public long getOwnerId() {
        return this.ownerId;
    }

    public String getDroneName() {
        return this.droneName;
    }

    public String getDroneModel() {
        return this.droneModel;
    }

    public int getBatteryLevel() {
        return this.batteryLevel;
    }

    public boolean getDocked() {
        return this.docked;
    }

    public boolean getInService() {
        return this.inService;
    }

    public List<String> getEquipment() {
        return this.equipment;
    }


}
