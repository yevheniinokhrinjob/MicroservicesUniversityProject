package com.politechnika.appuserservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;



    @NotEmpty
    private String fName;
    @NotEmpty
    private String lName;

    @Column(unique =  true)
    @Email
    private String email;

    private boolean enabled;
    @Size(min = 9, max = 11)
    private String phone;

    private String city;

    private String street;

    private String houseNumber;
    @NotEmpty
    private String password;

    @ManyToMany( fetch = FetchType.EAGER)
    private Set<AppUserRole> appUserRole = new HashSet<AppUserRole>(0);
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private VerificationToken token;

    public Set<AppUserRole> getAppUserRole() {
        return appUserRole;
    }

    public void setAppUserRole(Set<AppUserRole> appUserRole) {
        this.appUserRole = appUserRole;
    }


    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
