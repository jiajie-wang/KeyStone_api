package com.keystone.keystone.controller;

import java.util.Arrays;
import java.util.List;

import com.keystone.keystone.model.UserAccountInfo;
import com.keystone.keystone.model.UserBasicInfo;
import com.keystone.keystone.service.UserAccountInfoServ;
import com.keystone.keystone.service.UserBasicInfoServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalPageController {
    @Autowired
    private UserAccountInfoServ uaiService;
    @Autowired
    private UserBasicInfoServ ubiService;

    @GetMapping(value = "user/{userId}")
    public ResponseEntity<PersonalPageResponse> getUserInfo(@PathVariable("userId") int userId){
        PersonalPageResponse response = new PersonalPageResponse(uaiService.getUserAccountInfo(userId), ubiService.getUserBasicInfo(userId));
        return response.isNotValid() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "user/modify")
    public ResponseEntity<List<Integer>> saveUserInfo(@RequestBody PersonalPageResponse response){
        Integer idResultUai = uaiService.saveUserAccountInfo(response.getUai());
        Integer idResultUbi = ubiService.saveUserBasicInfo(response.getUbi());
        return ResponseEntity.ok().body(Arrays.asList(idResultUai, idResultUbi));
    }

}

class PersonalPageResponse {
    private UserAccountInfo uai;
    private UserBasicInfo ubi;

    PersonalPageResponse(UserAccountInfo uai, UserBasicInfo ubi){
        this.uai = uai;
        this.ubi = ubi;
    }

    public UserAccountInfo getUai() {
        return uai;
    }

    public void setUai(UserAccountInfo uai) {
        this.uai = uai;
    }

    public UserBasicInfo getUbi() {
        return ubi;
    }

    public void setUbi(UserBasicInfo ubi) {
        this.ubi = ubi;
    }

    public boolean isNotValid(){
        return uai == null && ubi == null;
    }
    
}