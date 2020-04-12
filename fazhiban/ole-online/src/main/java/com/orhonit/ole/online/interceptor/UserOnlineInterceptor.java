package com.orhonit.ole.online.interceptor;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.orhonit.ole.common.constants.UserConstants;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

public class UserOnlineInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Exception exception) {
		super.afterHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, exception);
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Map<String, Object> paramMap) throws Exception {
		User user = UserUtil.getCurrentUser();
		paramMap.put(UserConstants.ONLINE_USER, user);
		paramMap.put(UserConstants.ONLINE_USER_KEY, user.getUsername() + ":" + UUID.randomUUID().toString());
		
		paramMap.put(UserConstants.CIM_USER, user);
		paramMap.put(UserConstants.CIM_USER_KEY, user.getUsername() + ":" + UUID.randomUUID().toString());

		return super.beforeHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, paramMap);
	}

}
