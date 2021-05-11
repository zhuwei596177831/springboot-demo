package com.zhuweiwei.springbootlearning0405.jackson.objectmapper;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2020-11-01 12:55:40
 * @description
 */
@JsonIgnoreProperties(value = {"address"})
@JsonRootName(value = "student")
public class Student {
    @JsonProperty(value = "studentName")
    private String name;
    @JsonIgnore
    private String email;
    private String address;
    private Integer age;
    private Double money;
    /**
     * Date类型的序列化时要指定时区（我们处于东八区）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime localTime;
    private Boolean aBoolean;
    private List<String> stringList;
    private Map<String, String> stringMap;
    private String[] stringArray;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

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

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public Map<String, String> getStringMap() {
        return stringMap;
    }

    public void setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", date=" + date +
                ", localDateTime=" + localDateTime +
                ", localDate=" + localDate +
                ", localTime=" + localTime +
                ", aBoolean=" + aBoolean +
                ", stringList=" + stringList +
                ", stringMap=" + stringMap +
                ", stringArray=" + Arrays.toString(stringArray) +
                '}';
    }
}
