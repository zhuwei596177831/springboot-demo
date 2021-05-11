package com.zhuweiwei.springbootlearning0405.controller;

import com.zhuweiwei.springbootlearning0405.bean.StaticJackson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author 朱伟伟
 * @date 2020-11-01 16:28:38
 * @description
 */
@RestController
@RequestMapping("/testJackson")
public class JacksonController {

    @GetMapping(value = "/staticJackson")
    public StaticJackson staticJackson() {
        StaticJackson staticJackson = new StaticJackson();
        staticJackson.setDate(new Date());
        staticJackson.setLocalDate(LocalDate.now());
        staticJackson.setLocalDateTime(LocalDateTime.now());
        staticJackson.setLocalTime(LocalTime.now());
        staticJackson.setName("朱伟伟");
        return staticJackson;
    }

}
