package com.kazge.example.service;


import com.kazge.example.entity.User;
import com.kazge.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        validate(user);
        user.setId(UUID.randomUUID().toString());
        user = userRepository.save(user);
        User respUser = new User();

        respUser.setId(user.getId());

        return respUser;
    }

    public void validate(User user) {
    }
}
