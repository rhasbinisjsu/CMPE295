package com.cropsense.appmanager.Entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    
    private String line1;
    private String city;
    private String state;
    private String country;
    private String zip;

    public void setLine1(String l1) {
        this.line1 = l1;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZip(String zip)  {
        this.zip = zip;
    }

    public String getLine1() {
        return this.line1;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public String getZip() {
        return this.zip;
    }

}
