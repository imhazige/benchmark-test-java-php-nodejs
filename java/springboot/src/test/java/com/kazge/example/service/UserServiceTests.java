package com.kazge.example.service;


import com.kazge.example.BaseNoServerTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTests extends BaseNoServerTests {

    @Autowired
    UserService userService;

    @Test
    public void testEncodePassword(){
        String rawPassword = "123456";
        String[] encodeds = userService.encodePassword(rawPassword);
        Assert.assertEquals(32,encodeds[0].length());
        Assert.assertEquals(32,encodeds[1].length());

        boolean check = userService.verifyPassword(rawPassword,encodeds[0],encodeds[1]);
        Assert.assertEquals(true,check);
    }
}
