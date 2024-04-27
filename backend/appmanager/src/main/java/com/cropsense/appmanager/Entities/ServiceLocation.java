package com.cropsense.appmanager.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ServiceLocations")
public class ServiceLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "tenantId")
    private User owner;

    @Column(unique = false, nullable = false)
    private String locName;

    @Embedded
    @Column(nullable = false)
    private Address address;


    // Setter and Getter functions ----------------------------------------------------------------

    public void setLocationName(String name) {
        this.locName = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setAddress(Address a) {
        this.address = a;
    }

    public long getLocationId() {
        return this.locId;
    }

    public User getOwner() {
        return this.owner;
    }

    public String getLocationName() {
        return this.locName;
    }

    public Address getAddress() {
        return this.address;
    }

}
