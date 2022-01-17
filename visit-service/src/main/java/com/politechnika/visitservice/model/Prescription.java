package com.politechnika.visitservice.model;

import org.springframework.data.annotation.Id;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Prescription {

    @Id
    private String id;

    private String doctorEmail;
    private String patientEmail;
    private LocalDate date;
    private List<Medicine> medicines = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
