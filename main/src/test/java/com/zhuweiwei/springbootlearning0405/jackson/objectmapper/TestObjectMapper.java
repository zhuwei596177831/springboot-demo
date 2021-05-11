package com.zhuweiwei.springbootlearning0405.jackson.objectmapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.zhuweiwei.springbootlearning0405.jackson.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author 朱伟伟
 * @date 2020-11-01 12:36:33
 * @description
 */
public class TestObjectMapper {
    @Test
    public void test() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 美化输出
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "朱伟伟");
        map.put("age", 26);
        String value = objectMapper.writeValueAsString(map);
        objectMapper.writeValue(new File("D:\\objectMapper\\map.json"), map);
        byte[] bytes = objectMapper.writeValueAsBytes(map);
        System.out.println(value);
        Map<String, Object> readMap = objectMapper.readValue(value, Map.class);
        System.out.println(readMap.get("name"));
        System.out.println(readMap.get("age"));
        Map<String, Object> stringObjectMap = objectMapper.readValue(value, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(stringObjectMap.get("name"));
        System.out.println(stringObjectMap.get("age"));
        System.out.println(readMap.toString());
        readMap = objectMapper.readValue(bytes, Map.class);
        System.out.println(readMap.toString());
        readMap = objectMapper.readValue(new File("D:/objectMapper/map.json"), Map.class);
        System.out.println(readMap.toString());
    }

    @Test
    public void testJacksonStudent() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 启用美化输出
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 禁用在遇到未知属性的时候抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 启用根属性设置 @JsonRootName
//        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        //java8模块
        /** 开启java8提供的jsr310模块后默认：
         *{"age":null,"money":null,"date":1604208362224,"localDateTime":[2020,11,1,13,26,2,229000000],
         * "localDate":[2020,11,1],"localTime":[13,27,29,685000000],"aBoolean":null,
         * "stringList":null,"stringMap":null,"stringArray":null,"studentName":"朱伟伟"}
         **/
        /**配合@JsonFormat
         * @author: 朱伟伟
         * @date: 2020-11-01 13:44
         * @description:
         * {
         *   "age" : null,
         *   "money" : null,
         *   "date" : 1604209381508,
         *   "localDateTime" : "2020-11-01 13:43:01",
         *   "localDate" : "2020-11-01",
         *   "localTime" : "13:43:01",
         *   "aBoolean" : null,
         *   "stringList" : null,
         *   "stringMap" : null,
         *   "stringArray" : null,
         *   "studentName" : "朱伟伟"
         * }
         **/
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.registerModule(new Jdk8Module());
        //自动搜索所有模块，不需要我们手动注册
        objectMapper.findAndRegisterModules();
        Student student = new Student();
        student.setName("朱伟伟");
        student.setDate(new Date());
        student.setLocalDateTime(LocalDateTime.now());
        student.setLocalDate(LocalDate.now());
        student.setLocalTime(LocalTime.now());
        String value = objectMapper.writeValueAsString(student);
        System.out.println(value);
        String format = "{\"studentName\":\"朱伟伟\"}";
        Student student1 = objectMapper.readValue(format, Student.class);
        System.out.println(student1);
    }

    /**
     * @author: 朱伟伟
     * @date: 2020-11-01 14:05
     * @description: jackson java8时间反序列化测试
     **/
    @Test
    public void testJacksonParser() throws JsonProcessingException {
//        String format = "{\"date\":\"1604209828663\",\"localDateTime\":1604209828663,\"localDate\":1604209828663,\"localTime\":1604209828663}";
//        String format = "{\"date\":\"1604209828663\",\"localDateTime\":\"1604209828663\",\"localDate\":\"1604209828663\",\"localTime\":\"1604209828663\"}";
//        String format = "{\"date\":1604209828663,\"localDateTime\":2020-11-01 13:43:01,\"localDate\":2020-11-01,\"localTime\":13:43:01}";

        /**
         * @author: 朱伟伟
         * @date: 2020-11-01 14:04
         * @description: 时间戳不可以 只能是字符串格式配合@JsonFormat
         * JacksonParser{date=Sun Nov 01 13:50:28 CST 2020, localDateTime=2020-11-01T13:43:01, localDate=2020-11-01, localTime=13:43:01}
         **/
        String format = "{\"date\":1604209828663,\"localDateTime\":\"2020-11-01 13:43:01\",\"localDate\":\"2020-11-01\",\"localTime\":\"13:43:01\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.findAndRegisterModules();
        JacksonParser jacksonParser = objectMapper.readValue(format, JacksonParser.class);
        System.out.println(jacksonParser);
    }

    private static class JacksonParser {
        private Date date;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate localDate;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime localTime;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public LocalTime getLocalTime() {
            return localTime;
        }

        public void setLocalTime(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public String toString() {
            return "JacksonParser{" +
                    "date=" + date +
                    ", localDateTime=" + localDateTime +
                    ", localDate=" + localDate +
                    ", localTime=" + localTime +
                    '}';
        }
    }

    /**
     * @author: 朱伟伟
     * @date: 2020-11-01 14:07
     * @description: FastJson java8时间反序列化测试
     **/
    @Test
    public void testFastJsonParser() {
//        String format = "{\"date\":1604209828663,\"localDateTime\":2020-11-01 13:43:01,\"localDate\":2020-11-01,\"localTime\":13:43:01}";


        /**
         * FastJsonParser{date=Sun Nov 01 13:50:28 CST 2020, localDateTime=2020-11-01T13:50:28.663, localDate=2020-11-01, localTime=13:50:28.663}
         **/
//        String format = "{\"date\":1604209828663,\"localDateTime\":1604209828663,\"localDate\":1604209828663,\"localTime\":1604209828663}";
//        String format = "{\"date\":\"1604209828663\",\"localDateTime\":\"1604209828663\",\"localDate\":\"1604209828663\",\"localTime\":\"1604209828663\"}";

        /**
         * FastJsonParser{date=Sun Nov 01 13:50:28 CST 2020, localDateTime=2020-11-01T13:43:01, localDate=2020-11-01, localTime=13:43:01}
         **/
        String format = "{\"date\":\"2020-11-01 13:43:01\",\"localDateTime\":\"2020-11-01 13:43:01\",\"localDate\":\"2020-11-01\",\"localTime\":\"13:43:01\"}";
        FastJsonParser fastJsonParser = JSON.parseObject(format, FastJsonParser.class);
        System.out.println(fastJsonParser);
    }

    private static class FastJsonParser {
        private Date date;
        //        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
        //        @JSONField(format = "yyyy-MM-dd")
        private LocalDate localDate;
        //        @JSONField(format = "HH:mm:ss")
        private LocalTime localTime;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public LocalTime getLocalTime() {
            return localTime;
        }

        public void setLocalTime(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public String toString() {
            return "FastJsonParser{" +
                    "date=" + date +
                    ", localDateTime=" + localDateTime +
                    ", localDate=" + localDate +
                    ", localTime=" + localTime +
                    '}';
        }
    }

    @Test
    public void testFastJson() {
        FastJsonTest fastJsonTest = new FastJsonTest();
        fastJsonTest.setName("朱伟伟");
        fastJsonTest.setDate(new Date());
        fastJsonTest.setLocalDateTime(LocalDateTime.now());
        fastJsonTest.setLocalDate(LocalDate.now());
        fastJsonTest.setLocalTime(LocalTime.now());
        /**
         * @description: 默认：
         * {"date":1604212272728,"localDate":"2020-11-01","localDateTime":"2020-11-01T14:31:12.754","localTime":"14:31:12.755"}
         **/
//        System.out.println(JSON.toJSONString(fastJsonTest));
        /**
         * @description: 指定格式：
         * {"date":1604212384908,"localDate":"2020-11-01","localDateTime":"2020-11-01 14:33:04","localTime":"14:33:04"}
         **/
        System.out.println(JSON.toJSONString(fastJsonTest));
    }

    private static class FastJsonTest {
        @JSONField(name = "testName")
        private String name;
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private Date date;
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
        @JSONField(format = "yyyy-MM-dd")
        private LocalDate localDate;
        @JSONField(format = "HH:mm:ss")
        private LocalTime localTime;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public LocalTime getLocalTime() {
            return localTime;
        }

        public void setLocalTime(LocalTime localTime) {
            this.localTime = localTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
