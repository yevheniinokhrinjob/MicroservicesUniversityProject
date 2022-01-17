package com.politechnika.appuserservice.service;

import com.politechnika.appuserservice.model.AppUser;
import com.politechnika.appuserservice.model.AppUserRole;

import java.util.List;

public interface AppUserService {
    void addAppUser(AppUser appUser);
    void editAppUser(AppUser appUser, long id) ;
    void editAppUserPassword(String password, long id) ;
    List<AppUser> listAppUser();
    void removeAppUser(long id);
    AppUser getAppUser(long id);
    AppUser findByEmail(String login);
    void addRoleToAppUser(long userId, String role) ;
    void removeAppUserRole(long userId, String appUserRole) ;
    List<AppUser> listDoctors();
    AppUser getDoctor(long doctorId);
    AppUser getDoctorByEmail(String doctorEmail);
}
