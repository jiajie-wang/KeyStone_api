package com.keystone.keystone.repo;

import com.keystone.keystone.model.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//标签

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer>{
    
}
