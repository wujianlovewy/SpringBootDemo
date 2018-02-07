package com.hpay.springboot.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class StatelessRealm extends AuthorizingRealm {

	private TokenManager tokenManager;  
	
	private AuthService authService;
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof StatelessToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 //根据用户名查找角色，请根据需求实现    
        String userCode = (String) principals.getPrimaryPrincipal();   
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();  
        List<String> selectRoles = authService.selectRoles(userCode);  
        authorizationInfo.addRoles(selectRoles);  
        return authorizationInfo;  
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		 	StatelessToken statelessToken = (StatelessToken)token;  
         
	        String userCode = (String)statelessToken.getPrincipal();  
	          
	        checkUserExists(userCode);  
	          
	        String credentials = (String)statelessToken.getCredentials();  
	        boolean checkToken = tokenManager.checkToken(statelessToken);  
	        if (checkToken) {  
	            return new SimpleAuthenticationInfo(userCode, credentials, super.getName());  
	        }else{  
	            throw new AuthenticationException("token认证失败");  
	        }  
	}
	
	private void checkUserExists(String userCode) throws AuthenticationException {  
        Object principal = authService.getUserByUserCode(userCode);  
        if(principal == null){  
            throw new UnknownAccountException("userCode "+userCode+" wasn't in the system");  
        }  
    } 

	public TokenManager getTokenManager() {
		return tokenManager;
	}

	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	
}
