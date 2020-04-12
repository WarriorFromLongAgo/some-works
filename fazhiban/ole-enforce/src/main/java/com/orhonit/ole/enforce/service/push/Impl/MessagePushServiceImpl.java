package com.orhonit.ole.enforce.service.push.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.MessagePushDao;
import com.orhonit.ole.enforce.entity.MessagePushEntity;
import com.orhonit.ole.enforce.service.push.MessagePushService;

@Service
public class MessagePushServiceImpl implements MessagePushService {

	@Autowired
	private MessagePushDao messagePushDao;

	@Override
	public void updatePcPushSuccess(String id, int push_success) {
		this.messagePushDao.updatePcPushSuccess(id, push_success);
	}

	@Override
	public List<MessagePushEntity> offlinePush(String username) {
		return this.messagePushDao.getOfflinePush(username);
	}

	@Override
	public void updatePush(String id, int push_success) {
		this.messagePushDao.updatePush(id, push_success);
	}

	@Override
	public List<MessagePushEntity> offlinePcPush(String username) {
		return this.messagePushDao.getPcOfflinePush(username);
	}

}
