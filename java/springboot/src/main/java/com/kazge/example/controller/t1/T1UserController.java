package com.kazge.example.controller.t1;

import com.kazge.example.controller.AbstractUserController;
import com.kazge.example.entity.User;
import com.kazge.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/t1/users")
public class T1UserController extends AbstractUserController{

}
