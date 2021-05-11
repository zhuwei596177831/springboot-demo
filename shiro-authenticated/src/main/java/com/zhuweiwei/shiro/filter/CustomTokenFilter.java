package com.zhuweiwei.shiro.filter;

import com.zhuweiwei.shiro.customToken.CustomToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author 朱伟伟
 * @date 2021-05-11 23:27:53
 * @description
 */
public class CustomTokenFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String token = request.getParameter("token");
        return token.equals("1111111");
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new CustomToken(request.getParameter("token")));
        return false;
    }
}
