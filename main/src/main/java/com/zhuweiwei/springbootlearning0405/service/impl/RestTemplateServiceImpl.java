package com.zhuweiwei.springbootlearning0405.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuweiwei.springbootlearning0405.bean.Test;
import com.zhuweiwei.springbootlearning0405.mapper.RestTemplateMapper;
import com.zhuweiwei.springbootlearning0405.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zww
 * @date 2020-04-19 13:27
 * @description
 **/
@Service
public class RestTemplateServiceImpl implements RestTemplateService {

    @Autowired
    RestTemplateMapper restTemplateMapper;

    @Override
    public List<Test> findList(Test test) {
        QueryWrapper<Test> testQueryWrapper = new QueryWrapper<>();
        if (test != null) {
            if (!StringUtils.isEmpty(test.getName())) {
                testQueryWrapper.like("name", test.getName());
            }
            if (!StringUtils.isEmpty(test.getAccNo())) {
                testQueryWrapper.like("accNo", test.getAccNo());
            }
            if (!StringUtils.isEmpty(test.getId())) {
                testQueryWrapper.eq("id", test.getId());
            }
        }
        return restTemplateMapper.selectList(testQueryWrapper);
    }
}
