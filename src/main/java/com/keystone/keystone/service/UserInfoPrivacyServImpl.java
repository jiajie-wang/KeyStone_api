package com.keystone.keystone.service;

import java.util.Optional;

import com.keystone.keystone.model.UserInfoPrivacy;
import com.keystone.keystone.repo.UserInfoPrivacyRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoPrivacyServImpl implements UserInfoPrivacyServ {
    @Autowired
    private UserInfoPrivacyRepo uipRepo;

    @Override
    public UserInfoPrivacy getUserInfoPrivacy(int userId){
        Optional<UserInfoPrivacy> uip = uipRepo.findById(userId);
        return uip.isPresent() ? uip.get() : null;
    }

    @Override
    public int saveUserInfoPrivacy(UserInfoPrivacy uip){
        return uipRepo.save(uip).getUserId();
    }
}
