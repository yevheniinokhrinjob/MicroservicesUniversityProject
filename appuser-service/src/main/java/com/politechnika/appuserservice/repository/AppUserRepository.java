package com.politechnika.appuserservice.repository;

import com.politechnika.appuserservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository  extends JpaRepository<AppUser,Long> {
        AppUser findById(long id);
        AppUser findByEmail(String email);
}
