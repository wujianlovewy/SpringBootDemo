package com.hpay.springboot.shiro;

import java.io.Serializable;

/**
 * http://www.lcgblog.com/springmvcmybatisshiroresttemplate的实现客户端无状态验证及访问控制
 * http://blog.csdn.net/v2sking/article/details/71941530
 * @author wuj
 *
 */
public class AuthUser implements Serializable{

	private static final long serialVersionUID = -3277024798149713919L;

	private String userCode;
	
	private String pwd;
	
	public AuthUser(){}
	
	public AuthUser(String userCode){
		this.userCode = userCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
