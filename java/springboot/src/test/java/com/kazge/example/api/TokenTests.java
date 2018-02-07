package com.kazge.example.api;

import com.kazge.example.entity.User;
import com.kazge.example.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public class TokenTests extends BaseTTests{
    @Autowired
    UserService userService;

    @Test
    public void testVerifyToken(){
        User u = doCreate("/t1/users");

        String token = doGetToken(u);
        String userId = userService.verifyToken(token);
        Assert.assertEquals(u.getId(),userId);
    }

    @Override
    public String getBaseUrl() {
        return "/t2/token";
    }

    @Override
    Map<String, String> buildHeaders() {
        return null;
    }
}
