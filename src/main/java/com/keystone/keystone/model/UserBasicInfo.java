package com.keystone.keystone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//用户基本信息（个人资料页），信息在用户一开始未填写或之后清空的情况下为未知情况

@Entity
public class UserBasicInfo {
    //用户独有Id，主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    //头像，使用长文本储存
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;
    //用户昵称，限制长度30，不允许重复，用户昵称为空时在UI上显示用户Id
    @Column(length = 30, unique = true)
    private String userName;
    //用户性别，0暂无信息 1男性 2女性
    private int gender;
    //用户生日，未知为new Date(-1)，getTime()能够检测-1
    @Column(columnDefinition = "DATE")
    private Date birthday;
    //学校
    private String school;
    //感情关系，0未知 1单身 2恋爱中 3…… 可补充
    private int relaStat;
    //身高（cm）0未知
    private int height;
    //体重（斤）0未知
    private int weight;
    //文本储存标签id组，如“1,34,35,60,78”
    @Column(columnDefinition = "TEXT")
    private String tags;
    //偏好掩码，Friends 1 Love同性 2 Love异性 4 Foreign Contacts 8 Chat 16
    private int perfer;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public int getRelaStat() {
        return relaStat;
    }
    public void setRelaStat(int relaStat) {
        this.relaStat = relaStat;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public int getPerfer() {
        return perfer;
    }
    public void setPerfer(int perfer) {
        this.perfer = perfer;
    }
    
}
