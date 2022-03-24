package com.keystone.keystone.service;

import com.keystone.keystone.model.UserAccountInfo;

public interface UserAccountInfoServ {
    //使用ID获得用户的账户信息
    public UserAccountInfo getUserAccountInfo(int userId);
    //储存用户账户信息
    public int saveUserAccountInfo(UserAccountInfo uai);
}
