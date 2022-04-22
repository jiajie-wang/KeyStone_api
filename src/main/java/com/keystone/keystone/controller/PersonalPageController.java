package com.keystone.keystone.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.keystone.keystone.model.Tag;
import com.keystone.keystone.model.UserAccountInfo;
import com.keystone.keystone.model.UserBasicInfo;
import com.keystone.keystone.service.TagService;
import com.keystone.keystone.service.UserAccountInfoServ;
import com.keystone.keystone.service.UserBasicInfoServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//个人资料界面，目前所有URL都是瞎写的，URL需要更改请随时互相通知

@RestController
public class PersonalPageController {
    @Autowired
    private UserAccountInfoServ uaiService;
    @Autowired
    private UserBasicInfoServ ubiService;
    @Autowired
    private TagService tagService;

    //调取用户所有资料（不建议使用的请求）
    @GetMapping(value = "user/all/{userId}")
    @CrossOrigin
    public ResponseEntity<PersonalPageResponse> getUserInfo(@PathVariable("userId") int userId){
        if(uaiService.getUserAccountInfo(userId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        PersonalPageResponse response = new PersonalPageResponse(uaiService.getUserAccountInfo(userId), ubiService.getUserBasicInfo(userId), ubiService.getTagIdSet(userId));
        return response.isNotValid() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(response);
    }

    //仅调取账户信息
    @GetMapping(value = "user/account/{userId}")
    @CrossOrigin
    public ResponseEntity<UserAccountInfo> getUserAccountInfo(@PathVariable("userId") int userId){
        UserAccountInfo uai = uaiService.getUserAccountInfo(userId);
        return uai == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(uai);
    }

    //仅调取个人资料
    @GetMapping(value = "user/basic/{userId}")
    @CrossOrigin
    public ResponseEntity<UserBasicResponse> getUserBasicInfo(@PathVariable("userId") int userId){
        if(uaiService.getUserAccountInfo(userId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        UserBasicInfo ubi = ubiService.getUserBasicInfo(userId);
        UserBasicResponse ubr = new UserBasicResponse(ubi, ubiService.getTagIdSet(userId));
        return ubi == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(ubr);
    }

    //保存用户所有资料（不建议使用的请求）
    @PostMapping(value = "user/modify")
    @CrossOrigin
    public ResponseEntity<List<Integer>> saveUserInfo(@RequestBody PersonalPageResponse response){
        if(response.getUai().getUserId() != response.getUbi().getUserId())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Set<Tag> tagSet = new HashSet<Tag>();
        for(int tagId: response.getTagIdSet()){
            tagSet.add(tagService.getTag(tagId));
        }
        Integer idResultUai = uaiService.saveUserAccountInfo(response.getUai());
        //先保存ubi，然后用返回的整数值（userId）直接当作参数再保存Tag
        Integer idResultUbi = ubiService.saveUserTagSet(ubiService.saveUserBasicInfo(response.getUbi()), tagSet);
        return ResponseEntity.ok().body(Arrays.asList(idResultUai, idResultUbi));
    }

    //保存用户账户信息
    @PostMapping(value = "user/modify/account")
    @CrossOrigin
    public ResponseEntity<Integer> saveUserAccountInfo(@RequestBody UserAccountInfo uai){
        Integer idResult = uaiService.saveUserAccountInfo(uai);
        return ResponseEntity.ok().body(idResult);
    }

    //保存用户个人资料
    @PostMapping(value = "user/modify/basic")
    @CrossOrigin
    public ResponseEntity<Integer> saveUserBasicInfo(@RequestBody UserBasicResponse ubr){
        if(uaiService.getUserAccountInfo(ubr.getUbi().getUserId()) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Set<Tag> tagSet = new HashSet<Tag>();
        for(int tagId: ubr.getTagIdSet()){
            tagSet.add(tagService.getTag(tagId));
        }
        //先保存ubi，然后用返回的整数值（userId）直接当作参数再保存Tag
        Integer idResult = ubiService.saveUserTagSet(ubiService.saveUserBasicInfo(ubr.getUbi()), tagSet);
        return ResponseEntity.ok().body(idResult);
    }

    //在登入状态下验证密码
    @PostMapping(value = "user/verify")
    @CrossOrigin
    public ResponseEntity<Boolean> isCorrectPassword(@RequestBody PasswordVerifyResponse response){
        UserAccountInfo uai = uaiService.getUserAccountInfo(response.getUserId());
        return uai == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(response.getPassword().equals(uai.getPassword()));
    }

    //已经经过验证的情况下，更改用户密码
    @PostMapping(value = "user/password")
    @CrossOrigin
    public ResponseEntity<Integer> savePassword(@RequestBody PasswordVerifyResponse response){
        UserAccountInfo uai = uaiService.getUserAccountInfo(response.getUserId());
        uai.setPassword(response.getPassword());
        Integer idResult = uaiService.saveUserAccountInfo(uai);
        return ResponseEntity.ok().body(idResult);
    }
}

//getUserInfo 与 saveUserInfo 答复结构：{uai: {uai的内容}, ubi: {ubi的内容}, tagIdSet: [第一个tag的Id, 第二个tag的Id, ……]}
class PersonalPageResponse {
    private UserAccountInfo uai;
    private UserBasicInfo ubi;
    private Set<Integer> tagIdSet;

    PersonalPageResponse(UserAccountInfo uai, UserBasicInfo ubi, Set<Integer> tagIdSet){
        this.uai = uai;
        this.ubi = ubi;
        this.tagIdSet = tagIdSet;
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

    public Set<Integer> getTagIdSet() {
        return tagIdSet;
    }

    public void setTagIdSet(Set<Integer> tagIdSet) {
        this.tagIdSet = tagIdSet;
    }
    
}

//getUserBasicInfo和saveUserBasicInfo的答复结构：{ubi: {ubi的内容}, tagIdSet: [第一个tag的Id, 第二个tag的Id, ……]}
class UserBasicResponse {
    private UserBasicInfo ubi;
    private Set<Integer> tagIdSet;

    UserBasicResponse(UserBasicInfo ubi, Set<Integer> tagIdSet){
        this.ubi = ubi;
        this.tagIdSet = tagIdSet;
    }

    public UserBasicInfo getUbi() {
        return ubi;
    }

    public void setUbi(UserBasicInfo ubi) {
        this.ubi = ubi;
    }

    public Set<Integer> getTagIdSet() {
        return tagIdSet;
    }

    public void setTagIdSet(Set<Integer> tagIdSet) {
        this.tagIdSet = tagIdSet;
    }
    
}

class PasswordVerifyResponse{
    
    private int userId;
    private String password;

    public PasswordVerifyResponse(int userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}