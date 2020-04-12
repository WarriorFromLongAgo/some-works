package com.orhonit.ole.tts.service.warn.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.tts.dao.WarnPersonDao;
import com.orhonit.ole.tts.dto.WarnDTO;
import com.orhonit.ole.tts.entity.WarnPersonEntity;
import com.orhonit.ole.tts.repository.WarnPersonRepository;
import com.orhonit.ole.tts.service.warn.WarnPersonService;

@Service
public class WarnPersonServiceImpl implements WarnPersonService {

	@Autowired
	private WarnPersonRepository warnPersonRepository;
	
	@Autowired
	private WarnPersonDao warnPersonDao;

	@Override
	public void save(List<WarnPersonEntity> warnPersonEntities) {
		this.warnPersonRepository.save(warnPersonEntities);
	}

	@Override
	public WarnDTO getWarnPersonInfoById(String warnId) {
		return warnPersonDao.getWarnPersonInfoById(warnId);
	}
}
