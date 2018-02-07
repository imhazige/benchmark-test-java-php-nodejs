package com.kazge.example.controller;

import com.kazge.example.entity.User;
import com.kazge.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractUserController {

    @Autowired
    public UserService userService;

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping
    public List<User> getAll(@RequestParam(value = "limit",required = false) Integer limit) {
        return userService.getAll(limit);
    }

    @GetMapping("/{userId}")
    public User get(@PathVariable("userId") String userId) {
        return userService.get(userId);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable("userId") String userId, @RequestBody User user) {
        user.setId(userId);
        return userService.update(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") String userId) {
        userService.delete(userId);
    }
}
