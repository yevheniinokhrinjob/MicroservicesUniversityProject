package com.politechnika.visitservice.controller;

import com.politechnika.visitservice.model.Medicine;
import com.politechnika.visitservice.model.Prescription;
import com.politechnika.visitservice.model.Visit;
import com.politechnika.visitservice.service.MedicineServiceClient;
import com.politechnika.visitservice.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitController {

    VisitService visitService;
    MedicineServiceClient medicineServiceClient;


    @Autowired
    public VisitController(VisitService visitService, MedicineServiceClient medicineServiceClient){
        this.visitService=visitService;
        this.medicineServiceClient=medicineServiceClient;
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping
    public List<Visit> getVisitList(){
        return visitService.getAllVisits();
    }

    @RolesAllowed({"ROLE_DOCTOR"})
    @GetMapping("/{doctorEmail}")
    public ResponseEntity<?> getVisitsForDoctor(@PathVariable String doctorEmail, HttpServletRequest request){
        if(request.getUserPrincipal().getName().equals(doctorEmail)) {
            return ResponseEntity.ok().body(visitService.getVisitsForDoctor(doctorEmail)) ;
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }
    @RolesAllowed({"ROLE_USER","ROLE_DOCTOR", "ROLE_ADMIN"})
    @PostMapping
    public String createVisit(@RequestBody Visit visit, HttpServletRequest request){
        visit.setUserEmail(request.getUserPrincipal().getName());
        visitService.addVisit(visit);
        return "visit added";
    }
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/{visitId}")
    public String removeVisit(@PathVariable long visitId){
        visitService.removeVisit(visitId);
        return "visit removed";
    }

    @RolesAllowed({"ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN"})
    @GetMapping("/unpaid")
    public Visit getUnpaidVisit(HttpServletRequest request){
        String userEmail =request.getUserPrincipal().getName();
        return visitService.getUnpaidVisitForUser(userEmail);
    }
    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping("/acceptPayment/{userEmail}")
    public String acceptUserPayment(@PathVariable String userEmail){
        visitService.acceptVisit(userEmail);
        return "visit accepted";
    }
    @RolesAllowed({"ROLE_DOCTOR"})
    @PostMapping("/completeVisit/{visitId}")
    public String completeVisit(@PathVariable long visitId, @RequestBody Prescription prescription) {
        visitService.completeVisit(visitId);
        medicineServiceClient.addPrescription(prescription);
        return "visit completed";
    }

    @RolesAllowed({"ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN"})
    @GetMapping("/date/{stringDate}/doctorEmail/{doctorEmail}")
    public List<Time> getAvailableHours(@PathVariable String stringDate, @PathVariable String doctorEmail) throws ParseException {
        Date date=new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
        return visitService.getAvailableHours(date, doctorEmail);
    }
    @RolesAllowed({"ROLE_DOCTOR", "ROLE_ADMIN"})
    @GetMapping("/medicines")
    public  List<Medicine> getMedicines(){
        return medicineServiceClient.getMedicines();
    }

}
