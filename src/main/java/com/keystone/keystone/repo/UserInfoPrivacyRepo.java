package com.keystone.keystone.repo;

import com.keystone.keystone.model.UserInfoPrivacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoPrivacyRepo extends JpaRepository<UserInfoPrivacy, Integer> {
    
}
