package com.kazge.example;

import com.kazge.example.utils.CollectionUtils;
import com.kazge.example.utils.JacksonUtils;
import com.kazge.example.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BaseTests {
    public static final Logger logger = LoggerFactory.getLogger(BaseTests.class);

    public TestRestTemplate getRestTemplate() {
        return new TestRestTemplate();
    }

    public ResponseEntity<String> get(String url, Map<String, String> headersArgs, Object... params) {
        HttpHeaders headers = new HttpHeaders();
        if (null != headersArgs) {
            for (Map.Entry<String, String> en :
                    headersArgs.entrySet()) {
                headers.add(en.getKey(), en.getValue());
            }
        }
        url = UrlUtils.buildQueryString(url, CollectionUtils.buildMap(params));
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod
                .GET, new HttpEntity<>(headers), String.class);

        logger.info("response>>{}", response.getBody());

        return response;
    }

    public <T> T get(String url, Map<String, String> headersArgs, Class<T> clazz, Object... params) {
        ResponseEntity<String> response = get(url, headersArgs, params);
        T t = JacksonUtils.parse(response.getBody(), clazz);

        return t;
    }
}
