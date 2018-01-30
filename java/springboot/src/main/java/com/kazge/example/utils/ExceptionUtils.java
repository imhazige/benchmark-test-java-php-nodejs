package com.kazge.example.utils;


public class ExceptionUtils
{
    public static RuntimeException silence(Throwable ex){
        if (ex instanceof RuntimeException){
            return (RuntimeException)ex;
        }

        return new RuntimeException(ex);
    }
}
