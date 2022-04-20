package com.keystone.keystone.controller;

import com.keystone.keystone.service.UserAccountInfoServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//登陆界面部分

@RestController
public class SignInPageController {
    @Autowired
    private UserAccountInfoServ uaiService;

    //登录验证密码，目前支持用邮箱登录，返回值为是否成功验证和用户Id（验证失败的话用户Id为0）
    @PostMapping(value = "sign")
    @CrossOrigin
    public ResponseEntity<SignPack> isCorrectPassword(@RequestBody SignInPageResponse response){
        String actualPassword = uaiService.getPasswordByEmail(response.getEmail());
        boolean answer = response.getPassword().equals(actualPassword);
        int userId = answer ? uaiService.getUserIdByEmail(response.getEmail()) : 0;
        return actualPassword == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(new SignPack(answer, userId));
    }


}

//isCorrectPassword 答复结构：{email: ????, password: ????}
class SignInPageResponse{
    private String email;
    private String password;
    
    public SignInPageResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}

//isCorrectPassword 返回结构：{answer: 是否通过密码验证, userId: 用户的Id}
class SignPack{
    private boolean answer;
    private int userId;

    public SignPack(boolean answer, int userId) {
        this.answer = answer;
        this.userId = userId;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}