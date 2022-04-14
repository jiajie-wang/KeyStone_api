package com.keystone.keystone.service;

import com.keystone.keystone.model.UserAccountInfo;

public interface UserAccountInfoServ {
    //使用ID获得用户的账户信息
    public UserAccountInfo getUserAccountInfo(int userId);
    //储存用户账户信息，返回用户ID
    public int saveUserAccountInfo(UserAccountInfo uai);
    //登录时需校验密码，使用此方法用邮箱调取密码
    public String getPasswordByEmail(String email);
}
