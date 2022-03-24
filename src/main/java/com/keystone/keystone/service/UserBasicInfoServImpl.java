package com.keystone.keystone.service;

import java.util.Optional;

import com.keystone.keystone.model.UserBasicInfo;
import com.keystone.keystone.repo.UserBasicInfoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBasicInfoServImpl implements UserBasicInfoServ{
    @Autowired
    private UserBasicInfoRepo ubiRepo;

    @Override
    public UserBasicInfo getUserBasicInfo(int userId) {
        Optional<UserBasicInfo> ubi = ubiRepo.findById(userId);
        return ubi.isPresent() ? ubi.get() : null;
    }

    @Override
    public int saveUserBasicInfo(UserBasicInfo ubi) {
        return ubiRepo.save(ubi).getUserId();
    }
}
