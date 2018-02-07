package com.hpay.springboot.shiro;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractTokenManager implements TokenManager {

	protected UserTokenService userTokenService;

	protected String userTokenPrefix = "token_";

	protected long expirateTime;

	public abstract String createStringToken(String userCode);

	@Override
	public StatelessToken createToken(String userCode) {
		StatelessToken tokenModel = null;
		String token = userTokenService
				.getUserToken(userTokenPrefix + userCode);
		if (StringUtils.isEmpty(token)) {
			token = createStringToken(userCode);
		}
		userTokenService.updateUserToken(userTokenPrefix + userCode, token,
				expirateTime);
		tokenModel = new StatelessToken(userCode, token);
		return tokenModel;
	}

	protected boolean checkMemoryToken(StatelessToken model) {
		if (model == null) {
			return false;
		}
		String userCode = (String) model.getPrincipal();
		String credentials = (String) model.getCredentials();
		String token = userTokenService
				.getUserToken(userTokenPrefix + userCode);
		if (token == null || !credentials.equals(token)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean check(String authentication) {
		StatelessToken token = getToken(authentication);  
        if(token == null){  
            return false;  
        }  
        return checkMemoryToken(token);  
	}

	@Override
	public StatelessToken getToken(String authentication) {
		if (StringUtils.isEmpty(authentication)) {
			return null;
		}
		String[] au = authentication.split("_");
		if (au.length <= 1) {
			return null;
		}
		String userCode = au[0];
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < au.length; i++) {
			sb.append(au[i]);
			if (i < au.length - 1) {
				sb.append("_");
			}
		}
		return new StatelessToken(userCode, sb.toString());
	}

	@Override
	public void deleteToken(String userCode) {
		this.userTokenService.deleteUserToken(userCode);
	}

	public UserTokenService getUserTokenService() {
		return userTokenService;
	}

	public void setUserTokenService(UserTokenService userTokenService) {
		this.userTokenService = userTokenService;
	}

	public String getUserTokenPrefix() {
		return userTokenPrefix;
	}

	public void setUserTokenPrefix(String userTokenPrefix) {
		this.userTokenPrefix = userTokenPrefix;
	}

	public long getExpirateTime() {
		return expirateTime;
	}

	public void setExpirateTime(long expirateTime) {
		this.expirateTime = expirateTime;
	}
	
}
