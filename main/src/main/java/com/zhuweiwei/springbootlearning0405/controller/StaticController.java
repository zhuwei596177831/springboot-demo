package com.zhuweiwei.springbootlearning0405.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zww
 * @date 2020-04-08 23:57
 * @description
 **/
@RestController
@RequestMapping("/static")
public class StaticController {

    @GetMapping("/getName")
    public String getName() {
        return "xxx";
    }

}
