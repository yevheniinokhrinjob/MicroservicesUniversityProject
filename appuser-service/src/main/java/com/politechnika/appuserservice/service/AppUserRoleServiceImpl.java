package com.politechnika.appuserservice.service;


import com.politechnika.appuserservice.model.AppUserRole;
import com.politechnika.appuserservice.repository.AppUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppUserRoleServiceImpl implements AppUserRoleService {

    private AppUserRoleRepository appUserRoleRepository;

    @Autowired
    public AppUserRoleServiceImpl(AppUserRoleRepository appUserRoleRepository) {
        this.appUserRoleRepository = appUserRoleRepository;
    }

    @Override
    public void addAppUserRole(AppUserRole appUserRole) {
        appUserRoleRepository.save(appUserRole);
    }

    @Override
    public List<AppUserRole> listAppUserRole() {
        return appUserRoleRepository.findAll();
    }

    @Override
    public AppUserRole getAppUserRole(String role) {
        return appUserRoleRepository.findByRole(role);
    }


}
