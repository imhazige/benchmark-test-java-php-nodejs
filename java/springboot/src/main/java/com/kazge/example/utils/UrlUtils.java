package com.kazge.example.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class UrlUtils {
    public static String buildQueryString(String url, Map<Object, Object> params) {
        MultiValueMap<String, String> p = new LinkedMultiValueMap<String, String>();
        if (null != params) {
            for (Map.Entry<Object, Object> entry : params.entrySet()) {
                if (null == entry.getKey()){
                    continue;
                }
                if (null == entry.getValue()) {
                    continue;
                }
                p.add(entry.getKey().toString(), entry.getValue().toString());
            }
        }

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://abc.com").queryParams(p).build();
        String query = uriComponents.getQuery();
        if (url.indexOf()){

        }

        return uriComponents.toUriString();
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
                Entry en = (Entry) it.next();
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
