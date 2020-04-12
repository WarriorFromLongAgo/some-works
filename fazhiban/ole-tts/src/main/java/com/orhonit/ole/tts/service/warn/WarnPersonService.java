package com.orhonit.ole.tts.service.warn;

import java.util.List;

import com.orhonit.ole.tts.dto.WarnDTO;
import com.orhonit.ole.tts.entity.WarnPersonEntity;

public interface WarnPersonService {

	void save(List<WarnPersonEntity> warnPersonEntities);

	WarnDTO getWarnPersonInfoById(String warnId);

}
