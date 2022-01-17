package com.politechnika.medicineservice.service;

import com.politechnika.medicineservice.model.ContactData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "appuser-service")
public interface AppUserServiceClient {

    @GetMapping("users/doctors/contact")
    public List<ContactData> getDoctorsContactData();

    @GetMapping("users/doctors/email/{doctorEmail}/contact")
    public ContactData getDoctorContactDataFindByEmail(@PathVariable String doctorEmail);

}
