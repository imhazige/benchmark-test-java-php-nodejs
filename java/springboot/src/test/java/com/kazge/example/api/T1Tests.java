package com.kazge.example.api;

import java.util.Map;


public class T1Tests extends BaseTTests {
    @Override
    public String getBaseUrl() {
        return "/t1/users";
    }

    @Override
    Map<String, String> buildHeaders() {
        return null;
    }
}
