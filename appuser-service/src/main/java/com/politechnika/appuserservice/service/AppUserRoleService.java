package com.politechnika.appuserservice.service;



import com.politechnika.appuserservice.model.AppUserRole;

import java.util.List;

public interface AppUserRoleService {
    void addAppUserRole(AppUserRole appUserRole);
    List<AppUserRole> listAppUserRole();
    AppUserRole getAppUserRole(String role);

}
