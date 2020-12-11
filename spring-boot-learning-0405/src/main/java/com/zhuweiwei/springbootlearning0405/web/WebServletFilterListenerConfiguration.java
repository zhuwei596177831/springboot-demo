package com.zhuweiwei.springbootlearning0405.web;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author 朱伟伟
 * @date 2020-12-11 17:59:12
 * @description
 */
@Configuration(proxyBeanMethods = false)
@ServletComponentScan(basePackageClasses = {WebServletFilterListenerConfiguration.class})
public class WebServletFilterListenerConfiguration {
}
