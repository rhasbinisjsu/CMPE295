package com.cropsense.appmanager.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FenceCoordinates")
public class FenceCoordinate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "fenceId")
    private Fence fence;

    @Column
    private float xCoordinate;

    @Column
    private float yCoordinate;

    /*@Column
    private int order;*/

    // Constructor --------------------------------------------------------------------------------

    /*public FenceCoordinate(float x, float y, int order) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.order = order;
    }*/

    // Setter and Getter functions ----------------------------------------------------------------

    public void setFence(Fence fence) {
        this.fence = fence;
    }

    public void setXcoordinate(float x) {
        this.xCoordinate = x;
    }

    public void setYcoordinate(float y) {
        this.yCoordinate = y;
    }

    /*public void setOrder(int order) {
        this.order = order;
    }*/

    public long getCoordinateId() {
        return this.coordId;
    }

    public Fence getFence() {
        return this.fence;
    }

    public float getXcoordinate() {
        return this.xCoordinate;
    }

    public float getYcoordinate() {
        return this.yCoordinate;
    }

    /*public int getOrder() {
        return this.order;
    }*/

}
