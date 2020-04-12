package com.orhonit.ole.enforce.service.file;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.orhonit.ole.enforce.entity.AttachFileEntity;

public interface AttachFileService {
	
	/**
	 * 获取模板总数
	 * @param params
	 * @return
	 */
	int getFileCount(Map<String, Object> params);
	
	/**
	 * 获取文件总数
	 * @param caseNum
	 * @return
	 */
	int getFileCountByCaseNum(String caseNum);
	
	int getMp4CountCountByCaseNum(String caseNum);
	
	int getMp3CountCountByCaseNum(String caseNum);
	
	int getPicCountByCaseNum(String caseNum);

	/**
	 * 获取模板列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	Page<AttachFileEntity> getFileList(Map<String, Object> params, Integer start, Integer length);
}
