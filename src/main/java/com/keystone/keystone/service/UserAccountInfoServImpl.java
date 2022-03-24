package com.keystone.keystone.service;

import java.util.Optional;

import com.keystone.keystone.model.UserAccountInfo;
import com.keystone.keystone.repo.UserAccountInfoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountInfoServImpl implements UserAccountInfoServ{
    @Autowired
    private UserAccountInfoRepo uaiRepo;

    @Override
    public UserAccountInfo getUserAccountInfo(int userId) {
        Optional<UserAccountInfo> uai = uaiRepo.findById(userId);
        return uai.isPresent() ? uai.get() : null;
    }

    @Override
    public int saveUserAccountInfo(UserAccountInfo uai) {
        return uaiRepo.save(uai).getUserId();
    }
    
}
