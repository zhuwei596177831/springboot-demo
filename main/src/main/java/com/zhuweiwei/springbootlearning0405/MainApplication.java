package com.zhuweiwei.springbootlearning0405;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.zhuweiwei"})
@MapperScan(basePackages = {"com.zhuweiwei.springbootlearning0405.mapper"})
@EnableCaching
@EnableRabbit
//@EnableConfigurationProperties(value = {MyProperties.class})
//@ConfigurationPropertiesScan(basePackages = {"com.zhuweiwei.springbootlearning0405.properties"})
public class MainApplication {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
