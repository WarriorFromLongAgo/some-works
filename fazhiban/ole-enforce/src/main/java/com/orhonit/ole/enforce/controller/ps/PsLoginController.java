package com.orhonit.ole.enforce.controller.ps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.CaseDetailInfoDTO;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.DocContentDTO;
import com.orhonit.ole.enforce.dto.DocFlowDTO;
import com.orhonit.ole.enforce.dto.NextOpinionDTO;
import com.orhonit.ole.enforce.dto.ps.LoginUserDTO;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.docflow.DocFlowService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.utils.MD5Utils;
import com.orhonit.ole.sys.dto.FlowDTO;

/**
 * 公示系统
 * 公示登录相关控制器：申请陈述申辩、申请复议、申请听证登录接口
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/ps/dl")
public class PsLoginController {
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private DocFlowService docFlowService;
	
	@Autowired
	private DocContentService docContentService ;
	
	@Autowired
	private DocTemplateService docTemplateService;
	
	/**
	 * ps 公示登录接口
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/login")
	public Result<Object> login(@RequestParam(value="userName",required = false) String userName,
			@RequestParam(value="passward", required = false) String passward,
			@RequestParam(value="personType", required = false) String personType) {
		LoginUserDTO loginUser = new LoginUserDTO();
		loginUser.setUserName(userName);
		if(personType.equals("1")){
			loginUser.setIdCord(userName);
		}else{
			loginUser.setOrgCode(userName);
		}
		LoginUserDTO User = this.caseService.login(loginUser);
		
		if(User == null){
			return ResultUtil.toResponseWithData(ResultCode.ERROR,"用户名或密码错误");
		}
		if(!MD5Utils.MD5(passward).equals(User.getPassward())){
			return ResultUtil.toResponseWithData(ResultCode.ERROR,"用户名或密码错误");
		}
		if(!personType.equals(User.getPersonType())){
			return ResultUtil.toResponseWithData(ResultCode.ERROR,"用户名或密码错误");
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,User);
		
	}
	/**
	 * ps陈述申辩案件列表
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/listCssb")
	public Result<List<CaseListDTO>> listCssb(@RequestParam(value="userName",required = false) String userName,
			@RequestParam(value="type",required = false) String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<CaseListDTO> list = new ArrayList<CaseListDTO>();
		map.put("userName", userName);
		if("1".equals(type)){
			list = this.caseService.getListCssb(map);
		}else{
			list = this.caseService.getListCssbGs(map);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
	/**
	 * 获取案件内容、文书、审批记录
	 * @param id
	 * @return
	 */
	@GetMapping("/query/{caseId}")
	public Result<CaseDetailInfoDTO> queryByCaseId(@PathVariable String caseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeCaseSource", "1002");
		params.put("typeCaseStatus", "1500");
		params.put("typeCaseType", "1003");
		params.put("typePartySex", "1700");
		params.put("typePartyType", "1600");
		CaseDetailInfoDTO dto = this.caseService.queryCaseByCaseId(caseId,params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, dto);
	}
	/**
	 * ps提交
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/taskComplate")
	public Result<Object> startFlow(@RequestBody FlowDTO flowDTO) {
		this.caseService.saveTaskEntity(flowDTO, false);
		this.caseService.taskComplete(flowDTO);
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	/**
	 * ps获取当前登录人申请听证案件列表
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/listSqtz")
	public Result<List<CaseListDTO>> listSqtz(@RequestParam(value="userName",required = false) String userName,
			@RequestParam(value="type",required = false) String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<CaseListDTO> list = new ArrayList<CaseListDTO>();
		map.put("userName", userName);
		if("1".equals(type)){
			list = this.caseService.getListSqtz(map);
		}else{
			list = this.caseService.getListSqtzGs(map);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
		
	}
	/**
	 * ps申请复议列表
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/listSqfy")
	public Result<List<CaseListDTO>> listSqfy(@RequestParam(value="userName",required = false) String userName,
			@RequestParam(value="type",required = false) String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		List<CaseListDTO> list = new ArrayList<CaseListDTO>();
		if("1".equals(type)){
			list = this.caseService.getListSqfy(map);
		}else{
			list = this.caseService.getListSqfyGs(map);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}

	/**
	 * 获取当前登录人部门所在区域的法制办复议承办人的人员列表
	 * @return
	 */
	@GetMapping("/getFyContractorByDeptIdArea")
	public Result<List<NextOpinionDTO>> getFyContractorByDeptIdArea(@RequestParam(value="deptId",required = false) String deptId) {
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFyContractorByDeptIdArea(deptId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有听证受理的人员列表
	 * @return
	 */
	@GetMapping("/getTzslReconsiderationByDeptId")
	public Result<List<NextOpinionDTO>> getTzslReconsiderationByDeptId(@RequestParam(value="deptId",required = false) String deptId) {
		List<NextOpinionDTO> nextOpinionDTOs = this.flowService.getTzslReconsiderationByDeptId(deptId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取流程节点文书
	 * @param flowNode
	 * @return
	 */
	@GetMapping("/docFlows")
	@ResponseBody
	public Result<Object> getDocFlows(
			@RequestParam(value = "flowNode" , required = false) String flowNode, 
			@RequestParam(value = "flowType", required = false) String flowType) {
		Map<String, Object> params = new HashMap<>();
		params.put("flowNode", flowNode);
		params.put("flowType", flowType);
		List<DocFlowDTO> docFlowDTOs = docFlowService.getDocFlows(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, docFlowDTOs);
	}
	
	/**
	 * 根据模板ID获取输入框以及输入框内容
	 * 
	 * @param templateId
	 */
//	@GetMapping("/content")
//	public Result<Object> getTemplateContentById(@RequestParam String templateId, @RequestParam String caseId) {
//		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);
//
//		// 获取模板内容
//		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
//		
//		// 最终要返回给前台的html代码
//		StringBuilder stringBuilder = new StringBuilder();
//		
//		// 文书上需要填写内容的模块放到上面
//		Map<String, String> map = new HashMap<>();
//		map.put("datetime", "");
//		map.put("templateName", docTemplateEntity.getName());
//		this.docContentService.createHtml(stringBuilder, document, caseId, map, templateId);
//		// 返回到前台
//		map.put("htmlContent", stringBuilder.toString());
//		
//		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
//	}
	/**
	 * 将前台提交的json内容保存到数据库中
	 * @return
	 */
	@PostMapping("/save")
	public Result<Object> saveDocContent(@Valid DocContentDTO docContentDTO) {
		DocContentEntity docContentEntity = new DocContentEntity();
		BeanUtils.copyProperties(docContentDTO, docContentEntity);
		this.docContentService.saveDocContent(docContentEntity);
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
}
