package com.keystone.keystone.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

//用户基本信息（个人资料页），信息在用户一开始未填写或之后清空的情况下为未知情况

@Entity
public class UserBasicInfo {
    //用户独有Id，主键
    @Id
    private int userId;
    //头像，使用长文本储存
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;
    //用户昵称，限制长度30，不允许重复，用户昵称为空时在UI上显示用户Id
    @Column(length = 30, unique = true)
    private String userName;
    //用户性别，0暂无信息 1男性 2女性
    @Column(nullable = true)
    private int gender;
    //用户生日，未知为new Date(-1)，getTime()能够检测-1
    @Column(columnDefinition = "DATE")
    private Date birthday;
    //学校
    private String school;
    //感情关系，0未知 1单身 2恋爱中 3…… 可补充
    @Column(nullable = true)
    private int relaStat;
    //身高（cm）0未知
    @Column(nullable = true)
    private int height;
    //体重（斤）0未知
    @Column(nullable = true)
    private int weight;
    //多对多储存Tags，外键链接到Tag
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tagSet = new HashSet<Tag>();
    //偏好掩码，Friends 1 Love男性 2 Love女性 4 Foreign Contacts 8 Chat 16
    @Column(nullable = true)
    private int perfer;
    //个人介绍
    @Column(columnDefinition = "TEXT")
    private String introduction;
    //sai hi to others
    @Column(columnDefinition = "TEXT")
    private String sayHi;

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
    public Set<Tag> getTagSet() {
        return tagSet;
    }
    //此注解为了防止出现无限循环包含外键对象
    @JsonBackReference
    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }
    public int getPerfer() {
        return perfer;
    }
    public void setPerfer(int perfer) {
        this.perfer = perfer;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getSayHi() {
        return sayHi;
    }
    public void setSayHi(String sayHi) {
        this.sayHi = sayHi;
    }
    
    
}
