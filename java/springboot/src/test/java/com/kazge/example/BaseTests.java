package com.kazge.example;

import com.kazge.example.utils.CollectionUtils;
import com.kazge.example.utils.JacksonUtils;
import com.kazge.example.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BaseTests {
    public static final Logger logger = LoggerFactory.getLogger(BaseTests.class);

    /**
     * you have to use @Autowired, otherwise it will not request to running environment
     */
    @Autowired
    private TestRestTemplate restTemplate;

    public TestRestTemplate getRestTemplate() {
        return restTemplate;
    }

    public ResponseEntity<String> post(String url, Map<String, String> headersArgs, Object entity) {
        return execute(url, HttpMethod.POST, headersArgs, entity);
    }

    public ResponseEntity<String> delete(String url, Map<String, String> headersArgs) {
        return execute(url, HttpMethod.DELETE, headersArgs, null);
    }

    public ResponseEntity<String> put(String url, Map<String, String> headersArgs, Object entity) {
        return execute(url, HttpMethod.PUT, headersArgs, entity);
    }

    public HttpEntity buildRequestWithHeaders(Map<String, String> headersArgs, Object data) {
        HttpHeaders headers = new HttpHeaders();
        if (null != headersArgs) {
            for (Map.Entry<String, String> en :
                    headersArgs.entrySet()) {
                headers.add(en.getKey(), en.getValue());
            }
        }
        HttpEntity he = new HttpEntity<>(data, headers);

        return he;
    }

    public ResponseEntity<String> execute(String url, HttpMethod method, Map<String, String> headersArgs, Object data) {

        ResponseEntity<String> response = getRestTemplate().exchange(url, method, buildRequestWithHeaders(headersArgs, data), String.class);

        logger.info("response>>{}", response.getBody());

        return response;
    }

    public ResponseEntity<String> get(String url, Map<String, String> headersArgs, Object... params) {
        url = UrlUtils.appendQueryString(url, CollectionUtils.buildMap(params));

        return execute(url, HttpMethod.GET, headersArgs, null);
    }

    public <T> T get(String url, Map<String, String> headersArgs, Class<T> clazz, Object... params) {
        ResponseEntity<String> response = get(url, headersArgs, params);
        T t = JacksonUtils.parse(response.getBody(), clazz);

        return t;
    }
}
