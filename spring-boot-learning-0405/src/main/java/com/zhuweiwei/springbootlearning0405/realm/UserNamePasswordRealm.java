package com.zhuweiwei.springbootlearning0405.realm;

import com.zhuweiwei.springbootlearning0405.bean.LoginUser;
import com.zhuweiwei.springbootlearning0405.service.LoginUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zww
 * @date 2020-05-03 14:21
 * @description
 **/
public class UserNamePasswordRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Resource
    LoginUserService loginUserService;
    @Autowired
    EnterpriseCacheSessionDAO enterpriseCacheSessionDAO;

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
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String loginId = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        LoginUser loginUser = loginUserService.getByLoginId(loginId);
        if (loginUser == null) {
            throw new UnknownAccountException();
        }
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(loginUser.getPassword())) {
            throw new IncorrectCredentialsException();
        }
        //同一用户不同浏览器登录  互踢功能 原理：(删除之前用户登录生成的session使其subject isAuthenticated认证失败)
        Collection<Session> activeSessions = enterpriseCacheSessionDAO.getActiveSessions();
        if (!CollectionUtils.isEmpty(activeSessions)) {
            Subject subject = SecurityUtils.getSubject();
            for (Session activeSession : activeSessions) {
                SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (simplePrincipalCollection != null) {
                    LoginUser sessionLoginUser = (LoginUser) simplePrincipalCollection.getPrimaryPrincipal();
                    //同一用户不同浏览器登录
                    if (loginId.equals(sessionLoginUser.getLoginId()) && DigestUtils.md5DigestAsHex(password.getBytes()).equals(sessionLoginUser.getPassword())) {
                        if (!subject.getSession().getId().equals(activeSession.getId())) {
                            enterpriseCacheSessionDAO.delete(activeSession);
                        }
                    }
                }
            }
        }
        return new SimpleAuthenticationInfo(loginUser, password, getName());
    }
}
