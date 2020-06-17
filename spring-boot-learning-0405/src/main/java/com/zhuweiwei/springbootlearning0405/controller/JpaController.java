package com.zhuweiwei.springbootlearning0405.controller;

import com.zhuweiwei.springbootlearning0405.jpa.User;
import com.zhuweiwei.springbootlearning0405.jpa.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zww
 * @date 2020-04-14 22:16
 * @description
 **/
@RestController
@RequestMapping("/jpa")
public class JpaController {

    private static Logger logger = LoggerFactory.getLogger(JpaController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        logger.info("id的值：{}", id);
        User user = userRepository.findById(id).orElseGet(User::new);
        return user;
    }

    @GetMapping("/user/add")
    public User insertUser(User user) {
        User save = userRepository.save(user);
        return save;
    }

}
