package com.keystone.keystone.service;

import com.keystone.keystone.model.UserBasicInfo;

public interface UserBasicInfoServ {
    //使用ID获得用户个人资料信息（基本信息）
    public UserBasicInfo getUserBasicInfo(int userId);
    //储存用户基本信息
    public int saveUserBasicInfo(UserBasicInfo ubi);
}
