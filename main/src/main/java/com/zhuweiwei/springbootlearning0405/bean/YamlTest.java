package com.zhuweiwei.springbootlearning0405.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zww
 * @date 2020-04-06 23:28
 * @description
 **/
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "yaml-test")
@Component
public class YamlTest {
    private String name;
    private Integer age;
    private Date date;
    private List<String> list;
    private Map<String, Object> map;
    private YamlInnerClass yamlInnerClass;
}