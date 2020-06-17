package com.zhuweiwei.springbootlearning0405.controller;

import com.zhuweiwei.springbootlearning0405.bean.Student;
import com.zhuweiwei.springbootlearning0405.properties.MyProperties;
import com.zhuweiwei.springbootlearning0405.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zww
 * @date 2020-04-05 17:45
 * @description
 **/
@RestController
public class HelloController {

    @Value("${zookeeperAddress}")
    private String zookeeperAddress;
    @Value("${nginxAddress}")
    private String nginxAddress;

    @Resource
    TestService testService;
    @Autowired
    MyProperties myProperties;

    @GetMapping("/hello")
    public List<String> hello() {
        List<String> list = new ArrayList<>(testService.getList());
        list.add(zookeeperAddress);
        list.add(nginxAddress);
        list.addAll(myProperties.getEmails());
        list.add(myProperties.getProjectName());
        return list;
    }

    @GetMapping(value = "/student")
    public Student getStudent() {
        Student student = new Student();
        student.setName("朱伟伟");
        student.setAge(26);
        return student;
    }
}
