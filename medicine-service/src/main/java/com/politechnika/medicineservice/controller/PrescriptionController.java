package com.politechnika.medicineservice.controller;

import com.politechnika.medicineservice.model.Prescription;
import com.politechnika.medicineservice.repository.MedicineRepository;
import com.politechnika.medicineservice.repository.PrescriptionRepository;
import com.politechnika.medicineservice.service.AppUserServiceClient;
import com.politechnika.medicineservice.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    AppUserServiceClient appUserServiceClient;

    @Autowired
    PdfService pdfService;

    @RolesAllowed({"ROLE_DOCTOR"})
    @PostMapping()
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping()
    public List<Prescription> getPrescriptions()  {
        return prescriptionRepository.findAll();
    }

    @RolesAllowed({"ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN"})
    @GetMapping("/me")
    public void getOwnPrescriptions(@AuthenticationPrincipal Principal principal, HttpServletResponse response)  {
        List<Prescription> prescriptions = new LinkedList<>();
        for(Prescription prescription : prescriptionRepository.findAll()) {
            if(principal.getName().equals(prescription.getDoctorEmail()) || principal.getName().equals(prescription.getPatientEmail())) {
                prescriptions.add(prescription);
            }
        }
        pdfService.generatePdf(prescriptions, response);


    }

    @RolesAllowed({"ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN"})
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<?> getPrescription(@AuthenticationPrincipal Principal principal, @PathVariable String prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .map(prescription -> {
                    if(principal.getName().equals(prescription.getDoctorEmail()) || principal.getName().equals(prescription.getPatientEmail())) {
                        return ResponseEntity.ok(prescription);
                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot access this prescription!");
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    @RolesAllowed({"ROLE_DOCTOR", "ROLE_ADMIN"})
    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity deletePrescription(@PathVariable String prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .map(prescription -> { prescriptionRepository.delete(prescription);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @RolesAllowed({ "ROLE_DOCTOR", "ROLE_ADMIN"})
    @PutMapping("/{prescriptionId}")
    public ResponseEntity<Prescription> modifyPrescription(@PathVariable String prescriptionId, @RequestBody Prescription medicine) {
        return prescriptionRepository.findById(prescriptionId)
                .map(prescriptionFromDb -> {
                    prescriptionFromDb.setDoctorEmail(medicine.getDoctorEmail());
                    prescriptionFromDb.setPatientEmail(medicine.getDoctorEmail());
                    prescriptionFromDb.setDate(medicine.getDate());
                    prescriptionFromDb.setMedicines(medicine.getMedicines());
                    return ResponseEntity.ok(prescriptionRepository.save(prescriptionFromDb));
                }).orElse(ResponseEntity.notFound().build());
    }

    @RolesAllowed({ "ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN"})
    @GetMapping("/{prescriptionId}/contact")
    public ResponseEntity getPrescriberContactData(@AuthenticationPrincipal Principal principal, @PathVariable String prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .map(prescription -> {
                    if(principal.getName().equals(prescription.getDoctorEmail()) || principal.getName().equals(prescription.getPatientEmail())) {
                        return ResponseEntity.ok(appUserServiceClient.getDoctorContactDataFindByEmail(prescription.getDoctorEmail()));
                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot access data related to this prescription!");
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

}
