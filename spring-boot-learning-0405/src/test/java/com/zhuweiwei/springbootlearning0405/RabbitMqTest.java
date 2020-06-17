package com.zhuweiwei.springbootlearning0405;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zww
 * @date 2020-04-25 17:25
 * @description
 **/
@SpringBootTest
public class RabbitMqTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void sendMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "朱伟伟");
        map.put("name", "美月莉亚");
        rabbitTemplate.convertAndSend("exchange.direct", "zhuwewei.news", map);
    }

    @Test
    void sendJsonMessage() {
        com.zhuweiwei.springbootlearning0405.bean.Test test =
                new com.zhuweiwei.springbootlearning0405.bean.Test();
        test.setName("希岛爱理");
        test.setAccNo("520");
        rabbitTemplate.convertAndSend("exchange.direct", "zhuwewei.news", test);
    }

    @Test
    void receiveMessage() {
        Object o = rabbitTemplate.receiveAndConvert("zhuwewei.news");
        System.out.println(o.getClass());
        System.out.println(o);
        System.out.println(o.toString());
        System.out.println(JSON.toJSONString(o));
    }

}
