package com.keystone.keystone.service;

import java.util.Set;

import com.keystone.keystone.model.Tag;
import com.keystone.keystone.model.UserBasicInfo;

public interface UserBasicInfoServ {
    //使用ID获得用户个人资料信息（基本信息）
    public UserBasicInfo getUserBasicInfo(int userId);
    //储存用户基本信息，返回用户ID
    public int saveUserBasicInfo(UserBasicInfo ubi);
    //使用ID获得用户的所有Tag的ID
    public Set<Integer> getTagIdSet(int userId);
    //储存tag
    public int saveUserTagSet(int userId, Set<Tag> tagSet);
    //获得随机用户Id
    public int[] getRandomUser(int amount);
}
