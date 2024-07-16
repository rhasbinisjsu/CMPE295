package com.cropsense.app_server.Entities.Embeddables;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    
    // members variables
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;
    private String country;

    // default constructor
    public Address() {}

    // constructor
    public Address(
        String l1,
        String l2,
        String cty,
        String st,
        String z,
        String ctry
    ) {
        this.line1 = l1;
        this.line2 = l2;
        this.city = cty;
        this.state = st;
        this.zip = z;
        this.country = ctry;
    }

    // setters
    public void setLine1(String l1) {
        this.line1 = l1;
    }

    public void setLine2(String l2) {
        this.line2 = l2;
    }

    public void setCity(String cty) {
        this.city = cty;
    }

    public void setState(String st) {
        this.state = st;
    }

    public void setZip(String z) {
        this.zip = z;
    }

    public void setCountry(String ctry) {
        this.country = ctry;
    }

    // getters
    public String getLine1() {
        return this.line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getZip() {
        return this.zip;
    }

    public String getCountry() {
        return this.country;
    }

}
