package com.zhuweiwei.springbootlearning0405.dao;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @author zww
 * @date 2020-04-05 15:42
 * @description
 **/
@Repository
public class TestDao {

    public List<String> getList() {
        return Arrays.asList("111", "222","333","444");
    }

}
