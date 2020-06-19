package com.scottxuan.web.shiro;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.AbstractSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : scottxuan
 */
public abstract class AbstractShiroConfig {
    @Value("${spring.redis.host:}")
    private String host;
    @Value("${spring.redis.port:}")
    private int port;
    @Value("${spring.redis.timeout:}")
    private int timeout;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.database:}")
    private Integer database;

    private static final int ENTRY_TIMES = 64;
    private static final String MD5 = "MD5";

    /**
     * 用户realm
     * @return
     */
    protected abstract AuthorizingRealm userRealm();
    protected AuthorizingRealm userRealm2() {
        return null;
    }

    protected ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager, Map<String, String> filterChain) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.getFilters().put("authc", new ScottxuanFormFilter());
        shiroFilterFactoryBean.getFilters().put("roles", new ScottxuanRolesFilter());
        shiroFilterFactoryBean.getFilters().put("perms", new ScottxuanPermissionsFilter());
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
        return shiroFilterFactoryBean;
    }

    @Bean
    protected SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(getModularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        AuthorizingRealm realm1 = userRealm();
        AuthorizingRealm realm2 = userRealm2();
        if (realm1!=null) {
            realm1.setCredentialsMatcher(hashedCredentialsMatcher());
            realms.add(realm1);
        }
        if (realm2!=null) {
            realm2.setCredentialsMatcher(hashedCredentialsMatcher());
            realms.add(realm1);
        }
        securityManager.setRealms(realms);
        //配置redis缓存
        securityManager.setCacheManager(cacheManager());
        //配置自定义session管理，使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean("shiroCacheManager")
    protected CacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        cacheManager.setPrincipalIdFieldName("userId");
        cacheManager.setExpire(getGlobalSessionOut().intValue() / 1000);
        return cacheManager;
    }

    @Bean
    protected RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":"+port);
        redisManager.setDatabase(database);
        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * 自定义sessionManager
     *
     * @return SessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setCacheManager(cacheManager());
        long timeout = getGlobalSessionOut();
        if (timeout > 0) {
            //全局会话超时时间（单位毫秒），默认30分钟
            sessionManager.setGlobalSessionTimeout(timeout);
        }
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean
    protected SessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        Long expire = getGlobalSessionOut() / 1000;
        sessionDAO.setExpire(expire.intValue() + 1800);
        return sessionDAO;
    }


    /**
     * 多realm验证,一个通过即可
     */
    protected Authenticator getModularRealmAuthenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    protected Long getGlobalSessionOut() {
        return AbstractSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT * 4;
    }

//    /**
//     * shiro生命周期
//     */
//    @Bean("lifecycleBeanPostProcessor")
//    protected LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
     */
    @Bean("authorizationAttributeSourceAdvisor")
    @DependsOn({"securityManager"})
    protected AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean("advisorAutoProxyCreator")
    protected DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码; ）
     *
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(MD5);
        hashedCredentialsMatcher.setHashIterations(ENTRY_TIMES);
        return hashedCredentialsMatcher;
    }
}
