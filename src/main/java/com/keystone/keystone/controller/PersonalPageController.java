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

    //调取用户所有资料
    @GetMapping(value = "user/modify/{userId}")
    public ResponseEntity<PersonalPageResponse> getUserInfo(@PathVariable("userId") int userId){
        PersonalPageResponse response = new PersonalPageResponse(uaiService.getUserAccountInfo(userId), ubiService.getUserBasicInfo(userId), ubiService.getTagIdSet(userId));
        return response.isNotValid() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(response);
    }

    //仅调取个人资料
    @GetMapping(value = "user/{userId}")
    public ResponseEntity<UserBasicInfo> getUserBasicInfo(@PathVariable("userId") int userId){
        UserBasicInfo ubi = ubiService.getUserBasicInfo(userId);
        return ubi == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(ubi);
    }

    //保存用户所有资料
    @PostMapping(value = "user/modify")
    public ResponseEntity<List<Integer>> saveUserInfo(@RequestBody PersonalPageResponse response){
        Set<Tag> tagSet = new HashSet<Tag>();
        for(int tagId: response.getTagIdSet()){
            tagSet.add(tagService.getTag(tagId));
        }
        Integer idResultUai = uaiService.saveUserAccountInfo(response.getUai());
        //先保存ubi，然后用返回的整数值（userId）直接当作参数再保存Tag
        Integer idResultUbi = ubiService.saveUserTagSet(ubiService.saveUserBasicInfo(response.getUbi()), tagSet);
        return ResponseEntity.ok().body(Arrays.asList(idResultUai, idResultUbi));
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