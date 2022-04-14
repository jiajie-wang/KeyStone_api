package com.keystone.keystone.repo;

import java.util.List;

import com.keystone.keystone.model.UserAccountInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//用户账号信息

@Repository
public interface UserAccountInfoRepo extends JpaRepository<UserAccountInfo, Integer>{
    public List<UserAccountInfo> findAllByEmail(String email);
}
