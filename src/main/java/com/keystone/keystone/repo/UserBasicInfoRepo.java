package com.keystone.keystone.repo;

import com.keystone.keystone.model.UserBasicInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//用户基本信息（个人资料页）

@Repository
public interface UserBasicInfoRepo extends JpaRepository<UserBasicInfo, Integer>{
    
}
