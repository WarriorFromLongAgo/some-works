package com.orhonit.ole.enforce.controller.shareapi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dao.CaseDao;
import com.orhonit.ole.enforce.dto.DeptPersonDTO;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.CheckTypeEntity;
import com.orhonit.ole.enforce.service.casedeal.CaseDealService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.checktype.CheckTypeService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;

/*
 * 日常检查相关接口
 */
@RestController
@RequestMapping("/shareapi/checkDaily")
public class ShareApiCheckDailyController {

	@Autowired
	private UserService userService;

	@Autowired
	private CheckDailyService checkDailyService;

	@Autowired
	private FlowService flowService;

	@Autowired
	private CaseDealService caseDealService;

	@Autowired
	private CheckTypeService checkTypeService;
	
	@Autowired
	private CaseDao caseDao;

	/** 
	 * 日常检查保存提交接口
	 * @param businessId   检查编号
	 * @param comment    处理意见
	 * @param handleMode  处理方式
	 * @return
	 */
	@PostMapping("/saveDailyCheck")
	@Transactional
	public Result<Object> submitDailyCheck(@RequestParam(value = "businessId", required = false) String businessId,
			@RequestParam(value = "comment", required = false) String comment,
			@RequestParam(value = "handleMode", required = false) String handleMode) {

		if (StringUtils.isEmpty(businessId)) {
			// 检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查编号不能为空");
		}
		if (StringUtils.isEmpty(comment)) {
			// 处理意见
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见不能为空");
		}
		if (StringUtils.isEmpty(handleMode)) {
			// 处理方式
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理方式不能为空.");
		}
		
		CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(businessId);
		
		if ( checkDailyEntity == null ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "日常检查编号错误");
		}
		
		if ( !checkDailyEntity.getStatus().equals(CommonParameters.CheckStatus.CHECK_ZC.toString())) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "日常检查单状态错误");
		}
		
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		
		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setBusinessId(businessId);
		flowDTO.setHandleMode(handleMode);
		flowDTO.setComment(comment);
		flowDTO.setAssignee(user.getId().toString());
		flowDTO.setFlowKey(CommonParameters.FlowKey.DAY_CHECK);
		flowDTO.setNextAssignee(user.getId().toString());
		
		this.flowService.startFlowByKey(flowDTO);
		this.caseDealService.appSaveTaskEntity(flowDTO, true, user);
	    return ResultUtil.toResponse(ResultCode.SUCCESS);
	    
	}

	/**
	 *  日常检查保存接口
	 */
	@PostMapping("/save")
	@Transactional
	public Result<Object> save(@RequestParam(value = "checkTitle", required = false) String checkTitle,
			@RequestParam(value = "checkMode", required = false) String checkMode,
			@RequestParam(value = "roadName", required = false) String roadName,
			@RequestParam(value = "checkedDate", required = false) String checkedDate,
			@RequestParam(value = "checkObjectType", required = false) String checkObjectType,
			@RequestParam(value = "assistPersonId", required = false) String assistPersonId,
			@RequestParam(value = "checkedUserName", required = false) String checkedUserName,
			@RequestParam(value = "checkedUserId", required = false) String checkedUserId,
			@RequestParam(value = "registNum", required = false) String registNum,
			@RequestParam(value = "legalPerson", required = false) String legalPerson,
			@RequestParam(value = "checkSituation", required = false) String checkSituation,
			@RequestParam(value = "unitName", required = false) String unitName,
			@RequestParam(value = "lng", required = false) String lng,
			@RequestParam(value = "lat", required = false) String lat) {

		if (StringUtils.isEmpty(checkTitle)) {
			// 检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查标题不能为空");
		}
		if (StringUtils.isEmpty(checkMode)) {
			// 检查类型
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查类型不能为空");
		}
		if (StringUtils.isEmpty(checkedDate)) {
			// 检查时间
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查时间为空.");
		}
		if (StringUtils.isEmpty(checkObjectType)) {
			// 检查对象类型 1：个人 2：单位
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查对象类型为空.");
		}
		if (StringUtils.isEmpty(assistPersonId)) {
			// 协办人
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "协办人为空.");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		CheckDailyEntity checkEntity = new CheckDailyEntity();
		// String checkNum=checkDailyService.getCheckNum(user);
		// checkEntity.setCheckNum(checkNum);
		checkEntity.setCheckTitle(checkTitle);
		List<CheckTypeEntity> checkTypeByDeptId = this.checkTypeService.checkTypeByDeotId(user.getDept_id());
		for (CheckTypeEntity checkTypeEntity : checkTypeByDeptId) {
			if ( checkMode.equals(checkTypeEntity.getId().toString())) {
				checkEntity.setCheckMode(checkTypeEntity.getTitle());
			}
		}
		checkEntity.setCheckModeId(checkMode);
		checkEntity.setRoadName(roadName);
		try {
			checkEntity.setCheckedDate(sdf.parse(checkedDate));
		} catch (Exception e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查时间格式不正确");
		}
		checkEntity.setCheckObjectType(checkObjectType);
		checkEntity.setAssistPersonId(assistPersonId);
		checkEntity.setCheckedUserName(checkedUserName);
		checkEntity.setCheckedUserId(checkedUserId);
		checkEntity.setRegistNum(registNum);
		checkEntity.setLegalPerson(legalPerson);
		checkEntity.setCheckSituation(checkSituation);
		checkEntity.setUnitName(unitName);
		checkEntity.setLat(Double.valueOf(lat));
		checkEntity.setLng(Double.valueOf(lng));

		this.checkDailyService.saveAppCheckInfo(checkEntity, user);

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkEntity);
	}

	/**
	 * 根据部门ID获取检查类型
	 * @param deptID
	 *            部门ID
	 * @return
	 */
	@GetMapping("/checkType")
	public List<Map<String, Object>> getCheckTypeByDeptId() {

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		List<CheckTypeEntity> checkTypeByDeptId = this.checkTypeService.checkTypeByDeotId(user.getDept_id());

		List<Map<String, Object>> list = new ArrayList<>();

		Map<String, Object> map;
		for (CheckTypeEntity per : checkTypeByDeptId) {
			map = new HashMap<>();
			map.put("id", per.getId());
			map.put("parentId", "");
			map.put("text", per.getTitle());
			list.add(map);
		}
		List<Map<String, Object>> retMap = AppUtil.list2Tree(list, "parentId", "id", null);

		return retMap;
	}
	
	/**
	 * 查询日常检查协办人列表
	 * @return
	 */
	@GetMapping("/assist")
	public Result<Object> getAssist() {
		
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		List<DeptPersonDTO> deptPersonDTOs = this.caseDao.getDeptUserByCurrentUser(user.getDept_id(), CommonParameters.Role.LAW_ENFORCEMENT_OFFICIALS);
		if ( !deptPersonDTOs.isEmpty() ) {
			int index = -1;
			for ( int i = 0 ; i < deptPersonDTOs.size() ; i++ ) {
				if ( deptPersonDTOs.get(i).getPersonId().equals(personDTO.getId())) {
					index = i;
				}
			}
			if ( index != -1 ) {
				deptPersonDTOs.remove(index);
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, deptPersonDTOs);
	}
}
