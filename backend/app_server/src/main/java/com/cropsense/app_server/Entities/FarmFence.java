package com.cropsense.app_server.Entities;

import java.util.List;

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
@Table(name = "farmFences")
public class FarmFence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fenceId;

    @Column(nullable = false, unique = true)
    private long farmId;

    @OneToMany(targetEntity = FenceCoordinate.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fenceId", referencedColumnName = "fenceId")
    private List<FenceCoordinate> fcList;

    // setters
    
    // getters
    public long getFenceId() {
        return this.fenceId;
    }

    public long getFarmId() {
        return this.farmId;
    }

}
