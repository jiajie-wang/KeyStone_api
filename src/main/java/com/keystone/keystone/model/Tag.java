package com.keystone.keystone.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

//个人信息资料能够使用的标签（待完善）

@Entity
public class Tag {
    //tag独有Id，主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;
    //tag互斥组号，如同一用户不能同时拥有白羊座与金牛座的标签，互斥组号相同的标签不应出现在同一用户身上。0为无互斥组的独立tag
    //已抛弃的设想，目前没有作用
    @Column(nullable = true)
    private int mutexNum = 0;
    //tag名称，不允许重复
    @Column(unique = true)
    private String tagName;
    //独特标签，目前应用于性格Tag，它们在matching算法中被用到
    //0 普通兴趣Tag 1 Extrovert 2 Introvert 3 Judgment 4 Perceiving 5 Sensing/Intuition/Thinking/Feeling 
    @Column(nullable = true)
    private int uniqueTag;
    //为方便查找，多对多储存所有拥有此标签的ubi，外键链接到UserBasicInfo
    @ManyToMany(mappedBy = "tagSet")
    private Set<UserBasicInfo> ubiSet = new HashSet<UserBasicInfo>();
    
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
    public int getUniqueTag() {
        return uniqueTag;
    }
    public void setUniqueTag(int uniqueTag) {
        this.uniqueTag = uniqueTag;
    }
    public Set<UserBasicInfo> getUbiSet() {
        return ubiSet;
    }
    //此注解为了防止出现无限循环包含外键对象
    @JsonBackReference
    public void setUbiSet(Set<UserBasicInfo> ubiSet) {
        this.ubiSet = ubiSet;
    }
    
    
}
