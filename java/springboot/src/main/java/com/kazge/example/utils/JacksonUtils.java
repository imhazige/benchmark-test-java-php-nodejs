package com.kazge.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

public class JacksonUtils {
    public static String toJsonString(Object obj) {
        if (null == obj) {
            return "null";
        }
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = buildConfig();
        try {
            //http://stackoverflow.com/questions/15261456/jackson-disable-fail-on-empty-beans
            mapper.configure(FAIL_ON_EMPTY_BEANS, false);
            mapper.writeValue(writer, obj);
        } catch (Exception e) {
            throw ExceptionUtils.silence(e);
        }

        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse(String jsonString) {
        return parse(jsonString, Map.class);
    }

    private static ObjectMapper buildConfig(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(FAIL_ON_EMPTY_BEANS, false);

        return mapper;
    }

    public static <T> T parse(String jsonString, Class<T> type) {
        if (StringUtils.isBlank(jsonString)){
            return null;
        }
        ObjectMapper mapper = buildConfig();

        try {
            return mapper.readValue(jsonString, type);
        } catch (Exception e) {
            throw ExceptionUtils.silence(e);
        }
    }

    public static List<Map> parseList(String jsonString) {
        return parseList(jsonString,Map.class);
    }

    public static <T> List<T> parseList(String jsonString, Class<T> type) {
        if (StringUtils.isBlank(jsonString)){
            return null;
        }
        ObjectMapper mapper = buildConfig();
        List<T> someClassList;
        try {
            someClassList = mapper
                    .readValue(jsonString, mapper.getTypeFactory()
                            .constructCollectionType(List.class, type));

            return someClassList;
        } catch (Exception e) {
            throw ExceptionUtils.silence(e);
        }
    }

    public static String prettyPrint(String json){
        ObjectMapper mapper = buildConfig();
        try {
            Object obj = mapper.readValue(json, Object.class);
            String prettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

            return prettyString;
        } catch (Exception e) {
            return json;
        }
    }

}