package com.zhuweiwei.shiro.realm;

import com.zhuweiwei.entity.LoginUser;
import com.zhuweiwei.service.LoginUserService;
import com.zhuweiwei.util.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${md5Param.salt}")
    private String salt;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Autowired(required = false)
    LoginUserService loginUserService;
    //    @Autowired(required = false)
//    EnterpriseCacheSessionDAO enterpriseCacheSessionDAO;
    @Autowired(required = false)
    DefaultSessionManager defaultSessionManager;

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
//        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(loginUser.getPassword())) {
//            throw new IncorrectCredentialsException();
//        }
        //同一用户不同浏览器登录  互踢功能 原理：(删除之前用户登录生成的session使其subject isAuthenticated认证失败)
        //同一浏览器不同用户登录 给出提示 原理：(同一浏览器 认证时 还未重新写出cookie header里是之前的cookie
        // 此处获取的subject和SimpleSession是同一个)
//        Collection<Session> activeSessions = enterpriseCacheSessionDAO.getActiveSessions();
        //默认的MemorySessionDAO
        SessionDAO sessionDAO = defaultSessionManager.getSessionDAO();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        if (!CollectionUtils.isEmpty(activeSessions)) {
            Subject subject = SecurityUtils.getSubject();
            for (Session activeSession : activeSessions) {
                SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (simplePrincipalCollection != null) {
                    LoginUser sessionLoginUser = (LoginUser) simplePrincipalCollection.getPrimaryPrincipal();
                    //同一用户不同浏览器登录
                    if (loginId.equals(sessionLoginUser.getLoginId()) && PasswordHelper.md5(password, null, 3).equals(sessionLoginUser.getPassword())) {
                        if (!activeSession.getId().equals(subject.getSession().getId())) {
//                            enterpriseCacheSessionDAO.delete(activeSession);
                            sessionDAO.delete(activeSession);
                        }
                    }
                    //同一浏览器不同用户登录
                    else {
                        if (activeSession.getId().equals(subject.getSession().getId())) {
                            throw new RuntimeException("请先退出用户：" + sessionLoginUser.getUserName() + "后再登录");
                        }
                    }
                }
            }
        }
//        return new SimpleAuthenticationInfo(loginUser, DigestUtils.md5DigestAsHex(password.getBytes()), getName());
        return new SimpleAuthenticationInfo(loginUser, loginUser.getPassword(), getName());
    }
}
