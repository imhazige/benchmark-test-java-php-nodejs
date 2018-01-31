package com.kazge.example.api;

import com.kazge.example.BaseWithServerTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class T1Tests extends BaseWithServerTests {

    private final static String URL_PREFIX = "/t1/users";

    @Test
    public void getAllTest() {
        ResponseEntity<String> response = get(URL_PREFIX,null,"limit",100);

        logger.info("response>>{}",response.getBody());

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
