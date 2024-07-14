package com.cropsense.app_server.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "farmFences")
public class FarmFence extends GeoFence {

    @Column(nullable = true, unique = true)
    private long farmId;

    public long getFarmId() {
        return this.farmId;
    }

}
