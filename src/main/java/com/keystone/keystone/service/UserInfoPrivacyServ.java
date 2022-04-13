package com.keystone.keystone.service;

import com.keystone.keystone.model.UserInfoPrivacy;

public interface UserInfoPrivacyServ {
    //使用id获得用户信息隐私等级
    public UserInfoPrivacy getUserInfoPrivacy(int userId);
    //储存用户信息隐私等级
    public int saveUserInfoPrivacy(UserInfoPrivacy uip);

}
