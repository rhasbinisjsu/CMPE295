package com.cropsense.app_server.Entities;

import java.sql.Date;
import java.sql.Time;
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
@Table(name = "missionPlans")
public class MissionPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long missionId;

    @Column(nullable = true, unique = false)
    private long ownerId;

    @Column(nullable = false, unique = false)
    private long cropId;

    @Column(nullable = true, unique = false)
    private String missionDesc; 

    @Column(nullable = false, unique = false)
    private String missionType;

    @Column(nullable = false, unique = false)
    private Date missionDate;

    @Column(nullable = false, unique = false)
    private Time missionTime;

    @Column(nullable = false, unique = false)
    private String status;

    @OneToMany(targetEntity = MissionCoordinate.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="missionId", referencedColumnName = "missionId")
    private List<MissionCoordinate> coordinates; 

    // setters
    public void setOwnerId(long oId) {
        this.ownerId = oId;
    }

    public void setCropId(long cId) {
        this.cropId = cId;
    }

    public void setMissionDesc(String desc) {
        this.missionDesc = desc;
    }

    public void setMissionType(String mtype) {
        this.missionType = mtype;
    }

    public void setMissionDate(Date mDate) {
        this.missionDate = mDate;
    }

    public void setMissionTime(Time mTime) {
        this.missionTime = mTime;
    }

    public void setMissionStatus(String mStatus) {
        this.status = mStatus;
    }

    // getters
    public long getMissionId() {
        return this.missionId;
    }

    public long getOwnerId() {
        return this.ownerId;
    }

    public long getCropId() {
        return this.cropId;
    }

    public String getMissionDesc() {
        return this.missionDesc;
    }

    public String getMissionType() {
        return this.missionType;
    }

    public Date getMissionDate() {
        return this.missionDate;
    }

    public Time getMissionTime() {
        return this.missionTime;
    }

    public String getStatus() {
        return this.status;
    }

}
