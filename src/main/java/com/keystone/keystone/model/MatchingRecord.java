package com.keystone.keystone.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

//匹配消息记录
//假设A在匹配时被推送了B，那么B在接下来15天不会被推送给A，这个时间记录于此，即userId = A，matcherId = B，date是上一次最晚匹配的日期

@Entity
@IdClass(MatchingRecordKey.class)
public class MatchingRecord implements Serializable{
    //用户Id
    @Id
    private int userId;
    //已匹配过的另一用户的Id
    @Id
    private int matcherId;
    //上一次匹配的日期
    @Column(nullable = false)
    private Date date;

    public MatchingRecordKey getId(){
        MatchingRecordKey id = new MatchingRecordKey();
        id.setUserId(userId);
        id.setMatcherId(matcherId);
        return id;
    }
    public void setId(MatchingRecordKey id){
        userId = id.getUserId();
        matcherId = id.getMatcherId();
    }
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    
}
