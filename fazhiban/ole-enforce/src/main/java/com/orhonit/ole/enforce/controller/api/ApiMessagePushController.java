package com.orhonit.ole.enforce.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farsunset.cim.sdk.server.model.Message;
import com.orhonit.ole.cim.push.SystemMessagePusher;
import com.orhonit.ole.cim.util.BeanUtils;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.enforce.entity.MessagePushEntity;
import com.orhonit.ole.enforce.repository.MessagePushRepository;
import com.orhonit.ole.enforce.service.push.MessagePushService;
import com.orhonit.ole.online.handler.CimMessageSocketHandler;
import com.orhonit.ole.online.handler.UserLoginSocketHandler;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@RestController
@RequestMapping("/api/message")
@Slf4j
public class ApiMessagePushController {

	@Autowired
	private MessagePushService messagePushService;

	private static MessagePushRepository messagePushRepository;
	
	@Autowired
	public ApiMessagePushController(MessagePushRepository messagePushRepository2) {
		ApiMessagePushController.messagePushRepository = messagePushRepository2;
	}

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
	 *            案件、预警、评查
	 **/
	@RequestMapping("/send")
	public static String send(String msg, String username, String messageType, String caseNum) throws Exception {
		String id = UUID.randomUUID().toString();
		try {
			MessagePushEntity messagePushEntity = new MessagePushEntity();
			messagePushEntity.setId(id);
			messagePushEntity.setPush_content(msg);
			messagePushEntity.setPush_person(username);
			messagePushEntity.setCover_person(username);
			messagePushEntity.setInterface_type(Integer.valueOf(messageType));
			messagePushEntity.setModular_type(1);
			messagePushEntity.setModular_id(caseNum);
			messagePushEntity.setPush_success(1); // 1, 未发送  , // 2 发送成功
			messagePushEntity.setPc_push_success(1); // 1, 未发送  , // 2 发送成功
			messagePushEntity.setCreate_time(new Date());
			messagePushEntity.setUpdate_time(new Date());
			messagePushRepository.save(messagePushEntity);

			Message message = checkParams(msg, username, messageType, caseNum, id);
			SystemMessagePusher push = BeanUtils.getBean(SystemMessagePusher.class);
			push.push(message);
			// pc push start
			CimMessageSocketHandler cimMessageSocketHandler = SpringContext.getBean(CimMessageSocketHandler.class);
			
			Map<String, Object> map = new HashMap<>();
			map.put("msg", msg);
			map.put("username", username);
			map.put("messageType", messageType);
			map.put("caseNum", caseNum);
			boolean sendFlag = cimMessageSocketHandler.sendMsg(map);
			if ( sendFlag ) {
				messagePushEntity.setPc_push_success(2);
				messagePushRepository.save(messagePushEntity);
			}
			// pc push end
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/*
	 * mid 任务号id
	 */
	public static Message checkParams(String msg, String username, String messageType, String caseNum, String id) {
		Message message = new Message();
		message.setContent(msg);
		message.setReceiver(username);
		message.setMid(messageType);
		message.setAction("action");
		message.setFormat(caseNum);
		message.setExtra(id);
		return message;
	}

	/**
	 * 消息推送成功后更新标识
	 * 
	 * @param id
	 *            消息推送id
	 * @return
	 */
	@RequestMapping("/receivedPush")
	public Result<Object> receivedPush(@RequestParam(value = "id", required = false) String id) {
		if (StringUtils.isEmpty(id)) {
			// 用户名为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "id为空");
		}
		MessagePushEntity messagePushEntity = messagePushRepository.findOne(id);
		if (StringUtils.isEmpty(messagePushEntity.getId())) {
			// 此条消息暂无
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "此条消息暂无！");
		}
		messagePushService.updatePush(id, 2);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "消息推送接收成功！");
	}

	/**
	 * 拉取离线消息
	 * 
	 * @param id
	 *            消息推送id
	 * @return
	 */
	@RequestMapping("/offlinePush")
	public Result<Object> offlinePush(@RequestParam(value = "username", required = false) String username) {
		if (StringUtils.isEmpty(username)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "证件号为空");
		}
		List<MessagePushEntity> messagePushEntities = messagePushService.offlinePush(username);
		if (messagePushEntities == null || messagePushEntities.size() <= 0) {
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "暂无离线消息！");
		}
		for (MessagePushEntity messagePushEntity : messagePushEntities) {
			Message message = checkParams(messagePushEntity.getPush_content(), username,
					String.valueOf(messagePushEntity.getInterface_type()), messagePushEntity.getModular_id(),
					messagePushEntity.getId());
			SystemMessagePusher push = BeanUtils.getBean(SystemMessagePusher.class);
			push.push(message);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "离线消息推送成功！");
	}
}
