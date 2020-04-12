package com.orhonit.ole.warn.service.party.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.warn.entity.PartyInfoEntity;
import com.orhonit.ole.warn.repository.PartyInfoRepository;
import com.orhonit.ole.warn.service.party.PartyInfoService;

@Service
public class PartyInfoServiceImpl implements PartyInfoService {

	@Autowired
	private PartyInfoRepository partyInfoRepository;

	@Override
	public PartyInfoEntity findOne(String id) {
		return this.partyInfoRepository.findOne(id);
	}

	@Override
	public PartyInfoEntity findByCaseId(String caseId) {
		return this.partyInfoRepository.findByCaseId(caseId);
	}

	@Override
	public List<PartyInfoEntity> findAll() {
		return this.partyInfoRepository.findAll();
	}
	
	
}
