package com.kazge.example.api;

import com.kazge.example.BaseWithServerTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class T1Tests extends BaseWithServerTests {

    private final static String URL_PREFIX = "/t1/users";

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getAllTest() {
        ResponseEntity<String> response = get(URL_PREFIX,null,"limit",100);

        logger.info("response>>{}",response.getBody());

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
