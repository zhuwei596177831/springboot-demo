package com.zhuweiwei.springbootlearning0405.service;

import com.zhuweiwei.springbootlearning0405.bean.Test;

import java.util.List;

/**
 * @author zww
 * @date 2020-04-19 13:27
 * @description
 **/
public interface RestTemplateService {

    List<Test> findList(Test test);

}
