package com.orhonit.ole.enforce.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.DocFlowDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.docflow.DocFlowService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;

/**
 * APP文书控制器
 * 1. 文书提交
 * 2. 文书内容查询
 * 3. 查询每个节点所需要填写的文书
 * 4. 文书是否已经填写
 * 5.根据模板ID获取输入框以及输入框内容
 * 6.获取文书内容,将模板和数据(Json)绑定并返回html到前台
 * @author ebusu
 *
 */

@RestController
@RequestMapping("/api/temp")
public class ApiTemplateController {

	@Autowired
	private DocTemplateService docTemplateService;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private DocContentService docContentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CheckDailyService checkDailyService;
	
	@Autowired
	private LssuedService lssuedService;
	
	@Autowired
	private DocFlowService docFlowService;
	
	@Autowired 
	private DocContentRepository docContentRepository;
	
	/**
	 * 1. 文书提交
	 * */	
	@PostMapping("/save")
	public Result<Object> saveDocContent(@RequestParam(value="caseNum", required = false) String caseNum,
										 @RequestParam(value="tempCode",required = false) String tempCode,
										 @RequestParam(value="content",required = false) String content,
										 @RequestParam(value="contentId",required = false) String contentId) {
		
		if ( StringUtils.isEmpty(caseNum) ) {
			// 案件编号不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}
		
		if ( StringUtils.isEmpty(tempCode) ) {
			// 模板编号不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "模板编号为空.");
		}
		
		if ( StringUtils.isEmpty(content) ) {
			// 文书内容不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "文书内容为空.");
		}
		
		String caseId = "";
		DocContentEntity docContentEntity = new DocContentEntity();
		CaseEntity caseEntity =  this.caseService.getCaseByCaseNum(caseNum);
		if ( caseEntity == null ) {
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
			if ( checkDailyEntity == null ) {
				LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);
				if ( lssuedEntity != null ) {
					caseId = lssuedEntity.getId();
					docContentEntity.setFlowStatus(Integer.valueOf(lssuedEntity.getStatus()));
				}
			} else {
				caseId = checkDailyEntity.getId();
				docContentEntity.setFlowStatus(Integer.valueOf(checkDailyEntity.getStatus()));
			}
		} else {
			caseId = caseEntity.getId();
			docContentEntity.setFlowStatus(caseEntity.getCaseStatus());
		}
		
		if ( "".equals(caseId) ) {
			// 案件不存在
			return ResultUtil.toResponse(ResultCode.APP_CASE_NOT_EXIST);
		}
		
		DocTemplateEntity docTemplateEntity = this.docTemplateService.getTemplateByTemplateCode(tempCode);
		if ( docTemplateEntity == null ) {
			// 模板不存在
			return ResultUtil.toResponse(ResultCode.APP_TEMP_NOT_EXIST);
		}
		// 获取当前线程中的执法人员编号
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
					
		User user = this.userService.getUserByPersonId(personDTO.getId());
		
		DocContentEntity docContentEntityInfo = docContentRepository.findOne(contentId);		
		if(docContentEntityInfo==null){
	
			// 保存文书内容
			docContentEntity.setId(UUID.randomUUID().toString());
			docContentEntity.setCreateBy(user.getUsername());
			docContentEntity.setCreateDate(new Date());
			docContentEntity.setCreateName(user.getNickname());			
			docContentEntity.setCaseId(caseId);
			docContentEntity.setTemplateId(docTemplateEntity.getId());
			docContentEntity.setValue(content);
			
			this.docContentService.saveAppContent(docContentEntity);
		}else{
			//修改文书内容
			docContentEntityInfo.setUpdateBy(user.getUsername());
			docContentEntityInfo.setUpdateDate(new Date());
			docContentEntityInfo.setUpdateName(user.getNickname());
			docContentEntityInfo.setValue(content);
			docContentEntityInfo.setFlowStatus(docContentEntity.getFlowStatus());
			this.docContentService.saveAppContent(docContentEntityInfo);
		}
		
		
		
		// 返回成功标识
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	
//	/**
//	 * 2. 文书内容查询
//	 * */
//	@GetMapping("/getDonContent")
//	public Result<Object> getDocContent(@RequestParam(value="caseNum", required = false) String caseNum,
//										 @RequestParam(value="tempCode",required = false) String tempCode) {
//		
//		if ( StringUtils.isEmpty(caseNum) ) {
//			// 案件编号不能为空
//			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
//		}
//		
//		if ( StringUtils.isEmpty(tempCode) ) {
//			// 模板编号不能为空
//			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "模板编号为空.");
//		}
//		
//		
//		String caseId = "";
//		
//		CaseEntity caseEntity =  this.caseService.getCaseByCaseNum(caseNum);
//		if ( caseEntity == null ) {
//			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
//			if ( checkDailyEntity == null ) {
//				LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);
//				if ( lssuedEntity != null ) {
//					caseId = lssuedEntity.getId();
//				}
//			} else {
//				caseId = checkDailyEntity.getId();
//			}
//		} else {
//			caseId = caseEntity.getId();
//		}
//		
//		if ( "".equals(caseId) ) {
//			// 案件不存在
//			return ResultUtil.toResponse(ResultCode.APP_CASE_NOT_EXIST);
//		}
//		
//		DocTemplateEntity docTemplateEntity = this.docTemplateService.getTemplateByTemplateCode(tempCode);
//		if ( docTemplateEntity == null ) {
//			// 模板不存在
//			return ResultUtil.toResponse(ResultCode.APP_TEMP_NOT_EXIST);
//		}
//		
//		DocContentEntity docContentEntity = this.docContentService.findByCaseIdAndTemplateId(caseId, docTemplateEntity.getId());
//		
//		Map<String, String> map = new HashMap<String, String>();
//		
//		if ( docContentEntity != null ) {
//		
//			map = JsonUtil.fromJson(docContentEntity.getValue(), HashMap.class);
//			
//		}else{
//			return ResultUtil.toResponse(ResultCode.APP_TEMP_CONTENT_NULL);
//		}
//		
//		// 返回成功标识
//		
//		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
//		
//		
//	}
	
	/**
	 * 3. 查询每个节点所需要填写的文书
	 *  @param flowType 流程类型
	 *  @param flowNode 案件或检查状态
	 * */
	@GetMapping("/getDocList")
	public Result<Object> getDocList(
			@RequestParam(value = "flowNode" , required = false) String flowNode, 
			@RequestParam(value = "flowType", required = false) String flowType){
		if ( StringUtils.isEmpty(flowType) ) {
			// 流程类型不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "流程类型为空.");
		}
		
		if ( StringUtils.isEmpty(flowNode) ) {
			// 案件或检查状态不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件或检查状态为空.");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("flowNode", flowNode);
		params.put("flowType", flowType);
		List<DocFlowDTO> docFlowDTOs = docFlowService.getDocFlows(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, docFlowDTOs);	
	}
	
	/**
	 * 4.文书是否已经填写
	 *  @param templateIds 文书模板ID
	 *  @param caseNum 案件id
	 * */
	@GetMapping("/needAdd")
	public Result<Object> getNeedAdd(
			@RequestParam(value = "templateIds" , required = false) String templateIds, 
			@RequestParam(value = "caseNum", required = false) String caseNum) {
		
		if ( StringUtils.isEmpty(templateIds) ) {
			// 文书模板id
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "文书模板id为空.");
		}
		
		if ( StringUtils.isEmpty(caseNum) ) {
			// 案件编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}
		//获取案件id
		String caseId="";
		CaseEntity caseEntity =  this.caseService.getCaseByCaseNum(caseNum);
		if ( caseEntity == null ) {
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
			if ( checkDailyEntity == null ) {
				LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);
				if ( lssuedEntity != null ) {
					caseId = lssuedEntity.getId();
				}
			} else {
				caseId = checkDailyEntity.getId();
			}
		} else {
			caseId = caseEntity.getId();
		}
		
		if ( "".equals(caseId) ) {
			// 案件不存在
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件不存在.");
		}
		String[] templateId = templateIds.split(",");
		List<String> list = new ArrayList<String>();
		for(int i = 0; i<templateId.length; i++){
			// 获取文书内容
			List<DocContentEntity> docContentEntity = this.docFlowService.getNeedAdd(templateId[i], caseId);
			
			DocTemplateEntity docTemplateEntity = this.docContentService.findById(templateId[i]);
			if(StringUtils.isEmpty(docContentEntity) || docContentEntity.size() <= 0){
				list.add(docTemplateEntity.getName()+"未填写!");
			}else{
				list.add(docTemplateEntity.getName()+ "已填写!");
			}
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,list);	
	
	}
	
	/**
	 * 5.根据模板ID获取输入框以及输入框内容
	 * 
	 * @param templateId
	 * @param caseNum
	 * @param newOne
	 */
	@GetMapping("/content")
	public Result<Object> getTemplateContentById(
			@RequestParam(value = "templateId" , required = false) String templateId, 
			@RequestParam(value = "caseNum", required = false) String caseNum, 
			@RequestParam(value = "newOne", required = false) Boolean newOne){
		
		if ( StringUtils.isEmpty(templateId) ) {
			// 文书模板id
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "文书模板id为空.");
		}
		
		if ( StringUtils.isEmpty(caseNum) ) {
			// 案件编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}
		
		if ( StringUtils.isEmpty(newOne) ) {
			// 增加标志
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "添加标志为空.");
		}
		
		//获取案件id
		String caseId="";
		CaseEntity caseEntity =  this.caseService.getCaseByCaseNum(caseNum);
		if ( caseEntity == null ) {
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
			if ( checkDailyEntity == null ) {
				LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);
				if ( lssuedEntity != null ) {
					caseId = lssuedEntity.getId();
				}
			} else {
				caseId = checkDailyEntity.getId();
			}
		} else {
			caseId = caseEntity.getId();
		}
				
		if ( "".equals(caseId) ) {
					// 案件不存在
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件不存在.");
		}
		
		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);

		// 获取模板内容
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
		
		List<DocContentEntity> docContentEntitys = this.docContentRepository.findByTemplateIdAndCaseId(templateId, caseId);
		List<Map<String, String>> maps = new ArrayList<>();
		if(docContentEntitys.size()==0 || newOne) {
			// 最终要返回给前台的html代码
			StringBuilder stringBuilder = new StringBuilder();	
			// 文书上需要填写内容的模块放到上面
			Map<String, String> map = new HashMap<>();
			map.put("datetime", "");
			map.put("templateName", docTemplateEntity.getName());
			this.docContentService.createHtml(stringBuilder, document, caseId, map, templateId,new HashMap<>());
			// 返回到前台
			map.put("htmlContent", stringBuilder.toString());
			maps.add(map);
		}else {
			for (DocContentEntity docContentEntity : docContentEntitys) {
				// 最终要返回给前台的html代码
				StringBuilder stringBuilder = new StringBuilder();	
				Map<String, String> jsonValueMap = null;
				if (docContentEntity == null) {
					jsonValueMap = new HashMap<>();
				} else {
					jsonValueMap = JsonUtil.fromJson(docContentEntity.getValue(), HashMap.class);
				}
				// 文书上需要填写内容的模块放到上面
				Map<String, String> map = new HashMap<>();
				map.put("datetime", "");
				map.put("contentId", docContentEntity.getId());
				map.put("templateName", docTemplateEntity.getName());
				this.docContentService.createHtml(stringBuilder, document, caseId, map, templateId,jsonValueMap);
				// 返回到前台
				map.put("htmlContent", stringBuilder.toString());
				maps.add(map);
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, maps);
	}
	
	/**
	 * 6.获取文书内容,将模板和数据(Json)绑定并返回html到前台
	 * @param templateId
	 * @param caseNum
	 */
	@GetMapping("/detail")
	public Result<Object> getFinalContent(
			@RequestParam(value = "templateId" , required = false) String templateId, 
			@RequestParam(value = "caseNum", required = false) String caseNum, 
			@RequestParam(value = "contentId", required = false) String contentId) {
		
		if ( StringUtils.isEmpty(templateId) ) {
			// 文书模板id
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "文书模板id为空.");
		}
		
		if ( StringUtils.isEmpty(caseNum) ) {
			// 案件编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}
		
		if ( StringUtils.isEmpty(contentId) ) {
			// 内容ID
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "文书内容ID为空.");
		}
		
		//获取案件id
		String caseId="";

		CaseEntity caseEntity =  this.caseService.getCaseByCaseNum(caseNum);
		if ( caseEntity == null ) {
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
			if ( checkDailyEntity == null ) {
				LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);
				if ( lssuedEntity != null ) {
					caseId = lssuedEntity.getId();
				}
			} else {
				caseId = checkDailyEntity.getId();
			}
		} else {
			caseId = caseEntity.getId();
		}
		
		if ( "".equals(caseId) ) {
			// 案件不存在
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件不存在.");
		}
		
		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);

		// 获取模板内容
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
		
		// 获取文书内容
		String htmlContent = this.docContentService.getHtmlContent(templateId, caseId,contentId, document);
		
		Map<String, String> map = new HashMap<>();
		// 返回到前台
		map.put("htmlContent", htmlContent);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
	}
}
