package com.cropsense.app_server.Entities.Metrics;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class MetricBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long metricId;

    @Column(nullable = true, unique = false)
    private long cropId;

    @Column(nullable = false, unique = false)
    private Date collectionDate;


    // Setters

    public void setCropId(long cid) {
        this.cropId = cid;
    }

    public void setCollectionDate(Date collDate) {
        this.collectionDate = collDate;
    }

    // Getters

    public long getMetricId() {
        return this.metricId;
    }

    public long getCropId() {
        return this.cropId;
    }

    public Date getCollectionDate() {
        return this.collectionDate;
    }
    

}
