package com.zhuweiwei.springbootlearning0405.locale;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author zww
 * @date 2020-04-12 00:31
 * @description
 **/
public class MyLocale implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = Locale.getDefault();
        String localeParam = request.getParameter("locale");
        if (!StringUtils.isEmpty(localeParam)) {
            String[] split = localeParam.split("_");
            return new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
