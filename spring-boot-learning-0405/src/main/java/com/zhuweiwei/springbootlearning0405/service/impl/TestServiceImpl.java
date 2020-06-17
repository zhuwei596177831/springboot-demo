package com.zhuweiwei.springbootlearning0405.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhuweiwei.springbootlearning0405.bean.Test;
import com.zhuweiwei.springbootlearning0405.dao.TestDao;
import com.zhuweiwei.springbootlearning0405.mapper.TestMapper;
import com.zhuweiwei.springbootlearning0405.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zww
 * @date 2020-04-05 15:41
 * @description
 **/
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;

    @Autowired
    TestMapper testMapper;

    @Override
    public List<String> getList() {
        return testDao.getList();
    }

    @Override
    public List<Test> getXmlList() {
        Page<Object> page = PageHelper.startPage(1, 2);
        return testMapper.getXmlList();
    }
}
