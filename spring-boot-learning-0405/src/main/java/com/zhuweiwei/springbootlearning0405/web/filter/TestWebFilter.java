package com.zhuweiwei.springbootlearning0405.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author 朱伟伟
 * @date 2020-12-11 18:00:43
 * @description
 */
@WebFilter(filterName = "testWebFilter", urlPatterns = {"/testWebFilter"})
public class TestWebFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(TestWebFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("######TestWebFilter doFilter");
        chain.doFilter(request, response);
    }
}
