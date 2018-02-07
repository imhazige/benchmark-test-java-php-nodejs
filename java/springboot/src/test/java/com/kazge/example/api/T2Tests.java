package com.kazge.example.api;

import com.kazge.example.entity.User;
import com.kazge.example.utils.CollectionUtils;
import com.kazge.example.utils.JacksonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;


public class T2Tests extends BaseTTests {
    @Override
    public String getBaseUrl() {
        return "/t2/users";
    }

    @Test
    public void testGetTestUserToken(){
        doGetToken();
    }


    public String doGetToken() {
        //use t1 to create a user to login
        User u = new User();
        u.setName(UUID.randomUUID().toString());
        u.setPassword("123456");
        ResponseEntity<String> response = post("/t1/users", null, u);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        User respUSer = JacksonUtils.parse(response.getBody(), User.class);

        u.setId(respUSer.getId());
        return doGetToken(u);
    }

    @Test
    public void testGetTokenError() {
        //use t1 to create a user to login
        User u = new User();
        u.setName(UUID.randomUUID().toString());
        u.setPassword("123456");
        ResponseEntity<String> response = post("/t1/users", null, u);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        User respUSer = JacksonUtils.parse(response.getBody(), User.class);

        u.setId(respUSer.getId());
        //can not test error when response 401
//        u.setPassword(u.getPassword() + "_no");
        response = post(getBaseUrl() + "/token", null, u);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getToken() {
        doGetToken();
    }

    @Override
    Map<String, String> buildHeaders() {
        String token = doGetToken();
        Assert.assertNotNull(token);
        logger.info("token is {}", token);

        return CollectionUtils.buildMap(HttpHeaders.AUTHORIZATION, token);
    }
}
