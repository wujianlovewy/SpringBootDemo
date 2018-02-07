package com.hpay.springboot.shiro;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehcache缓存用户token实现类
 * @author jwu
 *
 */
public class EhcacheTokenServiceImpl implements UserTokenService {
	
	/** 
     * 对应ehcache.xml cache Name 
     */  
    private String userTokenCacheName ="userTokenCache";  
      
    /** 
     * ehcache缓存管理器 
     */  
    private CacheManager cacheManager;  
      
    @SuppressWarnings("rawtypes")
	public String getUserToken(String userCode){  
        Cache cache = getUserTokenCache();  
        if (cache == null) {  
            return null;  
        }else{  
            Element element = cache.get(userCode);  
            List keys = cache.getKeys();  
            for (Object object : keys) {  
                System.out.println(object);  
            }  
            if(element == null){  
                return null;  
            }else{  
                Object objectValue = element.getObjectValue();  
                if(objectValue == null){  
                    return null;  
                }else{  
                    return (String)objectValue;  
                }  
            }  
        }  
    }  
      
    public Cache getUserTokenCache(){  
        Cache cache = cacheManager.getCache(userTokenCacheName);  
        return cache;  
    }  
    
    @SuppressWarnings("rawtypes")
    public void updateUserToken(String userCode,String token,long seconds){  
        Cache cache = getUserTokenCache();  
        Element e = new Element(userCode, token);  
        e.setTimeToLive(new Long(seconds).intValue());  
        cache.put(e);  
        List keys = cache.getKeys();  
        for (Object object : keys) {  
            System.out.println(object);  
        }  
    }  
      
    public void deleteUserToken(String userCode){  
        Cache cache = getUserTokenCache();  
        cache.remove(userCode);  
    }  
  
    public String getUserTokenCacheName() {  
        return userTokenCacheName;  
    }  
  
    public void setUserTokenCacheName(String userTokenCacheName) {  
        this.userTokenCacheName = userTokenCacheName;  
    }  
  
    public CacheManager getCacheManager() {  
        return cacheManager;  
    }  
  
    public void setCacheManager(CacheManager cacheManager) {  
        this.cacheManager = cacheManager;  
    } 

}
