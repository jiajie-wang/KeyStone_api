package com.keystone.keystone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//用户账号信息

@Entity
public class UserAccountInfo {
    //用户独有Id，主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    //限制长度6-16位的密码，不得为空
    @Column(length = 16, nullable = false)
    private String password;
    //邮箱，每个邮箱仅限注册一个账号，不得为空
    @Column(unique = true, nullable = false)
    private String email;
    //电话，由于11位较长需要TEXT
    @Column(nullable = true)
    private String phoneNum;
    //验证问题，不得为空
    @Column(nullable = false)
    private String verifyQues;
    //验证问题答案，不得为空
    @Column(nullable = false)
    private String verifyAns;


    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getVerifyQues() {
        return verifyQues;
    }
    public void setVerifyQues(String verifyQues) {
        this.verifyQues = verifyQues;
    }
    public String getVerifyAns() {
        return verifyAns;
    }
    public void setVerifyAns(String verifyAns) {
        this.verifyAns = verifyAns;
    }
    
}
