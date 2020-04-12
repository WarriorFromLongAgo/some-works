package com.orhonit.ole.enforce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import lombok.extern.slf4j.Slf4j;

@Component
@RestController
@RequestMapping("/message")
@Slf4j
public class MessagePushController {

	@Autowired
	private MessagePushService messagePushService;

	private static MessagePushRepository messagePushRepository;

	@Autowired
	public MessagePushController(MessagePushRepository messagePushRepository2) {
		MessagePushController.messagePushRepository = messagePushRepository2;
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
		messagePushService.updatePcPushSuccess(id, 2);
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
		List<MessagePushEntity> messagePushEntities = messagePushService.offlinePcPush(username);
		if (messagePushEntities == null || messagePushEntities.size() <= 0) {
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "暂无离线消息！");
		}
		for (MessagePushEntity messagePushEntity : messagePushEntities) {
			// pc push start
			CimMessageSocketHandler cimMessageSocketHandler = SpringContext.getBean(CimMessageSocketHandler.class);
			
			Map<String, Object> map = new HashMap<>();
			map.put("msg", messagePushEntity.getPush_content());
			map.put("username", messagePushEntity.getPush_person());
			map.put("messageType", messagePushEntity.getInterface_type());
			map.put("caseNum", messagePushEntity.getModular_id());
			boolean sendFlag = cimMessageSocketHandler.sendMsg(map);
			if ( sendFlag ) {
				messagePushEntity.setPc_push_success(2);
				messagePushRepository.save(messagePushEntity);
			}
			// pc push end
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "离线消息推送成功！");
	}
}
