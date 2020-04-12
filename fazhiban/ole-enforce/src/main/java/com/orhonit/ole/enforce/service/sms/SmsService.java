package com.orhonit.ole.enforce.service.sms;

import com.orhonit.ole.enforce.entity.SmsEntity;

public interface SmsService {

	/**
	 * 保存
	 * @param SmsEntity
	 */
	void saveSms(SmsEntity smsEntity);

	
	/**
	 * 根据执法证号获取发送验证码信息
	 * @param certNum
	 * @return
	 */
	SmsEntity getSmsByCertNum(String certNum);
}
