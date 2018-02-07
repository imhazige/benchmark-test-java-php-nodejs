package com.kazge.example.service;


import com.kazge.example.entity.User;
import com.kazge.example.exception.ApiException;
import com.kazge.example.exception.ErrorDetail;
import com.kazge.example.exception.InvalidParametersException;
import com.kazge.example.repository.UserRepository;
import com.kazge.example.utils.ExceptionUtils;
import com.kazge.example.utils.PasswordHash;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Value("${jwt.secret}")
    private String jwtSecrete;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public static final int HASH_WIDTH = 16;
    public static final int HASH_ITERATIONS = 10000;

    @Autowired
    private UserRepository userRepository;


    public User create(User user) {
        validate(user);
        String[] pwds = encodePassword(user.getPassword());
        user.setPassword(pwds[0]);
        user.setSalt(pwds[1]);
        user.setId(UUID.randomUUID().toString());
        user.setCreateTime(new Date());
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

    /**
     * refer to https://docs.spring.io/spring-security/site/docs/5.0.1.RELEASE/reference/html5/#spring-security-crypto-encryption
     *
     * @param password
     * @return
     */
    public String[] encodePassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
//        byte[] bytes = KeyGenerators.secureRandom(16).generateKey();
        String salt = DatatypeConverter.printHexBinary(bytes);
        logger.info("salt length is {}", salt.length());

        byte[] bs = null;
        try {
            bs = PasswordHash.pbkdf2(password, salt, HASH_ITERATIONS, HASH_WIDTH, PasswordHash.PBKDF2_ALGORITHM_SHA512);
        } catch (Exception e) {
            throw ExceptionUtils.silence(e);
        }

        String encodedPwd = DatatypeConverter.printHexBinary(bs);

//        logger.info("first hash length is {}", password.length());

        //the match the algorithm other solution
        //spring way did not produced the same way
//        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(salt, 10000, 16);
//        String encodedPwd = encoder.encode(password);
//
//        MessageDigest mda = null;
//        try {
//            mda = MessageDigest.getInstance("SHA-512");
//        } catch (NoSuchAlgorithmException e) {
//            throw ExceptionUtils.silence(e);
//        }
//
//        byte[] digesta = mda.digest(encodedPwd.getBytes());
////
//        encodedPwd = DatatypeConverter.printHexBinary(digesta);


        logger.info("encodedPwd length is {}", encodedPwd.length());


//
        return new String[]{encodedPwd, salt};
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword, String salt) {
        byte[] bs = null;
        try {
            bs = PasswordHash.pbkdf2(rawPassword, salt, HASH_ITERATIONS, HASH_WIDTH, PasswordHash.PBKDF2_ALGORITHM_SHA512);
            String encodedPwd = DatatypeConverter.printHexBinary(bs);
            return encodedPassword.equals(encodedPwd);
        } catch (Exception e) {
            throw ExceptionUtils.silence(e);
        }

    }

    public User update(User user) {
        User fetchedUser = get(user.getId());
        if (null == fetchedUser) {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), null);
        }
        validate(user);

        String[] pwds = encodePassword(user.getPassword());
        fetchedUser.setPassword(pwds[0]);
        fetchedUser.setSalt(pwds[1]);
        fetchedUser.setUpdateTime(new Date());
        userRepository.save(fetchedUser);

        User respUser = new User();

        respUser.setId(user.getId());

        return respUser;
    }

    public String createToken(User user) {
        User u = userRepository.getByname(user.getName());
        if (null == u){
            throw new ApiException(HttpStatus.NOT_FOUND.value(),null);
        }
        boolean logined = verifyPassword(user.getPassword(),u.getPassword(),u.getSalt());
        if (!logined){
            throw new ApiException(HttpStatus.UNAUTHORIZED.value(),"invalid username or password.");
        }
        Date expiresAt = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
        return Jwts.builder()
                .setSubject(u.getId())
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, jwtSecrete).compact();
    }

    public String verifyToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(jwtSecrete).parseClaimsJws(token);
            final String userId = jws.getBody().getSubject();

            return userId;
        } catch (ExpiredJwtException e) {
            return null;
        } catch (UnsupportedJwtException e) {
            return null;
        } catch (MalformedJwtException e) {
            return null;
        } catch (SignatureException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
