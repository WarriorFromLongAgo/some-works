package com.orhonit.ole.tts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.RoleUtil;
import com.orhonit.ole.sys.model.Role;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.RoleService;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dto.CaseDetailInfoDTO;
import com.orhonit.ole.tts.dto.CaseDocDTO;
import com.orhonit.ole.tts.dto.CaseListDTO;
import com.orhonit.ole.tts.dto.DeptDTO;
import com.orhonit.ole.tts.dto.FlowTaskCommentDTO;
import com.orhonit.ole.tts.entity.AttachFileEntity;
import com.orhonit.ole.tts.entity.CaseEntity;
import com.orhonit.ole.tts.entity.CheckDailyEntity;
import com.orhonit.ole.tts.entity.DeptEntity;
import com.orhonit.ole.tts.entity.WarnInfoEntity;
import com.orhonit.ole.tts.entity.WarnPersonEntity;
import com.orhonit.ole.tts.repository.AttachFileRepository;
import com.orhonit.ole.tts.repository.CaseRepository;
import com.orhonit.ole.tts.repository.CheckDailyRepository;
import com.orhonit.ole.tts.repository.DeptRepository;
import com.orhonit.ole.tts.service.checkQuery.CheckQueryService;
import com.orhonit.ole.tts.service.flow.FlowService;
import com.orhonit.ole.tts.service.realtime.RealTimeService;
import com.orhonit.ole.tts.service.warn.WarnInfoService;
import com.orhonit.ole.tts.service.warn.WarnPersonService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/realTime")
public class RealTimeController {
	
	@Autowired
	private RealTimeService realTimeService;
	
	@Autowired
	AttachFileRepository attachFileRepository;
	
	@Autowired
	CaseRepository caseRepository;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private WarnInfoService warnInfoService;
	
	@Autowired
	private WarnPersonService warnPersonService;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private CheckDailyRepository checkDailyRepository;

	@Autowired
	private CheckQueryService checkQueryService;
	
	@Autowired
	private RoleService roleService;
	/**
	 * 获取案件列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CaseListDTO> queryCase(TableRequest request) {
		request.getParams().put("typeValue", "1002");
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {
			@Override
			public int count(TableRequest request) {
				return realTimeService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {
			@Override
			public List<CaseListDTO> list(TableRequest request) {
				List<CaseListDTO> list = realTimeService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/**
	 * 获取当前主体及其下级主体树
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/dept")
	@ApiOperation(value = "主体")
	public List<Map<String, Object>> deptAll() {
		User user = UserUtil.getCurrentUser();
		DeptEntity dept = null;
		if(!user.getUsername().equals("admin")){
			dept = this.deptRepository.findOne(user.getDept_id());
		}
		List<DeptDTO> deptTree = null;
		if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
			deptTree = realTimeService.deptTreeAll();
		}else if(dept != null && dept.getDeptProperty() == 3 && dept.getLawType().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				deptTree = realTimeService.deptTreeByAreaId(dept.getAreaId());
			}else{
				deptTree = realTimeService.deptTreeAll();
			}
		}
		else{
			//委办局
			deptTree = realTimeService.deptTreeByDeptId(user.getDept_id());
		}
		
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(DeptDTO per:deptTree){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parent_id",per.getParent_id());
			map.put("text", per.getName());
			map.put("refId", per.getId());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parent_id", "id",user.getDept_id());
		return retMap;
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
		CaseDetailInfoDTO dto = this.realTimeService.queryCaseByCaseId(caseId,params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, dto);
	}
	
	/**
	 * 获取附件列表
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/caseFileList/{caseId}")
	public Result<List<AttachFileEntity>> caseFileList(@PathVariable String caseId) {
		if(caseId!=null && !"".equals(caseId)){
			List<AttachFileEntity> entitys = attachFileRepository.findAllByCaseId(caseId);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, entitys);
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR, null);
	}
	
	/**
	 * 获取案件文书
	 * @param id
	 * @return
	 */
	@GetMapping("/query/docContent/{caseId}")
	public Result<List<CaseDocDTO>> queryDocContentByCaseId(@PathVariable String caseId) {
		List<CaseDocDTO> caseDocDTOs = this.realTimeService.queryDocContentByCaseId(caseId);
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
	 * 获取案件详情
	 * @param id
	 * @return
	 */
	@GetMapping("/caseByNum")
	public Result<CaseEntity> getCaseByNum(@RequestParam String caseNum) {
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseRepository.findByCaseNum(caseNum));
	}
	
	/**
	 * 获取日常检查详情
	 * @param id
	 * @return
	 */
	@GetMapping("/checkDailyByNum")
	public Result<CheckDailyEntity> checkDailyByNum(@RequestParam String checkNum) {
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkDailyRepository.findByCheckNum(checkNum));
	}
	
	/**
	 * 获取专项检查详情
	 * @param id
	 * @return
	 */
	@GetMapping("/checkByNum")
	public Result<Map<String,Object>> checkByNum(@RequestParam String checkNum) {
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkQueryService.getCheck(checkNum));
	}
	
	/**
	 * 添加实时预警
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/warnAdd")
	public Result<Object> realTimeWarnAdd(@RequestBody Map<String,Object> warn) {
		List<String> personIds = (ArrayList<String>)warn.get("personIds");
		List<WarnPersonEntity> warnPersonEntitys = new ArrayList<>();
		User user = UserUtil.getCurrentUser();
		List<Role> roles = this.roleService.listByUserId(user.getId());
		List<String> roleStrs=new ArrayList<>();
		for(Role role:roles){
			roleStrs.add(String.valueOf(role.getId()));
		}
		if(personIds!=null){
			WarnInfoEntity warnInfoEntity = new WarnInfoEntity();
			warnInfoEntity.setId(UUID.randomUUID().toString());
			warnInfoEntity.setContent(warn.get("content").toString());
			warnInfoEntity.setLevel(Integer.parseInt(warn.get("level").toString()));
			warnInfoEntity.setWarnType(warn.get("warnType").toString());
			warnInfoEntity.setType((Integer) warn.get("type"));
			warnInfoEntity.setRecordCode(warn.get("recordCode").toString());
			warnInfoEntity.setRecordId(warn.get("recordId").toString());
			warnInfoEntity.setRecordTitle(warn.get("recordTitle").toString());
			warnInfoEntity.setRecordStatus(warn.get("recordStatus").toString());
			warnInfoEntity.setFlowType(warn.get("flowType").toString());
			warnInfoEntity.setStar(RoleUtil.getStarByRoles(roleStrs));
			warnInfoEntity.setTaskId("0");
			warnInfoEntity.setCreateDate(new Date());
			warnInfoEntity.setCreateBy(user.getUsername());
			warnInfoEntity.setCreateName(user.getNickname());
			this.warnInfoService.save(warnInfoEntity);
			WarnPersonEntity warnPersonEntity;
			for (String personId : personIds) {
				warnPersonEntity=new WarnPersonEntity();
				warnPersonEntity.setId(UUID.randomUUID().toString());
				warnPersonEntity.setWarnId(warnInfoEntity.getId());
				warnPersonEntity.setIsDeal(CommonParameters.YuJChuL.WCL);
				warnPersonEntity.setDeptId(warn.get("deptId").toString());
				warnPersonEntity.setPersonId(personId);
				warnPersonEntity.setCreateDate(new Date());
				warnPersonEntity.setCreateBy(user.getUsername());
				warnPersonEntity.setCreateName(user.getNickname());
				warnPersonEntitys.add(warnPersonEntity);
			}
			this.warnPersonService.save(warnPersonEntitys);
		}
		return ResultUtil.success();
	}
	
	/**
	 * 获取日常检查列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/checkDailyList")
	public TableResponse<CaseListDTO> checkDailyList(TableRequest request) {
		
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return realTimeService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override
			public List<CaseListDTO> list(TableRequest request) {
				request.getParams().put("typeValue", "1002");
				List<CaseListDTO> list = realTimeService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}

	/**
	 * 获取专项检查列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/checkList")
	public TableResponse<CaseListDTO> checkList(TableRequest request) {
		
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return realTimeService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override
			public List<CaseListDTO> list(TableRequest request) {
				request.getParams().put("typeValue", "1002");
				List<CaseListDTO> list = realTimeService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
}
