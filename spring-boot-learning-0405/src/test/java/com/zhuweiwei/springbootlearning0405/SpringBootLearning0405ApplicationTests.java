package com.zhuweiwei.springbootlearning0405;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zhuweiwei.springbootlearning0405.bean.YamlTest;
import com.zhuweiwei.springbootlearning0405.mapper.TestMapper;
import com.zhuweiwei.springbootlearning0405.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
class SpringBootLearning0405ApplicationTests {

    @Autowired
    TestService testService;
    @Autowired
    YamlTest yamlTest;
    @Autowired
    DataSource dataSource;
    @Autowired
    TestMapper testMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Test
    void contextLoads() {
        System.out.println(testService.getList());
    }

    @Test
    void testYaml() {
        System.out.println(yamlTest);
    }

    @Test
    void testDatasource() {
        System.out.println(dataSource);
    }

    @Test
    void testMybatis() {
        List<com.zhuweiwei.springbootlearning0405.bean.Test> testList = testMapper.getTestList();
        System.out.println(JSON.toJSONString(testList));
    }

    @Test
    void testXml() {
        List<com.zhuweiwei.springbootlearning0405.bean.Test> xmlList = testMapper.getXmlList();
        System.out.println(JSON.toJSONString(xmlList));
    }

    @Test
    void testPageHelper() {
        PageHelper.startPage(1, 2);
        List<com.zhuweiwei.springbootlearning0405.bean.Test> testList = testMapper.getTestList();
        System.out.println(JSON.toJSONString(testList));
    }

    @Test
    void testRedis() {
        stringRedisTemplate.opsForValue().set("hello", "朱伟伟");
    }


}
