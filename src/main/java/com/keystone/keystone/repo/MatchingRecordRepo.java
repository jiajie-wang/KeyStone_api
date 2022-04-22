package com.keystone.keystone.repo;

import java.util.List;

import com.keystone.keystone.model.MatchingRecord;
import com.keystone.keystone.model.MatchingRecordKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//匹配纪录

@Repository
public interface MatchingRecordRepo extends JpaRepository<MatchingRecord, MatchingRecordKey>{
    public List<MatchingRecord> findAllByUserId(int userId);
}
