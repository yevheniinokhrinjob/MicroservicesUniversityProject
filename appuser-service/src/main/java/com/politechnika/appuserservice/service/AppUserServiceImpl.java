package com.politechnika.appuserservice.service;

import com.politechnika.appuserservice.exeption.AppUserNotFoundException;
import com.politechnika.appuserservice.model.AppUser;
import com.politechnika.appuserservice.model.AppUserRole;
import com.politechnika.appuserservice.model.VerificationToken;
import com.politechnika.appuserservice.repository.AppUserRepository;
import com.politechnika.appuserservice.repository.AppUserRoleRepository;
import com.politechnika.appuserservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class AppUserServiceImpl implements AppUserService {

    private NotificationService notificationService;
    private AppUserRepository appUserRepository;
    private AppUserRoleRepository appUserRoleRepository;
    private VerificationTokenRepository verificationTokenRepository;


    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, AppUserRoleRepository appUserRoleRepository, NotificationService notificationService , VerificationTokenRepository verificationTokenRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserRoleRepository=appUserRoleRepository;
        this.verificationTokenRepository=verificationTokenRepository;
        this.notificationService=notificationService;
    }

    @Override
    @Transactional
    public void addAppUser(AppUser appUser) {
        appUser.getAppUserRole().add(appUserRoleRepository.findByRole("ROLE_USER"));
        appUser.setPassword(hashPassword(appUser.getPassword()));
        VerificationToken token = new VerificationToken();
        token.setToken(generateString(15));
        appUser.setToken(token);
        verificationTokenRepository.save(token);
     //   notificationService.sendStudentNotification(appUser.getEmail(),token.getToken());
        notificationService.sendStudentNotification("storojuko@gmail.com",token.getToken());

        appUserRepository.save(appUser);
    }

    @Override
    public void editAppUser(AppUser appUser, long id) {
        AppUser appUserDB = appUserRepository.findById(id);
        if(appUserDB==null){
            throw new AppUserNotFoundException("User not found");
        }
        appUserDB.setfName(appUser.getfName());
        appUserDB.setlName(appUser.getlName());
        // appUserDB.setEmail(appUser.getEmail()); Immutable
        appUserDB.setCity(appUser.getCity());
        appUserDB.setStreet(appUser.getStreet());
        appUserDB.setHouseNumber(appUser.getHouseNumber());
        appUserRepository.save(appUserDB);
    }
    @Override
    public void editAppUserPassword(String password, long id)  {
        AppUser dbAppUser = appUserRepository.findById(id);
        if(dbAppUser == null){
            throw new AppUserNotFoundException("User not found");
        }
        dbAppUser.setPassword(hashPassword(password));

        appUserRepository.save(dbAppUser);
    }


    @Override
    public List<AppUser> listAppUser() {
        return appUserRepository.findAll();
    }

    @Override
    @Transactional
    public void removeAppUser(long id) {
        AppUser appUser = appUserRepository.findById(id);
        if(appUser==null){
            throw new AppUserNotFoundException("Not found");
        }
        if(appUser.getToken()!=null) {
            verificationTokenRepository.delete(appUser.getToken());
        }

        appUserRepository.delete(appUser);
    }

    @Override
    public AppUser getAppUser(long id) {
        AppUser appUser = appUserRepository.findById(id);
        if(appUser == null) {
            throw new AppUserNotFoundException("Not found");
        }
        return appUser;
    }

    @Override
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }
    @Override
    public void addRoleToAppUser(long userId, String role) {
        AppUser appUser = appUserRepository.findById(userId);
        if(appUser == null){
            throw new AppUserNotFoundException("User not found");
        }
        appUser.getAppUserRole().add(appUserRoleRepository.findByRole(role));
        appUserRepository.save(appUser);
    }
    @Override
    public void removeAppUserRole(long userId, String userRole)  {
        Set<AppUserRole> roles = new HashSet<>(0);
        AppUser appUser = appUserRepository.findById(userId);
        if(appUser == null){
            throw new AppUserNotFoundException("User not found");
        }
        for (AppUserRole role:appUser.getAppUserRole()
        ) {
            if(!role.getRole().equals(userRole)){
                roles.add(role);
            }
        }

        appUser.setAppUserRole(roles);
        appUserRepository.save(appUser);
    }

    private String generateString(int length)
    {
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
        Random rnd = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }
        return new String(text);
    }


    private String hashPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public List<AppUser> listDoctors(){
        List<AppUser> doctors = new ArrayList<>();
        for (AppUser user:appUserRepository.findAll()   ) {
            for (AppUserRole role:user.getAppUserRole()) {
                if(role.getRole().equals("ROLE_DOCTOR")){
                    doctors.add(user);
                }
            }
        }
        System.out.println(doctors);
        return doctors;
    }

    public AppUser getDoctor(long doctorId) {
        AppUser appUser = appUserRepository.findById(doctorId);
        if(appUser == null) {
            throw new AppUserNotFoundException("Not found a doctor with given id");
        }
        for (AppUserRole role : appUser.getAppUserRole()) {
            if(role.getRole().equals("ROLE_DOCTOR")){
                return appUser;
            }
        }
        throw new AppUserNotFoundException("Not found a doctor with given id");
    }

    public AppUser getDoctorByEmail(String doctorEmail) {
        AppUser appUser = appUserRepository.findByEmail(doctorEmail);
        if(appUser == null) {
            throw new AppUserNotFoundException("Not found a doctor with given email");
        }
        for (AppUserRole role : appUser.getAppUserRole()) {
            if(role.getRole().equals("ROLE_DOCTOR")){
                return appUser;
            }
        }
        throw new AppUserNotFoundException("Not found a doctor with given email");
    }
}
