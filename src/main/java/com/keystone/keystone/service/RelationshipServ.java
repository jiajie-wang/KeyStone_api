package com.keystone.keystone.service;

import java.util.List;
import java.util.Map;

import com.keystone.keystone.model.Relationship;
import com.keystone.keystone.model.RelationshipKey;

public interface RelationshipServ {
    //获取二人单向关系
    public int getLevel(int userId, int friendId);
    //获取二人双向关系
    public int getRelaLevel(int userId, int friendId);
    //删除单向关系
    public RelationshipKey deleteRelationship(int userId, int friendId);
    //储存某关系
    public RelationshipKey saveRelationship(Relationship relationship);
    //获取好友列表（好友Id，是否是密友）
    public Map<Integer, Boolean> getFriendList(int userId);
    //获取1级好友请求列表
    public List<Integer> getRequestList(int friendId);
    //获取密友请求列表
    public List<Integer> getCloseRequestList(int friendId);
    //获取推送Map并剔除好友
    public void rearrangeMatchers(int userId, Map<Integer, Double> matcherMap);
}
