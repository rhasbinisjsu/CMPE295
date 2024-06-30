package com.cropsense.app_server.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cropFences")
public class CropFence extends GeoFence {
    
    @Column(nullable = false, unique = true)
    private long cropId;

    public long getCropId() {
        return this.cropId;
    }

}
