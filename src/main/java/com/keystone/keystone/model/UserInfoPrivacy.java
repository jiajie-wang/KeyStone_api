package com.keystone.keystone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//用户基本信息的隐私等级：0，1，2 ,3 分别对应于 所有人可见，普通朋友及密友可见，仅密友可见, 仅自己可见
//在未设置情况下所有人可见, default = 0

@Entity
public class UserInfoPrivacy {
    // 用户独有id,主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    // 用户生日
    private int birthdayPrivacy;
    // 学校
    private int schoolPrivacy;
    // 感情关系
    private int relaStatPrivacy;
    // 身高（cm）
    private int heightPrivacy;
    // 体重（斤）
    private int weightPrivacy;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getBirthdayPrivacy() {
        return birthdayPrivacy;
    }
    public void setBirthdayPrivacy(int birthdayPrivacy) {
        this.birthdayPrivacy = birthdayPrivacy;
    }
    public int getSchoolPrivacy() {
        return schoolPrivacy;
    }
    public void setSchoolPrivacy(int schoolPrivacy) {
        this.schoolPrivacy = schoolPrivacy;
    }
    public int getRelaStatPrivacy() {
        return relaStatPrivacy;
    }
    public void setRelaStatPrivacy(int relaStatPrivacy) {
        this.relaStatPrivacy = relaStatPrivacy;
    }
    public int getHeightPrivacy() {
        return heightPrivacy;
    }
    public void setHeightPrivacy(int heightPrivacy) {
        this.heightPrivacy = heightPrivacy;
    }
    public int getWeightPrivacy() {
        return weightPrivacy;
    }
    public void setWeightPrivacy(int weightPrivacy) {
        this.weightPrivacy = weightPrivacy;
    }
    

}
