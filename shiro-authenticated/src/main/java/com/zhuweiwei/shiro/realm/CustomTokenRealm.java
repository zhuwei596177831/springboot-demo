package com.zhuweiwei.shiro.realm;

import com.zhuweiwei.shiro.customToken.CustomToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * @author 朱伟伟
 * @date 2021-05-11 23:43:22
 * @description
 */
public class CustomTokenRealm extends AuthenticatingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CustomToken customToken = (CustomToken) token;
        return new SimpleAuthenticationInfo(customToken.getToken(), customToken.getCredentials(), getName());
    }
}
