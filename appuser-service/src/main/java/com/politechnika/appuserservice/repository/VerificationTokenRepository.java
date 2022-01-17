package com.politechnika.appuserservice.repository;

import com.politechnika.appuserservice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findById(long id);
    VerificationToken findByToken(String token);
}
