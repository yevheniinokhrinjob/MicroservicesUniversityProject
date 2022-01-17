package com.politechnika.appuserservice.controller;


import com.politechnika.appuserservice.model.AppUser;
import com.politechnika.appuserservice.payload.ContactData;
import com.politechnika.appuserservice.payload.EditPasswordRequest;
import com.politechnika.appuserservice.service.AppUserRoleService;
import com.politechnika.appuserservice.service.AppUserService;
import com.politechnika.appuserservice.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private AppUserService appUserService;
    private VerificationTokenService verificationTokenService;


    @Autowired
    public AppUserController(AppUserService appUserService, AppUserRoleService appUserRoleService, VerificationTokenService verificationTokenService){
        this.appUserService = appUserService;
        this.verificationTokenService=verificationTokenService;
    }



    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping("/addRoleToUser/user/{userId}/role/{roleName}")
    public String addRoleToUser(@PathVariable Long userId, @PathVariable String roleName ){
        try {
            appUserService.addRoleToAppUser(userId,roleName);
            return "role added";
        } catch (Exception e) {
            return "user not found";
        }

    }

    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping("/removeRoleFromUser/user/{userId}/role/{roleName}")
    public String removeRoleFromUser(@PathVariable Long userId, @PathVariable String roleName ){
        try {
            appUserService.removeAppUserRole(userId,roleName);
            return "role removed";
        } catch (Exception e) {
            return "user not found";
        }

    }

    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping
    public List<AppUser> getUserList(){
        return appUserService.listAppUser();
    }


    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId, HttpServletRequest request){

        if(getCurrentUser(request).getId()==userId || request.isUserInRole("ROLE_ADMIN")) {
            return ResponseEntity.ok(appUserService.getAppUser(userId));

        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No permissions");
        }
    }

    @PostMapping("/registration")
    public String register(@RequestBody @Valid AppUser appUser){

        appUserService.addAppUser(appUser);
        return "added";
    }

    @GetMapping(value="/activate/{token}")
    public String verificationToken(@PathVariable("token") String token){
        verificationTokenService.active(token);
        return "activated";

    }
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/{userId}")
    public String removeUser(@PathVariable Long userId){

        appUserService.removeAppUser(userId);
        return "user removed";

    }
    @RolesAllowed({"ROLE_USER"})
    @PutMapping("/{userId}")
    public String editUserData(@PathVariable Long userId, @RequestBody @Valid AppUser appUser, HttpServletRequest request){

        if(getCurrentUser(request).getId()==userId) {
            appUserService.editAppUser(appUser, userId);
            return "edited user data";

        }else {
            return "no permissions";
        }
    }
    @RolesAllowed({"ROLE_USER"})
    @PutMapping("/editPassword/{userId}")
    public String editUserPassword(@PathVariable Long userId, @RequestBody EditPasswordRequest editPasswordRequest, HttpServletRequest request){
        if(getCurrentUser(request).getId()==userId) {
            appUserService.editAppUserPassword(editPasswordRequest.getPassword(), userId);
            return "edited password";

        }else {
            return "no permissions";
        }
    }

    @GetMapping("/doctors/contact")
    public List<ContactData> getDoctorsContactData(){
        List<ContactData> doctorsContactData =  appUserService.listDoctors().stream()
                .map(doctor -> {
                    ContactData doctorContactData = new ContactData();
                    doctorContactData.setEmail(doctor.getEmail());
                    doctorContactData.setfName(doctor.getfName());
                    doctorContactData.setlName(doctor.getlName());
                    doctorContactData.setPhone(doctor.getPhone());
                    return doctorContactData;
                }).collect(Collectors.toList());
        return doctorsContactData;
    }

    @GetMapping("/doctors/{doctorId}/contact")
    public ContactData getDoctorContactData(@PathVariable long doctorId) {
        AppUser doctor = appUserService.getDoctor(doctorId);
        ContactData doctorContactData = new ContactData();
        doctorContactData.setEmail(doctor.getEmail());
        doctorContactData.setfName(doctor.getfName());
        doctorContactData.setlName(doctor.getlName());
        doctorContactData.setPhone(doctor.getPhone());
        return doctorContactData;
    }

    @GetMapping("/doctors/email/{doctorEmail}/contact")
    public ContactData getDoctorContactDataFindByEmail(@PathVariable String doctorEmail) {
        AppUser doctor = appUserService.getDoctorByEmail(doctorEmail);
        ContactData doctorContactData = new ContactData();
        doctorContactData.setEmail(doctor.getEmail());
        doctorContactData.setfName(doctor.getfName());
        doctorContactData.setlName(doctor.getlName());
        doctorContactData.setPhone(doctor.getPhone());
        return doctorContactData;
    }

    private AppUser getCurrentUser(HttpServletRequest request){
        return appUserService.findByEmail(request.getUserPrincipal().getName());
    }


}
