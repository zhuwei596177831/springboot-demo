package com.zhuweiwei.springbootlearning0405.configuration;

import com.zhuweiwei.springbootlearning0405.listener.MySessionListener;
import com.zhuweiwei.springbootlearning0405.realm.UserNamePasswordRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import shiroFilter.AccessVisitFilter;
import shiroFilter.MyLogoutFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

/**
 * @author zww
 * @date 2020-05-03 14:18
 * @description
 **/
@Configuration
public class ShiroConfiguration {

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        simpleCookie.setName("rememberMeCookie");
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间30天 ,单位秒
        simpleCookie.setMaxAge(2592000);
        //-1：浏览器关闭即失效
//        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager());
        Collection<Realm> realms = new ArrayList<>();
        realms.add(userNamePasswordRealm());
        defaultWebSecurityManager.setRealms(realms);
        EhCacheManager shiroeEhCacheManager = new EhCacheManager();
        shiroeEhCacheManager.setCacheManagerConfigFile("classpath:my-ehcache.xml");
        defaultWebSecurityManager.setCacheManager(shiroeEhCacheManager);
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        filterChainDefinitionMap.put("/file/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/addLoginUser", "anon");
        filterChainDefinitionMap.put("/logout", "myLogout");
        filterChainDefinitionMap.put("/order/**", "authc");
        filterChainDefinitionMap.put("/permissionTest", "authc,perms[user:add]");
        filterChainDefinitionMap.put("/**", "accessVisit");
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        AccessVisitFilter accessVisitFilter = new AccessVisitFilter();
        MyLogoutFilter myLogoutFilter = new MyLogoutFilter();
        filters.put("accessVisit", accessVisitFilter);
        filters.put("myLogout", myLogoutFilter);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        return shiroFilterFactoryBean;
    }

    @Bean
    public UserNamePasswordRealm userNamePasswordRealm() {
        return new UserNamePasswordRealm();
    }

    @Bean
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
//        defaultWebSessionManager.setGlobalSessionTimeout(60000);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionValidationInterval(600000);
//        defaultWebSessionManager.setSessionValidationInterval(10000);
        Collection<SessionListener> sessionListeners = new ArrayList<>();
        MySessionListener mySessionListener = new MySessionListener();
        sessionListeners.add(mySessionListener);
        defaultWebSessionManager.setSessionListeners(sessionListeners);
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO);
        return defaultWebSessionManager;
    }

    /**
     * cacheManager多次创建报错问题：
     * 修改的地方一共有三处，它们是：
     * 配置EhcacheManagerFactoryBean并设置其为共享模式，
     * 配置DefaultAdvisorAutoProxyCreator，让Spring 来管理Shiro的Bean生命周期，
     * 配置lifecycleBeanPostProcessor，并让DefaultAdvisorAutoProxyCreator依赖于此对象。
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    /**
     * 添加LifecycleBeanPostProcessor配置项，LifecycleBeanPostProcessor将统一管理Initializable和Destroyable的实现类，
     * 从而达到统一管理Shiro Bean的生命周期的目的
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * DefaultAdvisorAutoProxyCreator的作用是动态代理Shiro的事务，最终将事务交由Spring进行统一管理。
     * 此配置项需要依赖于LifecycleBeanPostProcessor
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
