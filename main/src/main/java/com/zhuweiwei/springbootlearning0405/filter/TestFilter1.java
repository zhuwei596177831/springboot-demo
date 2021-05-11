package com.zhuweiwei.springbootlearning0405.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author zww
 * @date 2020-04-06 13:22
 * @description
 **/
@WebFilter(urlPatterns = {"/testFilter1"})
public class TestFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("testFilter1....");
        chain.doFilter(request, response);
    }
}
