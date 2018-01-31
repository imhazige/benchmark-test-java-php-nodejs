package com.kazge.example.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class UrlUtils {

    public static String appendQueryString(String url, Map params) {
        if (null == url) {
            url = "";
        }

        String query = buildQueryString(params);
        if (url.indexOf("?") > -1) {
            query = "&" + query;
        } else {
            query = "?" + query;
        }

        return url + query;
    }

    public static String buildQueryString(Map params) {
        if (null == params || params.isEmpty()) {
            return null;
        }
        StringBuilder sb;
        try {
            Iterator it = params.entrySet().iterator();
            sb = new StringBuilder();
            while (it.hasNext()) {
                Map.Entry en = (Map.Entry) it.next();
                Object key = en.getKey();
                Object value = en.getValue();
                if (null == key || null == value) {
                    continue;
                }
                key = URLEncoder.encode(key.toString(), "utf-8");
                value = URLEncoder.encode(value.toString(), "utf-8");
                sb.append("&" + key + "=" + value);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String s = sb.toString();

        if (s.startsWith("&")) {
            s = s.substring(1);
        }

        return s;
    }
}
