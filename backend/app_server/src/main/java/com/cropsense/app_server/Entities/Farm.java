package com.cropsense.app_server.Entities;

import com.cropsense.app_server.Entities.Embeddables.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "farms")
public class Farm {

    // member attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long farmId;

    @Column(nullable = true, unique = false)
    private long ownerId;

    @Column(nullable = false, unique = false)
    private String name;

    @Embedded
    private Address address;

    @OneToOne(targetEntity = FarmFence.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "farmId", referencedColumnName = "farmId")
    private FarmFence locationFence;

    // setters
    public void setName(String n) {
        this.name = n;
    }

    public void setAddress(Address a) {
        this.address = a;
    }

    // getters
    public long getId() {
        return this.farmId;
    }

    public long getOwnerId() {
        return this.ownerId;
    }

    public String getName() {
        return this.name;
    }

    public Address getAddress() {
        return this.address;
    }

}
