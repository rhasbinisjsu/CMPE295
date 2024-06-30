package com.cropsense.app_server.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fenceCoordinates")
public class FenceCoordinate { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fcoordId;

    @Column(nullable = true, unique = false)
    private long fenceId;

    @Column(nullable = false, unique = false)
    private String fenceType;

    @Column(nullable = false, unique = false)
    private int chainId;

    @Column(nullable = false, unique = false)
    private float lat;

    @Column(nullable = false, unique = false)
    private float lng;

    // setters
    public void setFenceId(long fenceId) {
        this.fenceId = fenceId;
    }

    public void setFenceType(String fType) {
        this.fenceType = fType;
    }

    public void setChainId(int o) {
        this.chainId = o;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    // getters
    public long getFcoorId() {
        return this.fcoordId;
    }

    public long getFenceId() {
        return this.fenceId;
    }

    public String getFenceType() {
        return this.fenceType;
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
    

}
