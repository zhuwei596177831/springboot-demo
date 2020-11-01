package com.zhuweiwei.springbootlearning0405.jackson;

/**
 * @author 朱伟伟
 * @date 2020-10-31 16:31:05
 * @description
 */
public class User {
    private String name = "YourBatman";
    private Integer age = 18;

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
}
