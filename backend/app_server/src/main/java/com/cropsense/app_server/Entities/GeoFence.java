package com.cropsense.app_server.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

@MappedSuperclass
public abstract class GeoFence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fenceId;

    @OneToMany(targetEntity = FenceCoordinate.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fenceId", referencedColumnName = "fenceId")
    private List<FenceCoordinate> fcList;
    
    // getters
    public long getFenceId() {
        return this.fenceId;
    }

}
