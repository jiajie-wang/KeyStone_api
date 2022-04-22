package com.keystone.keystone.controller;

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

@RestController
public class ForgetPasswordPageController {
    @Autowired
    private UserAccountInfoServ uaiService;

    //调取用户验证问题
    @GetMapping(value="user/forgetPassword/{email}")
    @CrossOrigin
    public ResponseEntity<String> getUserVerifyQues(@PathVariable("email") String email){
        String VerifyQues = uaiService.getUserVerifyQues(email);
        return VerifyQues.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(VerifyQues);
    }

    //验证安全问题答案
    @PostMapping(value = "user/forgetPassword/verifyAns")
    @CrossOrigin
    public ResponseEntity<SignPack> isCorrectVerifyAns(@RequestBody VerifyQuesResponse response){
        String expectedAns = uaiService.getUserVerifyAns(response.getEmail());
        String actualAns = response.getVerifyAns();
        boolean answer = expectedAns.equals(actualAns);
        int userId = answer ? uaiService.getUserIdByEmail(response.getEmail()) : 0;
        return expectedAns == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) : ResponseEntity.ok().body(new SignPack(answer, userId));
    }

}

//isCorrectVerifyAns 答复结构：{email:???, verifyAns:???}
class VerifyQuesResponse{
    private String email;
    private String verifyAns;

    public VerifyQuesResponse(String email, String verifyAns){
        this.email = email;
        this.verifyAns = verifyAns;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyAns() {
        return verifyAns;
    }

    public void setVerifyAns(String verifyAns) {
        this.verifyAns = verifyAns;
    }
    
}