package com.zhuweiwei.shiro.config;

import com.zhuweiwei.shiro.filter.AccessVisitFilter;
import com.zhuweiwei.shiro.filter.CustomTokenFilter;
import com.zhuweiwei.shiro.filter.MyLogoutFilter;
import com.zhuweiwei.shiro.MySessionListener;
import com.zhuweiwei.shiro.realm.CustomTokenRealm;
import com.zhuweiwei.shiro.realm.UserNamePasswordRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
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
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringValueResolver;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author zww
 * @date 2020-05-03 14:18
 * @description
 **/
@Configuration
public class ShiroConfiguration implements EmbeddedValueResolverAware {

    private int hashIterations;

    /**
     * @author: 朱伟伟
     * @date: 2021-05-09 19:11
     * @description: 自定义 {@link RememberMeManager}{@link SimpleCookie}
     * 用于记住我登录 shiro默认有提供
     * @see DefaultWebSecurityManager
     **/
    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("shiroRememberMeCookie");
        simpleCookie.setHttpOnly(true);
        //自定义记住我登录的cookie生效时间30天 ,单位秒
        simpleCookie.setMaxAge(2592000);
//        -1：浏览器关闭即失效
//        simpleCookie.setMaxAge(-1);
        cookieRememberMeManager.setCookie(simpleCookie);
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-05-09 20:33
     * @description: session持久化 用于同一用户不同浏览器登录  互踢功能
     **/
    @Bean
    public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return enterpriseCacheSessionDAO;
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
     * @author: 朱伟伟
     * @date: 2021-05-09 22:23
     * @description: 设置会话cookie超时时间和session超时时间后，关闭浏览器重新打开  依然可以进入目标页面
     * 相当于间接的实现了记住我功能
     **/
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //自定义shiro管理回话的cookie 默认有提供 名称：JSESSIONID 存活时间 -1 浏览器关闭 即失效
        SimpleCookie shiroSessionId = new SimpleCookie("shiroSessionIdCookie");
//        shiroSessionId.setMaxAge(2 * 60);//两分钟
        defaultWebSessionManager.setSessionIdCookie(shiroSessionId);
        //设置会话超时时间 默认30分钟
//        defaultWebSessionManager.setGlobalSessionTimeout(2 * 60 * 1000);//两分钟
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionValidationInterval(600000);
//        defaultWebSessionManager.setSessionValidationInterval(10000);
        Collection<SessionListener> sessionListeners = new ArrayList<>();
        MySessionListener mySessionListener = new MySessionListener();
        sessionListeners.add(mySessionListener);
        defaultWebSessionManager.setSessionListeners(sessionListeners);
        defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO());
        return defaultWebSessionManager;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager());
        Collection<Realm> realms = new ArrayList<>();
        realms.add(userNamePasswordRealm());
        realms.add(new CustomTokenRealm());
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
        filterChainDefinitionMap.put("/systemToken", "customTokenFilter");
        filterChainDefinitionMap.put("/testJackson/**", "anon");
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
        CustomTokenFilter customTokenFilter = new CustomTokenFilter();
        filters.put("accessVisit", accessVisitFilter);
        filters.put("myLogout", myLogoutFilter);
        filters.put("customTokenFilter", customTokenFilter);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        return shiroFilterFactoryBean;
    }

    @Bean
    public UserNamePasswordRealm userNamePasswordRealm() {
        UserNamePasswordRealm userNamePasswordRealm = new UserNamePasswordRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME);
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        userNamePasswordRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userNamePasswordRealm;
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

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex("000000".getBytes()).getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex("000000".getBytes()));
        System.out.println(new SimpleHash(Md5Hash.ALGORITHM_NAME, "000000", null, 1).toHex());
        System.out.println(new SimpleHash(Md5Hash.ALGORITHM_NAME, "000000", null, 2).toHex());
        System.out.println(new SimpleHash(Md5Hash.ALGORITHM_NAME, "000000", null, 3).toHex());
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        hashIterations = Integer.parseInt(Objects.requireNonNull(resolver.resolveStringValue("${md5Param.hashIterations}")));
    }
}
