package com.cropsense.appmanager.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Fences")
public class Fence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fenceId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "locId")
    private ServiceLocation location;

    // Setter and Getter functions ----------------------------------------------------------------

    public void setLocation(ServiceLocation svcLoc) {
        this.location = svcLoc;
    }

    public long getFenceId() {
        return this.fenceId;
    }

    public ServiceLocation getLocation() {
        return this.location;
    }
    

}
