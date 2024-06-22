package com.cropsense.app_server.Entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "missionCoordinates")
public class MissionCoordinate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long missionCoordId;

    @Column(nullable = true, unique = false)
    private long missionId;

    @Column(nullable = false, unique = false)
    private int chainId;
    
    @Column(nullable = false, unique = false)
    private float lat;
    
    @Column(nullable = false, unique = false)
    private float lng;
    
    @Column(nullable = false, unique = false)
    private List<String> actions;

    // setters
    public void setMissionId(long mi) {
        this.missionId = mi;
    }

    public void setChainId(int order) {
        this.chainId = order;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setActions(List<String> a) {
        this.actions = a;
    }

    public void setAction(String action) {
        this.actions.add(action);
    }

    // getters
    public long getMissionCoordId() {
        return this.missionCoordId;
    }

    public long getMissionId() {
        return this.missionId;
    }

    public int getChainId() {
        return this.chainId; 
    }

    public float getLat() {
        return this.lat;
    }

    public float getLng() {
        return this.lng;
    }

    public List<String> getActions() {
        return this.actions;
    }


}
