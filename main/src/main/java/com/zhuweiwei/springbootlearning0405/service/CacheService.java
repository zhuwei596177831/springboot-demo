package com.zhuweiwei.springbootlearning0405.service;

import com.zhuweiwei.springbootlearning0405.bean.Test;

import java.util.List;

/**
 * @author zww
 * @date 2020-04-18 17:01
 * @description
 **/
public interface CacheService {
    Test getTestById(Long id);

    Test updateById(Test test);

    List<Test> getTestList();

    List<Test> getTestListByCondition(Test test);

    String deleteTestById(Long id);

    Test insertTest(Test test);
}
