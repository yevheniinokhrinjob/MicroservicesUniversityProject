package com.politechnika.visitservice.service;

import com.politechnika.visitservice.model.Visit;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface VisitService {
    void addVisit(Visit visit);
    void removeVisit(long id);
    List<Visit> getAllVisits();
    List<Visit> getVisitsForDoctor(String doctorEmail);
    List<Time> getAvailableHours(Date date, String doctorEmail);
    Visit getUnpaidVisitForUser(String userEmail);
    Visit getVisit(long id);
    void acceptVisit(String userEmail);
    void completeVisit (long visitId);
}
