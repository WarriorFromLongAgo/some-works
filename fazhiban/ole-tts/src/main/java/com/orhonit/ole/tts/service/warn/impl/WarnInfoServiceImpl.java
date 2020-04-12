package com.orhonit.ole.tts.service.warn.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.WarnDao;
import com.orhonit.ole.tts.dto.WarnDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.repository.WarnInfoRepository;
import com.orhonit.ole.tts.service.warn.WarnInfoService;

@Service
public class WarnInfoServiceImpl implements WarnInfoService {

	@Autowired
	private WarnInfoRepository warnInfoRepository;
	
	@Autowired
	WarnDao warnDao;

	@Override
	public void save(WarnInfoEntity warnInfoEntity) {
		this.warnInfoRepository.save(warnInfoEntity);
	}

	@Override
	public int getListCount(Map<String, Object> params) {
		return warnDao.listCount(params);
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> params, Integer start, Integer length) {
		return warnDao.list(params,start,length);
	}

	@Override
	public int getBaseListCount(Map<String, Object> params) {
		return warnDao.baseListCount(params);
	}

	@Override
	public List<WarnDTO> getBaseList(Map<String, Object> params, Integer start, Integer length) {
		return warnDao.baseList(params,start,length);
	}

	@Override
	public WarnDTO getWarnById(String warnId) {
		return warnDao.getWarnInfo(warnId);
	}

	@Override
	public Map<String, Object> getWarnByIdAndPersonId(String warnId) {
		return warnDao.getWarnById(warnId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getPersonByWarnId(String warnId) {
		@SuppressWarnings("rawtypes")
		Map params = new HashMap<String,Object>();
		params.put("warnId", warnId);
		params.put("username", UserUtil.getCurrentUser().getUsername());
		return warnDao.getPersonByWarnId(params);
	}

	@Override
	public void isRead(String warnId, String personId) {
		warnDao.isRead(warnId,personId);
	}
}
