package com.orhonit.ole.report.service.casedoc;

import java.util.Map;

import org.jsoup.nodes.Document;

import com.orhonit.ole.report.entity.DocContentEntity;

public interface DocContentService {

	/**
	 * 生成html代码
	 * @param stringBuilder
	 * @param document
	 * @param caseId
	 * @param map
	 */
	void createHtml(StringBuilder stringBuilder, Document document, String caseId, Map<String, String> map);

	/**
	 * 保存json数据
	 * @param docContentEntity
	 */
	void save(DocContentEntity docContentEntity);

	/**
	 * 获取文书内容
	 * @param templateId
	 * @param caseId
	 * @param partyId
	 * @param document
	 * @return
	 */
	String getHtmlContent(String templateId, String caseId, String partyId, Document document);

}
