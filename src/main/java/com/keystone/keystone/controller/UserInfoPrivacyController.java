package com.keystone.keystone.controller;

import com.keystone.keystone.model.UserInfoPrivacy;
import com.keystone.keystone.service.UserInfoPrivacyServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//用户隐私部分
@RestController
public class UserInfoPrivacyController {
    @Autowired
    private UserInfoPrivacyServ uipService;

    //Get UserInfoPrivacy level
    @GetMapping(value = "user/uip/{userId}")
    @CrossOrigin
    public ResponseEntity<UserInfoPrivacy> getUserInfoPrivacy(@PathVariable("userId") int userId){
        UserInfoPrivacy response = uipService.getUserInfoPrivacy(userId);
        return (Integer)userId == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(response);
    }

    //set UserInfoPrivacy level
    @PostMapping(value = "user/uip/modify")
    @CrossOrigin
    public ResponseEntity<Integer> saveUserInfoPrivacy(@RequestBody UserInfoPrivacy responsUserInfoPrivacy){
        Integer idResultUip = uipService.saveUserInfoPrivacy(responsUserInfoPrivacy);
        return ResponseEntity.ok().body(idResultUip);
    }


}
