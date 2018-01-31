package com.kazge.example;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseWithServerTests extends BaseTests{

    @Autowired
    TestRestTemplate testRestTemplate;

    @Override
    public TestRestTemplate getRestTemplate() {
        return testRestTemplate;
    }
}
