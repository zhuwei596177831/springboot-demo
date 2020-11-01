package com.zhuweiwei.springbootlearning0405.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhuweiwei.springbootlearning0405.filter.TestFilter;
import com.zhuweiwei.springbootlearning0405.locale.MyLocale;
import com.zhuweiwei.springbootlearning0405.servlet.TestServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zww
 * @date 2020-04-05 16:40
 * @description
 **/
@Configuration
@ServletComponentScan(basePackages = {
        "com.zhuweiwei.springbootlearning0405.filter",
        "com.zhuweiwei.springbootlearning0405.listener"
})
public class MvcConfiguration implements WebMvcConfigurer {

    @Bean
    @Lazy
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new TestServlet());
        servletRegistrationBean.addUrlMappings("/testServlet");
        return servletRegistrationBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocale();
    }

    @Bean
    @Lazy
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new TestFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/testFilter"));
        return filterRegistrationBean;
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        List<HttpMessageConverter<?>> httpMessageConverterList = converters;
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteNullListAsEmpty);
//        List<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.APPLICATION_JSON);
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
//        converters.clear();
//        converters.add(fastJsonHttpMessageConverter);
//        converters.addAll(httpMessageConverterList);
//    }

//    @Bean
//    public HttpMessageConverter httpMessageConverter() {
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteNullListAsEmpty);
//        List<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.APPLICATION_JSON);
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
//        return fastJsonHttpMessageConverter;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyHandlerInterceptor()).excludePathPatterns("/static/**");
    }

}
