package com.zhuweiwei.springbootlearning0405.service;

import com.zhuweiwei.springbootlearning0405.bean.LoginUser;

/**
 * @author zww
 * @date 2020-05-03 14:15
 * @description
 **/
public interface LoginUserService {
    LoginUser getByLoginName(String username);

    Integer addLoginUser(LoginUser loginUser);
}
