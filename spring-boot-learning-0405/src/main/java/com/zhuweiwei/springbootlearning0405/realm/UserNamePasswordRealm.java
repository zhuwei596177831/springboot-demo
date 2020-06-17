package com.zhuweiwei.springbootlearning0405.realm;

import com.zhuweiwei.springbootlearning0405.bean.LoginUser;
import com.zhuweiwei.springbootlearning0405.service.LoginUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zww
 * @date 2020-05-03 14:21
 * @description
 **/
public class UserNamePasswordRealm extends AuthorizingRealm {

    @Resource
    LoginUserService loginUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LoginUser loginUser = (LoginUser) principalCollection.getPrimaryPrincipal();
        if (loginUser.getLoginId().equals("zww")) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            Set<String> permissions = new LinkedHashSet<>();
            permissions.add("user:add");
            simpleAuthorizationInfo.setStringPermissions(permissions);
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            System.out.println("非正常浏览器关闭。。。");
            subject.logout();
        }
        if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
            String username = usernamePasswordToken.getUsername();
            String password = new String((char[]) usernamePasswordToken.getPassword());
            LoginUser loginUser = loginUserService.getByLoginName(username);
            if (loginUser == null) {
                throw new UnknownAccountException();
            }
            if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(loginUser.getPassword())) {
                throw new IncorrectCredentialsException();
            }
            return new SimpleAuthenticationInfo(loginUser, password, getName());
        }
        return null;
    }
}
