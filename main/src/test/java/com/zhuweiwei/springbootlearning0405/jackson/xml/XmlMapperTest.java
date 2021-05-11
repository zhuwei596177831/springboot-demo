package com.zhuweiwei.springbootlearning0405.jackson.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 朱伟伟
 * @date 2020-11-01 15:32:51
 * @description
 */
public class XmlMapperTest {
    @Test
    public void test() throws JsonProcessingException {
        XmlTest xmlTest = new XmlTest();
        xmlTest.setEmail("596177831@qq.com");
        xmlTest.setName("朱伟伟");
        xmlTest.setAge(26);
        xmlTest.setDate(new Date());
        xmlTest.setLocalDateTime(LocalDateTime.now());
        xmlTest.setLocalDate(LocalDate.now());
//        XmlMapper xmlMapper = new XmlMapper();
//        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        xmlMapper.findAndRegisterModules();
//        System.out.println(xmlMapper.writeValueAsString(xmlTest));
    }
}
