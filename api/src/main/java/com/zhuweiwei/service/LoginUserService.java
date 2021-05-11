package com.zhuweiwei.service;


import com.zhuweiwei.entity.LoginUser;

/**
 * @author zww
 * @date 2020-05-03 14:15
 * @description
 **/
public interface LoginUserService {
    LoginUser getByLoginId(String loginId);

    Integer addLoginUser(LoginUser loginUser);
}
