package com.orhonit.ole.warn.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserIdToken extends UsernamePasswordToken{

	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
}
