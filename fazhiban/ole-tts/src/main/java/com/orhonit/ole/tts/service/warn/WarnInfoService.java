package com.orhonit.ole.tts.service.warn;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.WarnDTO;
import com.orhonit.ole.tts.entity.WarnInfoEntity;

public interface WarnInfoService {

	int getListCount(Map<String, Object> params);

	List<Map<String, Object>> getList(Map<String, Object> params, Integer start, Integer length);

	WarnDTO getWarnById(String warnId);

	int getBaseListCount(Map<String, Object> params);

	List<WarnDTO> getBaseList(Map<String, Object> params, Integer start, Integer length);

	void save(WarnInfoEntity warnInfoEntity);

	Object getWarnByIdAndPersonId(String warnId);

	List<Map<String, Object>> getPersonByWarnId(String warnId);

	void isRead(String warnId, String personId);

}
