package com.hpay.springboot.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import net.sf.ehcache.CacheManager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 * @author jwu
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * token管理类
	 */
	@Bean  
    public TokenManager tokenManager(CacheManager cacheManager, ShiroProperties shiroProperties){  
         System.out.println("ShiroConfig.getTokenManager()");  
         //默认的token管理实现类 32位uuid  
         DefaultTokenManagerImpl tokenManager = new DefaultTokenManagerImpl();  
         //token失效时间  
         tokenManager.setExpirateTime(shiroProperties.getExpirateTime());  
          
         //用户token委托给ehcache管理  
         EhcacheTokenServiceImpl userTokenService = new EhcacheTokenServiceImpl();  
         userTokenService.setCacheManager(cacheManager);  
         tokenManager.setUserTokenService(userTokenService);  
         
         // 安全的jwttoken方式 不用担心token被拦截            
         /*RaFilterJwtTokenManagerImpl tokenManager1 = new RaFilterJwtTokenManagerImpl();  
         JwtUtil jwtUtil = new JwtUtil();  DefaultWebSecurityManager
         jwtUtil.setProfiles(bootProperties.getKey());  
         tokenManager1.setJwtUtil(jwtUtil);  
         tokenManager1.setExpirateTime(bootProperties.getExpirateTime());  
         EhCacheLoginFlagHelper ehCacheLoginFlagHelper = new EhCacheLoginFlagHelper();  
         ehCacheLoginFlagHelper.setCacheManager(cacheManager);  
         tokenManager1.setLoginFlagOperHelper(ehCacheLoginFlagHelper);  
         tokenManager1.setUserTokenOperHelper(ehCacheUserTokenHelper); */ 
 
         return tokenManager;  
    }  
	
	/*@Bean
    public CacheManager ehCacheManager(){
        System.out.println("ShiroConfiguration.getEhCacheManager()");
        return  CacheManager.newInstance("src/main/resources/ehcache.xml");
    }*/

	
    /** 
     * 无状态域 
     * @return 
     */  
    @Bean  
    public StatelessRealm statelessRealm(TokenManager tokenManager,@Qualifier("authService") AuthService authService){  
    	 System.out.println("ShiroConfig.getStatelessRealm()");  
         StatelessRealm realm = new StatelessRealm();  
         realm.setTokenManager(tokenManager);  
         realm.setAuthService(authService);
         return realm;  
    } 
    
    /** 
     * 会话管理类 禁用session 
     * @return 
     */  
    @Bean  
    public DefaultSessionManager defaultSessionManager(){  
    	 System.out.println("ShiroConfig.getDefaultSessionManager()");  
         DefaultSessionManager manager = new DefaultSessionManager();  
         manager.setSessionValidationSchedulerEnabled(false);  
         return manager;  
    }  
    
    /** 
     * 安全管理类 
     * @param statelessRealm 
     * @return 
     */  
    @Bean  
    public SecurityManager defaultWebSecurityManager(StatelessRealm statelessRealm){  
    	 System.out.println("ShiroConfig.getDefaultWebSecurityManager()");  
         DefaultWebSecurityManager manager = new DefaultWebSecurityManager();  
           
         //禁用sessionStorage  
         DefaultSubjectDAO de = (DefaultSubjectDAO)manager.getSubjectDAO();  
         DefaultSessionStorageEvaluator defaultSessionStorageEvaluator =(DefaultSessionStorageEvaluator)de.getSessionStorageEvaluator();  
         defaultSessionStorageEvaluator.setSessionStorageEnabled(false);  
           
         manager.setRealm(statelessRealm);  
           
         //无状态主题工程，禁止创建session  
         StatelessDefaultSubjectFactory statelessDefaultSubjectFactory = new StatelessDefaultSubjectFactory();  
         manager.setSubjectFactory(statelessDefaultSubjectFactory);  
           
         manager.setSessionManager(defaultSessionManager());  
         //设置了SecurityManager采用使用SecurityUtils的静态方法 获取用户等  
         SecurityUtils.setSecurityManager(manager);  
         return manager;  
    }  
    
    /** 
     * Shiro生命周期处理 
     * @return 
     */  
    @Bean  
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){  
    	 System.out.println("ShiroConfig.getLifecycleBeanPostProcessor()");  
         return new LifecycleBeanPostProcessor();  
    }
    
    /** 
     * 身份验证过滤器 
     * @param manager 
     * @param tokenManager 
     * @return 
     */  
    @Bean  
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,TokenManager tokenManager){  
    	 System.out.println("ShiroConfig.getShiroFilterFactoryBean()");  
         ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();  
         bean.setSecurityManager(securityManager);  
         Map<String,Filter> filters = new HashMap<String,Filter>();  
         //无需增加 shiro默认会添加该filter  
         //filters.put("anon", anonymousFilter());   
           
         //无状态授权过滤器   
         //特别注意！自定义的StatelessAuthcFilter   
         //不能声明为bean 否则shiro无法管理该filter生命周期，该过滤器会执行其他过滤器拦截过的路径  
         //这种情况通过普通spring项目集成shiro不会出现，boot集成会出现 
         StatelessAuthcFilter statelessAuthcFilter = statelessAuthcFilter(tokenManager);  
         filters.put("statelessAuthc", statelessAuthcFilter);  
         bean.setFilters(filters);  
         //注意是LinkedHashMap 保证有序  
         Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
           
         //1， 相同url规则，后面定义的会覆盖前面定义的(执行的时候只执行最后一个)。  
         //2， 两个url规则都可以匹配同一个url，只执行第一个  
         filterChainDefinitionMap.put("/html/**", "anon");  
         filterChainDefinitionMap.put("/resource/**", "anon");  
         filterChainDefinitionMap.put("/login", "anon");  
         filterChainDefinitionMap.put("/login/**", "anon");  
         filterChainDefinitionMap.put("/favicon.ico", "anon");  
         filterChainDefinitionMap.put("/**", "statelessAuthc");  
           
         bean.setFilterChainDefinitionMap(filterChainDefinitionMap);  
           
         //字符串方式创建过滤链 \n换行   
//       String s = "/resource/**=anon\n/html/**=anon\n/login/**=anon\n/login=anon\n/**=statelessAuthc";  
//       bean.setFilterChainDefinitions(s);  
         return bean;  
    }
    
    /**  
     *     
     * @Function: ShiroConfig::anonymousFilter  
     * @Description: 该过滤器无需增加 shiro默认会添加该filter  
     * @return  
     * @version: v1.0.0  
     * @author: wuj  
     * @date: 2017年5月8日 下午5:39:10   
     * @history:  
     * Date         Author          Version            Description  
     *-------------------------------------------------------------  
     */  
    public AnonymousFilter anonymousFilter(){  
    	System.out.println("ShiroConfig.anonymousFilter()");  
         return new AnonymousFilter();  
    }  
    
    /** 
     *  
     * @Function: ShiroConfig::statelessAuthcFilter 
     * @Description:  无状态授权过滤器 注意不能声明为bean 否则shiro无法管理该filter生命周期,
     *                 该过滤器会执行其他过滤器拦截过的路径 
     * @param tokenManager 
     * @return 
     * @version: v1.0.0 
     * @author: wuj
     * @date: 2017年5月8日 下午5:38:55  
     * @history: 
     * Date         Author          Version            Description 
     *------------------------------------------------------------- 
     */  
    public StatelessAuthcFilter statelessAuthcFilter(TokenManager tokenManager){  
    	System.out.println("ShiroConfig.statelessAuthcFilter()");  
        StatelessAuthcFilter statelessAuthcFilter = new StatelessAuthcFilter();  
        statelessAuthcFilter.setTokenManager(tokenManager);  
        return statelessAuthcFilter;  
   } 
	
}
