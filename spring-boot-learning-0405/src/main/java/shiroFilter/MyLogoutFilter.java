package shiroFilter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author zww
 * @date 2020-05-03 16:01
 * @description
 **/
public class MyLogoutFilter extends LogoutFilter {

    @Override
    public String getRedirectUrl() {
        return "login";
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        if (WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("GET")
                && servletPath.equals("/logout")) {
            subject.logout();
            issueRedirect(request, response, getRedirectUrl());
        }
        return false;
    }

}
