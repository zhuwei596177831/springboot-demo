package com.zhuweiwei.springbootlearning0405.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 朱伟伟
 * @date 2020-08-19 21:05:26
 * @description
 **/
@WebFilter(urlPatterns = {"/file/uploadFile"})
public class FileFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        ServletInputStream servletInputStream = request.getInputStream();
//        BufferedReader bufferedReader = request.getReader();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println(httpServletRequest.getClass());
        System.out.println(httpServletRequest.getContentType());
        System.out.println(httpServletRequest.getParameter("name"));
        System.out.println(httpServletRequest.getParameter("email"));
        chain.doFilter(request, response);
    }
}
