package com.keystone.keystone.repo;

import java.util.List;

import com.keystone.keystone.model.Relationship;
import com.keystone.keystone.model.RelationshipKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//关系

@Repository
public interface RelationshipRepo extends JpaRepository<Relationship, RelationshipKey>{
    public List<Relationship> findAllByUserId(int userId);
    public List<Relationship> findAllByFriendId(int friendId);
}
