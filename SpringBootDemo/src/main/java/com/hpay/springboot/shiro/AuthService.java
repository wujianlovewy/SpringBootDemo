package com.hpay.springboot.shiro;

import java.util.List;

public interface AuthService {

	public List<String> selectRoles(String principal);
	
	public AuthUser getUserByUserCode(String userCode);
	
}
