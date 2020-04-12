package com.orhonit.ole.tts.service.file;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.orhonit.ole.tts.entity.AttachFileEntity;

public interface AttachFileService {
	
	/**
	 * 获取模板总数
	 * @param params
	 * @return
	 */
	int getFileCount(Map<String, Object> params);

	/**
	 * 获取模板列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	Page<AttachFileEntity> getFileList(Map<String, Object> params, Integer start, Integer length);
}
