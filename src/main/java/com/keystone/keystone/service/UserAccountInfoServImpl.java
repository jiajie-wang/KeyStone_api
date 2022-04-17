package com.keystone.keystone.service;

import java.util.List;
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

    @Override
    public String getPasswordByEmail(String email) {
        //虽然是List但理论上里面的uai对象不会超过一个，因为Email是特有值不会重复
        List<UserAccountInfo> uaiList = uaiRepo.findAllByEmail(email);
        if(uaiList.size() == 0)
            return null;
        else
            return uaiList.get(0).getPassword();
    }

    @Override
    public int getUserIdByEmail(String email) {
        List<UserAccountInfo> uaiList = uaiRepo.findAllByEmail(email);
        if(uaiList.size() == 0)
            return 0;
        else
            return uaiList.get(0).getUserId();
    }

    @Override
    public String getUserVerifyQues(String email) {
        List<UserAccountInfo> uaiList = uaiRepo.findAllByEmail(email);
        if(uaiList.size() == 0)
            return null;
        else
            return uaiList.get(0).getVerifyQues();
    }

    @Override
    public String getUserVerifyAns(String email) {
        List<UserAccountInfo> uaiList = uaiRepo.findAllByEmail(email);
        if(uaiList.size() == 0)
            return null;
        else
            return uaiList.get(0).getVerifyAns();
    }
    

    
}
