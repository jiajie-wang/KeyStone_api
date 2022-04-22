package com.keystone.keystone.model;

import java.io.Serializable;

//这是MatchingRecord类使用的复合主键，由用户Id和对应另一个已经匹配过的人的Id组成

public class MatchingRecordKey implements Serializable{
    private int userId;
    private int matcherId;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getMatcherId() {
        return matcherId;
    }
    public void setMatcherId(int matcherId) {
        this.matcherId = matcherId;
    }
    
}
