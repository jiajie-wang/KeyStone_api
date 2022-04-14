package com.keystone.keystone.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.keystone.keystone.model.Tag;
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

    @Override
    public Set<Integer> getTagIdSet(int userId) {
        Set<Integer> tagIdSet = new HashSet<Integer>();
        UserBasicInfo ubi = ubiRepo.findById(userId).get();
        for(Tag tag: ubi.getTagSet()){
            tagIdSet.add(tag.getTagId());
        }
        return tagIdSet;
    }

    @Override
    public int saveUserTagSet(int userId, Set<Tag> tagSet) {
        UserBasicInfo ubi = ubiRepo.findById(userId).get();
        ubi.getTagSet().addAll(tagSet);
        ubiRepo.save(ubi);
        return userId;
    }

}
