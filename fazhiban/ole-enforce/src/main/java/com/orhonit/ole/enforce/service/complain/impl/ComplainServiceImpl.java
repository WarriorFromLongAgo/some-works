package com.orhonit.ole.enforce.service.complain.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.entity.WarnComplainEntity;
import com.orhonit.ole.enforce.repository.WarnComplainRepository;
import com.orhonit.ole.enforce.service.complain.ComplainService;

@Service
public class ComplainServiceImpl implements ComplainService {

	@Autowired
	WarnComplainRepository warnComplainRepository;
	
	@Override
	public Boolean save(WarnComplainEntity warnComplainEntity) {
		try {
			warnComplainRepository.save(warnComplainEntity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
