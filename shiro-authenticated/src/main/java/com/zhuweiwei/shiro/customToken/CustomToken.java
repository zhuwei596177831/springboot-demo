package com.zhuweiwei.shiro.customToken;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 朱伟伟
 * @date 2021-05-11 23:23:15
 * @description
 */
public class CustomToken implements AuthenticationToken {
    private final String token;

    public CustomToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
