package com.politechnika.visitservice.repository;

import com.politechnika.visitservice.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitRepository   extends JpaRepository<Visit,Long> {
        Visit findById(long id);
        List<Visit> findAllByDoctorEmail (String email);
        List<Visit> findAllByUserEmailAndIsPaid(String userEmail, boolean isPaid);
}
