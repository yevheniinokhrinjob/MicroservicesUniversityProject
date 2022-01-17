package com.politechnika.appuserservice.repository;

import com.politechnika.appuserservice.model.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {
    AppUserRole findByRole(String role);
}
