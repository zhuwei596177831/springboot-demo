package com.zhuweiwei.springbootlearning0405.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuweiwei.springbootlearning0405.bean.LoginUser;
import com.zhuweiwei.springbootlearning0405.mapper.LoginUserMapper;
import com.zhuweiwei.springbootlearning0405.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zww
 * @date 2020-05-03 14:15
 * @description
 **/
@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    LoginUserMapper loginUserMapper;

    @Override
    public LoginUser getByLoginId(String username) {
        QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginId", username);
        return loginUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer addLoginUser(LoginUser loginUser) {
        return loginUserMapper.insert(loginUser);
    }
}
