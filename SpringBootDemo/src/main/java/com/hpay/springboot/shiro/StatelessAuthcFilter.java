package com.hpay.springboot.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class StatelessAuthcFilter extends AccessControlFilter {

	@Autowired  
    private TokenManager tokenManager;  
      
    public TokenManager getTokenManager() {  
        return tokenManager;  
    }  
  
    public void setTokenManager(TokenManager tokenManager) {  
        this.tokenManager = tokenManager;  
    }  
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object arg2) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;  
        System.out.println("拦截到的url:" + httpRequest.getRequestURL().toString());  
       
        //前端token授权信息放在请求头中传入  
        String authorization = RequestUtil.newInstance().getRequestHeader(  
                (HttpServletRequest) request, "authorization");  
        if (StringUtils.isEmpty(authorization)) {  
            onLoginFail(response, "请求头不包含认证信息authorization");  
            return false;  
        }  
        // 获取无状态Token  
        StatelessToken accessToken = tokenManager.getToken(authorization);  
        try {  
            // 委托给Realm进行登录  
            getSubject(request, response).login(accessToken);  
        } catch (Exception e) {  
        	System.out.println("auth error:" + e.getMessage());  
            onLoginFail(response, "auth error:" + e.getMessage()); // 6、登录失败  
            return false;  
        }  
        // 通过isPermitted 才能调用doGetAuthorizationInfo方法获取权限信息  
        getSubject(request, response).isPermitted(httpRequest.getRequestURI());  
        return true; 
	}

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1)
			throws Exception {
		return false;
	}
	
	//登录失败时默认返回401状态码    
    private void onLoginFail(ServletResponse response,String errorMsg) throws IOException {    
      HttpServletResponse httpResponse = (HttpServletResponse) response;    
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    
      httpResponse.setContentType("text/html");  
      httpResponse.setCharacterEncoding("utf-8");  
      httpResponse.getWriter().write(errorMsg);    
      httpResponse.getWriter().close();  
    }    

}
