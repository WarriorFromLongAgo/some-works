package com.orhonit.ole.server.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.orhonit.ole.common.constants.UserConstants;
import com.orhonit.ole.server.filter.LogoutFilter;
import com.orhonit.ole.server.filter.RestfulFilter;
import com.orhonit.ole.sys.config.MyShiroRealm;

/**
 * shiro配置
 * 
 * @author caodw
 *
 */
@Configuration
public class ShiroConfiguration {

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/sys/login/**", "anon");
		filterChainDefinitionMap.put("/files/*", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		
		filterChainDefinitionMap.put("/sso/**", "anon");
		filterChainDefinitionMap.put("/api/**", "anon");
		
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setLoginUrl("/login.html");
		shiroFilterFactoryBean.setSuccessUrl("/index.html");

		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setRedirectUrl("/base/login.html");

		RestfulFilter restfulFilter = new RestfulFilter();

		shiroFilterFactoryBean.getFilters().put("authc", restfulFilter);
		shiroFilterFactoryBean.getFilters().put("logout", logoutFilter);

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager(EhCacheManager cacheManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setCacheManager(cacheManager);

		return securityManager;
	}

	@Bean
	public BaseShiroRealm myShiroRealm() {
		BaseShiroRealm baseShiroRealm = new BaseShiroRealm();
		baseShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());

		return baseShiroRealm;
	}

	/**
	 * 凭证匹配器
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(UserConstants.HASH_ITERATIONS);

		return hashedCredentialsMatcher;
	}

	/**
	 * Shiro生命周期处理器
	 * 
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
	 * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	 * 
	 * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}