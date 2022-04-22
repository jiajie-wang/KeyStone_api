package com.keystone.keystone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import com.keystone.keystone.model.Relationship;
import com.keystone.keystone.model.RelationshipKey;
import com.keystone.keystone.repo.RelationshipRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RelationshipServImpl implements RelationshipServ{
    @Autowired
    RelationshipRepo relaRepo;

    @Override
    public int getLevel(int userId, int friendId) {
        RelationshipKey id = new RelationshipKey();
        id.setUserId(userId);
        id.setFriendId(friendId);
        Optional<Relationship> rela = relaRepo.findById(id);
        return rela.isPresent() ? rela.get().getLevel() : 0;
    }

    @Override
    public int getRelaLevel(int userId, int friendId) {
        RelationshipKey idPos = new RelationshipKey();
        idPos.setUserId(userId);
        idPos.setFriendId(friendId);
        Optional<Relationship> relaPos = relaRepo.findById(idPos);
        RelationshipKey idNeg = new RelationshipKey();
        idNeg.setUserId(friendId);
        idNeg.setFriendId(userId);
        Optional<Relationship> relaNeg = relaRepo.findById(idNeg);
        if(!relaPos.isPresent() || !relaNeg.isPresent())
            return 0;
        else{
            return Math.min(relaPos.get().getLevel(), relaNeg.get().getLevel());
        }
    }

    @Override
    public RelationshipKey deleteRelationship(int userId, int friendId){
        try{
            RelationshipKey id = new RelationshipKey();
            id.setUserId(userId);
            id.setFriendId(friendId);
            relaRepo.deleteById(id);
            return id;
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public RelationshipKey saveRelationship(Relationship relationship) {
        return relaRepo.save(relationship).getId();
    }

    @Override
    public Map<Integer, Boolean> getFriendList(int userId) {
        List<Relationship> posList = relaRepo.findAllByUserId(userId);
        Map<Integer, Boolean> result = new HashMap<Integer, Boolean>();
        for(Relationship rela: posList){
            int friendId = rela.getFriendId();
            RelationshipKey idRev = new RelationshipKey();
            idRev.setUserId(friendId);
            idRev.setFriendId(userId);
            Optional<Relationship> relaRevOp = relaRepo.findById(idRev);
            if(relaRevOp.isPresent())
                result.put(friendId, Math.min(rela.getLevel(), relaRevOp.get().getLevel()) == 2);
        }
        return result;
    }

    @Override
    public List<Integer> getRequestList(int friendId) {
        List<Relationship> negList = relaRepo.findAllByFriendId(friendId);
        List<Integer> result = new ArrayList<Integer>();
        for(Relationship rela: negList){
            int userId = rela.getUserId();
            RelationshipKey idRev = new RelationshipKey();
            idRev.setUserId(friendId);
            idRev.setFriendId(userId);
            Optional<Relationship> relaRevOp = relaRepo.findById(idRev);
            if(!relaRevOp.isPresent())
                result.add(userId);
        }
        return result;
    }

    @Override
    public List<Integer> getCloseRequestList(int friendId) {
        List<Relationship> negList = relaRepo.findAllByFriendId(friendId);
        List<Integer> result = new ArrayList<Integer>();
        for(Relationship rela: negList){
            int userId = rela.getUserId();
            RelationshipKey idRev = new RelationshipKey();
            idRev.setUserId(friendId);
            idRev.setFriendId(userId);
            Optional<Relationship> relaRevOp = relaRepo.findById(idRev);
            if(relaRevOp.isPresent() && rela.getLevel() == 2 && relaRevOp.get().getLevel() == 1)
                result.add(userId);
        }
        return result;
    }

    @Override
    public void rearrangeMatchers(int userId, Map<Integer, Double> matcherMap) {
        for(Entry<Integer, Double> e : matcherMap.entrySet()){
            if(userId == e.getKey()){
                matcherMap.put(userId, -1.0);
                continue;
            }
            RelationshipKey id = new RelationshipKey();
            id.setUserId(userId);
            id.setFriendId(e.getKey());
            Optional<Relationship> relaOp = relaRepo.findById(id);
            if(relaOp.isEmpty())
                continue;
            Relationship rela = relaOp.get();
            if(rela.getLevel() > 0)
                matcherMap.put(e.getKey(), -1.0);
        }
    }

}
