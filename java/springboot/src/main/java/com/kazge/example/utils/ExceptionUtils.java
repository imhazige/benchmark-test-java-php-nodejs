package com.kazge.example.utils;


import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    public static RuntimeException silence(Throwable ex) {
        if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        }

        return new RuntimeException(ex);
    }

    public static String getStackTraceAsString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = sw.toString(); // stack trace as a string

        return sStackTrace;
    }
}
