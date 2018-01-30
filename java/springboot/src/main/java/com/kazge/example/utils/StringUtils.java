package com.kazge.example.utils;

public class StringUtils extends org.springframework.util.StringUtils {
    public static boolean isBlank(String str){
        if (isEmpty(str)){
            return true;
        }

        return isEmpty(str.trim());
    }
}
