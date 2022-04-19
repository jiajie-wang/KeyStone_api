package com.keystone.keystone.service;

import java.util.Map;

import com.keystone.keystone.model.MatchingRecord;
import com.keystone.keystone.model.MatchingRecordKey;

public interface MatchingRecordServ {
    //获取推送Map并依据推送时间重新评判优先级
    public void rearrangeMatchers(int userId, Map<Integer, Double> matcherMap);
    //储存某关系
    public MatchingRecordKey saveRecord(MatchingRecord record);
    //清空所有匹配记录
    public void clearAllRecords();
}
