package com.keystone.keystone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//本文档与所有与“Path”相关的文档全部为练习时作为参考的例子，与实际项目无关，请勿改动

@Entity
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pathId;
    private String pathName;
    private double para1;
    private int para2;
    private boolean isValid;
    public int getPathId() {
        return pathId;
    }
    public void setPathId(int pathId) {
        this.pathId = pathId;
    }
    public String getPathName() {
        return pathName;
    }
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
    public double getPara1() {
        return para1;
    }
    public void setPara1(double para1) {
        this.para1 = para1;
    }
    public int getPara2() {
        return para2;
    }
    public void setPara2(int para2) {
        this.para2 = para2;
    }
    public boolean getIsValid() {
        return isValid;
    }
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
