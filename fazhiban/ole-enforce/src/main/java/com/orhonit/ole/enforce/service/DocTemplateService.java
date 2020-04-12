package com.orhonit.ole.enforce.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.orhonit.ole.enforce.entity.DocTemplateEntity;

public interface DocTemplateService {
	
	/**
	 * 保存模板 
	 * @param docTemplateEntity
	 */
	void saveDocTemplate(DocTemplateEntity docTemplateEntity);

	/**
	 * 获取模板
	 * @param id
	 * @return
	 */
	DocTemplateEntity findOne(String id);

	/**
	 * 获取模板总数
	 * @param params
	 * @return
	 */
	int getDocTemplateCount(Map<String, Object> params);

	/**
	 * 获取模板列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	Page<DocTemplateEntity> getDocTemplateList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * 更新模板
	 * @param docTemplateEntity
	 */
	void updateDocTemplate(DocTemplateEntity docTemplateEntity);

	/**
	 * 根据模板编号获取模板信息
	 * @param tempCode
	 * @return
	 */
	DocTemplateEntity getTemplateByTemplateCode(String tempCode);
	
	/**
	 * 获取所有模版编号-模版代码
	 * @return
	 */
	Map<String, String> getTemplateCode();

}
