package com.hpay.springboot.shiro;

import javax.servlet.http.HttpServletRequest;


public class RequestUtil {
	private RequestUtil(){}
	
	public static RequestUtil newInstance(){
		return new RequestUtil();
	}
	
	public String getRequestHeader(HttpServletRequest request, String headerName){
		return request.getHeader(headerName);
	}
}
