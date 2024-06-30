package com.cropsense.app_server.Entities.Embeddables;

import jakarta.persistence.Embeddable;

@Embeddable
public class Name {
    
    // member variables
    private String fname;
    private String lname;

    // default constructor
    public Name() {}

    // constructor
    public Name(String first, String last) {
        this.fname = first;
        this.lname = last;
    }

    // setters
    public void setFname(String first) {
        this.fname = first;
    }

    public void setLname(String last) {
        this.lname = last;
    }

    // getters
    public String getFname() {
        return this.fname;
    }

    public String getLname() {
        return this.lname;
    }

}
