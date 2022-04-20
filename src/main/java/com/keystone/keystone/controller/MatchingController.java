package com.keystone.keystone.controller;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

import com.keystone.keystone.model.MatchingRecord;
import com.keystone.keystone.model.MatchingRecordKey;
import com.keystone.keystone.model.UserBasicInfo;
import com.keystone.keystone.service.MatchingRecordServ;
import com.keystone.keystone.service.RelationshipServ;
import com.keystone.keystone.service.TagService;
import com.keystone.keystone.service.UserAccountInfoServ;
import com.keystone.keystone.service.UserBasicInfoServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController {
    @Autowired
    private UserAccountInfoServ uaiService;
    @Autowired
    private UserBasicInfoServ ubiService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MatchingRecordServ recordService;
    @Autowired
    private RelationshipServ relaService;

    //用用户Id获取三个适合匹配的其他用户，将含这三个用户的队列返回到前端
    @GetMapping(value = "match/{userId}")
    @CrossOrigin
    public ResponseEntity<Queue<Integer>> getMatchers(@PathVariable("userId") int userId){
        //用户不存在则返回404
        if(uaiService.getUserAccountInfo(userId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        //建立将用来储存的数据结构
        Map<Integer, Integer> normalMap = new HashMap<Integer, Integer>();
        Map<Integer, Integer> uniqueMap = new HashMap<Integer, Integer>();
        Map<Integer, Double> matcherMap = new HashMap<Integer, Double>();
        Queue<Integer> matcherQueue = new ArrayDeque<Integer>();
        //获取用户的tag信息，解析性格与爱好，分别按每个标签提取符合的用户，纳入Map
        //normalMap储存普通标签的相同个数，凡是拥有任一相同标签的用户都会被纳入Map
        //uniqueMap储存性格标签的满足条件个数，凡是满足任一个条件的用户都会被纳入Map
        //性格标签满足条件：
        //Extrovert-Introvert/Judgment-Perceiving 两个用户的标签对应相异
        //Sensing-Intuition/Thinking-Feeling 两个用户的标签对应相同
        Set<Integer> tagSet = ubiService.getTagIdSet(userId);
        for(int i : tagSet){
            switch(tagService.getTag(i).getUniqueTag()){
                case 0: 
                for(UserBasicInfo ubi : tagService.getTag(i).getUbiSet()){
                    int matcherId = ubi.getUserId();
                    if(normalMap.containsKey(matcherId))
                        normalMap.put(matcherId, normalMap.get(matcherId) + 1);
                    else
                        normalMap.put(matcherId, 1);
                }
                break;

                case 1:
                for(UserBasicInfo ubi : tagService.getTag(2).getUbiSet()){
                    int matcherId = ubi.getUserId();
                    if(uniqueMap.containsKey(matcherId))
                        uniqueMap.put(matcherId, uniqueMap.get(matcherId) + 1);
                    else
                        uniqueMap.put(matcherId, 1);
                }
                break;

                case 2:
                for(UserBasicInfo ubi : tagService.getTag(1).getUbiSet()){
                    int matcherId = ubi.getUserId();
                    if(uniqueMap.containsKey(matcherId))
                        uniqueMap.put(matcherId, uniqueMap.get(matcherId) + 1);
                    else
                        uniqueMap.put(matcherId, 1);
                }
                break;

                case 3:
                for(UserBasicInfo ubi : tagService.getTag(4).getUbiSet()){
                    int matcherId = ubi.getUserId();
                    if(uniqueMap.containsKey(matcherId))
                        uniqueMap.put(matcherId, uniqueMap.get(matcherId) + 1);
                    else
                        uniqueMap.put(matcherId, 1);
                }
                break;

                case 4:
                for(UserBasicInfo ubi : tagService.getTag(3).getUbiSet()){
                    int matcherId = ubi.getUserId();
                    if(uniqueMap.containsKey(matcherId))
                        uniqueMap.put(matcherId, uniqueMap.get(matcherId) + 1);
                    else
                        uniqueMap.put(matcherId, 1);
                }
                break;

                case 5:
                for(UserBasicInfo ubi : tagService.getTag(i).getUbiSet()){
                    int matcherId = ubi.getUserId();
                    if(uniqueMap.containsKey(matcherId))
                        uniqueMap.put(matcherId, uniqueMap.get(matcherId) + 1);
                    else
                        uniqueMap.put(matcherId, 1);
                }
                break;
            }
        }
        //按标签情况计算权重
        //普通标签权重：0 0个相同标签 1 1-3个 2 4-5个 3 6-9个 4 10-13个 之后每多4个相同标签权重+1
        //性格标签权重：每满足一个条件权重+1
        //matcherMap中的毛权重为 普通标签权重 * 0.5 + 性格标签权重 * 0.5
        for(Entry<Integer, Integer> e : normalMap.entrySet()){
            int normalWeight = 0;
            if(e.getValue() < 1)
                normalWeight = 0;
            else if(e.getValue() < 4)
                normalWeight = 1;
            else if(e.getValue() < 6)
                normalWeight = 2;
            else if(e.getValue() < 10)
                normalWeight = 3;
            else 
                normalWeight = 4 + (e.getValue() - 10) / 4;
            matcherMap.put(e.getKey(), 0.5 * normalWeight);
        }

        for(Entry<Integer, Integer> e : uniqueMap.entrySet()){
            int matcherId = e.getKey();
            if(matcherMap.containsKey(matcherId))
                matcherMap.put(matcherId, matcherMap.get(matcherId) + 0.5 * e.getValue());
            else
                matcherMap.put(matcherId, 0.5 * e.getValue());
        }
        //解析用户的偏好掩码与性别，根据偏好与匹配者的性取向重调权重
        //目前的计算方法是，如果有friend需求，则符合性取向的权重上涨一半，之后没有friend需求的权重折半
        //如果没有friend需求，则不符合性取向的权重变为十分之一，之后没有love需求的权重折半
        //foreign contact暂时不确定作用，可能需要对方的国籍资料
        //如果有chat需求，则同样有chat prefer的权重上涨五分之一
        //对方性取向不符合自己性别的，如果没有friend需求则权重变为四分之一
        boolean[] preferArray = analysePrefer(ubiService.getUserBasicInfo(userId).getPerfer());

        for(Entry<Integer, Double> e : matcherMap.entrySet()){
            int matcherId = e.getKey();
            boolean[] matcherPrefer = analysePrefer(ubiService.getUserBasicInfo(matcherId).getPerfer());
            int gender = ubiService.getUserBasicInfo(userId).getGender();
            int matcherGender = ubiService.getUserBasicInfo(matcherId).getGender();
            if(preferArray[0]){
                if(matcherGender != 0 && preferArray[matcherGender])
                    matcherMap.put(matcherId, e.getValue() * 1.5);
                if(!matcherPrefer[0])
                    matcherMap.put(matcherId, e.getValue() * 0.5);
            }else{
                if(matcherGender != 0 && !preferArray[matcherGender])
                    matcherMap.put(matcherId, e.getValue() * 0.1);
                if(!matcherPrefer[1] && !matcherPrefer[2])
                    matcherMap.put(matcherId, e.getValue() * 0.5);
            }
            if(preferArray[4] && matcherPrefer[4])
                matcherMap.put(matcherId, e.getValue() * 1.2);
            if(gender != 0 && !matcherPrefer[gender] && !matcherPrefer[0])
                matcherMap.put(matcherId, e.getValue() * 0.25);
        }
        //根据已推送的情况重调权重，已推送过的用户的权重将变为十分之一
        recordService.rearrangeMatchers(userId, matcherMap);
        //去除好友与本人，不会被正常匹配到
        relaService.rearrangeMatchers(userId, matcherMap);
        //取三个权重最大的用户入队列，如果缺乏符合条件的用户，随机在数据库中抽取充数
        int returnNum = 3;
        for(int i = 0; i < returnNum; i++){
            int maxMatcher = 0;
            double maxWeight = 0;
            for(Entry<Integer, Double> e : matcherMap.entrySet()){
                if(e.getValue() > maxWeight){
                    maxMatcher = e.getKey();
                    maxWeight = e.getValue();
                }
            }
            if(maxMatcher == 0 || maxWeight <= 0){
                int amount = returnNum - i;
                int[] randomUsers = ubiService.getRandomUser(amount);
                for(int j = 0; j < amount; j++)
                    matcherQueue.offer(randomUsers[j]);
                break;
            }else{
                matcherQueue.offer(maxMatcher);
                matcherMap.put(maxMatcher, -1.0);
            }
        }
        //对于入队列的用户，记录在匹配记录中
        for(int inQueueUser : matcherQueue){
            MatchingRecordKey id = new MatchingRecordKey();
            MatchingRecord record = new MatchingRecord();
            id.setUserId(userId);
            id.setMatcherId(inQueueUser);
            record.setId(id);
            record.setDate(new Date(System.currentTimeMillis()));
            recordService.saveRecord(record);
        }
        //返回队列
        return matcherQueue == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(matcherQueue);
    }

    //方便测试使用的清库方法，请勿使用户接触此请求
    @GetMapping(value = "match/clear")
    @CrossOrigin
    public ResponseEntity<Boolean> clearRecords(){
        recordService.clearAllRecords();
        return ResponseEntity.ok().body(true);
    }

    //解析偏好掩码（Friends 1 Love男性 2 Love女性 4 Foreign Contacts 8 Chat 16）
    private static boolean[] analysePrefer(int prefer){
        boolean[] preferArray = new boolean[5];
        int pos = 0;
        while(prefer > 0){
            if(prefer % 2 == 1)
                preferArray[pos] = true;
            prefer /= 2;
            pos++;
        }
        return preferArray;
    }
}
