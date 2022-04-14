package com.keystone.keystone.controller;

import java.util.List;

import com.keystone.keystone.model.Path;
import com.keystone.keystone.service.PathService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//本文档与所有与“Path”相关的文档全部为练习时作为参考的例子，与实际项目无关，请勿改动

@RestController
public class SimpleExample {

    // @GetMapping(value = "simple")
    // public String getString(@RequestParam(name = "id") int userId){
    //     return "You " + userId +" got me";
    // }

    // @GetMapping(value = "path/{id}")
    // public String getString2(@PathVariable("id") int pathId){
    //     return "Path: " + pathId;
    // }

    @Autowired
    private PathService pathService;

    @GetMapping(value = "path")
    public ResponseEntity<List<Path>> getPaths(){
        List<Path> paths = pathService.getValidPath();
        return paths == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(paths);
    }

    @GetMapping(value = "path/{pathId}")
    public ResponseEntity<Path> getPath(@PathVariable("pathId") int pathId){
        Path path = pathService.getPath(pathId);
        return path == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(path);
    }

    @PostMapping(value = "path")
    public ResponseEntity<Integer> createPath(@RequestBody Path path){
        Integer idResult = pathService.savePath(path);
        return idResult == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(idResult);
    }

}
