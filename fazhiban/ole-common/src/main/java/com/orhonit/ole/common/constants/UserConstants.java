package com.orhonit.ole.common.constants;

/**
 * 用户相关常量
 * 
 * @author caodw
 *
 */
public interface UserConstants {

	/** 加密次数 */
	int HASH_ITERATIONS = 3;

	String LOGIN_USER = "login_user";

	String USER_PERMISSIONS = "user_permissions";

	String ONLINE_USER = "online_user";

	String ONLINE_USER_KEY = "online_user_key";
	
	String CIM_USER = "cim_user";

	String CIM_USER_KEY = "cim_user_key";

	/** 登陆token(nginx中默认header无视下划线) */
	String LOGIN_TOKEN = "login-token";
}
