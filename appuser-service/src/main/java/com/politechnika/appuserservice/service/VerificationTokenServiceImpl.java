package com.politechnika.appuserservice.service;


import com.politechnika.appuserservice.model.VerificationToken;
import com.politechnika.appuserservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    VerificationTokenRepository tokenRepository;
    AppUserService appUserService;
    @Autowired
    public  VerificationTokenServiceImpl(VerificationTokenRepository tokenRepository, AppUserService appUserService){
        this.tokenRepository=tokenRepository;
        this.appUserService=appUserService;
    }
    public void addVerificationToken(VerificationToken token){
        tokenRepository.save(token);
    }
    public void removeVerificationToken(VerificationToken token){
        tokenRepository.delete(token);
    }
    public VerificationToken getTockenById(long id){
        return tokenRepository.findById(id);
    }
    public VerificationToken getTockenByTockenCode(String tokenCode){
        return  tokenRepository.findByToken(tokenCode);
    }
    public List<VerificationToken> getList(){
       return tokenRepository.findAll();
    }

    @Transactional
    public void active(String token){
        VerificationToken token1 = getTockenByTockenCode(token);
        token1.getAppUser().setEnabled(true);
        appUserService.editAppUser(token1.getAppUser(), token1.getAppUser().getId());
        removeVerificationToken(token1);
    }
}
