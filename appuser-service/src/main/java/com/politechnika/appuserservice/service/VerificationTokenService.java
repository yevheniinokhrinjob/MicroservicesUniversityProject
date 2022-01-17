package com.politechnika.appuserservice.service;


import com.politechnika.appuserservice.model.VerificationToken;

import java.util.List;

public interface VerificationTokenService {

    void addVerificationToken(VerificationToken token);
    void removeVerificationToken(VerificationToken token);
    VerificationToken getTockenById(long id);
    VerificationToken getTockenByTockenCode(String tokenCode);
    List<VerificationToken> getList();
    void active(String token);
}
