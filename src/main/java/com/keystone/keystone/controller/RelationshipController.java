package com.keystone.keystone.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.keystone.keystone.model.Relationship;
import com.keystone.keystone.model.RelationshipKey;
import com.keystone.keystone.service.RelationshipServ;
import com.keystone.keystone.service.UserAccountInfoServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//好友关系部分

@RestController
public class RelationshipController {
    @Autowired
    private UserAccountInfoServ uaiService;
    @Autowired
    private RelationshipServ relaService;

    //获取二人关系
    @GetMapping(value = "rela/{userId}/{friendId}")
    @CrossOrigin
    public ResponseEntity<Integer> getRelationShip(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId){
        if(uaiService.getUserAccountInfo(userId) == null || uaiService.getUserAccountInfo(friendId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok().body(relaService.getRelaLevel(userId, friendId));
    }
    //获取某人好友列表（双向）
    @GetMapping(value = "rela/friends1/{userId}")
    @CrossOrigin
    public ResponseEntity<List<Integer>> getFriendList(@PathVariable("userId") int userId){
        if(uaiService.getUserAccountInfo(userId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        List<Integer> result = new ArrayList<Integer>();
        for(Entry<Integer, Boolean> entry: relaService.getFriendList(userId).entrySet()){
            if(!entry.getValue())
                result.add(entry.getKey());
        }
        return ResponseEntity.ok().body(result);
    }
    //获取某人密友列表
    @GetMapping(value = "rela/friends2/{userId}")
    @CrossOrigin
    public ResponseEntity<List<Integer>> getCloseFriendList(@PathVariable("userId") int userId){
        if(uaiService.getUserAccountInfo(userId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        List<Integer> result = new ArrayList<Integer>();
        for(Entry<Integer, Boolean> entry: relaService.getFriendList(userId).entrySet()){
            if(entry.getValue())
                result.add(entry.getKey());
        }
        return ResponseEntity.ok().body(result);
    }
    //获取某人待处理的其他人请求好友信息
    @GetMapping(value = "rela/request1/{friendId}")
    @CrossOrigin
    public ResponseEntity<List<Integer>> getRequestList(@PathVariable("friendId") int friendId){
        if(uaiService.getUserAccountInfo(friendId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok().body(relaService.getRequestList(friendId));
    }
    //获取某人待处理的好友请求密友信息
    @GetMapping(value = "rela/request2/{friendId}")
    @CrossOrigin
    public ResponseEntity<List<Integer>> getCloseRequestList(@PathVariable("friendId") int friendId){
        if(uaiService.getUserAccountInfo(friendId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok().body(relaService.getCloseRequestList(friendId));
    }
    //添加单向好友关系，用于发出请求与同意请求，post一个RelationshipKey（由userId与friendId组成）
    //若两人已经为好友或密友，返回403 forbidden，理论上不应当出现此情况
    @PostMapping(value = "rela/add1")
    @CrossOrigin
    public ResponseEntity<RelationshipKey> addFriend(@RequestBody RelationshipKey id){
        if(uaiService.getUserAccountInfo(id.getUserId()) == null || uaiService.getUserAccountInfo(id.getFriendId()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if(relaService.getRelaLevel(id.getUserId(), id.getFriendId()) == 0){
            Relationship relationship = new Relationship();
            relationship.setId(id);
            relationship.setLevel(1);
            return ResponseEntity.ok().body(relaService.saveRelationship(relationship));
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    //升级单向密友关系，用于发出请求与同意请求
    //若两人已经为密友或未成为好友，返回403 forbidden，理论上不应当出现此情况
    @PostMapping(value = "rela/add2")
    @CrossOrigin
    public ResponseEntity<RelationshipKey> addCloseFriend(@RequestBody RelationshipKey id){
        if(uaiService.getUserAccountInfo(id.getUserId()) == null || uaiService.getUserAccountInfo(id.getFriendId()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if(relaService.getRelaLevel(id.getUserId(), id.getFriendId()) == 1){
            Relationship relationship = new Relationship();
            relationship.setId(id);
            relationship.setLevel(2);
            return ResponseEntity.ok().body(relaService.saveRelationship(relationship));
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    //删除单向好友关系，用于拒绝请求
    //若两人已经为好友或密友，返回403 forbidden，理论上不应当出现此情况
    //如果A拒绝了B，那么RelationshipKey中user是A，friend是B
    @PostMapping(value = "rela/refuse1")
    @CrossOrigin
    public ResponseEntity<RelationshipKey> refuseFriend(@RequestBody RelationshipKey id){
        if(uaiService.getUserAccountInfo(id.getUserId()) == null || uaiService.getUserAccountInfo(id.getFriendId()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if(relaService.getRelaLevel(id.getUserId(), id.getFriendId()) == 0){
            RelationshipKey idResult = relaService.deleteRelationship(id.getFriendId(), id.getUserId());
            return idResult == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(idResult);
        }
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    //降级单向密友关系，用于拒绝请求
    //若两人已经为密友或未成为好友，返回403 forbidden，理论上不应当出现此情况
    //如果A拒绝了B，那么RelationshipKey中user是A，friend是B
    @PostMapping(value = "rela/refuse2")
    @CrossOrigin
    public ResponseEntity<RelationshipKey> refuseCloseFriend(@RequestBody RelationshipKey id){
        if(uaiService.getUserAccountInfo(id.getUserId()) == null || uaiService.getUserAccountInfo(id.getFriendId()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if(relaService.getRelaLevel(id.getUserId(), id.getFriendId()) == 1){
            RelationshipKey idRev = new RelationshipKey();
            idRev.setUserId(id.getFriendId());
            idRev.setFriendId(id.getUserId());
            Relationship relationship = new Relationship();
            relationship.setId(idRev);
            relationship.setLevel(1);
            return ResponseEntity.ok().body(relaService.saveRelationship(relationship));
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    //删除双向好友关系，用于删除好友
    @PostMapping(value = "rela/delete1")
    @CrossOrigin
    public ResponseEntity<RelationshipKey> DeleteFriend(@RequestBody RelationshipKey id){
        if(uaiService.getUserAccountInfo(id.getUserId()) == null || uaiService.getUserAccountInfo(id.getFriendId()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if(relaService.getRelaLevel(id.getUserId(), id.getFriendId()) == 1){
            relaService.deleteRelationship(id.getUserId(), id.getFriendId());
            RelationshipKey idResult = relaService.deleteRelationship(id.getFriendId(), id.getUserId());
            return idResult == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(idResult);
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    //降级双向密友关系，用于降级密友
    @PostMapping(value = "rela/delete2")
    @CrossOrigin
    public ResponseEntity<RelationshipKey> DeleteCloseFriend(@RequestBody RelationshipKey id){
        if(uaiService.getUserAccountInfo(id.getUserId()) == null || uaiService.getUserAccountInfo(id.getFriendId()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if(relaService.getRelaLevel(id.getUserId(), id.getFriendId()) == 2){
            RelationshipKey idRev = new RelationshipKey();
            idRev.setUserId(id.getFriendId());
            idRev.setFriendId(id.getUserId());
            Relationship relationship = new Relationship();
            relationship.setId(id);
            relationship.setLevel(1);
            Relationship relationshipRev = new Relationship();
            relationshipRev.setId(idRev);
            relationshipRev.setLevel(1);
            relaService.saveRelationship(relationship);
            return ResponseEntity.ok().body(relaService.saveRelationship(relationshipRev));
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
