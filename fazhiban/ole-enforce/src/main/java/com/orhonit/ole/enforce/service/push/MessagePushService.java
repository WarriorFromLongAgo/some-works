package com.orhonit.ole.enforce.service.push;

import java.util.List;

import com.orhonit.ole.enforce.entity.MessagePushEntity;

public interface MessagePushService {

	void updatePcPushSuccess(String id, int push_success);

	List<MessagePushEntity> offlinePush(String username);
	
	void updatePush(String id, int push_success);

	List<MessagePushEntity> offlinePcPush(String username);
}
