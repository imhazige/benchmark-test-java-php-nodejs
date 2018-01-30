package com.kazge.example.service;


import com.kazge.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class T1Service {
    @Autowired
    private UserRepository userRepository;

}
