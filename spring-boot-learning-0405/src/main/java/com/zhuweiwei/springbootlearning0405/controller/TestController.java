package com.zhuweiwei.springbootlearning0405.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuweiwei.springbootlearning0405.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zww
 * @date 2020-04-05 23:55
 * @description
 **/
@RestController
public class TestController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/test")
    @Transactional
    public String test() {
        String insertSql = "insert into test(name) values('哈哈哈哈')";
        jdbcTemplate.update(insertSql);
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errStat", 1);
            jsonObject.put("errMsg", "出异常了");
            throw new MyException(jsonObject.toJSONString());
        }
        String selectSql = "select * from test";
        return JSON.toJSONString(jdbcTemplate.queryForList(selectSql));
    }

    @GetMapping("/getList")
    public String getList() {
        String selectSql = "select * from test";
        return JSON.toJSONString(jdbcTemplate.queryForList(selectSql));
    }

}
