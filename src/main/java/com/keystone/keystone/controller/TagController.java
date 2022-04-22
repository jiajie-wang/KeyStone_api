package com.keystone.keystone.controller;

import com.keystone.keystone.model.Tag;
import com.keystone.keystone.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//此文档的所有方法理论仅供管理员操作，用户不得接触

@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    //管理员手动插入一条Tag信息
    @PostMapping(value = "tagControl")
    @CrossOrigin
    public ResponseEntity<Integer> createTag(@RequestBody Tag tag) {
        Integer idResult = tagService.saveTag(tag);
        return idResult == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(idResult);
    }

    //管理员一键插入所有在录的固有Tag
    @GetMapping(value = "tagControl/Inherent")
    @CrossOrigin
    public ResponseEntity<Boolean> InitializeInherentTags(){
        for(int i = 1; i <= TagConfig.tagIntInfo.length; i++){
            Tag tag = new Tag();
            tag.setTagId(i);
            tag.setMutexNum(TagConfig.tagIntInfo[i - 1][1]);
            tag.setUniqueTag(TagConfig.tagIntInfo[i - 1][2]);
            tag.setTagName(TagConfig.tagStringInfo[i - 1]);
            tagService.saveTag(tag);
        }
        return ResponseEntity.ok().body(true);
    }
}
