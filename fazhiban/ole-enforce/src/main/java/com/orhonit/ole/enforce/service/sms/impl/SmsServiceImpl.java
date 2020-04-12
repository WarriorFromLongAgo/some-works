package com.orhonit.ole.enforce.service.sms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.SmsDao;
import com.orhonit.ole.enforce.entity.SmsEntity;
import com.orhonit.ole.enforce.repository.SmsRepository;
import com.orhonit.ole.enforce.service.sms.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private SmsRepository smsRepository;
	
	@Autowired
	private SmsDao SmsDao;

	@Override
	public void saveSms(SmsEntity smsEntity) {
		this.smsRepository.save(smsEntity);
	}

	@Override
	public SmsEntity getSmsByCertNum(String certNum) {
		Map<String, Object> params=new HashMap<>();
		params.put("certNum", certNum);
		List<SmsEntity> smsList= SmsDao.getSmsByCertNum(params);
		if(smsList!=null&&smsList.size()>0){
			return smsList.get(0);
		}else{
			return null;
		}
	}
}
