package com.orhonit.ole.report.service.casedoc.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.report.dto.DocTemplateNodeDTO;
import com.orhonit.ole.report.entity.DocContentEntity;
import com.orhonit.ole.report.entity.PartyInfoEntity;
import com.orhonit.ole.report.exception.EnforceException;
import com.orhonit.ole.report.repository.DocContentRepository;
import com.orhonit.ole.report.repository.PartyInfoRepository;
import com.orhonit.ole.report.service.casedoc.DocContentService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@Service
public class DocContentServiceImpl implements DocContentService{
	
	@Value(value = "${docTemplate.notConvert.tagNames}")
	private String notConvertTagNamems;

	@Value(value = "${docTemplate.convertTag}")
	private String convertTag;
	
	@Autowired
	private PartyInfoRepository partyInfoRepository;
	
	@Autowired
	private DocContentRepository docContentRepository;
	
	@Override
	public void createHtml(StringBuilder stringBuilder, Document document, String caseId, Map<String, String> map) {
		Elements elements = document.getElementsByTag(convertTag);
		this.convertTemplateToHtml(elements, stringBuilder, caseId, convertTag, map);
		
		// 公共字段赋值,公共字段不需要输入内容,内容从相应的数据表中查询出来放到value值当中
		this.commonFiledRender(stringBuilder, document, caseId);
		
	}
	
	private void commonFiledRender(StringBuilder stringBuilder, Document document, String caseId) {
		String[] tagNames = notConvertTagNamems.split(",");
		if (tagNames != null && tagNames.length > 0) {
			for (String tagName : tagNames) {
				Elements elements = document.getElementsByTag(tagName);
				this.convertTemplateToHtml(elements, stringBuilder, caseId, tagName, null);
			}
		}

	}
	
	/**
	 * 根据TagName判断是否需要输入内容
	 * @param elements
	 * @param stringBuilder
	 * @param caseId
	 * @param tagName
	 * @param map
	 */
	private void convertTemplateToHtml(Elements elements, StringBuilder stringBuilder, String caseId, String tagName, Map<String, String> map) {
		try {
			if ( tagName.equals("partyInfo")) {
				// 当事人
				PartyInfoEntity partyInfoEntity = this.partyInfoRepository.findByCaseId(caseId);
				if ( elements != null && elements.size() > 0 ) {
					for ( Element element : elements) {
						String nodeContent = element.html();
						DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
						Method method = partyInfoEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
						this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, method.invoke(partyInfoEntity), tagName, true, null);
					}
				}
			} else if ( tagName.equals(convertTag)) {
				if ( elements != null && elements.size() > 0 ) {
					for ( Element element : elements) {
						String nodeContent = element.html();
						DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
						this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, null, tagName, false, map);
					}
				}
			}
		}catch(Exception e ) {
			e.printStackTrace();
			throw new EnforceException(ResultCode.BUSI_ERROR);
		}
		
		
	}

	/**
	 * 将Bean转换成Html代码
	 * @param stringBuilder
	 * @param docTemplateNodeDTO
	 * @param objData
	 * @param tagName
	 * @param readOnly
	 * @param map
	 */
	private void appendHtmlFormGroup(StringBuilder stringBuilder, DocTemplateNodeDTO docTemplateNodeDTO, Object objData,
			String tagName, Boolean readOnly, Map<String, String> map) {
		
		stringBuilder.append("<div class=\"form-group\">").append("<label class=\"col-md-2 control-label\">")
				.append(docTemplateNodeDTO.getLabel()).append("</label>").append("<div class=\"col-md-10\">");
		
		if (readOnly) {
			stringBuilder.append("<input readonly=\"readonly\" class=\"form-control\" type=\"text\" value=\"").append(objData == null ? "" : objData.toString()).append("\" />");
		}
		else {
			if (docTemplateNodeDTO.getType().equals("input")) {
				stringBuilder.append("<input class=\"form-control\" placeholder=\"")
					.append(docTemplateNodeDTO.getLabel()).append("\" type=\"text\" name=\"")
					.append(docTemplateNodeDTO.getValue())
					.append("\" />");
			}
			else if (docTemplateNodeDTO.getType().equals("datetime")) {
				if ( map.get("datetime").equals("")) {
					map.put("datetime", docTemplateNodeDTO.getValue());
				} else {
					map.put("datetime", map.get("datetime") + "," + docTemplateNodeDTO.getValue());
				}
				
				
				stringBuilder.append("<input class=\"form-control\" placeholder=\"")
					.append(docTemplateNodeDTO.getLabel()).append("\" type=\"text\" name=\"")
					.append(docTemplateNodeDTO.getValue())
					.append("\" id=\"").append(docTemplateNodeDTO.getValue()).append("\" />");
			}
			else if (docTemplateNodeDTO.getType().equals("textarea")) {
				stringBuilder.append("<textarea class=\"form-control\" placeholder=\"")
					.append(docTemplateNodeDTO.getLabel()).append("\" name=\"")
					.append(docTemplateNodeDTO.getValue())
					.append("\" />");
			}
			else if (docTemplateNodeDTO.getType().equals("select")) {
				stringBuilder.append("<select class=\"form-control\" name=\"")
					.append(docTemplateNodeDTO.getValue())
					.append("\" >");
				stringBuilder.append("<option value=\"\">请选择</option>");
				this.getSelectOptionByValue(stringBuilder, docTemplateNodeDTO.getValue());
				stringBuilder.append("</select>");
			}
		}
		stringBuilder.append("</div></div>");
	}
	
	/**
	 * 根据模板中的ole.select.value获取下拉框中的option内容
	 * @param value
	 * @return
	 */
	private void getSelectOptionByValue(StringBuilder stringBuilder, String value) {
		if ( value.equals("zfryszdw") ) {
			List<PartyInfoEntity> entities = this.partyInfoRepository.findAll();
			for( PartyInfoEntity entity : entities ) {
				stringBuilder.append("<option value=\"").append(entity.getId()).append("\">")
					.append(entity.getName()).append("</option>");
			}
		}
	}

	@Override
	public void save(DocContentEntity docContentEntity) {
		User user = UserUtil.getCurrentUser();
		docContentEntity.setId(UUID.randomUUID().toString());
		docContentEntity.setCreateBy(user.getUsername());
		docContentEntity.setCreateDate(new Date());
		docContentEntity.setCreateName(user.getNickname());
		docContentEntity.setUpdateBy(user.getUsername());
		docContentEntity.setUpdateDate(new Date());
		docContentEntity.setUpdateName(user.getNickname());
		docContentEntity.setPartyId(this.partyInfoRepository.findByCaseId(docContentEntity.getCaseId()).getId());
		this.docContentRepository.save(docContentEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getHtmlContent(String templateId, String caseId, String partyId, Document document) {
		
		try {
			DocContentEntity docContentEntity = this.docContentRepository.findByTemplateIdAndCaseIdAndPartyId(templateId, caseId, partyId);
			Map<String, String> jsonValueMap = JsonUtil.fromJson(docContentEntity.getValue(), HashMap.class);
			StringBuilder stringBuilder = new StringBuilder();
			Elements elements = document.getElementsByTag(convertTag);
			if ( elements != null && elements.size() > 0 ) {
				for (Element element : elements ) {
					String nodeContent = element.html();
					DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
					if ( StringUtils.isEmpty(docTemplateNodeDTO.getCss()) ) {
						element.html(jsonValueMap.get(docTemplateNodeDTO.getValue()));
					} else {
						if ( docTemplateNodeDTO.getCss().equals("underline")) {
							element.html("<span style=\"text-decoration: underline;\">" + jsonValueMap.get(docTemplateNodeDTO.getValue()) + "</span>");
						}
					}
					
				}
			}
			// 当事人信息
			PartyInfoEntity partyInfoEntity = this.partyInfoRepository.findByCaseId(caseId);
			String[] tagNames = notConvertTagNamems.split(",");
			if (tagNames != null && tagNames.length > 0) {
				for (String tagName : tagNames) {
					if ( tagName.equals("partyInfo")) {
						Elements notConvertElements = document.getElementsByTag(tagName);
						for (Element element : notConvertElements ) {
							String nodeContent = element.html();
							DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
							Method method = partyInfoEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
							element.html(method.invoke(partyInfoEntity).toString());
						}
					}
				}
			}
			stringBuilder.append(document.toString());
			return stringBuilder.toString();
		}catch(Exception e ) {
			e.printStackTrace();
			throw new EnforceException(ResultCode.BUSI_ERROR);
		}
	}

}
