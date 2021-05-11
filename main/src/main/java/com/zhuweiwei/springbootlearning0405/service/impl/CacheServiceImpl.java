package com.zhuweiwei.springbootlearning0405.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuweiwei.springbootlearning0405.bean.Test;
import com.zhuweiwei.springbootlearning0405.mapper.CacheMapper;
import com.zhuweiwei.springbootlearning0405.service.CacheService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zww
 * @date 2020-04-18 17:01
 * @description
 **/
@Service
@CacheConfig(cacheNames = "testInfo")
public class CacheServiceImpl implements CacheService {

    @Resource
    CacheMapper cacheMapper;

    @Override
    @Cacheable(key = "'getTestList'")
    public List<Test> getTestList() {
        return cacheMapper.selectList(new QueryWrapper<>());
    }

    @Override
    @Cacheable(cacheNames = "getTestListByCondition")
    public List<Test> getTestListByCondition(Test test) {
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        if (test != null) {
            if (!StringUtils.isEmpty(test.getName())) {
                queryWrapper.eq("name", test.getName());
            }
            if (!StringUtils.isEmpty(test.getAccNo())) {
                queryWrapper.eq("accNo", test.getAccNo());
            }
        }
        return cacheMapper.selectList(queryWrapper);
    }

    @Override
    @Cacheable(condition = "#id != null", key = "#id", unless = "#id == 6")
    public Test getTestById(Long id) {
        return cacheMapper.selectById(id);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(key = "#test.id")
            },
            evict = {
                    @CacheEvict(key = "'getTestList'"),
                    @CacheEvict(cacheNames = "getTestListByCondition", allEntries = true)
            }
    )
    public Test updateById(Test test) {
        cacheMapper.updateById(test);
        return test;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#id"),
                    @CacheEvict(key = "'getTestList'"),
                    @CacheEvict(cacheNames = "getTestListByCondition", allEntries = true)
            }
    )
    public String deleteTestById(Long id) {
        int i = cacheMapper.deleteById(id);
        if (i > 0) {
            return "成功";
        }
        return "失败";
    }

    @Override
    @Caching(
            put = {
                    @CachePut(key = "#result.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "getTestListByCondition", allEntries = true),
                    @CacheEvict(key = "'getTestList'")
            }
    )
    public Test insertTest(Test test) {
        cacheMapper.insert(test);
        return test;
    }
}
