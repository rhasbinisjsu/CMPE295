package com.cropsense.app_server.Entities.Embeddables;

import java.util.HashMap;

import jakarta.persistence.Embeddable;

@Embeddable
public class MetricCoordinate {
    
    private float lng;
    private float lat;


    // Getters & Setters

    public void setLng(float newLng) {
        this.lng = newLng;
    }

    public void setLat(float newLat) {
        this.lat = newLat;
    }

    public float getLng() {
        return this.lng;
    }

    public float getLat() {
        return this.lat;
    }

    public HashMap<String,Float> getCoordinate() {
        
        HashMap<String,Float> coord = new HashMap<>();

        float lngVal = this.lng;
        float latVal = this.lat;

        coord.put("lat", latVal);
        coord.put("lng", lngVal);

        return coord;
    }

}
