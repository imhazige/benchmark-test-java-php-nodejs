package com.kazge.example.repository;

import com.kazge.example.BaseNoServerTests;
import com.kazge.example.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTests extends BaseNoServerTests {
    @Autowired
    UserRepository userRepository;

    @Test
    public void testMany(){
        User u = userRepository.getByname("abc");
    }
}
