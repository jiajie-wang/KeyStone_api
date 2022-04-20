package com.keystone.keystone.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

//用户关系，（暂时，可更改）共有陌生、好友、密友三级，分别用无数据（数据库内）/0（返回方法）、1、2代表
//关系是层进式的，比如两人若想成为密友必须先互是好友
//如果在表中有user A - friend B与user B - friend A的关系，则A与B互为好友
//如果在表中只有user A - friend B的关系，则是属于A向B好友请求的阶段，A与B仍然视为非好友，但B的请求列表有A
//解除好友会同时移除双向的关系
//其他级别的关系同理

@Entity
@IdClass(RelationshipKey.class)
public class Relationship implements Serializable{
    //用户Id
    @Id
    private int userId;
    //好友/密友的Id
    @Id
    private int friendId;
    //级别
    private int level;

    public RelationshipKey getId(){
        RelationshipKey id = new RelationshipKey();
        id.setUserId(userId);
        id.setFriendId(friendId);
        return id;
    }
    public void setId(RelationshipKey id){
        userId = id.getUserId();
        friendId = id.getFriendId();
    }
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
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    
}
