package com.orhonit.ole.enforce.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ExcelUtil;
import com.orhonit.ole.common.utils.FileUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.SysUtil;
import com.orhonit.ole.enforce.config.UploadConfig;
import com.orhonit.ole.enforce.dto.CaseDetailInfoDTO;
import com.orhonit.ole.enforce.dto.CaseDocDTO;
import com.orhonit.ole.enforce.dto.CaseInfoDTO;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.DeptPersonDTO;
import com.orhonit.ole.enforce.dto.FlowTaskCommentDTO;
import com.orhonit.ole.enforce.dto.LssuedDTO;
import com.orhonit.ole.enforce.dto.PerVerifyInfoDTO;
import com.orhonit.ole.enforce.dto.YujDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.SysUserEntity;
import com.orhonit.ole.enforce.repository.CaseRepository;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.repository.SysUserRepository;
import com.orhonit.ole.enforce.service.casedeal.CaseDealService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.caseinfo.impl.FlowThreadService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.enforce.service.perverify.PerVerifyService;
import com.orhonit.ole.enforce.service.yuj.YujService;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 案件控制器
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/case")
@Slf4j
public class CaseController {
	
	@Value("${files.punishBill}")
	private String filesPath;
	
	@Value("${upload.serverUrl}")
	private String serverUrl;
	
	
	@Autowired
	private PerVerifyService perVerifyService;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private FlowThreadService flowThreadService;
	
	@Autowired
	private CaseDealService caseDealService;

	@Autowired
    private YujService yujService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CheckDailyService checkDailyService;
	
	@Autowired
	private LssuedService lssuedService;
	
	@Autowired
	private UploadConfig uploadConfig;
	
	@Autowired
	private DocContentRepository docContentRepository;
	
	@Autowired
	private SysUserRepository sysUserRepository;
	
	@Autowired
	private CaseRepository caseRepository;
	
	//@PostMapping("/save")
	public void saveCase(@RequestBody @Valid CaseInfoDTO caseInfoDTO) {
		CaseEntity caseEntity = new CaseEntity();
		BeanUtils.copyProperties(caseInfoDTO, caseEntity);
		//if(caseEntity.getCaseSource().equals(CommonParameters.CaseSourceCheckType.PRO.toString())){
		//	this.caseService.updateCheckDaily(caseInfoDTO.getCheckId());
		//}else if(caseEntity.getCaseSource().equals(CommonParameters.CaseSourceCheckType.DAILY.toString())){
			
		//}
		
//		FlowDTO flowDTO = this.caseService.save(caseEntity);
////		flowDTO.setDealType(caseInfoDTO.getDealType().toString());
//		if ( flowDTO != null ){
//			flowDTO.setHandleMode(caseInfoDTO.getDealType().toString());
//			this.flowThreadService.setFlowDTO(flowDTO);
//			this.flowThreadService.setFlowType(1);
//			Thread ft = new Thread(this.flowThreadService);
//			ft.start();
//			this.caseDealService.saveTaskEntity(flowDTO, true);
//		}
	}
	
	@PostMapping("/save")
	public Result<Object> saveCasea(@RequestBody Map<String,Map<String,Object>> info) {
		CaseInfoDTO caseInfoDTO = new CaseInfoDTO();
		PerVerifyInfoDTO perVerifyInfoDTO = new PerVerifyInfoDTO();
		caseInfoDTO.setId(info.get("preInfo").get("caseId").toString());
		caseInfoDTO.setCaseNum(info.get("preInfo").get("caseNum").toString());
		// 案件编号,生成规则(处罚-机构-日期-4位序列  )  e.g. CF-15010001003-20171130-0003
		if(caseInfoDTO.getId()==null || caseInfoDTO.getId().equals("")){
			caseInfoDTO.setId(UUID.randomUUID().toString());
			caseInfoDTO.setCaseNum(caseService.getCaseNum());
			perVerifyInfoDTO.setCaseId(caseInfoDTO.getId());
		}
		caseInfoDTO.setCaseName(info.get("caseInfo").get("caseName").toString());
		caseInfoDTO.setCaseSource(info.get("caseInfo").get("caseSource").toString());
		caseInfoDTO.setCaseHandler(info.get("caseInfo").get("caseHandler").toString());
		caseInfoDTO.setCaseAddress(info.get("caseInfo").get("caseAddress").toString());
		caseInfoDTO.setCaseType(info.get("caseInfo").get("caseType").toString());
		if(caseInfoDTO.getCaseSource().equals("5")||caseInfoDTO.getCaseSource().equals("6")) {
			caseInfoDTO.setCheckId(info.get("caseInfo").get("checkId").toString());
		}
		caseInfoDTO.setBriefCaseContent(info.get("caseInfo").get("briefCaseContent").toString());
		caseInfoDTO.setCaseZpr(info.get("caseInfo").get("caseZpr").toString());
		try {
			caseInfoDTO.setCaseApplyDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(info.get("caseInfo").get("caseApplyDate").toString()));
			caseInfoDTO.setCaseTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(info.get("caseInfo").get("caseTime").toString()));
			caseInfoDTO.setCaseZpdate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(info.get("caseInfo").get("caseZpdate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		caseInfoDTO.setCaseStatus(new Integer(info.get("caseInfo").get("caseStatus").toString()));
		caseInfoDTO.setCaseZzfryid(info.get("caseInfo").get("caseZzfryid").toString());
		caseInfoDTO.setCaseZzfryname(info.get("caseInfo").get("caseZzfryname").toString());
		
		String fzfryids[]={};
		
		if(info.get("caseInfo").get("caseFzfryid").toString().contains("[") && info.get("caseInfo").get("caseFzfryid").toString().contains("]")){
			fzfryids = info.get("caseInfo").get("caseFzfryid").toString().substring(1, info.get("caseInfo").get("caseFzfryid").toString().length()-1).split(",");
		}else{
			fzfryids = info.get("caseInfo").get("caseFzfryid").toString().split(",");
		}
		String fzfryName = "";
		String fzfryId = "";
		for(int i = 0;i<fzfryids.length;i++){
			User user = this.userService.getUserByPersonId(fzfryids[i].trim());
			if(!StringUtils.isEmpty(user.getNickname())){
				fzfryName += user.getNickname()+",";
			}
			fzfryId += fzfryids[i].trim()+",";
		}
		caseInfoDTO.setCaseFzfryid(fzfryId.substring(0, fzfryId.length()-1));
		caseInfoDTO.setCaseFzfryname(fzfryName.substring(0, fzfryName.length()-1));
		if(info.get("caseInfo").get("comment") != null && !info.get("caseInfo").get("comment").equals("")){
			caseInfoDTO.setDealType(new Integer(info.get("caseInfo").get("dealType").toString()));
			caseInfoDTO.setComment(info.get("caseInfo").get("comment").toString());
			perVerifyInfoDTO.setComment(info.get("caseInfo").get("comment").toString());
			perVerifyInfoDTO.setDealType(new Integer(info.get("caseInfo").get("dealType").toString()));
		}
		CaseEntity caseEntity = new CaseEntity();
		BeanUtils.copyProperties(caseInfoDTO, caseEntity);
		FlowDTO flowDTO = this.caseService.save(caseEntity);
		if ( flowDTO != null ){
			flowDTO.setHandleMode(caseInfoDTO.getDealType().toString());
			this.flowThreadService.setFlowDTO(flowDTO);
			this.flowThreadService.setFlowType(1);
			Thread ft = new Thread(this.flowThreadService);
			ft.start();
			this.caseDealService.saveTaskEntity(flowDTO, true);
		}
		if(perVerifyInfoDTO.getCaseId() == null || perVerifyInfoDTO.getCaseId().equals("")){
			perVerifyInfoDTO.setCaseId(info.get("preInfo").get("caseId").toString());
		}
		if(info.get("preInfo").get("type") != null && !"".equals(info.get("preInfo").get("type"))){
			perVerifyInfoDTO.setType(new Integer(info.get("preInfo").get("type").toString()));
		}
		if(info.get("preInfo").get("age") != null && !"".equals(info.get("preInfo").get("age"))){
			perVerifyInfoDTO.setAge(new Integer(info.get("preInfo").get("age").toString()));
		}
		if(info.get("preInfo").get("sex") != null && !"".equals(info.get("preInfo").get("sex"))){
			perVerifyInfoDTO.setSex(new Integer(info.get("preInfo").get("sex").toString()));
		}
		if(info.get("preInfo").get("perVerifyStatus") != null && !"".equals(info.get("preInfo").get("perVerifyStatus"))){
			perVerifyInfoDTO.setPerVerifyStatus(new Integer(info.get("preInfo").get("perVerifyStatus").toString()));
		}
		perVerifyInfoDTO.setName(info.get("preInfo").get("name").toString());
		perVerifyInfoDTO.setUnitName(info.get("preInfo").get("unitName").toString());
		perVerifyInfoDTO.setUnitAddress(info.get("preInfo").get("unitAddress").toString());
		perVerifyInfoDTO.setTel(info.get("preInfo").get("tel").toString());
		perVerifyInfoDTO.setIdCard(info.get("preInfo").get("idCard").toString());
		perVerifyInfoDTO.setPostCode(info.get("preInfo").get("postCode").toString());
		perVerifyInfoDTO.setAddress(info.get("preInfo").get("address").toString());
		perVerifyInfoDTO.setOrgCode(info.get("preInfo").get("orgCode").toString());
		perVerifyInfoDTO.setLegalName(info.get("preInfo").get("legalName").toString());
		perVerifyInfoDTO.setOrgIdCard(info.get("preInfo").get("orgIdCard").toString());
		
		perVerifyService.savePerVerify(perVerifyInfoDTO);
		
		//如果是来自专项或日常检查则复制对应检查勘验笔录
		//一般 849b8d5d-a112-42b6-8e6a-cdde385c3c05
		if(caseEntity.getCaseSource().equals("5")) {
			//专项 b8678bbb-831c-4d6d-9276-fd403ddc3ceb
			List<DocContentEntity> docContentEntitys =docContentRepository.findByTemplateIdAndCaseId("b8678bbb-831c-4d6d-9276-fd403ddc3ceb", caseEntity.getCheckId());
			DocContentEntity docContentEntity1 = new DocContentEntity();
			for (DocContentEntity docContentEntity : docContentEntitys) {
				if(docContentEntity != null) {
					docContentEntity1.setCreateBy(UserUtil.getCurrentUser().getUsername());
					docContentEntity1.setCreateDate(new Date());
					docContentEntity1.setCreateName(UserUtil.getCurrentUser().getNickname());
					docContentEntity1.setTemplateId("849b8d5d-a112-42b6-8e6a-cdde385c3c05");
					docContentEntity1.setCaseId(caseEntity.getId());
					docContentEntity1.setFlowStatus(CommonParameters.CaseStatus.AJLA);
					docContentEntity1.setId(UUID.randomUUID().toString());
					docContentEntity1.setValue(docContentEntity.getValue());
					docContentRepository.save(docContentEntity1);
				}
			}
			return ResultUtil.toResponse(ResultCode.SUCCESS);
		}else if(caseEntity.getCaseSource().equals("6")) {
			//日常 90e31509-4c45-4341-b2d6-4dfb798ba671
			List<DocContentEntity> docContentEntitys =docContentRepository.findByTemplateIdAndCaseId("90e31509-4c45-4341-b2d6-4dfb798ba671", caseEntity.getCheckId());
			DocContentEntity docContentEntity1 = new DocContentEntity();
			for (DocContentEntity docContentEntity : docContentEntitys) {
				if(docContentEntity != null) {
					docContentEntity1.setCreateBy(UserUtil.getCurrentUser().getUsername());
					docContentEntity1.setCreateDate(new Date());
					docContentEntity1.setCreateName(UserUtil.getCurrentUser().getNickname());
					docContentEntity1.setTemplateId("849b8d5d-a112-42b6-8e6a-cdde385c3c05");
					docContentEntity1.setCaseId(caseEntity.getId());
					docContentEntity1.setFlowStatus(CommonParameters.CaseStatus.AJLA);
					docContentEntity1.setId(UUID.randomUUID().toString());
					docContentEntity1.setValue(docContentEntity.getValue());
					docContentRepository.save(docContentEntity1);
				}
			}
			return ResultUtil.toResponse(ResultCode.SUCCESS);
		}else {
			return ResultUtil.toResponse(ResultCode.SUCCESS);
		}
		
	}
	
	@PostMapping("/update")
	public Result<Object> updataCase(@RequestBody @Valid CaseInfoDTO caseInfoDTO) {
		CaseInfoDTO casee = caseService.findOne(caseInfoDTO.getId());
		BeanUtils.copyProperties(caseInfoDTO, casee);
		CaseEntity caseEntity = new CaseEntity();
		BeanUtils.copyProperties(casee, caseEntity);
		this.caseService.update(caseEntity);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,caseEntity);
	}
	
	/**
	 * 获取案件列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CaseListDTO> listCase(TableRequest request) {
		
		request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
		//request.getParams().put("myCase", UserUtil.getCurrentUser().getNickname());
		request.getParams().put("caseStatus", 11);
		request.getParams().put("typeValue", "1002");
		request.getParams().put("caseZpr",UserUtil.getCurrentUser().getId());
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return caseService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override
			public List<CaseListDTO> list(TableRequest request) {
				List<CaseListDTO> list = caseService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/**
	 * 获取案件内容
	 * @param id
	 * @return
	 */
	@GetMapping("/getCase/{id}")
	@ResponseBody
	public Result<CaseInfoDTO> getCaseInfoById(@PathVariable String id) {
		//根据ID获取案件
		CaseInfoDTO caseInfoDTO = this.caseService.findOne(id);
		
		String fzfryids = caseInfoDTO.getCaseFzfryid();
		
		String fzfryid[] = fzfryids.split(",");
		
		List<String> list = new ArrayList<>();
		
		for(int i=0;i<fzfryid.length;i++){
			list.add(fzfryid[i].trim());
		}
		
		caseInfoDTO.setCaseFzfryList(list);
		
		//用户校验
		/*if(!UserUtil.getCurrentUser().getId().toString().equals(caseInfoDTO.getCaseZpr())){
			return ResultUtil.toResponseWithData(ResultCode.ERROR,null);
		}*/
		
		if(caseInfoDTO.getCaseStatus()==CommonParameters.CaseStatus.AJZC){
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseInfoDTO);
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR,null);
	}
	
	/**
	 * 测试restTemplate
	 */
	@GetMapping("/test")
	public void getTestRestTemplate() throws Exception{
		
//		String caseId = "b61e0e3e-bd4a-45ac-b424-99fd7ae1a44d";
//		
//		String url = "http://localhost:8080/enforce/case/getCase/" + caseId;
//		
//		// restTemplate.getForObject(url, CaseInfoDTO.class);
//		
//		// ResponseEntity<CaseInfoDTO> response = restTemplate.getForEntity(url, CaseInfoDTO.class);
//		
		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		//JSONObject jsonObj = JSONObject.fromObject(params);
		JSONObject jsonObj  = new JSONObject();
		jsonObj.put("username", "admin");
		jsonObj.put("password", "admin");
		String loginUrl = "http://localhost:8080/enforce/sys/login";
		//HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
		//String result = restTemplate.postForObject(loginUrl, formEntity, String.class);
		//log.info("result is {}.", result);
		// String result = restTemplate.getForObject(url, String.class);
		
		
//		MultiValueMap<String, Object> postData = new LinkedMultiValueMap<String, Object>();
//		postData.add("username", "admin");
//		postData.add("password", "admin");
//		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(postData);
//		HttpEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);
//		System.out.println("headers: " + response.getHeaders());
//		System.out.println("body: " + response.getBody());
//		
//		
//		
//		HttpHeaders headerstest = response.getHeaders();
//		Iterator<Entry<String, List<String>>> iter = headerstest.entrySet().iterator();
//		if ( iter.hasNext() ) {
//			Entry<String, List<String>> list = iter.next();
//			String key = list.getKey();
//			System.out.println(key);
//			List<String> values = list.getValue();
//			if ( values != null ) {
//				for ( String value : values) {
//					
//					System.out.println(value);
//				}
//			}
//		}
		
//		String resultResponse = RestTemplateUtil.post(loginUrl, jsonObj, MediaType.APPLICATION_JSON, String.class);
//		System.out.println("===================================");
//		System.out.println(resultResponse);
		
		HttpHeaders headers = new HttpHeaders();  
        /*headers.add(HttpHeadersImpl.ACCEPT, "application/json"); 
        headers.add(HttpHeadersImpl.ACCEPT_ENCODING, "gzip"); 
        headers.add(HttpHeadersImpl.CONTENT_ENCODING, "UTF-8"); 
        headers.add(HttpHeadersImpl.CONTENT_TYPE, 
                "application/json; charset=UTF-8"); 
        headers.add(HttpHeadersImpl.COOKIE, token); 
        headers.add("Token", token);*/  
        headers.add("Accept", "application/json");  
        headers.add("Accpet-Encoding", "gzip");  
        headers.add("Content-Encoding", "UTF-8");  
        headers.add("Content-Type", "application/json; charset=UTF-8");  
        System.out.println(jsonObj.toString());
		// HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);4
        MultiValueMap<String, Object> postData = new LinkedMultiValueMap<String, Object>();
		postData.add("username", "admin");
		postData.add("password", "admin");
		HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(postData);
		
		ResponseEntity<String> exchangeResult = restTemplate.exchange(loginUrl, HttpMethod.POST, formEntity, String.class);
		HttpHeaders responseHeader = exchangeResult.getHeaders();
		List<String> cooikes = responseHeader.get("Set-Cookie");
		HttpHeaders caseheaders = new HttpHeaders();  
		// caseheaders.add(headerName, headerValue);
		for (String c : cooikes ) {
			if ( c.indexOf("JSESSIONID=") != -1 ) {
				String[] a = c.split(";");
				System.out.println("jseesion_id is : "  + a[0] + ";");
				caseheaders.add("Cookie", a[0]);
			}
		}
		
		
		
		HttpEntity<String> headerEntity = new HttpEntity<String>( caseheaders);
		
	
		
		String caseId = "b61e0e3e-bd4a-45ac-b424-99fd7ae1a44d";
		
		String url = "http://localhost:8080/enforce/case/getCase/" + caseId;
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, headerEntity, String.class);
		 
		log.info("{}" ,response.getStatusCodeValue());
		log.info("{}" ,response.getHeaders());
		log.info("{}" ,response.getStatusCode());
		log.info("{}" ,response.getBody());
		
		ResponseEntity<CaseInfoDTO> caseInfoDTO = restTemplate.exchange(url, HttpMethod.GET, headerEntity, CaseInfoDTO.class);
		
		log.info("caseName is {}", caseInfoDTO.getBody().getCaseName());
		
	}
	
	/**
	 * 获取当前登录人执法主体以及下属主体下的所有执法人员
	 * @return
	 */
	@GetMapping("/deptPerson")
	public Result<String> getDeptUserByCurrentUser(){
		String result = this.caseService.getDeptUserByCurrentUser();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, result);
		
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
		params.put("punishType", "2060");
		CaseDetailInfoDTO dto = this.caseService.queryCaseByCaseId(caseId,params);
		if(dto != null && dto.getPunishBill() != null && !dto.getPunishBill() .equals("")) {
			dto.setPunishBillUrl(serverUrl+"file/"+filesPath.split("/")[filesPath.split("/").length-1]+dto.getPunishBill());
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, dto);
	}
	
	/**
	 * 获取案件文书
	 * @param id
	 * @return
	 */
	@GetMapping("/query/docContent/{caseId}")
	public Result<List<CaseDocDTO>> queryDocContentByCaseId(@PathVariable String caseId) {
		List<CaseDocDTO> caseDocDTOs = this.caseService.queryDocContentByCaseId(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseDocDTOs);
	}
	
	/**
	 * 获取审批记录
	 * @param id
	 * @return
	 */
	@GetMapping("/flow/comment/{businessId}/{key}")
	public Result<List<FlowTaskCommentDTO>> getFlowCommentByBusiAndKey(@PathVariable String businessId, @PathVariable String key) {
		String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId(key, businessId);
		List<FlowTaskCommentDTO> comments = this.flowService.getCommemntByProcessInstanceId(pid);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, comments);
	}
	
	/**
	 * 监督人员获取案件列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/queryList")
	public TableResponse<CaseListDTO> queryCase(TableRequest request) {
		request.getParams().put("typeValue", "1002");
		
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return caseService.getQueryCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override
			public List<CaseListDTO> list(TableRequest request) {
				List<CaseListDTO> list = caseService.getQueryCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/*
	 * 案件立案关联权责事项
	 */
	@PostMapping("/updatePotence")
	public Result<Object> updataPotence(@RequestBody CaseInfoDTO caseInfoDTO) {
		CaseEntity caseEntity = new CaseEntity();
		BeanUtils.copyProperties(caseInfoDTO, caseEntity);
		caseEntity = this.caseService.updatePotence(caseEntity);
		if(caseInfoDTO.getDealType() == CommonParameters.SimpleFlow.FROM_LA_TO_DCQZ) {
			SysUserEntity SysUserEntity = sysUserRepository.findById(caseInfoDTO.getNextAssignee());
			if(!caseEntity.getCaseZzfryid().equals(SysUserEntity.getPersonId())) {
				caseEntity.setCaseWqryid(caseEntity.getCaseZzfryid());
				caseEntity.setCaseWqryname(caseEntity.getCaseZzfryname());
				caseEntity.setCaseZzfryid(SysUserEntity.getPersonId());
				caseEntity.setCaseZzfryname(SysUserEntity.getNickname());
				this.caseService.updateWqToZzf(caseEntity);
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,caseEntity);
	}
	
	/*
	 * 案件立案关联权责事项
	 */
	@PostMapping("/updateWqToZzf")
	public Result<Object> updateWqToZzf(@RequestBody CaseInfoDTO caseInfoDTO) {
		CaseEntity caseEntity = this.caseRepository.findOne(caseInfoDTO.getId());
		if(caseInfoDTO.getDealType() == CommonParameters.SimpleFlow.TO_SEARCH_PROOF) {
			SysUserEntity SysUserEntity = sysUserRepository.findById(caseInfoDTO.getNextAssignee());
			if(!caseEntity.getCaseZzfryid().equals(SysUserEntity.getPersonId())) {
				caseEntity.setCaseWqryid(caseEntity.getCaseZzfryid());
				caseEntity.setCaseWqryname(caseEntity.getCaseZzfryname());
				caseEntity.setCaseZzfryid(SysUserEntity.getPersonId());
				caseEntity.setCaseZzfryname(SysUserEntity.getNickname());
				this.caseService.updateWqToZzf(caseEntity);
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,caseEntity);
	}

	@GetMapping("/getUserByPersionId")
	public String getUserByPersionId(@Param("personId") String personId){
		User user = userService.getUserByPersonId(personId);
		return user.getId().toString();
	}
	
	/**
	 * 选择安源为“日常”或者“专项检查”时获取相对应的单据
	 */
	@GetMapping("/getCheckIdsByType/{checkType}")
	public Result<Object> getCheckIdsByType(@PathVariable String checkType) {
		if( checkType.equals(CommonParameters.CaseSourceCheckType.PRO)) {
			//专项检查
			List<LssuedDTO> lssuedDTOs = this.lssuedService.getCaseSourceCheck();
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS,lssuedDTOs);
		} else if (checkType.equals(CommonParameters.CaseSourceCheckType.DAILY)) {
			// 日常检查
			List<CheckDailyDTO> checkDailyDTOs = this.checkDailyService.getCaseSourceCheck();
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS,checkDailyDTOs);
		} else {
			return ResultUtil.toResponseWithData(ResultCode.BUSI_ERROR,"参数错误.");
		}
	}
	
	/**
	 * 判断当前登录人是否为主执法人员
	 * @param id
	 * @return
	 */
	@GetMapping("/judgeZperson")
	@ResponseBody
	public Result<Object> getJudgePerson(@RequestParam String caseId,@RequestParam String caseNum) {
		//当前登录人ID
		String personId=UserUtil.getCurrentUser().getPerson_id();
		// 获取案件或者日常检查内容
		String num = caseNum.substring(0,2);
		if(num.equals("FC")){
			//案件
			CaseEntity caseEnty= this.caseService.getCaseByCaseNum(caseNum);
			if(personId.equals(caseEnty.getCaseZzfryid())){
				return ResultUtil.toResponse(ResultCode.SUCCESS);
			}else{
				return ResultUtil.toResponse(ResultCode.ERROR);
			}
		}else if(num.equals("RC")){
			//日常检查
			CheckDailyDTO checkDailyDTO = this.checkDailyService.findOneCheckDaily(caseId);
			
			if(personId.equals(checkDailyDTO.getPersonId())){
				return ResultUtil.toResponse(ResultCode.SUCCESS);
			}else{
				return ResultUtil.toResponse(ResultCode.ERROR);
			}
			
		}else{
			return ResultUtil.toResponse(ResultCode.ERROR);
		}
	
	}

	
	/**
	 * 获取案源所关联的检查名称
	 */
	@GetMapping("/findCheckTitle")
	@ResponseBody
	public LssuedDTO getCheckNames(@RequestParam String caseSource,@RequestParam String checkId) {
		LssuedDTO lssuedDTO =new LssuedDTO();
		lssuedDTO = this.caseService.findCheckName(caseSource,checkId);
		return lssuedDTO;
	}
	
	@RequestMapping("/find/{id}")
	@ApiOperation(value = "根据专项id获取执法人员")
	public DeptPersonDTO get(@PathVariable String id) {
		DeptPersonDTO deptPersonDTO = new DeptPersonDTO();
		deptPersonDTO = this.caseService.getByCaseId(id);
		return deptPersonDTO;
	}
	
	/**
	 * 更新案件是否公示字段
	 */
	@PostMapping("/updataisps")
	public Result<Object> updataIsPs(@RequestBody CaseInfoDTO caseInfoDTO) {
		CaseEntity caseEntity = new CaseEntity();
		BeanUtils.copyProperties(caseInfoDTO, caseEntity);
		this.caseService.updateIsPs(caseEntity);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,caseEntity);
	}
	
	@GetMapping("/downLoadCaseCata")
	public Result<Object> downLoadCaseCata(@Param("caseId") String caseId){
		//根据ID获取案件
		CaseInfoDTO caseInfoDTO = this.caseService.findOne(caseId);
		if(caseInfoDTO == null){
			//获取不到案件则终止操作
			return ResultUtil.toResponseWithData(ResultCode.ERROR,null);
		}
		//格式化日期字符串
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String caseNum = caseInfoDTO.getCaseNum();
		String caseCatalogPath,split,path;
		if(SysUtil.sysIsWin()){
			caseCatalogPath = uploadConfig.getWinCaseCatalogPath();
			split = "\\";
			path = uploadConfig.getWinPath();
		}else{
			caseCatalogPath = uploadConfig.getOtherCaseCatalogPath();
			split = "/";
			path = uploadConfig.getOtherPath();
		}
		//获取预警信息
		List<YujDTO> YujDTOs = this.yujService.getWarnList(caseId);
		//生成预警信息的Excel
		//生成预警信息的Excel
		//创建表头
		String[] headers=new String[]{"预警星级","预警级别","预警内容","预警指标","预警类型","创建人姓名","创建时间","案件状态","流程类型","部门名称"};
		//用于保存列表数据
		List<Object[]> yjDTOLists = new ArrayList<Object[]>();
		
		for (YujDTO YujDTO : YujDTOs) {
			Object[] yjDTOs = new Object[10];
			yjDTOs[0]=YujDTO.getStar();
			yjDTOs[1]=YujDTO.getLevel();
			yjDTOs[2]=YujDTO.getContent();
			yjDTOs[3]=YujDTO.getType();
			yjDTOs[4]=YujDTO.getWarnType();
			yjDTOs[5]=YujDTO.getCreateName();
			yjDTOs[6]=formatter.format(YujDTO.getCreateDate());
			yjDTOs[7]=YujDTO.getRecordStatus();
			yjDTOs[8]=YujDTO.getFlowType();
			yjDTOs[9]=YujDTO.getDeptName();
			yjDTOLists.add(yjDTOs);
		}
		ExcelUtil.excelLocal(caseCatalogPath + caseNum, "预警信息列表", headers, yjDTOLists);
		System.out.println("预警信息生成完成");
		//获取流程审批记录
		String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId("case", caseNum);
		List<FlowTaskCommentDTO> comments = this.flowService.getCommemntByProcessInstanceId(pid);
		//生成流程审批记录的Excel
		//创建表头
		headers=new String[]{"责任人","任务名称","时间","批注"};
		//用于保存列表数据
		List<Object[]> commentsLists = new ArrayList<Object[]>();
		
		for (FlowTaskCommentDTO commentDTO : comments) {
			Object[] commentDTOs = new Object[4];
			commentDTOs[0]=commentDTO.getUser();
			commentDTOs[2]=formatter.format(commentDTO.getTime());
			commentDTOs[3]=commentDTO.getMsg();
			commentDTOs[1]=commentDTO.getTaskName();
			commentsLists.add(commentDTOs);
		}
		ExcelUtil.excelLocal(caseCatalogPath + caseNum, "流程审批记录", headers, commentsLists);
		System.out.println("流程审批记录生成完成");
		//创建压缩包保存目录
		new File(caseCatalogPath + "package").mkdirs();
		//生成文书
		caseService.createCaseCateLog(caseId);
		System.out.println("文书生成完成");
		//复制附件
		caseService.caseFileCopy(caseId);
		System.out.println("附件拷贝完成");
		//打压缩包
		FileUtil.Archive(caseCatalogPath + caseNum, caseCatalogPath + "package" + split + caseNum + ".zip");
		System.out.println("案件档案归档完成");
		//生成归档文件下载链接
		String downLoadUrl = uploadConfig.getServerUrl()+ "file/"+caseCatalogPath.substring(path.length()).replaceAll("\\\\+", "/") + "package/" + caseNum + ".zip";
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,downLoadUrl);
	}
	
}
