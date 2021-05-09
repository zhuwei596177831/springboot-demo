package shiroFilter;

import com.alibaba.fastjson.JSON;
import com.zhuweiwei.springbootlearning0405.bean.Result;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zww
 * @date 2020-05-03 15:45
 * @description
 **/
public class AccessVisitFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        return subject.getPrincipal() != null && (subject.isAuthenticated() || subject.isRemembered());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (isAjax(servletRequest)) {
            Result result = new Result(401, "未认证通过", null);
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setHeader("sessionStatus", "sessionTimeout");
            responseJson(result, httpServletResponse);
        } else {
            redirectToLogin(servletRequest, servletResponse);
        }
        return false;
    }

    private Boolean isAjax(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        return httpServletRequest.getHeader("x-requested-with") != null
                && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
    }

    private void responseJson(Result result, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(JSON.toJSONString(result));
    }
}
