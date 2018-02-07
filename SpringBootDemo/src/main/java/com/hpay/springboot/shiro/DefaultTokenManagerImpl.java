package com.hpay.springboot.shiro;

import java.util.UUID;

/**
 * 默认token管理实现类
 * @author jwu
 */
public class DefaultTokenManagerImpl extends AbstractTokenManager {

	@Override
	public boolean checkToken(StatelessToken statelessToken) {
		return super.checkMemoryToken(statelessToken); 
	}

	@Override
	public String createStringToken(String userCode) {
		//创建32位uuid生成的token
		return UUID.randomUUID().toString().replace("-", ""); 
	}
	
}
