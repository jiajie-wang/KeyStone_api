package com.keystone.keystone.model;

import java.io.Serializable;

//这是Relationship类使用的复合主键，由用户Id和对应的熟人/好友/密友的Id组成

public class RelationshipKey implements Serializable{
    
    private int userId;
    private int friendId;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getFriendId() {
        return friendId;
    }
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
