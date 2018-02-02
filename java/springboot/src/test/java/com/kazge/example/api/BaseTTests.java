package com.kazge.example.api;

import com.kazge.example.BaseWithServerTests;
import com.kazge.example.entity.User;
import com.kazge.example.utils.JacksonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public abstract class BaseTTests extends BaseWithServerTests {
    public abstract String getBaseUrl();

    abstract Map<String, String> buildHeaders();

    public User doCreate(String url) {
        if (null == url) {
            url = getBaseUrl();
        }
        User u = new User();
        u.setName(UUID.randomUUID().toString());
        u.setPassword("123456");
        ResponseEntity<String> response = post(url, buildHeaders(), u);

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());

        User respUSer = JacksonUtils.parse(response.getBody(), User.class);

        u.setId(respUSer.getId());

        return u;
    }

    @Test
    public void createTest() {
        doCreate(getBaseUrl());
    }

    @Test
    public void getAllTest() {
        ResponseEntity<String> response = get(getBaseUrl(), buildHeaders(), "limit", 100);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    public User get(String userId) {
        ResponseEntity<String> response = get(getBaseUrl() + "/" + userId, null);

        return JacksonUtils.parse(response.getBody(), User.class);
    }

    @Test
    public void getTest() {
        try {
            User u = doCreate(getBaseUrl());
            get(u.getId());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateTest() {
        User u = doCreate(getBaseUrl());
        u.setName(u.getName() + "update");
        ResponseEntity<String> response = put(getBaseUrl() + "/" + u.getId(), buildHeaders(), u);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void deleteTest() {
        User u = doCreate(getBaseUrl());
        u.setPassword(u.getName() + "update");
        ResponseEntity<String> response = delete(getBaseUrl() + "/" + u.getId(), buildHeaders());

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
