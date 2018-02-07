package com.hpay.springboot.shiro;

import org.apache.shiro.authc.AuthenticationToken;

//StatelessToken令牌类
public class StatelessToken implements AuthenticationToken {

	private static final long serialVersionUID = 2912596949124555971L;

	private String userCode;

	private String token;

	public StatelessToken(String userCode, String token) {
		this.userCode = userCode;
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return userCode;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	public String getUserCode() {
		return userCode;
	}

	public String getToken() {
		return token;
	}

}
