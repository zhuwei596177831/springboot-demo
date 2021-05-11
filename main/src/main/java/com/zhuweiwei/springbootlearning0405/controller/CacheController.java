package com.zhuweiwei.springbootlearning0405.controller;

import com.zhuweiwei.springbootlearning0405.bean.Test;
import com.zhuweiwei.springbootlearning0405.service.CacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zww
 * @date 2020-04-18 17:00
 * @description
 **/
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Resource
    CacheService cacheService;


    @GetMapping("/getTestById/{id}")
    public Test getTestById(@PathVariable(name = "id", required = true) Long id) {
        return cacheService.getTestById(id);
    }

    @GetMapping("/updateTestById")
    public Test updateById(Test test) {
        return cacheService.updateById(test);
    }

    @GetMapping("/getTestList")
    public List<Test> getTestList() {
        return cacheService.getTestList();
    }

    @GetMapping("/getTestListByCondition")
    public List<Test> getTestListByCondition(Test test) {
        return cacheService.getTestListByCondition(test);
    }

    @GetMapping("/deleteTestById/{id}")
    public String deleteTestById(@PathVariable(name = "id") Long id) {
        return cacheService.deleteTestById(id);
    }

    @GetMapping("/insertTest")
    public Test insertTest(Test test) {
        return cacheService.insertTest(test);
    }

}
