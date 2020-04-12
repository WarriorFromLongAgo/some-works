package com.orhonit.ole.enforce.service.casedoc;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.orhonit.ole.enforce.dto.api.ApiCaseDocDTO;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;

public interface DocContentService {

	/**
	 * 生成html代码
	 * @param stringBuilder
	 * @param document
	 * @param caseId
	 * @param map
	 * @param templateId
	 */
	void createHtml(StringBuilder stringBuilder, Document document, String caseId, Map<String, String> map, String templateId,Map<String, String> jsonValueMap);

	/**
	 * 保存json数据
	 * @param docContentEntity
	 */
	void save(DocContentEntity docContentEntity);
	
	/**
	 * 保存json数据
	 * @param docContentEntity
	 */
	void saveDocContent(DocContentEntity docContentEntity);

	/**
	 * 获取文书内容
	 * @param templateId
	 * @param caseId
	 * @param contentId
	 * @param document
	 * @return
	 */
	String getHtmlContent(String templateId, String caseId,String contentId, Document document);

	/**
	 * 保存app提交上来的数据
	 * @param docContentEntity
	 */
	void saveAppContent(DocContentEntity docContentEntity);
	
	/**
	 * 查询文书内容
	 * @param Map 
	 * */
	ApiCaseDocDTO findDocInfo(Map<String, Object> params);
	
	/**
	 * 根据案件和模板获取文书内容
	 * @param caseId
	 * @param templateId
	 * @return
	 */
	List<DocContentEntity> findByCaseIdAndTemplateId(String caseId, String templateId);
	
	/**
	 * 根据模板ID查询文书信息
	 * */
	DocTemplateEntity findById(String templateId);
}
