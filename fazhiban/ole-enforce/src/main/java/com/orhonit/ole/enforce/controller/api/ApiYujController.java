package com.orhonit.ole.enforce.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.RoleUtil;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dao.YujDao;
import com.orhonit.ole.enforce.dto.DeptDTO;
import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.YujDTO;
import com.orhonit.ole.enforce.dto.YujPersonCountDTO;
import com.orhonit.ole.enforce.dto.YujPersonDTO;
import com.orhonit.ole.enforce.dto.api.ApiCountDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.entity.WarnInfoEntity;
import com.orhonit.ole.enforce.entity.WarnPersonEntity;
import com.orhonit.ole.enforce.repository.CaseRepository;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.dept.DeptService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.enforce.service.yuj.YujService;
import com.orhonit.ole.enforce.utils.PageList;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dao.SysDeptDao;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.AreaEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.AreaRepository;
import com.orhonit.ole.sys.service.UserService;

/**
 * 预警控制器 1. 总数 2. 级别统计（红黄蓝分组） 3. 预警列表 4. 预警详情 5. 预警处理 6. 添加实时预警
 * 
 * @author zhangjy
 *
 */
@RestController
@RequestMapping("/api/yuj")
public class ApiYujController {

	@Autowired
	private YujService yujService;

	@Autowired
	private YujDao yujDao;

	@Autowired
	private DeptService deptService;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SysDeptDao sysDeptDao;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private CheckDailyService checkDailyService;

	@Autowired
	private LssuedService lssuedService;

	/**
	 * 1.预警总数
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/count")
	public Result<Object> count(@RequestParam(value = "deal", required = false) String deal,
			@RequestParam(value = "deptId", required = false) String deptId) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		/* User user = this.userService.getUserByPersonId(personDTO.getId()); */

		if (!StringUtils.isEmpty(deptId)) {
			if (!deptId.equals("null")) {
				params.put("deptId", deptId);
			}
		} else {
			if (!StringUtils.isEmpty(deal)) {
				if (!deal.equals("null")) {
					try {
						Integer.valueOf(deal);
					} catch (NumberFormatException e) {
						return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
					}
					params.put("deal", Integer.valueOf(deal));
				}
			}
			params.put("pid", personDTO.getId());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		int count = yujDao.YuJcount(params);
		result.put("yujCount", count);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, result);
	}

	/**
	 * 2.级别统计（红黄蓝分组）
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/levelCount")
	public Result<Object> levelCount(@RequestParam(value = "deptId", required = false) String deptId,
			@RequestParam(value = "deal", required = false) String deal,
			@RequestParam(value = "type", required = false) String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		if (!StringUtils.isEmpty(deptId)) {
			if (!deptId.equals("null")) {
				if (dept.getLaw_type().equals(CommonParameters.LawType.JD)) {
					if (type.equals("1")) {
						params.put("type", type);
						params.put("deptId", "");
					} else {
						params.put("deptId", deptId);
					}
				} else {
					params.put("deptId", deptId);
				}
			}
		} else {
			// 获取登录人角色
			List<PersonAppDTO> personAppDTO = this.personDao.getPersonRole(personDTO.getId());
			List<Integer> roleId = new ArrayList<Integer>();
			for (int i = 0; i < personAppDTO.size(); i++) {
				if (personAppDTO.get(i).getRoleId() == null) {
					return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
							ResultCode.APP_ROLE_ERROR.getMsg());
				} else {
					roleId.add(personAppDTO.get(i).getRoleId());
				}
			}

			if (roleId.contains(CommonParameters.Role.LAW_ENFORCEMENT_OFFICIALS)) {
				params.put("personId", personDTO.getId());
			}
		}

		if (!StringUtils.isEmpty(deal)) {
			params.put("deal", deal);
		}

		List<ApiCountDTO> list = yujDao.levelCount(params);

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}

	/**
	 * 3.预警列表
	 * 
	 * @param start
	 *            开始位置
	 * @param length
	 *            长度
	 * @param deal
	 *            处理 1，未处理 2
	 **/
	@GetMapping("/list")
	public Result<Object> list(@RequestParam(value = "start", required = false, defaultValue = "1") Integer start,
			@RequestParam(value = "length", required = false, defaultValue = "20") Integer length,
			@RequestParam(value = "deal", required = false) String deal,
			@RequestParam(value = "level", required = false) String level,
			@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "warnType", required = false) String warnType,
			@RequestParam(value = "deptId", required = false) String deptId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "star", required = false) String star) {
		try {
			Integer.valueOf(start);
			Integer.valueOf(length);
		} catch (NumberFormatException e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "参数错误，必须传整型");
		}
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
//		PageList<YujDTO> yujList = new PageList<>();
		PageList<PsCaseDTO> caseList = new PageList<>();
		YujDTO yujDTO = new YujDTO();
		yujDTO.setCurrentPage(start < 1 ? 1 : start);
		yujDTO.setPageSize(length);
		yujDTO.setIsDeal(deal);
		yujDTO.setLevel(level);
		yujDTO.setWarnType(warnType);
		yujDTO.setType(type);
		yujDTO.setCreateBy(user.getUsername());
		yujDTO.setCaseName(caseName);
		yujDTO.setStar(star);
		yujDTO.setPersonId(personDTO.getId());
		if(null != dept && null != dept.getLaw_type() && !StringUtils.isEmpty(dept.getLaw_type())) {
			if("2".equals(dept.getLaw_type())){
				yujDTO.setAreaId(dept.getArea_id());
			}else{
				yujDTO.setDeptId(deptId);
			}
		}
//		if (!StringUtils.isEmpty(warnType)) {
//			if (!warnType.equals("null")) {
//				params.put("warnType", warnType);
//
//				if (warnType.equals("3")) {
//					params.put("createBy", user.getUsername());
//				}
//			}
//		}

//		List<YujDTO> list = yujService.appList(params, Integer.valueOf(start), Integer.valueOf(length));
		caseList = yujService.appListByRealTime(yujDTO);

		// 统计预警已处理未处理
//		if(null != caseList && null != caseList.getDatas() && 0 < caseList.getDatas().size()) {
//			for (int i = 0; i < caseList.getDatas().size(); i++) {
//				
//				List<YujPersonCountDTO> personCount = this.yujService.getWarnPersonCount(list.get(i).getId());
//				
//				caseList.getDatas().get(i).setYujPersonCount(personCount);
//			}
//		}

//		result.put("YujDTO", list);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseList);
	}
	
	@GetMapping("/listWarnInfo")
	public Result<PageList<YujDTO>> listWarnInfo(@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "deal", required = false) String deal,
			@RequestParam(value = "level", required = false) String level,
			@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "warnType", required = false) String warnType,
			@RequestParam(value = "deptId", required = false) String deptId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "star", required = false) String star) {
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		PageList<YujDTO> pageList = new PageList<>();
		YujDTO yujDTO = new YujDTO();
		yujDTO.setCurrentPage(start < 1 ? 1 : start);
		yujDTO.setPageSize(length);
		yujDTO.setIsDeal(deal);
		yujDTO.setLevel(level);
		yujDTO.setType(type);
		yujDTO.setQueryDate(queryDate);
		yujDTO.setCaseName(caseName);
		yujDTO.setStar(star);
		yujDTO.setPersonId(personDTO.getId());
		if("2".equals(dept.getLaw_type())){
			yujDTO.setAreaId(dept.getArea_id());
		}else{
			yujDTO.setDeptId(deptId);
		}
		if (!StringUtils.isEmpty(warnType)) {
			if (!warnType.equals("null")) {
				yujDTO.setWarnType(warnType);
				if (warnType.equals("3")) {
					yujDTO.setCreateBy(user.getUsername());
				}
			}
		}

		pageList = yujService.appListWarnInfo(yujDTO);

		// 统计预警已处理未处理
		if(null != pageList && null != pageList.getDatas() && 0 < pageList.getDatas().size()) {
			for (int i = 0; i < pageList.getDatas().size(); i++) {
				
				List<YujPersonCountDTO> personCount = this.yujService.getWarnPersonCount(pageList.getDatas().get(i).getId());
				
				pageList.getDatas().get(i).setYujPersonCount(personCount);
				
			}
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, pageList);
	}
	
	public Result<Object> listsss(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "deal", required = false) String deal,
			@RequestParam(value = "level", required = false) String level,
			@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "warnType", required = false) String warnType,
			@RequestParam(value = "deptId", required = false) String deptId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "star", required = false) String star) {
		try {
			Integer.valueOf(start);
			Integer.valueOf(length);
		} catch (NumberFormatException e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "参数错误，必须传整型");
		}
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		Map<String, Object> params = new HashMap<String, Object>();

		DeptDTO dept = new DeptDTO();
		if (!StringUtils.isEmpty(deptId) && !user.isAdmin() && !deptId.equals("null")) {
			// 获取主体信息
			dept = this.deptService.findDeptInfoById(deptId);

			if (dept != null) {
				if (dept.getDept_property() == CommonParameters.DeptProperty.XZJG
						&& dept.getLaw_type().equals(CommonParameters.LawType.JD)) {
					if (type.equals("1")) {
						AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(dept.getArea_id()));
						// 如果是市本级的法制办则显示所有
						if (!CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
							// 旗县区法制办
							deptId = this.sysDeptDao.deptListByAreaId(dept.getArea_id());
//							params.put("deptIds", deptId);
							params.put("deptIds", dept.getId() + "," + deptId);
						}
					} else {
						deptId = this.sysDeptDao.deptListByParentIdString(dept.getId());
						params.put("deptIds", dept.getId() + "," + deptId);
					}
				} else {
					deptId = this.sysDeptDao.deptListByParentIdString(dept.getId());
					params.put("deptIds", dept.getId() + "," + deptId);
				}
			} else {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "部门不存在");
			}
		} else {
			params.put("pid", personDTO.getId());
		}

		if (!StringUtils.isEmpty(warnType)) {
			if (!warnType.equals("null")) {
				params.put("warnType", warnType);

				if (warnType.equals("3")) {
					params.put("createBy", user.getUsername());
				}
			}
		}

		if (!StringUtils.isEmpty(star)) {
			if (!warnType.equals("null")) {
				params.put("star", star);
			}
		}

		if (!StringUtils.isEmpty(level)) {
			if (!level.equals("null")) {
				params.put("level", Integer.valueOf(level));
			}
		}

		if (!StringUtils.isEmpty(caseName)) {
			if (!caseName.equals("null")) {
				params.put("caseName", caseName);
			}
		}

		if (!StringUtils.isEmpty(deal)) {
			if (!deal.equals("null")) {
				params.put("deal", Integer.valueOf(deal));
			}
		}

		if (!StringUtils.isEmpty(queryDate)) {
			if (!queryDate.equals("null")) {
				params.put("queryDate", Integer.valueOf(queryDate));
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();

		List<YujDTO> list = yujService.appList(params, Integer.valueOf(start), Integer.valueOf(length));

		// 统计预警已处理未处理
		for (int i = 0; i < list.size(); i++) {

			List<YujPersonCountDTO> personCount = this.yujService.getWarnPersonCount(list.get(i).getId());

			list.get(i).setYujPersonCount(personCount);

		}

		result.put("YujDTO", list);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, result);
	}

	/**
	 * 4.预警详情
	 * 
	 * @param yujId
	 * @param personId
	 *            查自己的预警传
	 * @param request
	 * @return
	 */
	@GetMapping("/query")
	public Result<Object> queryByYujId(@RequestParam(value = "warnId", required = false) String warnId,
			@RequestParam(value = "personId", required = false) String personId) {
		if (StringUtils.isEmpty(warnId)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "预警ID为空.");
		}

		YujDTO yujDTO = this.yujService.getWarnInfoByWarnId(warnId);

		Map<String, Object> params = new HashMap<>();
		params.put("warnId", warnId);
		params.put("personId", personId);

		List<YujPersonDTO> list = this.yujService.getWarnPerson(params);

		List<YujPersonCountDTO> personCount = this.yujService.getWarnPersonCount(warnId);

		yujDTO.setYujPersons(list);
		yujDTO.setYujPersonCount(personCount);

		Map<String, Object> result = new HashMap<>();
		result.put("yujDTO", yujDTO);

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, result);

	}

	/**
	 * 5.预警处理
	 * 
	 * @param yujId
	 * @param dealResult
	 * @return
	 */
	@PostMapping("/warnDeal")
	public Result<Object> up(@RequestParam(value = "yujId", required = false) String warnId,
			@RequestParam(value = "dealResult", required = false) String dealResult) {
		if (StringUtils.isEmpty(warnId)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "预警ID为空.");
		}
		if (StringUtils.isEmpty(dealResult)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "处理结果为空.");
		}

		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		YujPersonDTO yujPersonDTO = new YujPersonDTO();
		yujPersonDTO.setPersonId(personDTO.getId());
		yujPersonDTO.setWarnId(warnId);
		yujPersonDTO.setDealResult(dealResult);
		yujPersonDTO.setIsDeal(CommonParameters.YuJChuL.YCL);
		yujPersonDTO.setUpdateBy(user.getUsername());
		yujPersonDTO.setUpdateDate(new Date());
		yujPersonDTO.setUpdateName(user.getNickname());

		YujDTO yujDTO = new YujDTO();
		yujDTO.setId(warnId);
		yujDTO.setUpdateBy(user.getUsername());
		yujDTO.setUpdateDate(new Date());
		yujDTO.setUpdateName(user.getNickname());

		try {
			this.yujService.warnDeal(yujPersonDTO);
			this.yujService.warnUpdate(yujDTO);
		} catch (Exception e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_YUJ_FAIL.getCode(), "预警处理失败！");
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "处理成功！");
	}

	/**
	 * 6.添加实时预警
	 * 
	 * @param level
	 *            预警级别
	 * @param content
	 *            预警内容
	 * @param recordCode
	 *            案件或检查编号
	 * @param mode
	 *            类型：日常检查5;专项检查4;案件6;
	 * @param personIds
	 *            被预警人员ID
	 * @return
	 */
	@PostMapping("/warnAdd")
	public Result<Object> realTimeWarnAdd(@RequestParam(value = "level", required = false) int level,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "recordCode", required = false) String recordCode,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "personIds", required = false) List<String> personIds) {

		if (StringUtils.isEmpty(level)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "预警级别为空！");
		}

		if (StringUtils.isEmpty(content)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "预警内容为空！");
		}

		if (StringUtils.isEmpty(recordCode)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空！");
		}

		if (StringUtils.isEmpty(personIds)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "预警人员为空！");
		}

		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		// 保存预警信息
		WarnInfoEntity warnInfoEntity = new WarnInfoEntity();

		// 查询案件或检查信息
		if (mode.equals(CommonParameters.FlowType.SPECIAL_CHECK)) {
			// 专项检查

			LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(recordCode);
			warnInfoEntity.setFlowType(CommonParameters.FlowType.SPECIAL_CHECK);
			warnInfoEntity.setRecordTitle(lssuedEntity.getCheckTitle());
			warnInfoEntity.setRecordId(lssuedEntity.getId());
			warnInfoEntity.setRecordStatus(lssuedEntity.getStatus());
			warnInfoEntity.setType(CommonParameters.Type.WARN_LSSUED);

		} else if (mode.equals(CommonParameters.FlowType.DAILY_CHECK)) {
			// 日常检查

			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(recordCode);
			warnInfoEntity.setFlowType(CommonParameters.FlowType.DAILY_CHECK);
			warnInfoEntity.setRecordTitle(checkDailyEntity.getCheckTitle());
			warnInfoEntity.setRecordId(checkDailyEntity.getId());
			warnInfoEntity.setRecordStatus(checkDailyEntity.getStatus());
			warnInfoEntity.setType(CommonParameters.Type.WARN_DAILY);

		} else if (mode.equals(CommonParameters.FlowType.CASE)) {
			// 案件
			CaseEntity caseEntity = this.caseRepository.findByCaseNum(recordCode);
			warnInfoEntity.setFlowType(caseEntity.getFlowType());
			warnInfoEntity.setRecordTitle(caseEntity.getCaseName());
			warnInfoEntity.setRecordId(caseEntity.getId());
			warnInfoEntity.setRecordStatus(caseEntity.getCaseStatus().toString());
			if (caseEntity.getFlowType().equals(CommonParameters.FlowType.MAJOR_CASE)) {
				warnInfoEntity.setType(CommonParameters.Type.WARN_MAJOR_CASE);
			} else {
				warnInfoEntity.setType(CommonParameters.Type.WARN_CASE);
			}

		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "mode错误！");
		}

		warnInfoEntity.setId(UUID.randomUUID().toString());
		warnInfoEntity.setLevel(level);
		warnInfoEntity.setContent(content);
		warnInfoEntity.setRecordCode(recordCode);
		warnInfoEntity.setTaskId("0");
		warnInfoEntity.setWarnType(CommonParameters.WarnType.SSYJ);

		// 获取当前登录人角色
		List<PersonAppDTO> persons = this.personDao.getPersonRole(personDTO.getId());

		List<String> roles = new ArrayList<String>();

		for (PersonAppDTO person : persons) {
			roles.add(person.getRoleId().toString());
		}

		warnInfoEntity.setStar(RoleUtil.getStarByRoles(roles));
		warnInfoEntity.setCreateDate(new Date());
		warnInfoEntity.setCreateBy(user.getUsername());
		warnInfoEntity.setCreateName(user.getNickname());

		List<WarnPersonEntity> list = new ArrayList<>();
		if (personIds.size() > 0) {
			for (String personId : personIds) {
				WarnPersonEntity warnPersonEntity = new WarnPersonEntity();
				// 查询被预警人的部门
				User person = this.userService.getUserByPersonId(personId);

				warnPersonEntity.setId(UUID.randomUUID().toString());
				warnPersonEntity.setPersonId(personId);
				warnPersonEntity.setDeptId(person.getDept_id());
				warnPersonEntity.setWarnId(warnInfoEntity.getId());
				warnPersonEntity.setIsDeal(CommonParameters.YuJChuL.WCL);
				warnPersonEntity.setCreateDate(new Date());
				warnPersonEntity.setCreateBy(user.getUsername());
				warnPersonEntity.setCreateName(user.getNickname());

				list.add(warnPersonEntity);

			}
		}

		try {
			if (warnInfoEntity != null) {
				this.yujService.saveWarnInfo(warnInfoEntity);
			}

			if (list != null && list.size() > 0) {
				this.yujService.saveWarnPerson(list);
			}
		} catch (Exception e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_YUJ_FAIL.getCode(), "预警添加失败！");
		}

		for (int i = 0; i < list.size(); i++) {
			User person = this.userService.getUserByPersonId(personIds.get(i));
			try {
				ApiMessagePushController.send("您有一条案件被预警，请点击查看...", person.getUsername(), "2", warnInfoEntity.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "预警添加成功！");
	}

	/**
	 * 7.星级统计（红黄蓝分组）
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/starCount")
	public Result<Object> starCount(@RequestParam(value = "deptId", required = false) String deptId,
			@RequestParam(value = "type", required = false) String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		if (!StringUtils.isEmpty(deptId)) {
			if (!deptId.equals("null")) {
				if (dept.getLaw_type().equals(CommonParameters.LawType.JD)) {
					if (type.equals("1")) {
						params.put("type", type);
						params.put("deptId", "");
					} else {
						params.put("deptId", deptId);
					}
				} else {
					params.put("deptId", deptId);
				}
			}
		} else {
			// 获取登录人角色
			List<PersonAppDTO> personAppDTO = this.personDao.getPersonRole(personDTO.getId());
			List<Integer> roleId = new ArrayList<Integer>();
			for (int i = 0; i < personAppDTO.size(); i++) {
				if (personAppDTO.get(i).getRoleId() == null) {
					return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
							ResultCode.APP_ROLE_ERROR.getMsg());
				} else {
					roleId.add(personAppDTO.get(i).getRoleId());
				}
			}

			if (roleId.contains(CommonParameters.Role.LAW_ENFORCEMENT_OFFICIALS)) {
				params.put("personId", personDTO.getId());
			}
		}

		List<ApiCountDTO> list = yujDao.starCount(params);

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
}
