package com.kazge.example.exception;

import com.kazge.example.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    Object
    defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        if (e instanceof  ApiException){
            ApiException ae = (ApiException)e;
            response.setStatus(ae.getStatus());

            return ae.toString();
        }

        logger.error("unhandled server exception",e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String errorStack = ExceptionUtils.getStackTraceAsString(e);

        return errorStack;
    }
}
