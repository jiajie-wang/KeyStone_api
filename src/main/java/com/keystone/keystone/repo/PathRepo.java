package com.keystone.keystone.repo;

import java.util.List;

import com.keystone.keystone.model.Path;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//本文档与所有与“Path”相关的文档全部为练习时作为参考的例子，与实际项目无关，请勿改动

@Repository
public interface PathRepo extends JpaRepository<Path, Integer>{
    public List<Path> findAllByIsValid(boolean isValid);
}
