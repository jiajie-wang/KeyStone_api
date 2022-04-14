package com.keystone.keystone.controller;

import com.keystone.keystone.service.UserAccountInfoServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//登陆界面部分

@RestController
public class SignInPageController {
    @Autowired
    private UserAccountInfoServ uaiService;

    //登录验证密码，目前支持用邮箱登录
    @PostMapping(value = "sign")
    public ResponseEntity<Boolean> isCorrectPassword(@RequestBody SignInPageResponse response){
        String actualPassword = uaiService.getPasswordByEmail(response.getEmail());
        return actualPassword == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(response.getPassword().equals(actualPassword));
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