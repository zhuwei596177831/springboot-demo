package com.zhuweiwei.springbootlearning0405.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author 朱伟伟
 * @date 2020-12-11 17:13:01
 * @description
 */
public class CheckFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(CheckFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("######CheckFilter doFilter");
        chain.doFilter(request, response);
    }
}
