package com.politechnika.visitservice.service;

import com.politechnika.visitservice.exeption.VisitCreationException;
import com.politechnika.visitservice.exeption.VisitNotFoundException;
import com.politechnika.visitservice.model.Visit;
import com.politechnika.visitservice.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VisitServiceImpl  implements VisitService{

    VisitRepository visitRepository;

    @Autowired
    public  VisitServiceImpl(VisitRepository visitRepository){
        this.visitRepository=visitRepository;
    }

    @Override
    public void addVisit(Visit visit) {
        System.out.println(visit.getTime() + "   "+ getAvailableHours(visit.getDate(),visit.getDoctorEmail()));
        if(getUnpaidVisitForUser(visit.getUserEmail())!=null) {
           throw new VisitCreationException("Pay for your last visit");
        }
        if(!getAvailableHours(visit.getDate(),visit.getDoctorEmail()).contains(visit.getTime())){
                throw new VisitCreationException("This time is already taken");
        }
        visit.setActual(true);
        visitRepository.save(visit);
    }

    @Override
    public void removeVisit(long id) {
        if(visitRepository.findById(id)==null){
            throw new VisitNotFoundException("Visit not found");
        }
        visitRepository.deleteById(id);
    }

    @Override
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public List<Visit> getVisitsForDoctor(String doctorEmail) {
        return visitRepository.findAllByDoctorEmail(doctorEmail);
    }

    public Visit getUnpaidVisitForUser(String userEmail){
        List<Visit> unpaidVisits = visitRepository.findAllByUserEmailAndIsPaid(userEmail,false);
        if(unpaidVisits.size()>0){
            return unpaidVisits.get(0);
        }
       return null;
    }

    public void completeVisit (long visitId){
        Visit visit=visitRepository.findById(visitId);
        if(visit==null){
            throw new VisitNotFoundException("Visit not found");
        }
        visit.setActual(false);
        visitRepository.save(visit);
    }
    public Visit getVisit(long id){
        return visitRepository.findById(id);
    }

    public void acceptVisit(String userEmail){
        Visit visit = getUnpaidVisitForUser(userEmail);
        if(visit==null){
            throw new VisitNotFoundException("Visit not found");
        }
        visit.setPaid(true);
        visitRepository.save(visit);
    }
    @Override
    public List<Time> getAvailableHours(Date date, String doctorEmail) {
        List<Time> timeList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");
        for (Visit visit: visitRepository.findAllByDoctorEmail(doctorEmail)
             ) {
            if(simpleDateFormat.format(visit.getDate()).equals(simpleDateFormat.format(date))){
                timeList.add(visit.getTime());
            }
        }
     /*   for (Visit visit: visitRepository.findAllByDateAndDoctorEmail(date,doctorEmail)) {
            timeList.add(visit.getTime());
        }*/
        Time time= new Time(1);
        List<Time> availableTime = new ArrayList<Time>();
        for(long t = 36000000; t<64800000; t=t+1800000){
            time.setTime(t);
            if(!timeList.contains(time)){
                availableTime.add(new Time(t));
            }

        }
        return availableTime;
    }
}