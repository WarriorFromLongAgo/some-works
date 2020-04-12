package com.orhonit.ole.tts.service.doc.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.alibaba.fastjson.JSON;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.sys.dao.DictDao;
import com.orhonit.ole.sys.model.SysDictEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.SysDictRepository;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.CaseDao;
import com.orhonit.ole.tts.dao.PersonDao;
import com.orhonit.ole.tts.dto.CaseDTO;
import com.orhonit.ole.tts.dto.CheckDailyDTO;
import com.orhonit.ole.tts.dto.DocTemplateNodeDTO;
import com.orhonit.ole.tts.dto.ZfrDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseDocDTO;
import com.orhonit.ole.tts.entity.CaseDealEntity;
import com.orhonit.ole.tts.entity.CaseEntity;
import com.orhonit.ole.tts.entity.CheckDailyEntity;
import com.orhonit.ole.tts.entity.CheckRecordEntity;
import com.orhonit.ole.tts.entity.DocContentEntity;
import com.orhonit.ole.tts.entity.DocTemplateEntity;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.entity.PartyInfoEntity;
import com.orhonit.ole.tts.repository.CaseRepository;
import com.orhonit.ole.tts.repository.CheckDailyRepository;
import com.orhonit.ole.tts.repository.DocContentRepository;
import com.orhonit.ole.tts.repository.DocTemplateRepository;
import com.orhonit.ole.tts.repository.LssuedRepository;
import com.orhonit.ole.tts.repository.PartyInfoRepository;
import com.orhonit.ole.tts.service.casedeal.CaseDealService;
import com.orhonit.ole.tts.service.checkdaily.CheckDailyService;
import com.orhonit.ole.tts.service.doc.DocContentService;
import com.orhonit.ole.tts.utils.EnforceException;

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
	
	@Autowired
	private SysDictRepository sysDictRepository;
	
	@Autowired
	private CheckDailyService checkDailyService;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private CaseDao caseDao;
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private CheckDailyRepository checkDailyRepository;
	
	@Autowired
	private LssuedRepository lssuedRepository;
	
	@Autowired
	private CaseDealService caseDealService;
	
	@Autowired
	private DictDao DictDao;
	
	@Autowired
	private DocTemplateRepository docTemplateRepository;
	
	
	private static final String BLANK = "&nbsp;";
	
	private static final String TABLE_DATA = "tableData";
	
	@Override
	public void createHtml(StringBuilder stringBuilder, Document document, String caseId, Map<String, String> map, String templateId) {
		Elements elements = document.getElementsByTag(convertTag);
		this.convertTemplateToHtml(elements, stringBuilder, caseId, convertTag, map, templateId);
		
		// 公共字段赋值,公共字段不需要输入内容,内容从相应的数据表中查询出来放到value值当中
		this.commonFiledRender(stringBuilder, document, caseId, templateId);
		
	}
	
	private void commonFiledRender(StringBuilder stringBuilder, Document document, String caseId, String templateId) {
		String[] tagNames = notConvertTagNamems.split(",");
		if (tagNames != null && tagNames.length > 0) {
			for (String tagName : tagNames) {
				Elements elements = document.getElementsByTag(tagName);
				this.convertTemplateToHtml(elements, stringBuilder, caseId, tagName, null,templateId);
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
	@SuppressWarnings("unchecked")
	private void convertTemplateToHtml(Elements elements, StringBuilder stringBuilder, String caseId, String tagName, Map<String, String> map,String templateId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DocContentEntity docContentEntity = this.docContentRepository.findByTemplateIdAndCaseId(templateId, caseId);
		Map<String, String> jsonValueMap = null;
		if (docContentEntity == null) {
			jsonValueMap = new HashMap<>();
		} else {
			jsonValueMap = JsonUtil.fromJson(docContentEntity.getValue(), HashMap.class);
		}
		try {
			if ( tagName.equals("partyInfo")) {
				// 当事人
				PartyInfoEntity partyInfoEntity = this.partyInfoRepository.findByCaseId(caseId);
				if ( elements != null && elements.size() > 0 && partyInfoEntity!=null) {
					for ( Element element : elements) {
						String nodeContent = element.html();
						DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
						Method method = partyInfoEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
						Object value = method.invoke(partyInfoEntity);
						if ( docTemplateNodeDTO.getValue().equals("name")) {
							if ( value == null || "".equals(value) ) {
								Method uintMethod = partyInfoEntity.getClass().getMethod("getUnitName");
								value = uintMethod.invoke(partyInfoEntity);
							}
						} else if (docTemplateNodeDTO.getValue().equals("address")){
							// 2 是公司
							if ( partyInfoEntity.getType().intValue() == CommonParameters.PartyType.COMPANY) {
								Method uintMethod = partyInfoEntity.getClass().getMethod("getUnitAddress");
								value = uintMethod.invoke(partyInfoEntity);
							} else {
								Method uintMethod = partyInfoEntity.getClass().getMethod("getAddress");
								value = uintMethod.invoke(partyInfoEntity);
							}
							
						} 
						else if ( docTemplateNodeDTO.getValue().equals("sex")) {
							 List<SysDictEntity> sexDictEntites = this.sysDictRepository.findByTypeValueOrderBySortAsc(CommonParameters.DictType.SEX);
							 for ( SysDictEntity sysDictEntity : sexDictEntites ) {
								 if ( value != null && sysDictEntity.getEnumValue().equals(value.toString())) {
									 value = sysDictEntity.getEnumDesc();
									 break;
								 }
							 }
						}
						this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, value, tagName, true, null);
					}
				}
			} 
			else if ( tagName.equals("flowComment")) {
				// 审批意见, 文书当中的所有审批意见需要回显到页面中, 而不是从前台手动添加
				List<CaseDealEntity> caseDealEntities = this.caseDealService.getCaseDealByCaseId(caseId);
				Map<Integer, CaseDealEntity> dealModeCommentMap = new HashMap<>();
				caseDealEntities.forEach(caseDealEntity -> {
					dealModeCommentMap.put(caseDealEntity.getCaseStatus(), caseDealEntity);
				});
				if ( elements != null && elements.size() > 0 ) {
					for ( Element element : elements) {
						String nodeContent = element.html();
						DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
						CaseDealEntity tmpCaseDealEntity = dealModeCommentMap.get(Integer.valueOf(docTemplateNodeDTO.getValue()));
						String value = null;
						if( tmpCaseDealEntity != null ) {
							Method method = tmpCaseDealEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getRef()));
							value = method.invoke(tmpCaseDealEntity).toString();
							
							if ( !StringUtils.isEmpty(docTemplateNodeDTO.getDateFormat())) {
								SimpleDateFormat flowCommentDateFormat = new SimpleDateFormat(docTemplateNodeDTO.getDateFormat());
								value = flowCommentDateFormat.format(method.invoke(tmpCaseDealEntity));
							}
							
							if ( value.indexOf("@#$") != -1 ) {
								value = value.substring(0, value.indexOf("@#$"));
							}
							
						}
						this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, value, tagName, true, null);
					}
				}
			}
			
			else if ( tagName.equals("caseInfo")) {
				// 案件信息-执法人信息
				//CaseDetailInfoDTO caseDetailInfoDTO = caseDao.getCaseDetailInfoForDoc(caseId);
				CaseDTO caseDTO = caseDao.getCaseDTO(caseId);
				if ( caseDTO != null ) {
					//主执法人信息
					ZfrDTO zZfrDTO = personDao.getZzfrInfo(caseDTO.getCaseZzfryid());
					//副执法人信息s
					ZfrDTO fZfrDTO = personDao.getFzfrInfo(caseDTO.getCaseFzfryid());
					if ( elements != null && elements.size() > 0 ) {
						for ( Element element : elements) {
							String nodeContent = element.html();
							DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
							if(keyInClass(CaseDTO.class,docTemplateNodeDTO.getValue())){
								Method method = caseDTO.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
								Object value = null;
								if ( docTemplateNodeDTO.getType().equals("datetime")) {
									value = sdf.format(method.invoke(caseDTO));
								} else {
									value = method.invoke(caseDTO);
								}
								this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, value, tagName, true, null);
							}
							else if(keyInClass(ZfrDTO.class,docTemplateNodeDTO.getValue())){
								if(docTemplateNodeDTO.getValue().substring(0, 5).equals("caseZ")){
									if(zZfrDTO!=null){
										Method method = zZfrDTO.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
										this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, method.invoke(zZfrDTO), tagName, true, null);
									}
								}else if(docTemplateNodeDTO.getValue().substring(0, 5).equals("caseF")){
									if(fZfrDTO!=null){
										Method method = fZfrDTO.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
										this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, method.invoke(fZfrDTO), tagName, true, null);
									}
								}
							}
						}
					}
				}
			} else if ( tagName.equals(convertTag)) {
				if ( elements != null && elements.size() > 0 ) {
					for ( Element element : elements) {
						String nodeContent = element.html();
						DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
						
						this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, jsonValueMap.get(docTemplateNodeDTO.getValue()), tagName, false, map);
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
	@SuppressWarnings("rawtypes")
	private void appendHtmlFormGroup(StringBuilder stringBuilder, DocTemplateNodeDTO docTemplateNodeDTO, Object objData,
			String tagName, Boolean readOnly, Map<String, String> map) {
		
		// 如果模板中label为空的话，前台输入页面则不要渲染
		if ( StringUtils.isEmpty(docTemplateNodeDTO.getLabel())) {
			return ;
		}
		
		stringBuilder.append("<div class=\"form-group\">").append("<label class=\"col-md-2 control-label\">")
				.append(docTemplateNodeDTO.getLabel()).append("</label>").append("<div class=\"col-md-10\">");
		
		if (readOnly) {
			stringBuilder.append("<input readonly=\"readonly\" class=\"form-control\" type=\"text\" value=\"").append(objData == null ? "" : objData.toString()).append("\" />");
		}
		else {
			String classes = "\"form-control";
			if(docTemplateNodeDTO.getClasses() != null && docTemplateNodeDTO.getClasses().length>0){
				String[] classs = docTemplateNodeDTO.getClasses();
				for(int i = 0;i<classs.length;i++){
					classes +=" "+classs[i];
				}
			}
			classes += "\"";
			if (docTemplateNodeDTO.getType().equals("input")) {
				stringBuilder.append("<input class="+classes+" placeholder=\"")
					.append(docTemplateNodeDTO.getLabel()).append("\" type=\"text\" name=\"")
					.append(docTemplateNodeDTO.getValue())
					.append("\"");
				stringBuilder.append(" iname=\""+docTemplateNodeDTO.getLabel()+"\" ");
				
				if ( !StringUtils.isEmpty(docTemplateNodeDTO.getRequired()) && docTemplateNodeDTO.getRequired().equals(CommonParameters.Effect.EFFECT.toString())) {
					stringBuilder.append(" data-bv-notempty=\"true\" data-bv-notempty-message=\"字段不能为空.\"");
				}
				
				if ( !StringUtils.isEmpty(docTemplateNodeDTO.getMaxLength())) {
					stringBuilder.append(" maxlength=\"")
						.append(docTemplateNodeDTO.getMaxLength().trim())
						.append("\"");
				}
				// set value stringBuilder.append(c);
				if ( objData != null ) {
					stringBuilder.append(" value=\"").append(objData.toString()).append("\"");
				}
				stringBuilder.append(" />");
			}
			else if (docTemplateNodeDTO.getType().equals("datetime")) {
				if ( map.get("datetime").equals("")) {
					map.put("datetime", docTemplateNodeDTO.getValue());
				} else {
					map.put("datetime", map.get("datetime") + "," + docTemplateNodeDTO.getValue());
				}
				
				
				stringBuilder.append("<input class="+classes+" placeholder=\"")
					.append(docTemplateNodeDTO.getLabel()).append("\" type=\"text\" name=\"")
					.append(docTemplateNodeDTO.getValue()).append("\"");
				if ( objData != null ) {
					stringBuilder.append(" value=\"").append(objData.toString()).append("\"");
				}
				
				stringBuilder.append(" iname=\""+docTemplateNodeDTO.getLabel()+"\" ");
				
				stringBuilder.append(" id=\"").append(docTemplateNodeDTO.getValue()).append("\" />");
			}
			else if (docTemplateNodeDTO.getType().equals("textarea")) {
				stringBuilder.append("<textarea class="+classes+" placeholder=\"")
					.append(docTemplateNodeDTO.getLabel()).append("\" name=\"")
					.append(docTemplateNodeDTO.getValue()).append("\" iname=\""+docTemplateNodeDTO.getLabel()+"\" ")
					.append(">");
				if ( objData != null ) {
					stringBuilder.append(objData.toString());
				}
				
				stringBuilder.append("</textarea>");
			}
			else if (docTemplateNodeDTO.getType().equals("select")) {
				stringBuilder.append("<select class="+classes+" name=\"")
					.append(docTemplateNodeDTO.getValue()).append("\" iname=\""+docTemplateNodeDTO.getLabel()+"\" ")
					.append(">");
				stringBuilder.append("<option value=\"\">请选择</option>");
				if(objData==null){
					objData="";
				}
				this.getSelectOptionByDict(stringBuilder, docTemplateNodeDTO.getDict(), objData.toString());
				stringBuilder.append("</select>");
			}
			else if (docTemplateNodeDTO.getType().equals("checkbox")) {
				if(objData==null){
					objData="";
				}
				this.getCheckBoxOptionByDict(stringBuilder, docTemplateNodeDTO.getDict(), objData.toString(), docTemplateNodeDTO.getValue());
			}
			else if (docTemplateNodeDTO.getType().equals("table")) {
				
				stringBuilder.append("<table id=\"").append(docTemplateNodeDTO.getTableId()).append("\"><tr>");
				
				for ( int i = 0 ; i < docTemplateNodeDTO.getTableHeaders().length ; i ++ ) {
					stringBuilder.append("<th>").append(docTemplateNodeDTO.getTableHeaders()[i]).append("</th>");
				}
				
				stringBuilder.append("<th>删除</th>");
				
				stringBuilder.append("</tr>");
				
				if ( objData != null && ((List) objData).size() > 0 ) {
					List<LinkedHashMap<String, Object>> tableData = (List<LinkedHashMap<String, Object>>) objData;
					for ( int i = 0 ; i < tableData.size() ; i ++ ) {
						
						LinkedHashMap<String, Object> lineData = (LinkedHashMap<String, Object>) tableData.get(i);
						
						stringBuilder.append("<tr>");
						
						for ( int j = 0 ; j < docTemplateNodeDTO.getLineValues().length ; j ++ ) {
							stringBuilder.append("<td><input class="+classes+" type='text' name='").append(docTemplateNodeDTO.getLineValues()[j]).append("' value='").append(lineData.get(docTemplateNodeDTO.getLineValues()[j])).append("' /></td>");
						}
						
						stringBuilder.append("<td><a href='javascripot:;' class='form-control' onclick='deletetr(this)'><i class='layui-icon'>&#xe640;</i></a></td>");
						stringBuilder.append("</tr>");
					}
				}
				
				stringBuilder.append("</table>");
				
				stringBuilder.append("<button class=\"form-control col-md-2\" onclick=\"addtr();\">增加</button>");
				
				this.createScript(stringBuilder, docTemplateNodeDTO.getTableId(), docTemplateNodeDTO.getColumnCount(), docTemplateNodeDTO.getLineValues(), objData);
				
				
			}
		}
		stringBuilder.append("</div></div>");
	}
	
	/**
	 * 根据模板中的ole.select.dict.value获取下拉框中的option内容
	 * @param value
	 * @return
	 */
	private void getSelectOptionByDict(StringBuilder stringBuilder, String dict,String value) {
		if (!(dict == null || dict.equals(""))) {
			List<SysDictEntity> list = sysDictRepository.findByTypeValueOrderBySortAsc(dict);
			for( SysDictEntity entity : list ) {
				if(value !=null && value.equals(entity.getEnumValue())){
					stringBuilder.append("<option value=\"").append(entity.getEnumValue()).append("\" selected=\"selected\">").append(entity.getEnumDesc()).append("</option>");

				}else{
					stringBuilder.append("<option value=\"").append(entity.getEnumValue()).append("\">").append(entity.getEnumDesc()).append("</option>");
				}
				
			}
		}
	}
	
	/**
	 * 获取checkbox多选款的选项
	 * @param stringBuilder
	 * @param typeValue
	 * @param objData
	 */
	private void getCheckBoxOptionByDict(StringBuilder stringBuilder, String typeValue,String objData, String templateValue) {
		if (!(typeValue == null || typeValue.equals(""))) {
			List<SysDictEntity> list = sysDictRepository.findByTypeValueOrderBySortAsc(typeValue);
			for( SysDictEntity entity : list ) {
				
				List<String> objs = JSON.parseArray(objData, String.class);
				
				if ( objs != null && objs.size() > 0 && objs.contains(entity.getEnumValue())) {
					stringBuilder.append("<label class=\"checkbox-inline\"><input type=\"checkbox\" name=\"").append(templateValue).append("\" checked=\"checked\" value=\"").append(entity.getEnumValue())
						.append("\" />").append(entity.getEnumDesc()).append("</label>");
				} else {
					stringBuilder.append("<label class=\"checkbox-inline\"><input type=\"checkbox\" name=\"").append(templateValue)
					.append("\" value=\"").append(entity.getEnumValue()).append("\" />").append(entity.getEnumDesc()).append("</label>");
				}
				

				
			}
		}
	}
	

	@Override
	public void save(DocContentEntity docContentEntity) {
		DocContentEntity perDocContentEntity = this.docContentRepository.findByTemplateIdAndCaseId(docContentEntity.getTemplateId(), docContentEntity.getCaseId());
		if (perDocContentEntity != null) {
			this.docContentRepository.delete(perDocContentEntity);
		}
		User user = UserUtil.getCurrentUser();
		docContentEntity.setId(UUID.randomUUID().toString());
		docContentEntity.setCreateBy(user.getUsername());
		docContentEntity.setCreateDate(new Date());
		docContentEntity.setCreateName(user.getNickname());
		docContentEntity.setUpdateBy(user.getUsername());
		docContentEntity.setUpdateDate(new Date());
		docContentEntity.setUpdateName(user.getNickname());
		
		CaseEntity caseEntity = this.caseRepository.findOne(docContentEntity.getCaseId());
		if ( caseEntity != null ) {
			docContentEntity.setFlowStatus(caseEntity.getCaseStatus());
		} else {
			CheckDailyEntity checkDailyEntity = this.checkDailyRepository.findOne(docContentEntity.getCaseId());
			if ( checkDailyEntity != null ) {
				docContentEntity.setFlowStatus(Integer.valueOf(checkDailyEntity.getStatus()));
			} else {
				LssuedEntity lssuedEntity = this.lssuedRepository.findOne(docContentEntity.getCaseId());
				if ( lssuedEntity != null) {
					docContentEntity.setFlowStatus(Integer.valueOf(lssuedEntity.getStatus()));
				} else {
					throw new EnforceException(ResultCode.INTERNAL_SERVER_ERROR);
				}
			}
		}
		// docContentEntity.setPartyId(this.partyInfoRepository.findByCaseId(docContentEntity.getCaseId()).getId());
		this.docContentRepository.save(docContentEntity);
	}
	
	@Override
	public void saveDocContent(DocContentEntity docContentEntity) {
		DocContentEntity perDocContentEntity = this.docContentRepository.findByTemplateIdAndCaseId(docContentEntity.getTemplateId(), docContentEntity.getCaseId());
		if (perDocContentEntity != null) {
			this.docContentRepository.delete(perDocContentEntity);
		}
		docContentEntity.setId(UUID.randomUUID().toString());
		docContentEntity.setCreateDate(new Date());
		docContentEntity.setUpdateDate(new Date());
		
		CaseEntity caseEntity = this.caseRepository.findOne(docContentEntity.getCaseId());
		if ( caseEntity != null ) {
			docContentEntity.setFlowStatus(caseEntity.getCaseStatus());
		} else {
			CheckDailyEntity checkDailyEntity = this.checkDailyRepository.findOne(docContentEntity.getCaseId());
			if ( checkDailyEntity != null ) {
				docContentEntity.setFlowStatus(Integer.valueOf(checkDailyEntity.getStatus()));
			} else {
				LssuedEntity lssuedEntity = this.lssuedRepository.findOne(docContentEntity.getCaseId());
				if ( lssuedEntity != null) {
					docContentEntity.setFlowStatus(Integer.valueOf(lssuedEntity.getStatus()));
				} else {
					throw new EnforceException(ResultCode.INTERNAL_SERVER_ERROR);
				}
			}
		}
		// docContentEntity.setPartyId(this.partyInfoRepository.findByCaseId(docContentEntity.getCaseId()).getId());
		this.docContentRepository.save(docContentEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getHtmlContent(String templateId, String caseId,  Document document) {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DocContentEntity docContentEntity = this.docContentRepository.findByTemplateIdAndCaseId(templateId, caseId);
			Map<String, Object> jsonValueMap = JsonUtil.fromJson(docContentEntity.getValue(), HashMap.class);
			StringBuilder stringBuilder = new StringBuilder();
			Elements elements = document.getElementsByTag(convertTag);
			if ( elements != null && elements.size() > 0 ) {
				for (Element element : elements ) {
					String nodeContent = element.html();
					DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
					if ( StringUtils.isEmpty(docTemplateNodeDTO.getTableId())) {
						if ( "0".equals(docTemplateNodeDTO.getIsShow())) {
							element.html("");
							element.remove();
							continue;
						}
						if ( StringUtils.isEmpty(docTemplateNodeDTO.getCss()) ) {
							if ( StringUtils.isEmpty(docTemplateNodeDTO.getValue())) {
								// 如果value为空,则要去从其他地方去取
								Date date = sdf.parse(jsonValueMap.get(docTemplateNodeDTO.getRef()).toString());
								Calendar ca = Calendar.getInstance();
								ca.setTime(date);
								if ( docTemplateNodeDTO.getElement().equals("month")) {
									element.html(String.valueOf((Integer.valueOf(ca.get(this.getCalanderFiledByString(docTemplateNodeDTO.getElement()))) + 1 )));
								} else {
									element.html(Integer.valueOf(ca.get(this.getCalanderFiledByString(docTemplateNodeDTO.getElement()))).toString());
								}
							} else {
								// if zxfs, 2050, 
								if ( !StringUtils.isEmpty(docTemplateNodeDTO.getType()) && docTemplateNodeDTO.getType().equals("checkbox")) {
									List<SysDictEntity> list = sysDictRepository.findByTypeValueOrderBySortAsc(docTemplateNodeDTO.getDict());
									String checkBoxContent = "";
									for( SysDictEntity entity : list ) {
										
										List<String> objs = JSON.parseArray(jsonValueMap.get(docTemplateNodeDTO.getValue()).toString(), String.class);
										
										if ( objs != null && objs.size() > 0 && objs.contains(entity.getEnumValue())) {
											checkBoxContent += entity.getEnumDesc() + ",";
										}
									}
									element.html(checkBoxContent.substring(0, checkBoxContent.length() - 1));
								} else {
									// element.html(jsonValueMap.get(docTemplateNodeDTO.getValue()).toString());
									element.html(jsonValueMap.get(docTemplateNodeDTO.getValue()) == null ? " " : jsonValueMap.get(docTemplateNodeDTO.getValue()).toString());
								}
							}
						} else {
							if ( docTemplateNodeDTO.getCss().equals("underline")) {
								if ( StringUtils.isEmpty(docTemplateNodeDTO.getValue())) {
									// 如果value为空,则要去从其他地方去取
									Date date = sdf.parse(jsonValueMap.get(docTemplateNodeDTO.getRef()).toString());
									Calendar ca = Calendar.getInstance();
									ca.setTime(date);
									String tmpContent = "";
									
									if ( docTemplateNodeDTO.getElement().equals("month")) {
										tmpContent = String.valueOf((Integer.valueOf(ca.get(this.getCalanderFiledByString(docTemplateNodeDTO.getElement()))) + 1 ));
									} else {
										tmpContent =  Integer.valueOf(ca.get(this.getCalanderFiledByString(docTemplateNodeDTO.getElement()))).toString();
									}
									
									if ( !StringUtils.isEmpty(docTemplateNodeDTO.getPadBlankNum())) {
										StringBuilder esb = new StringBuilder();
										StringBuilder asb = new StringBuilder();
										for ( int i = 0 ; i < Integer.valueOf(docTemplateNodeDTO.getPadBlankNum()).intValue() ; i ++ ) {
											esb.append(BLANK);
											asb.append(BLANK);
										}
										tmpContent = esb.toString() + tmpContent + asb.toString();
									}
									element.html("<span style=\"text-decoration: underline;\">" + tmpContent + "</span>");
								} else {
									String tmpContent = jsonValueMap.get(docTemplateNodeDTO.getValue()).toString();
									if ( !StringUtils.isEmpty(docTemplateNodeDTO.getPadBlankNum())) {
										StringBuilder esb = new StringBuilder();
										StringBuilder asb = new StringBuilder();
										for ( int i = 0 ; i < Integer.valueOf(docTemplateNodeDTO.getPadBlankNum()).intValue() ; i ++ ) {
											esb.append("&nbsp;");
											asb.append("&nbsp;");
										}
										tmpContent = esb.toString() + tmpContent + asb.toString();
									}
									element.html("<span style=\"text-decoration: underline;\">" + tmpContent + "</span>");
								}
							}
						}
					} else {
						// 需要对table进行渲染
						element.html("");
						element.remove();
						Elements tableElememnt = document.getElementById(docTemplateNodeDTO.getTableId()).select("tbody");
						Element tbody = tableElememnt.get(0);
						StringBuilder tableBuilder = new StringBuilder();
						List<LinkedHashMap<String, Object>> tableData = (List<LinkedHashMap<String, Object>>) jsonValueMap.get(TABLE_DATA);
						if ( tableData != null && tableData.size() > 0 ) {
							for ( int i = 0 ; i < tableData.size() ; i ++ ) {
								LinkedHashMap<String, Object> lineData = tableData.get(i);
								tableBuilder.append("<tr>");
								for ( int j = 0 ; j < docTemplateNodeDTO.getLineValues().length ; j ++ ) {
									tableBuilder.append("<td>");
									if ( lineData != null && lineData.get(docTemplateNodeDTO.getLineValues()[j]) != null) {
										tableBuilder.append(lineData.get(docTemplateNodeDTO.getLineValues()[j]).toString());
									}
									tableBuilder.append("</td>");
								}
								tableBuilder.append("</tr>");
							}
						}
						tbody.append(tableBuilder.toString());
					}
					
				}
			}
			// 当事人信息
			PartyInfoEntity partyInfoEntity = this.partyInfoRepository.findByCaseId(caseId);
			// 案件-执法人信息
			//CaseDetailInfoDTO caseDetailInfoDTO = caseDao.getCaseDetailInfoForDoc(caseId);
			//CaseEntity caseEntity= this.caseRepository.findOne(caseId);
			CaseDTO caseDTO = caseDao.getCaseDTO(caseId);
			
			String[] tagNames = notConvertTagNamems.split(",");
			if (tagNames != null && tagNames.length > 0) {
				for (String tagName : tagNames) {
					if ( tagName.equals("partyInfo")) {
						Elements notConvertElements = document.getElementsByTag(tagName);
						for (Element element : notConvertElements ) {
							String nodeContent = element.html();
							DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
							Object value = null;
							if ( docTemplateNodeDTO.getValue().equals("name")) {
								Method method = partyInfoEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
								value = method.invoke(partyInfoEntity);
								if ( value == null || "".equals(value) ) {
									Method unitMethod = partyInfoEntity.getClass().getMethod("getUnitName");
									value = unitMethod.invoke(partyInfoEntity);
								}
							} 
							else if (docTemplateNodeDTO.getValue().equals("address")){
								// 2 是公司
								if ( partyInfoEntity.getType().intValue() == CommonParameters.PartyType.COMPANY) {
									Method uintMethod = partyInfoEntity.getClass().getMethod("getUnitAddress");
									value = uintMethod.invoke(partyInfoEntity);
								} else {
									Method uintMethod = partyInfoEntity.getClass().getMethod("getAddress");
									value = uintMethod.invoke(partyInfoEntity);
								}
								
							} 
							else if (docTemplateNodeDTO.getValue().equals("sex")){
								// 反显性别
								Method method = partyInfoEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
								value = method.invoke(partyInfoEntity);
								value = DictDao.getDescByValue("1700", value.toString());
							} 
							else {
								Method method = partyInfoEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
								value = method.invoke(partyInfoEntity);
							}
							element.html(value == null ? " " : value.toString());
						}
					}
					else if ( tagName.equals("caseInfo")) {
						
						Elements notConvertElements = document.getElementsByTag(tagName);
						for (Element element : notConvertElements ) {
							//主执法人信息
							ZfrDTO zZfrDTO = personDao.getZzfrInfo(caseDTO.getCaseZzfryid());
							//副执法人信息s
							ZfrDTO fZfrDTO = personDao.getFzfrInfo(caseDTO.getCaseFzfryid());
							String nodeContent = element.html();
							DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
							if(keyInClass(ZfrDTO.class,docTemplateNodeDTO.getValue())){
								if(docTemplateNodeDTO.getValue().substring(0, 5).equals("caseZ")){
									Method method = zZfrDTO.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
									element.html(method.invoke(zZfrDTO) == null ? "" : method.invoke(zZfrDTO).toString());
								}else if(docTemplateNodeDTO.getValue().substring(0, 5).equals("caseF")){
									Method method = fZfrDTO.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
									element.html(method.invoke(fZfrDTO) == null ? "" : method.invoke(fZfrDTO).toString());
								}
							}else if(keyInClass(CaseDTO.class,docTemplateNodeDTO.getValue())){
								Method method = caseDTO.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
								Object value = null;
								if ( docTemplateNodeDTO.getType().equals("datetime")) {
									value = sdf.format(method.invoke(caseDTO));
								} else {
									value = method.invoke(caseDTO);
								}
								
								element.html(value == null ? "" : value.toString());
							}
						}
					}
					else if ( tagName.equals("checkUser")) {
						
						CheckDailyDTO checkDailyDTO = checkDailyService.findOneCheckDaily(caseId);
						CheckRecordEntity checkRecordEntity = checkDailyService.findOneCheck(caseId);
						
						Elements notConvertElements = document.getElementsByTag(tagName);
						for (Element element : notConvertElements ) {
							if( checkDailyDTO != null ) {
								element.html( checkDailyDTO.getCheckObjectType().equals("1") ? checkDailyDTO.getCheckedUserName() : checkDailyDTO.getUnitName() );
							} else if(checkRecordEntity != null) {
								element.html(checkRecordEntity.getCheckObjectType().equals("1") ? checkRecordEntity.getCheckedUserName() : checkRecordEntity.getUnitName());
							}else{
								element.html("");
							}
						}
					}
					
					else if ( tagName.equals("flowComment")) {
						// 审批意见, 文书当中的所有审批意见需要回显到页面中, 而不是从前台手动添加
						Elements notConvertElements = document.getElementsByTag(tagName);
						// TODO 如果存在退回再提交可能有问题
						List<CaseDealEntity> caseDealEntities = this.caseDealService.getCaseDealByCaseId(caseId);
						Map<String, CaseDealEntity> dealModeCommentMap = new HashMap<>();
						caseDealEntities.forEach(caseDealEntity -> {
							dealModeCommentMap.put(caseDealEntity.getCaseStatus().toString(), caseDealEntity);
						});
						
						for (Element element : notConvertElements ) {
							String nodeContent = element.html();
							DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
							// Method method = partyInfoEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getValue()));
							if ( dealModeCommentMap.get(docTemplateNodeDTO.getValue()) != null ) {
								CaseDealEntity tmpCaseDealEntity = dealModeCommentMap.get(docTemplateNodeDTO.getValue());
								Method method = tmpCaseDealEntity.getClass().getMethod("get" + StrUtil.captureName(docTemplateNodeDTO.getRef()));
								String value = method.invoke(tmpCaseDealEntity).toString();
								if ( value.indexOf("@#$") != -1 ) {
									value = value.substring(0, value.indexOf("@#$"));
								}
								if ( !StringUtils.isEmpty(docTemplateNodeDTO.getDateFormat())) {
									SimpleDateFormat flowCommentDateFormat = new SimpleDateFormat(docTemplateNodeDTO.getDateFormat());
									
									value = flowCommentDateFormat.format(method.invoke(tmpCaseDealEntity));
								}
								if ( !StringUtils.isEmpty(docTemplateNodeDTO.getCss()) ) {
									if ( docTemplateNodeDTO.getCss().equals("text-align:right"))
									element.html("<p style=\"text-align:right\">" + value + "</p>");
								} else {
									element.html(value);
								}
								
							} else {
								element.html(" ");
							}
						}
//						
//						
//						if ( elements != null && elements.size() > 0 ) {
//							for ( Element element : elements) {
//								String nodeContent = element.html();
//								DocTemplateNodeDTO docTemplateNodeDTO = JsonUtil.fromJson(nodeContent, DocTemplateNodeDTO.class);
//								String content = dealModeCommentMap.get(docTemplateNodeDTO.getValue());
//								this.appendHtmlFormGroup(stringBuilder, docTemplateNodeDTO, content, tagName, true, null);
//							}
//						}
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
	
	private int getCalanderFiledByString(String element) {
		if ( element.equals("year")) {
			return Calendar.YEAR; 
		} else if ( element.equals("month")) {
			return Calendar.MONTH; 
		} else if ( element.equals("day")) {
			return Calendar.DAY_OF_MONTH; 
		} else if ( element.equals("hour")) {
			return Calendar.HOUR_OF_DAY; 
		} else if ( element.equals("minute")) {
			return Calendar.MINUTE; 
		}
		return 0;
	}
	
	private Boolean keyInClass(@SuppressWarnings("rawtypes") Class cls,String key){
		Field[] fields = cls.getDeclaredFields();
		Map<String,String> map = new HashMap<String, String>();
		for(Field field:fields){
			map.put(field.getName(), null);
		}
		return map.containsKey(key);
	}

	@Override
	public void saveAppContent(DocContentEntity docContentEntity) {
		this.docContentRepository.save(docContentEntity);
	}

	@Override
	public ApiCaseDocDTO findDocInfo(Map<String, Object> params) {
		return this.caseDao.findDocInfo(params);
	}
	
	@SuppressWarnings("rawtypes")
	private void createScript(StringBuilder stringBuilder, String tableId, String columnCount, String[] lineValue, Object objData) {
		
		stringBuilder.append("<script type=\"text/javascript\">")
			.append("function addtr(){  var table = $(\"#").append(tableId).append("\");");
		stringBuilder.append("var tr=$(\"<tr>");
		for ( int i = 0 ; i < Integer.valueOf(columnCount).intValue() ; i ++ ) {
			stringBuilder.append("<td><input class='form-control' type='text' name='").append(lineValue[i]).append("' /></td>");
		}
		
		stringBuilder.append("<td><a href='javascripot:;' class='form-control' onclick='deletetr(this)'><i class='layui-icon'>&#xe640;</i></a></td>");
		
		stringBuilder.append("</tr>\");table.append(tr); } ");
		
		if ( objData != null && ((List)objData).size() > 0) {
			// stringBuilder.append("init table");
		} else {
			stringBuilder.append("addtr();");
		}
		
		stringBuilder.append("function deletetr(tdobject){  var td=$(tdobject); td.parents(\"tr\").remove();  } ");
		
		stringBuilder.append("var tableId = '").append(tableId).append("' ; var needToErgodicTable = true; var columnCount = ").append(columnCount).append(";</script>");
		
	}

	@Override
	public DocContentEntity findByCaseIdAndTemplateId(String caseId, String templateId) {
		return this.docContentRepository.findByTemplateIdAndCaseId(templateId, caseId);
	}

	@Override
	public DocTemplateEntity findById(String templateId) {
		// TODO Auto-generated method stub
		return this.docTemplateRepository.findById(templateId);
	}
	
}
