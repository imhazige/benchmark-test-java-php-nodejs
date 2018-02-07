package com.kazge.example.controller;

import com.kazge.example.auth.RequireAuth;
import com.kazge.example.entity.User;
import com.kazge.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequireAuth
@RestController
@RequestMapping(path = "/t2")
public class TokenController extends AbstractUserController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @PostMapping("/token")
    @RequireAuth(false)
    public String createToken(@RequestBody User user) {
        String token = userService.createToken(user);
//        logger.info("response token {}", token);

        return token;
    }
}
