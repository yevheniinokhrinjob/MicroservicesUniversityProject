package com.politechnika.visitservice.service;

import com.politechnika.visitservice.model.Medicine;
import com.politechnika.visitservice.model.Prescription;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@FeignClient(name = "medicine-service")
public interface MedicineServiceClient {

    @GetMapping("/medicines")
    public List<Medicine> getMedicines();

    @PostMapping("/prescriptions")
    public void addPrescription(@RequestBody Prescription prescription);

}
