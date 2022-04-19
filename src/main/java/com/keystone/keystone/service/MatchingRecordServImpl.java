package com.keystone.keystone.service;

import java.util.Map;
import java.util.Map.Entry;

import com.keystone.keystone.model.MatchingRecord;
import com.keystone.keystone.model.MatchingRecordKey;
import com.keystone.keystone.repo.MatchingRecordRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchingRecordServImpl implements MatchingRecordServ{
    @Autowired MatchingRecordRepo recordRepo;

    @Override
    public void rearrangeMatchers(int userId, Map<Integer, Double> matcherMap) {
        MatchingRecordKey id = new MatchingRecordKey();
        id.setUserId(userId);
        for(Entry<Integer, Double> entry : matcherMap.entrySet()){
            id.setMatcherId(entry.getKey());
            MatchingRecord record = recordRepo.findById(id).get();
            if(record == null)
                continue;
            long currentTime = System.currentTimeMillis();
            //15天=1296000000毫秒
            if(currentTime - record.getDate().getTime() < 1296000000l){
                if(entry.getValue() > 0)
                    matcherMap.put(entry.getKey(), 0.1 * entry.getValue());
                else
                    matcherMap.put(entry.getKey(), -1.0);
            }else{
                recordRepo.deleteById(id);
            }
        }
    }

    @Override
    public MatchingRecordKey saveRecord(MatchingRecord record) {
        return recordRepo.save(record).getId();
    }

    @Override
    public void clearAllRecords() {
        recordRepo.deleteAll();
    }
    
}
