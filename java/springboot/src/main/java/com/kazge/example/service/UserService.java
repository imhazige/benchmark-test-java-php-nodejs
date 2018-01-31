package com.kazge.example.service;


import com.kazge.example.entity.User;
import com.kazge.example.exception.ErrorDetail;
import com.kazge.example.exception.InvalidParametersException;
import com.kazge.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder

    public User create(User user) {
        validate(user);
        String[] pwds = encodePassword(user.getPassword());
        user.setPassword(pwds[0]);
        user.setSalt(pwds[1]);
        user.setId(UUID.randomUUID().toString());
        user = userRepository.save(user);
        User respUser = new User();

        respUser.setId(user.getId());

        return respUser;
    }

    public boolean existsUser(String name, String excludeId) {
        User u = userRepository.getByname(name);

        if (null == u) {
            return false;
        }

        if (null != excludeId && excludeId.equals(u.getId())) {
            return false;
        }

        return true;
    }

    public void validate(User user) {
        List<ErrorDetail> errors = new ArrayList<>();
        if (existsUser(user.getName(), user.getId())) {
            ErrorDetail ed = new ErrorDetail();
            ed.setDetail("user name " + user.getName() + " already exists.");
            errors.add(ed);
        }

        if (!errors.isEmpty()) {
            InvalidParametersException e = new InvalidParametersException();
            e.setErrorDetails(errors);

            throw e;
        }
    }

    public List<User> getAll(Integer limit) {
        if (null == limit || limit < 1 || limit > 100) {
            limit = 100;
        }
        return userRepository.findAllLimit(limit);
    }

    public User delete(String userId) {
        userRepository.delete(userId);

        User respUser = new User();

        respUser.setId(userId);

        return respUser;
    }

    public User get(String userId) {
        return userRepository.findOne(userId);
    }

    public String[] encodePassword(String password) {
        String salt = "";
        String encodedPwd = "";

        return new String[]{encodedPwd, salt};
    }

    public User update(User user) {
        validate(user);

        String[] pwds = encodePassword(user.getPassword());
        user.setPassword(pwds[0]);
        user.setSalt(pwds[1]);
        userRepository.save(user);

        User respUser = new User();

        respUser.setId(user.getId());

        return respUser;
    }
}
