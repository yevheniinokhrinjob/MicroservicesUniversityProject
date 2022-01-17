package com.politechnika.appuserservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String role;
    @JsonIgnore
    @ManyToMany(mappedBy = "appUserRole")
    private Set<AppUser> appUsers = new HashSet<>();

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
