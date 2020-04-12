package com.orhonit.ole.online.handler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.orhonit.ole.common.constants.UserConstants;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.sys.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 在线用户列表推送
 * 
 * @author caodw
 *
 *
 */
@Slf4j(topic = "adminLogger")
@Component
public class UserOnlineSocketHandler implements WebSocketHandler {

	/**
	 * 登录用户打开所有页面的连接
	 */
	public static ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		User user = (User) session.getAttributes().get(UserConstants.ONLINE_USER);
		String username = user.getUsername();
		synchronized (username.intern()) {
			String onlineUserKey = (String) session.getAttributes().get(UserConstants.ONLINE_USER_KEY);
			sessions.putIfAbsent(onlineUserKey, session);

			log.debug("在线列表：{}连接成功", user.getNickname());
		}
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		removeSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		removeSession(session);
	}

	private void removeSession(WebSocketSession session) throws IOException {
		User user = (User) session.getAttributes().get(UserConstants.ONLINE_USER);
		String username = user.getUsername();
		synchronized (username.intern()) {
			if (session.isOpen()) {
				session.close();
			}

			String onlineUserKey = (String) session.getAttributes().get(UserConstants.ONLINE_USER_KEY);
			sessions.remove(onlineUserKey);
			log.debug("在线列表：移除连接{}", user.getNickname());
		}
	}

	/**
	 * 推送在线列表
	 */
	@Async
	public void sendToAll() {
		Set<String> keys = sessions.keySet();
		keys.parallelStream().forEach(k -> {
			try {
				WebSocketSession session = sessions.get(k);
				if (session != null && session.isOpen()) {
					session.sendMessage(new TextMessage(JsonUtil.toJson(UserLoginSocketHandler.onlineUsers.values())));
				} else {
					sessions.remove(k);
					System.out.println(k);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
