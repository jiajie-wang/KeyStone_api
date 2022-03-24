package com.keystone.keystone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//个人信息资料能够使用的标签（待完善）

@Entity
public class Tag {
    //tag独有Id，主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;
    //tag互斥组号，如同一用户不能同时拥有白羊座与金牛座的标签，互斥组号相同的标签不应出现在同一用户身上。0为无互斥组的独立tag
    private int mutexNum;
    //tag名称
    private String tagName;
    
    public int getTagId() {
        return tagId;
    }
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
    public int getMutexNum() {
        return mutexNum;
    }
    public void setMutexNum(int mutexNum) {
        this.mutexNum = mutexNum;
    }
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    
}
