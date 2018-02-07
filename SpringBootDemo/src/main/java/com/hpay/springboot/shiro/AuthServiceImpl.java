package com.hpay.springboot.shiro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("authService")
@CacheConfig(cacheNames="auth")
public class AuthServiceImpl implements AuthService{

	@Override
	@Cacheable
	public List<String> selectRoles(String principal) {
		List<String> roles = new ArrayList<String>();  
        if("admin".equals(principal)){  
            roles.add("admin");  
            roles.add("vistor");  
        }  
        return roles;  
	}

	@Override
	@Cacheable
	public AuthUser getUserByUserCode(String userCode) {
		if("888".equals(userCode)){
			AuthUser user = new AuthUser(userCode);
			user.setPwd("888");
			return user;
		}
		return null;
	}

}
