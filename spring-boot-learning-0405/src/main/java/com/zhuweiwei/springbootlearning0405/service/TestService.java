package com.zhuweiwei.springbootlearning0405.service;

import com.zhuweiwei.springbootlearning0405.bean.Test;

import java.util.List;

/**
 * @author zww
 * @date 2020-04-05 15:41
 * @description
 **/
public interface TestService {

    List<String> getList();

    List<Test> getXmlList();

}
