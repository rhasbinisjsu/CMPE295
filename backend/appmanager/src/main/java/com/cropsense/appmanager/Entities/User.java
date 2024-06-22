package com.cropsense.appmanager.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tenantId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = false, nullable = false)
    private String pswd;

    @Column(unique = false, nullable = false)
    private String fname;

    @Column(unique = false, nullable = false)
    private String lname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String pnumber;

    // Setter and Getter functions ----------------------------------------------------------------
    
    public void setUsername(String uname) {
        this.username = uname;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public void setFname(String firstName) {
        this.fname = firstName;
    }

    public void setLname(String lastName) {
        this.lname = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPNumber(String number) {
        this.pnumber = number;
    }

    public long getTenantId() {
        return this.tenantId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPswd() {
        return this.pswd;
    }

    public String getFname() {
        return this.fname;
    }

    public String getLname() {
        return this.lname;
    }

    public String getEmail() {
        return this.email;
    }

    public String get_PNumber() {
        return this.pnumber;
    }

}
