package com.zhuweiwei.springbootlearning0405.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @author zww
 * @date 2020-04-06 13:22
 * @description
 **/
public class TestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("testFilter....");
        chain.doFilter(request, response);
    }
}
