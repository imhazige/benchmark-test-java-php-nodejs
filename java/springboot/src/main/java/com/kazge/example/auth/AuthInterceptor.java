package com.kazge.example.auth;

import com.kazge.example.service.UserService;
import com.kazge.example.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
//        logger.info("preHandle --- {} ",handler.getClass().getName());
        if( handler instanceof HandlerMethod ) {
            // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().isAnnotationPresent(RequireAuth.class)) {
                RequireAuth ra = handlerMethod.getMethod().getAnnotation(RequireAuth.class);
                return !ra.value() || authenticate(httpServletRequest,httpServletResponse);
            }else if (handlerMethod.getBeanType().isAnnotationPresent(RequireAuth.class)){
                //this also work
//            }else if (handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(RequireAuth.class)){
                RequireAuth ra = handlerMethod.getBeanType().getAnnotation(RequireAuth.class);
                return !ra.value() || authenticate(httpServletRequest,httpServletResponse);
            }

        }
        return true;
    }

    private boolean authenticate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authHeader)){
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //JWT token verify
        String userId =  userService.verifyToken(authHeader);
        if (null == userId){
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
