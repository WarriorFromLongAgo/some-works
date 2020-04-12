package com.orhonit.ole.cim.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farsunset.cim.sdk.server.model.Message;
import com.orhonit.ole.cim.push.SystemMessagePusher;
import com.orhonit.ole.cim.util.BeanUtils;
import com.orhonit.ole.cim.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {

	private static String result;

	/**
	 * 关于http参数获取， struts2 的模型驱动 比如 http 参数 sender=xiaomao&receiver=xiaogou
	 * struts自动会将参数的值
	 * 存入getModel()返回的对象的对应属性中，即xiaomao会存入message.sender属性,xiaogou会存入message
	 * .receiver属性
	 * 省去了request.getParameter("sender")方式获取参数，，如果参数名在getModel()返回的对象中不存在
	 * ，则需要用request.getParameter()获取 其他相关*Action.java中 同理，这里做统一说明!
	 * 
	 * @param msg
	 *            推送的消息内容
	 * @param username
	 *            推送的用户名
	 * @param messageType
	 *            推送的界面类型
	 * @param caseNum
	 *            案件或预警id
	 **/
	@RequestMapping("/send")
	public static String send(String msg, String username, String messageType, String caseNum) throws Exception {
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			Message message = checkParams(msg, username, messageType, caseNum);
			datamap.put("code", 200);
			SystemMessagePusher push = BeanUtils.getBean(SystemMessagePusher.class);
			push.push(message);
			data.put("id", message.getMid());
			data.put("createTime", String.valueOf(message.getTimestamp()));
			datamap.put("data", data);
		} catch (Exception e) {
			datamap.put("code", 500);
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/*
	 * mid 任务号id
	 */
	public static Message checkParams(String msg, String username, String messageType, String caseNum) {
		Message message = new Message();
		message.setContent(msg);
		message.setReceiver(username);
		message.setMid(messageType);
		message.setAction("action");
		message.setSender(caseNum);
		message.setMid(StringUtil.getUUID());
		return message;
	}
}
