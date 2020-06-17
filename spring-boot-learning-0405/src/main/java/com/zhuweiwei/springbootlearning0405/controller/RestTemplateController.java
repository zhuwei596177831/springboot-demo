package com.zhuweiwei.springbootlearning0405.controller;

import com.alibaba.fastjson.JSON;
import com.zhuweiwei.springbootlearning0405.bean.Test;
import com.zhuweiwei.springbootlearning0405.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zww
 * @date 2020-04-19 13:25
 * @description
 **/
@RestController
@RequestMapping("/restTemplate")
public class RestTemplateController {

    @Autowired
    RestTemplateService restTemplateService;

    @GetMapping("/getForObject/{id}")
    public List<Test> getForObject(@RequestParam String name, String accNo,
                                   @PathVariable(value = "id", required = false) String id,
                                   @RequestHeader(name = "user-token", required = false) String userToken) {
        Test test = new Test();
        if (!StringUtils.isEmpty(name)) {
            test.setName(name);
        }
        if (!StringUtils.isEmpty(accNo)) {
            test.setAccNo(accNo);
        }
        if (!StringUtils.isEmpty(id)) {
            test.setId(id);
        }
        System.out.println("参数：" + JSON.toJSONString(test));
        System.out.println("user-token：" + userToken);
        List<Test> forObject = restTemplateService.findList(test);
        return forObject;
    }

    @PostMapping("/postForObject")
    public List<Test> postForObject(HttpServletRequest request, String testName,
                                    Test test, @RequestHeader(name = "user-token", required = false) String userToken) {
        System.out.println(request.getContentType());
        System.out.println("requestParam testName：" + testName);
        System.out.println("user-token：" + userToken);
        System.out.println("test参数：" + JSON.toJSONString(test));
        List<Test> forObject = restTemplateService.findList(test);
        return forObject;
    }

    @PostMapping("/postForBody")
    public List<Test> postForBody(
            @RequestParam String testAccNo,
            HttpServletRequest request,
            @RequestHeader(name = "user-token", required = false) String userToken,
            @RequestBody Test test) {
        System.out.println("testAccNo：" + testAccNo);
        System.out.println(request.getContentType());
        System.out.println("user-token：" + userToken);
        System.out.println("body参数：" + JSON.toJSONString(test));
        return restTemplateService.findList(test);
    }

}
