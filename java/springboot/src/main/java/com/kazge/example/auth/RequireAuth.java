package com.kazge.example.auth;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.TYPE, ElementType.METHOD
})
@Documented
public @interface RequireAuth {
    boolean value() default true;
}
