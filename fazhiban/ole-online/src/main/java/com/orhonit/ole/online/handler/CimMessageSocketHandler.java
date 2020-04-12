package com.orhonit.ole.online.handler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
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
 * 消息推送
 * 
 * @author caodw
 *
 *
 */
@Slf4j(topic = "adminLogger")
@Component
public class CimMessageSocketHandler implements WebSocketHandler {
	
	public static ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("afterConnectionEstablished");
		
		User user = (User) session.getAttributes().get(UserConstants.CIM_USER);
		String username = user.getUsername();
		log.debug("cim新增连接{}:{}", user.getUsername());
		synchronized (username.intern()) {
			String cimUserKey = (String) session.getAttributes().get(UserConstants.CIM_USER_KEY);
			sessions.putIfAbsent(cimUserKey, session);
		}
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info("handleMessage");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.info("handleTransportError");
		removeSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("afterConnectionClosed");
		removeSession(session);
	}
	
	private void removeSession(WebSocketSession session) throws IOException {
		User user = (User) session.getAttributes().get(UserConstants.CIM_USER);
		String username = user.getUsername();
		synchronized (username.intern()) {
			if (session.isOpen()) {
				session.close();
			}
			String onlineUserKey = (String) session.getAttributes().get(UserConstants.CIM_USER_KEY);
			sessions.remove(onlineUserKey);
			log.debug("cim移除连接{}:{}", user.getNickname(), onlineUserKey);
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		log.info("supportsPartialMessages");
		return false;
	}

	public boolean sendMsg(Map<String, Object> map) {
		log.info("params is {}", map);
		
		Iterator<Entry<String, WebSocketSession>> iter = sessions.entrySet().iterator();
		boolean result = false;
		
		while ( iter.hasNext() ) {
			Entry<String, WebSocketSession> entry = iter.next();
			try {
				String username = (String)map.get("username");
				if ( username.equals(entry.getKey().substring(0, entry.getKey().indexOf(":")))) {
					log.info("send msessage content is {}", map.get("msg"));
					entry.getValue().sendMessage(new TextMessage(JsonUtil.toJson(map)));
					result = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
