package com.zhuweiwei.springbootlearning0405.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 朱伟伟
 * @date 2020-08-19 20:54:09
 * @description
 **/
@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/testFile")
    public String testFile() {
        return "file";
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(String name, String email, MultipartFile testFile) {
        System.out.println("=============");
        System.out.println(name);
        System.out.println(email);
        return "成功";
    }

}
