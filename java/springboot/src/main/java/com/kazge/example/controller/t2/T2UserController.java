package com.kazge.example.controller.t2;

import com.kazge.example.auth.RequireAuth;
import com.kazge.example.controller.AbstractUserController;
import com.kazge.example.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequireAuth
@RestController
@RequestMapping(path = "/t2/users")
public class T2UserController extends AbstractUserController {

}
