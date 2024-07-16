package com.cropsense.app_server.Entities;

import com.cropsense.app_server.Entities.Embeddables.Name;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    // member attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String uname;

    @Column(nullable = false, unique = false)
    private String pswd;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private Name name;

    @Column(nullable = false, unique = true)
    private String pnumber;

    // forign key mapping
    @OneToMany(targetEntity = Farm.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ownerId", referencedColumnName = "id")
    private List<Farm> farms;

    @OneToMany(targetEntity = MissionPlan.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ownerId", referencedColumnName = "id")
    private List<MissionPlan> missionPlans;

    // setters
    public void setUname(String un) {
        this.uname = un;
    }

    public void setPswd(String encodedPwd) {
        this.pswd = encodedPwd;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setName(Name n) {
        this.name = n;
    }

    public void setPnumber(String pn) {
        this.pnumber = pn;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getUname() {
        return this.uname;
    }

    public String getPswd() {
        return this.pswd;
    }

    public String getEmail() {
        return this.email;
    }

    public Name getName() {
        return this.name;
    }

    public String getPnumber() {
        return this.pnumber;
    }

}
