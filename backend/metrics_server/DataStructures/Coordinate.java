package com.cropsense.metrics_server.DataStructures;

public class Coordinate {
    
    private float lat;
    private float lng;

    public Coordinate() {}

    public void setLat(float newLat) {
        this.lat = newLat;
    }

    public void setLng(float newLng) {
        this.lng = newLng;
    }

    public float getLat() {
        return this.lat;
    }

    public float getLng() {
        return this.lng;
    }

    public boolean compareCoordinates(Coordinate c1, Coordinate c2) {
        float c1Lat = c1.getLat();
        float c1Lng = c1.getLng();
        float c2Lat = c2.getLat();
        float c2Lng = c2.getLng();

        boolean equivalent = false;

        if (c1Lat == c2Lat) {
            if (c1Lng == c2Lng) {
                equivalent = true;
            }
            else {
                equivalent = false;
            }
        }
        else {
            equivalent = false;
        }

        return equivalent;
    }

}
