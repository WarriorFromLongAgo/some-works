package com.orhonit.ole.enforce.controller.api;

import java.text.SimpleDateFormat;
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
import com.orhonit.ole.enforce.config.UploadConfig;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dto.CaseDocDTO;
import com.orhonit.ole.enforce.dto.CheckDocDTO;
import com.orhonit.ole.enforce.dto.FlowCommentDTO;
import com.orhonit.ole.enforce.dto.FlowTaskCommentDTO;
import com.orhonit.ole.enforce.dto.NextOpinionDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.CheckRecordEntity;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.repository.AttachFileRepository;
import com.orhonit.ole.enforce.repository.CaseRepository;
import com.orhonit.ole.enforce.repository.CheckRecordRepository;
import com.orhonit.ole.enforce.repository.MessagePushRepository;
import com.orhonit.ole.enforce.service.casedeal.CaseDealService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.checkQuery.CheckQueryService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * APP流程相关控制器 
 * 1. 审批意见查询 
 * 2. 初步核实提交 
 * 3. 案件审核审批的提交 
 * 4. 日常检查提交 
 * 5. 流程审批记录查询 
 * 6. 文书列表 
 * 7. 现场处理提交 
 * 8. 一般、简易案件结案归档 
 * 9. 日常领导审核审批提交 
 * 10.专项领导审核审批意见提交 
 * 11.获取当前登录人所在部门的执法人员列表
 * 12.获取当前登录人所在部门的并且有审核权限的人员列表 
 * 13.获取当前登录人所在部门的并且有审批权限的人员列表 
 * 14.立案提交 
 * 15.流程前进
 * 16.先行告知提交 1
 * 7.案件处理提交 
 * 18.专项检查现场处理提交
 * 19.案件合议提交 
 * 20.获取当前登录人所在部门的并且有法制受理权限的人员列表
 * 
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/api/flow")
public class ApiFlowController {

	@Autowired
	private FlowService flowService;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private CaseService caseService;

	@Autowired
	private CaseDealService caseDealService;

	@Autowired
	private UserService userService;

	@Autowired
	private CheckDailyService checkDailyService;

	@Autowired
	private LssuedService lssuedService;

	@Autowired
	private CheckQueryService checkQueryService;

	@Autowired
	private CheckRecordRepository checkRecordRepository;

	@Autowired
	UploadConfig uploadConfig;

	@Autowired
	AttachFileRepository attachFileRepository;
	
	@Autowired
	private CaseRepository caseRepository;

	/**
	 * 1.审批处理意见
	 * 
	 * @param caseId
	 *            案件编号 taskName taskName 流程编号
	 * @return
	 */
	@GetMapping("/comment")
	public Result<Map<String, Object>> getCommentByBusikeyAndTaskName(
			@RequestParam(value = "caseId", required = false) String caseId,
			@RequestParam(value = "taskName", required = false) String taskName) {

		if (StringUtils.isEmpty(caseId)) {
			// 案件号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}

		if (StringUtils.isEmpty(taskName)) {
			// 流程类型号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "流程类型为空.");
		}

		if (Integer.valueOf(taskName) == CommonParameters.CommentType.CASE_FILING) {

			taskName = "立案审批";
		} else if (Integer.valueOf(taskName) == CommonParameters.CommentType.CASE_DEAL) {

			taskName = "处理审批";
		} else if (Integer.valueOf(taskName) == CommonParameters.CommentType.CASE_CLOSED) {

			taskName = "结案审批";
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "taskName is error!");
		}

		String busikey = caseId;

		List<FlowCommentDTO> flowCommentDTOs = this.flowService.getCommentByBusikeyAndTaskName(busikey, taskName);

		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put("flowCommentDTOs", flowCommentDTOs);

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}

	/**
	 * 2.初步核实提交
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param message
	 *            核实人意见
	 * @param partyType
	 *            当事人类型
	 * @param caseStatus
	 *            下一步案件状态
	 * @param name
	 *            当时人姓名
	 * @param sex
	 *            性别
	 * @param age
	 *            年龄
	 * @param idCard
	 *            身份证号
	 * @param tel
	 *            电话
	 * @param address
	 *            住址
	 * @param postCode
	 *            邮编
	 * @param legalName
	 *            法定代表人
	 * @param orgIdCard
	 *            法人身份证号
	 * @param unitName
	 *            单位名称
	 * @param unitAddress
	 *            单位地址
	 * @param orgCode
	 *            组织机构代码
	 * @return
	 */
	/*
	 * @PostMapping("/add") public Result<Object> addCaseInfo(
	 * 
	 * @RequestParam(value = "caseNum" , required = false) String caseNum,
	 * 
	 * @RequestParam(value = "message" , required = false) String message,
	 * 
	 * @RequestParam(value = "caseStatus" , required = false) String caseStatus,
	 * 
	 * @RequestParam(value = "partyType", required = false) String partyType, //个人
	 * 
	 * @RequestParam(value = "name" , required = false) String name,
	 * 
	 * @RequestParam(value = "sex", required = false) String sex,
	 * 
	 * @RequestParam(value = "age" , required = false) String age,
	 * 
	 * @RequestParam(value = "idCard", required = false) String idCard,
	 * 
	 * @RequestParam(value = "tel" , required = false) String tel,
	 * 
	 * @RequestParam(value = "address" , required = false) String address,
	 * 
	 * @RequestParam(value = "postCode" , required = false) String postCode , //公司
	 * 
	 * @RequestParam(value = "legalName", required = false) String legalName,
	 * 
	 * @RequestParam(value = "orgIdCard" , required = false) String orgIdCard,
	 * 
	 * @RequestParam(value = "unitName" , required = false) String unitName,
	 * 
	 * @RequestParam(value = "unitAddress" , required = false) String unitAddress,
	 * 
	 * @RequestParam(value = "orgCode", required = false) String orgCode){
	 * 
	 * if( StringUtils.isEmpty(caseNum) ){ //案件编号为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "案件编号为空."); }
	 * 
	 * if( StringUtils.isEmpty(message) ){ //核实人意见为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "核实人意见为空."); }
	 * 
	 * //获取当前登录人ID PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
	 * PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
	 * 
	 * //获取案件ID CaseEntity caseEntity = caseService.getCaseByCaseNum(caseNum);
	 * 
	 * PartyInfoEntity partyInfo =
	 * this.partyInfoRepository.findByCaseId(caseEntity.getId());
	 * 
	 * if(partyInfo == null){ //新增 partyInfo = new PartyInfoEntity();
	 * partyInfo.setId(UUID.randomUUID().toString());
	 * partyInfo.setCaseId(caseEntity.getId());
	 * partyInfo.setCreateBy(user.getCertNum()); partyInfo.setCreateDate(new
	 * Date()); partyInfo.setCreateName(user.getName()); }else{
	 * partyInfo.setUpdateBy(user.getCertNum()); partyInfo.setUpdateDate(new
	 * Date()); partyInfo.setUpdateName(user.getName());
	 * 
	 * } PartyInfoEntity partyInfoEntity = new PartyInfoEntity(); if(
	 * StringUtils.isEmpty(partyType) ){ //当事人类型为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "当事人类型为空."); }else if(
	 * Integer.valueOf(partyType)==CommonParameters.PartyType.PERSON ){ //个人 if(
	 * StringUtils.isEmpty(name) ){ //姓名为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "姓名为空.");
	 * }
	 * 
	 * if( StringUtils.isEmpty(sex) ){ //性别为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "性别为空.");
	 * }
	 * 
	 * if( StringUtils.isEmpty(age) ){ //年龄为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "案件编号为空."); }
	 * 
	 * if( StringUtils.isEmpty(idCard) ){ //身份证号为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "身份证号为空."); }
	 * 
	 * if( StringUtils.isEmpty(tel) ){ //电话为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "电话为空.");
	 * }
	 * 
	 * if( StringUtils.isEmpty(address) ){ //住址为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "住址为空.");
	 * }
	 * 
	 * if( StringUtils.isEmpty(postCode) ){ //邮编为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "邮编为空.");
	 * }
	 * 
	 * partyInfo.setName(name); partyInfo.setAge(Integer.valueOf(age));
	 * partyInfo.setSex(Integer.valueOf(sex)); partyInfo.setIdCard(idCard);
	 * partyInfo.setTel(tel); partyInfo.setAddress(address);
	 * partyInfo.setPostCode(postCode);
	 * partyInfo.setType(Integer.valueOf(partyType)); partyInfoEntity =
	 * this.partyInfoRepository.save(partyInfo);
	 * 
	 * }else if( Integer.valueOf(partyType)==CommonParameters.PartyType.COMPANY ){
	 * //公司 if( StringUtils.isEmpty(legalName) ){ //法人为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "法人为空.");
	 * }
	 * 
	 * if( StringUtils.isEmpty(orgIdCard) ){ //法人身份证号为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "法人身份证号为空."); }
	 * 
	 * if( StringUtils.isEmpty(unitName) ){ //工作单位为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "工作单位为空."); }
	 * 
	 * if( StringUtils.isEmpty(unitAddress) ){ //法人为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "工作单位地址为空."); }
	 * 
	 * if( StringUtils.isEmpty(orgCode) ){ //组织机构代码为空 return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "组织机构代码为空."); }
	 * 
	 * partyInfo.setLegalName(legalName); partyInfo.setOrgIdCard(orgIdCard);
	 * partyInfo.setUnitName(unitName); partyInfo.setOrgCode(orgCode);
	 * partyInfo.setUnitAddress(unitAddress);
	 * partyInfo.setType(Integer.valueOf(partyType)); partyInfoEntity =
	 * this.partyInfoRepository.save(partyInfo); }else{ return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "personType is error."); }
	 * 
	 * FlowDTO flowDTO = new FlowDTO(); if ( Integer.valueOf(caseStatus) ==
	 * CommonParameters.SimpleFlow.NOT_DEAL) { //不予处理 1
	 * flowDTO.setAssignee(user.getId()); flowDTO.setBusinessId(caseNum);
	 * flowDTO.setComment(message);
	 * flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
	 * flowDTO.setHandleMode(CommonParameters.SimpleFlow.NOT_DEAL.toString());
	 * flowDTO.setNextAssignee(user.getId());
	 * this.flowService.taskComplete(flowDTO); } else if (
	 * Integer.valueOf(caseStatus) == CommonParameters.SimpleFlow.LOCATE_PRO ) { //
	 * 现场处理 3 flowDTO.setAssignee(user.getId()); flowDTO.setBusinessId(caseNum);
	 * flowDTO.setComment(message);
	 * flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
	 * flowDTO.setHandleMode(CommonParameters.SimpleFlow.LOCATE_PRO.toString());
	 * flowDTO.setNextAssignee(user.getId());
	 * this.flowService.taskComplete(flowDTO); } else if (
	 * Integer.valueOf(caseStatus) == CommonParameters.SimpleFlow.PLACE_CASE ){ //立案
	 * 2 flowDTO.setAssignee(user.getId()); flowDTO.setBusinessId(caseNum);
	 * flowDTO.setComment(message);
	 * flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
	 * flowDTO.setHandleMode(CommonParameters.SimpleFlow.PLACE_CASE.toString());
	 * flowDTO.setNextAssignee(user.getId());
	 * this.flowService.taskComplete(flowDTO); }else{ return
	 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
	 * "caseStatus is error."); } return
	 * ResultUtil.toResponseWithData(ResultCode.SUCCESS, "提交成功！"); }
	 */

	/**
	 * 3.案件审核审批的提交
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param caseStatus
	 *            案件状态
	 * @param nextCaseStatus
	 *            下一步案件状态
	 * @param nextAssignee
	 *            审批人
	 * @param comment
	 *            审核/审批意见
	 * @return
	 */
	@PostMapping("/addComment")
	public Result<Object> addComment(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "caseStatus", required = false) String caseStatus,
			@RequestParam(value = "nextCaseStatus", required = false) String nextCaseStatus,
			@RequestParam(value = "nextAssignee", required = false) String nextAssignee,
			@RequestParam(value = "comment", required = false) String comment) {

		String msg = null; // 推送的消息内容
		String username = null; // 推送的用户名
		String pushType = null; // 推送的类型
		User userInfo = null;

		if (!StringUtils.isEmpty(nextAssignee) && Integer.valueOf(caseStatus) != CommonParameters.SHSPType.LASP) {
			userInfo = this.userService.getUserById(Long.valueOf(nextAssignee));
		}

		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}

		if (StringUtils.isEmpty(caseStatus)) {
			// 案件状态为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件状态为空.");
		}

		if (StringUtils.isEmpty(comment)) {
			// 审批/审核意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审批/审核意见为空.");
		}

		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		// 获取当前登录用户信息
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		FlowDTO flowDTO = new FlowDTO();

		// (审批状态)查询下一个流程的办理人
		CaseEntity caseEntuty = this.caseService.getCaseByCaseNum(caseNum);
		PersonDTO next = this.personDao.findUserInfo(caseEntuty.getCaseZzfryid());

		if (Integer.valueOf(caseStatus) == CommonParameters.SHSPType.LASH) {

			if (StringUtils.isEmpty(nextAssignee)) {
				// 审批人为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审批人为空.");
			}

			// 立案审核1
			flowDTO.setAssignee(user.getId());

			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(nextAssignee);
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.APPROVAL;
			username = userInfo.getUsername();
			pushType = CommonParameters.PushMessage.CASE_TYPE_1;
		} else if (Integer.valueOf(caseStatus) == CommonParameters.SHSPType.LASP) {
			// 立案审批 2
			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);

			if (StringUtils.isEmpty(nextCaseStatus)) {
				// 下一步案件状态号为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一步案件状态号为空.");
			} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.NOT_FILING) {

				// 不予立案
				flowDTO.setHandleMode(CommonParameters.SimpleFlow.NOT_FILING.toString());
				flowDTO.setNextAssignee(next.getId());
				msg = "您有一条案件不予立案，请点击查看...";
				username = next.getCertNum();	
			} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_SEARCH_PROOF) {
				// 重新立案
				if (StringUtils.isEmpty(nextAssignee)) {
					// 下一节点处理人为空
					return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一节点处理人为空.");
				}
				PersonDTO nextUser = this.personDao.findUserInfo(nextAssignee);
				
				caseEntuty.setCaseWqryid(caseEntuty.getCaseZzfryid());
				caseEntuty.setCaseWqryname(caseEntuty.getCaseZzfryname());
				caseEntuty.setCaseZzfryid(nextUser.getId());
				caseEntuty.setCaseZzfryname(nextUser.getName());
				try {
					this.caseService.updateWqToZzf(caseEntuty);
				} catch (Exception e) {
					e.printStackTrace();
					return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "外勤转内勤失败.");
				}
				
				// 调查取证
				flowDTO.setHandleMode(CommonParameters.SimpleFlow.TO_SEARCH_PROOF.toString());
				flowDTO.setNextAssignee(nextUser.getId());
				msg = CommonParameters.PushMessage.ENFORCE_PERSON;
				username = nextUser.getCertNum();
				
			} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.RE_FILING) {

				flowDTO.setHandleMode(CommonParameters.SimpleFlow.RE_FILING.toString());
				flowDTO.setNextAssignee(next.getId());
				msg = "您有一条案件需要重新立案，请点击查看...";
				username = next.getCertNum();
			} else {
				// 下一步案件状态号错误
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一步案件状态号错误.");
			}
		
			this.flowService.taskComplete(flowDTO);
			pushType = CommonParameters.PushMessage.CASE_TYPE_4;

		} else if (Integer.valueOf(caseStatus) == CommonParameters.SHSPType.CLSH) {

			// 处理审核 3
			if (StringUtils.isEmpty(nextAssignee)) {
				// 审批人为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审批人为空.");
			}

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(nextAssignee);
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.APPROVAL;
			username = userInfo.getUsername();
			pushType = CommonParameters.PushMessage.CASE_TYPE_1;
		} else if (Integer.valueOf(caseStatus) == CommonParameters.SHSPType.CLSP) {
			// 处理审批 4

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(next.getId());
			this.flowService.taskComplete(flowDTO);
			msg = CommonParameters.PushMessage.ENFORCE_PERSON;
			username = next.getCertNum();
			pushType = CommonParameters.PushMessage.CASE_TYPE_4;
		} else if (Integer.valueOf(caseStatus) == CommonParameters.SHSPType.JASH) {
			// 结案审核 5
			if (StringUtils.isEmpty(nextAssignee)) {
				// 审批人为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审批人为空.");
			}

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(nextAssignee);
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.APPROVAL;
			username = userInfo.getUsername();
			pushType = CommonParameters.PushMessage.CASE_TYPE_1;
		} else if (Integer.valueOf(caseStatus) == CommonParameters.SHSPType.JASP) {
			// 结案审批 6

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(next.getId());
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.ENFORCE_PERSON;
			username = next.getCertNum();
			pushType = CommonParameters.PushMessage.CASE_TYPE_4;
		} else if (Integer.valueOf(caseStatus) == CommonParameters.SimpleFlow.TO_ENFORCEMENT_APPROVE) {
			// 强制执行审核 49
			if (StringUtils.isEmpty(nextAssignee)) {
				// 审批人为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审批人为空.");
			}

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(nextAssignee);
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.APPROVAL;
			username = userInfo.getUsername();
			pushType = CommonParameters.PushMessage.CASE_TYPE_1;
		} else if (Integer.valueOf(caseStatus) == CommonParameters.SimpleFlow.TO_ENFORCEMENT) {
			// 强制执行审批 50

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(next.getId());
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.ENFORCE_PERSON;
			username = next.getCertNum();
			pushType = CommonParameters.PushMessage.CASE_TYPE_4;
		} else if (Integer.valueOf(caseStatus) == 207) {
			// 撤销立案审核 207 CommonParameters.SimpleFlow.TO_CXLASH
			if (StringUtils.isEmpty(nextAssignee)) {
				// 审批人为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审批人为空.");
			}

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(nextAssignee);
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.APPROVAL;
			username = userInfo.getUsername();
			pushType = CommonParameters.PushMessage.CASE_TYPE_1;
		} else if (Integer.valueOf(caseStatus) == 209) {
			// 撤销立案审批 209 CommonParameters.SimpleFlow.TO_CXLASP

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(comment);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(next.getId());
			this.flowService.taskComplete(flowDTO);

			msg = CommonParameters.PushMessage.ENFORCE_PERSON;
			username = next.getCertNum();
			pushType = CommonParameters.PushMessage.CASE_TYPE_4;
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "caseStatus is error.");
		}

		try {
			ApiMessagePushController.send(msg, username, pushType, caseNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "审核/审批意见提交成功！");
	}

	/**
	 * 4.日常检查提交
	 * 
	 */
	@PostMapping("/dailyCheck/submit")
	public Result<Object> submitDailyCheck(@RequestParam(value = "checkNum", required = false) String checkNum,
			@RequestParam(value = "handleMode", required = false) String handleMode,
			@RequestParam(value = "comment", required = false) String comment) {

		if (StringUtils.isEmpty(checkNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "日常检查编号不能为空.");
		}

		if (StringUtils.isEmpty(handleMode)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理类型不能为空.");
		}

		if (StringUtils.isEmpty(comment)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "提交意见不能为空.");
		}

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();

		User user = this.userService.getUserByPersonId(personDTO.getId());

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(user.getId().toString());
		flowDTO.setBusinessId(checkNum);
		flowDTO.setComment(comment);
		flowDTO.setHandleMode(handleMode);
		flowDTO.setFlowKey("dayCheck");
		flowDTO.setNextAssignee(user.getId().toString());
		this.flowService.startFlowByKey(flowDTO);
		this.caseDealService.appSaveTaskEntity(flowDTO, true, user);
		return ResultUtil.success();
	}

	/**
	 * 5.app 流程审批记录
	 * 
	 * @param caseNum
	 *            单据编号
	 * @param mode
	 *            单据类型
	 * @return
	 */
	@GetMapping("/list/comment")
	public Result<Object> getFlowCommentByBusiKeyAndFlowKey(
			@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "mode", required = false) String mode) {

		if (StringUtils.isEmpty(caseNum)) {
			// 检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}

		if (StringUtils.isEmpty(mode)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型不能为空");
		}

		String flowKey = "";

		if (mode.equals(CommonParameters.FlowType.DAILY_CHECK)) {
			flowKey = CommonParameters.FlowKey.DAY_CHECK;
		} else if (mode.equals(CommonParameters.FlowType.SPECIAL_CHECK)) {
			flowKey = CommonParameters.FlowKey.PRO_CHECK;
		} else if (mode.equals(CommonParameters.FlowType.CASE)) {
			flowKey = CommonParameters.FlowKey.CASE;
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型错误");
		}

		String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId(flowKey, caseNum);
		List<FlowTaskCommentDTO> comments = this.flowService.getCommemntByProcessInstanceId(pid);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, comments);
	}

	/**
	 * 6.app 文书列表
	 * 
	 * @param caseNum
	 *            单据编号
	 * @param mode
	 *            单据类型
	 * @return
	 */
	@GetMapping("/list/doc")
	public Result<Object> getDocByBusiKeyAndFlowKey(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "mode", required = false) String mode) {

		if (StringUtils.isEmpty(caseNum)) {
			// 检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}

		if (StringUtils.isEmpty(mode)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型不能为空");
		}

		if (mode.equals(CommonParameters.FlowType.DAILY_CHECK)) {
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
			List<CheckDocDTO> checkDocDTOs = this.checkDailyService.queryDocContentByCheckId(checkDailyEntity.getId());

			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkDocDTOs);

		} else if (mode.equals(CommonParameters.FlowType.SPECIAL_CHECK)) {
			LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);
			List<CheckDocDTO> caseDocDTOs = this.lssuedService.queryDocContentByCaseId(lssuedEntity.getId());

			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseDocDTOs);
		} else if (mode.equals(CommonParameters.FlowType.CASE)) {
			CaseEntity caseEntity = this.caseService.getCaseByCaseNum(caseNum);

			List<CaseDocDTO> caseDocDTOs = this.caseService.queryDocContentByCaseId(caseEntity.getId());
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseDocDTOs);
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型错误");
		}
	}

	/**
	 * 7.现场处理提交
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param potenceId
	 *            权责ID
	 * @param potenceLawMapId
	 *            违责ID
	 * @param caseReason
	 *            案由
	 * @param nextCaseStatus
	 *            下一个节点状态
	 * @param message
	 *            处理意见
	 * @return
	 */
	@PostMapping("/sceneDeal")
	public Result<Object> sceneDeal(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "potenceId", required = false) String potenceId,
			@RequestParam(value = "potenceLawMapId", required = false) String potenceLawMapId,
			@RequestParam(value = "caseReason", required = false) String caseReason,
			@RequestParam(value = "nextCaseStatus", required = false) String nextCaseStatus,
			@RequestParam(value = "message", required = false) String message) {

		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}

		if (StringUtils.isEmpty(nextCaseStatus)) {
			// 下一步案件状态
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一步案件状态不能为空");
		}

		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见不能为空");
		}

		if (StringUtils.isEmpty(caseReason)) {
			// 案由为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案源不能为空");
		}

		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		FlowDTO flowDTO = new FlowDTO();
		CaseEntity caseEntity = new CaseEntity();
		caseEntity.setCaseReason(caseReason);
		caseEntity.setCaseNum(caseNum);

		if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_PUNISH) {

			if (StringUtils.isEmpty(potenceId)) {
				// 权责ID为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "权责ID不能为空");
			}

			if (StringUtils.isEmpty(potenceLawMapId)) {
				// 违责ID为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "违责ID不能为空");
			}

			caseEntity.setPotenceId(potenceId);
			caseEntity.setPotenceLawMapId(potenceLawMapId);
			this.caseService.updatePotenceAndSource(caseEntity);

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(message);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(user.getId());
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.TO_PUNISH.toString());
			this.flowService.taskComplete(flowDTO);
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_WARNING) {

			this.caseService.updatePotenceAndSource(caseEntity);

			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(message);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.TO_WARNING.toString());
			flowDTO.setNextAssignee(user.getId());
			this.flowService.taskComplete(flowDTO);
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "nextCaseStatus参数错误！");
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "现场处理提交成功！");
	}

	/**
	 * 8.一般、简易案件结案归档
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param nextCaseStatus
	 *            下一步案件状态
	 * @param message
	 *            处理意见
	 * @param isPs
	 *            是否公示
	 * @punishType 处罚类别
	 * @punishCash 处罚金额
	 * @punishBill 缴费凭证
	 * @return
	 */
	@PostMapping("/caseClosed")
	public Result<Object> caseClosed(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "nextCaseStatus", required = false) String nextCaseStatus,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "isPs", required = false) String isPs,
			@RequestParam(value = "punishType", required = false) String punishType,
			@RequestParam(value = "punishCash", required = false) String punishCash,
			@RequestParam(value = "punishBill", required = false) String punishBill) {

		if (StringUtils.isEmpty(punishType)) {
			// 处罚类别为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处罚类型不能为空");
		}

		if (punishType.equals(CommonParameters.PunishType.FK)) {
			if (StringUtils.isEmpty(punishCash)) {
				// 处罚金额为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处罚金额不能为空");
			}

			if (StringUtils.isEmpty(punishBill)) {
				// 缴费凭证为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "缴费凭证不能为空");
			}
		}

		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}

		if (StringUtils.isEmpty(nextCaseStatus)) {
			// 下一步案件状态为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一步案件状态不能为空");
		}

		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见不能为空");
		}

		if (StringUtils.isEmpty(isPs)) {
			// 是否公示为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "是否公示不能为空");
		}

		CaseEntity caseEntity = this.caseService.getCaseByCaseNum(caseNum);
		caseEntity.setIsPs(isPs);
		caseEntity.setPunishBill(punishBill);
		caseEntity.setPunishType(punishType);
		caseEntity.setPunishCash(punishCash);
		this.caseService.updateIsPs(caseEntity);
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		FlowDTO flowDTO = new FlowDTO();
		if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_EASY_FILE) {
			// 简易流程
			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(message);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(user.getId());
			this.flowService.taskComplete(flowDTO);
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_COMMONLY_FILE) {
			// 一般流程
			flowDTO.setAssignee(user.getId());
			flowDTO.setBusinessId(caseNum);
			flowDTO.setComment(message);
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setNextAssignee(user.getId());
			this.flowService.taskComplete(flowDTO);
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "nextCaseStatus参数错误！");
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "案件结案提交成功！");

	}

	/**
	 * 9.检查领导审核审批提交
	 * 
	 * @param checkNum
	 *            检查编号
	 * @param message
	 *            审核/审批意见
	 * @param approver
	 *            审批人 (审核时传，从审核到审批的时候传该参数，该参数区分审核审批)
	 * @param isAgree
	 *            是否同意 同意109 不同意110 (审批时传)
	 */
	@PostMapping("/checkApproval")
	public Result<Object> submit(@RequestParam(value = "checkNum", required = false) String checkNum,
			@RequestParam(value = "isAgree", required = false) String isAgree,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "approver", required = false) String approver) {

		String msg = null; // 推送的消息内容
		String username = null; // 推送的用户名
		String pushType = null; // 推送的类型
		User userInfo = null;

		if (!StringUtils.isEmpty(approver)) {
			userInfo = this.userService.getUserById(Long.valueOf(approver));
		}

		if (StringUtils.isEmpty(checkNum)) {
			// 检查编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查编号为空.");
		}

		if (StringUtils.isEmpty(message)) {
			// 审批/审核意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审批/审核意见为空.");
		}
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		FlowDTO flowDTO = new FlowDTO();

		CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(checkNum);
		if (checkDailyEntity != null) {
			// 日常检查
			if (!StringUtils.isEmpty(approver)) {
				// 审核
				flowDTO.setAssignee(user.getId());
				flowDTO.setBusinessId(checkNum);
				flowDTO.setComment(message);
				flowDTO.setFlowKey(CommonParameters.FlowKey.DAY_CHECK);
				flowDTO.setNextAssignee(approver);

				msg = CommonParameters.PushMessage.DAILY_APPROVAL;
				username = userInfo.getUsername();
				pushType = CommonParameters.PushMessage.CASE_TYPE_5;
			} else {
				// 审批
				if (StringUtils.isEmpty(isAgree)) {
					// 是否同意为空
					return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "是否同意为空.");
				}
				try {
					Integer.valueOf(isAgree);
				} catch (Exception e) {
					e.printStackTrace();
					return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "是否同意必须传整型.");
				}
				// 获取该条检查的执法人员
				PersonDTO nextAssignee = this.personDao.findUserInfo(checkDailyEntity.getPersonId());
				if (Integer.valueOf(isAgree) == CommonParameters.CheckDailyFlow.TY) {
					// 同意
					flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.TY.toString());
					msg = CommonParameters.PushMessage.DAILY_ENFORCE_PERSON;
				} else if (Integer.valueOf(isAgree) == CommonParameters.CheckDailyFlow.BTY) {
					// 不同意
					flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.BTY.toString());
					msg = "您有一条日常检查没有通过审核审批，请点击查看...";
				}
				flowDTO.setAssignee(user.getId());
				flowDTO.setBusinessId(checkNum);
				flowDTO.setComment(message);
				flowDTO.setFlowKey(CommonParameters.FlowKey.DAY_CHECK);
				flowDTO.setNextAssignee(nextAssignee.getId());

				username = nextAssignee.getCertNum();
				pushType = CommonParameters.PushMessage.CASE_TYPE_7;
			}
		} else {
			// 专项检查
			if (!StringUtils.isEmpty(approver)) {
				// 审核
				flowDTO.setAssignee(user.getId());
				flowDTO.setBusinessId(checkNum);
				flowDTO.setComment(message);
				flowDTO.setFlowKey(CommonParameters.FlowKey.PRO_CHECK);
				flowDTO.setNextAssignee(approver);

				msg = CommonParameters.PushMessage.SPECIAL_APPROVAL;
				username = userInfo.getUsername();
				pushType = CommonParameters.PushMessage.CASE_TYPE_6;
			} else {

				// 审批
				if (StringUtils.isEmpty(isAgree)) {
					// 是否同意为空
					return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "是否同意为空.");
				}
				try {
					Integer.valueOf(isAgree);
				} catch (Exception e) {
					e.printStackTrace();
					return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "是否同意必须传整型.");
				}

				String zfryId = this.checkQueryService.getCheckZfryIdByCheckNum(checkNum);
				// 获取该条检查的执法人员
				PersonDTO nextAssignee = this.personDao.findUserInfo(zfryId);
				if (Integer.valueOf(isAgree) == CommonParameters.CheckDailyFlow.TY) {
					// 同意
					flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.TY.toString());
					msg = CommonParameters.PushMessage.SPECIAL_ENFORCE_PERSON;
				} else if (Integer.valueOf(isAgree) == CommonParameters.CheckDailyFlow.BTY) {
					// 不同意
					flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.BTY.toString());
					msg = "您有一条专项检查没有通过审核审批，请点击查看...";
				}
				flowDTO.setAssignee(user.getId());
				flowDTO.setBusinessId(checkNum);
				flowDTO.setComment(message);
				flowDTO.setFlowKey(CommonParameters.FlowKey.PRO_CHECK);
				flowDTO.setNextAssignee(nextAssignee.getId());

				username = nextAssignee.getCertNum();
				pushType = CommonParameters.PushMessage.CASE_TYPE_8;
			}
		}
		try {
			this.flowService.taskComplete(flowDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithMsg(ResultCode.APP_SUBMIT_ERROR.getCode(), "审核/审批意见提交失败.");
		}

		try {
			ApiMessagePushController.send(msg, username, pushType, checkNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "审核/审批意见提交成功！");

	}

	/**
	 * 11.获取当前登录人所在部门的执法人员列表
	 * 
	 * @return
	 */
	@GetMapping("/getPersonByDeptId")
	public Result<List<NextOpinionDTO>> getPersonByDeptId() {
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findPersonInfo(personDTO.getId());
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getPersonByDeptId(user.getDeptId());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 12.获取当前登录人所在部门的并且有审核权限的人员列表
	 * 
	 * @return
	 */
	@GetMapping("/getHaveRoleOpinionByDeptId")
	public Result<List<NextOpinionDTO>> getHaveRoleOpinionByDeptId() {
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findPersonInfo(personDTO.getId());
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getHaveRoleOpinionByDeptProperty(user.getDeptId());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 13.获取当前登录人所在部门的并且有审批权限的人员列表
	 * 
	 * @return
	 */
	@GetMapping("/getApproveUserByDeptId")
	public Result<List<NextOpinionDTO>> getApproveUserByDeptId() {
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findPersonInfo(personDTO.getId());
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getApproveUserByDeptProperty(user.getDeptId());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 14.立案提交
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param potenceId
	 *            权责ID
	 * @param potenceLawMapId
	 *            违责ID
	 * @param caseReason
	 *            案由
	 * @param limitType
	 *            时限类型 天：1，小时：2
	 * @param limitValue
	 *            时限值
	 * @param nextAssigneeId
	 *            审核人
	 * @param message
	 *            处理意见
	 */
	@PostMapping("/filingCase")
	public Result<Object> filingCase(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "potenceId", required = false) String potenceId,
			@RequestParam(value = "potenceLawMapId", required = false) String potenceLawMapId,
			@RequestParam(value = "caseReason", required = false) String caseReason,
			@RequestParam(value = "limitType", required = false) String limitType,
			@RequestParam(value = "limitValue", required = false) String limitValue,
			@RequestParam(value = "nextAssigneeId", required = false) String nextAssigneeId,
			@RequestParam(value = "message", required = false) String message) {

		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}

		if (StringUtils.isEmpty(potenceId)) {
			// 权责ID为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "权责ID不能为空");
		}

		if (StringUtils.isEmpty(potenceLawMapId)) {
			// 违责ID为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "违责ID不能为空");
		}

		if (StringUtils.isEmpty(caseReason)) {
			// 案由为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案由不能为空");
		}

		if (StringUtils.isEmpty(limitType)) {
			// 时限类型为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "时限类型不能为空");
		}

		if (StringUtils.isEmpty(limitValue)) {
			// 时限值为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "时限值不能为空");
		}

		if (StringUtils.isEmpty(nextAssigneeId)) {
			// 审核人为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "审核人不能为空");
		}

		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见不能为空");
		}
		try {
			Integer.valueOf(limitValue);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，时限值必须传整型");
		}
		CaseEntity caseEntity = new CaseEntity();
		caseEntity.setCaseNum(caseNum);
		caseEntity.setPotenceId(potenceId);
		caseEntity.setPotenceLawMapId(potenceLawMapId);
		caseEntity.setCaseReason(caseReason);
		caseEntity.setLimitValue(limitValue);
		caseEntity.setLimitType(limitType);
		this.caseService.updataCaseInfo(caseEntity);

		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(user.getId());
		flowDTO.setBusinessId(caseNum);
		flowDTO.setComment(message);
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setNextAssignee(nextAssigneeId);
		this.flowService.taskComplete(flowDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "立案提交成功");
	}

	/**
	 * 15.流程前进
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param nextAssigneeId
	 *            下一节点处理人
	 * @param message
	 *            处理意见
	 **/
	@PostMapping("/flowForward")
	public Result<Object> flowForward(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "nextAssigneeId", required = false) String nextAssigneeId,
			@RequestParam(value = "message", required = false) String message) {
		String nextAssignee = "";
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空");
		}
		if (StringUtils.isEmpty(nextAssigneeId)) {
			nextAssignee = user.getId();
		} else {
			nextAssignee = nextAssigneeId;
		}

		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见不能为空");
		}

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(user.getId());
		flowDTO.setBusinessId(caseNum);
		flowDTO.setComment(message);
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setNextAssignee(nextAssignee);
		this.flowService.taskComplete(flowDTO);

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "提交成功");
	}

	/**
	 * 16.先行告知提交
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param nextAssigneeId
	 *            当事人ID
	 * @param message
	 *            处理意见
	 */
	@PostMapping("/firstGaozhi")
	public Result<Object> firstGaozhi(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "nextAssigneeId", required = false) String nextAssigneeId,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "timeDate", required = false) String timeDate) {
		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空");
		}
		if (StringUtils.isEmpty(nextAssigneeId)) {
			// 当事人ID
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "当事人不能为空");
		}
		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见不能为空");
		}
		if (StringUtils.isEmpty(timeDate)) {
			// 时限为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "时限不能为空");
		}
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(user.getId());
		flowDTO.setBusinessId(caseNum);
		flowDTO.setComment(message);
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setNextAssignee(nextAssigneeId);
		flowDTO.setTimeDate(timeDate);
		this.flowService.taskComplete(flowDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "先行告知提交成功");
	}

	/**
	 * 17.案件处理提交
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param nextCaseStatus
	 *            下一流程案件状态 申请复议 24 结案申请22 申请强制执行23
	 * @param message
	 *            处理意见
	 * @param partyInfoId
	 *            当事人ID
	 * @param timeDate
	 *            处理时限
	 */
	@PostMapping("/caseDeal")
	public Result<Object> caseDeal(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "nextCaseStatus", required = false) String nextCaseStatus,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "partyInfoId", required = false) String partyInfoId,
			@RequestParam(value = "timeDate", required = false) String timeDate) {
		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空");
		}
		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见时限值不能为空");
		}
		if (StringUtils.isEmpty(nextCaseStatus)) {
			// 下一流程案件状态为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一流程案件状态不能为空");
		}

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		FlowDTO flowDTO = new FlowDTO();

		if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_APPLY_RECONSIDERATION) {
			// 申请复议 24
			if (StringUtils.isEmpty(partyInfoId)) {
				// 当事人ID为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "当事人ID不能为空");
			}
			if (StringUtils.isEmpty(timeDate)) {
				// 处理时限为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理时限不能为空");
			}
			flowDTO.setNextAssignee(user.getId());
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.TO_APPLY_RECONSIDERATION.toString());
			flowDTO.setTimeDate(timeDate);
		} else if (Integer
				.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.NO_APPLY_RECONSIDERATION_ACCEPT_PUNISHMENT) {
			// 申请强制执行 23
			flowDTO.setNextAssignee(user.getId());
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.NO_APPLY_RECONSIDERATION_ACCEPT_PUNISHMENT.toString());
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_ACCEPT_PUNISHMENT) {
			// 结案申请 22
			flowDTO.setNextAssignee(user.getId());
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.TO_ACCEPT_PUNISHMENT.toString());
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "nextCaseStatus is error!");
		}
		flowDTO.setAssignee(user.getId());
		flowDTO.setBusinessId(caseNum);
		flowDTO.setComment(message);
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		this.flowService.taskComplete(flowDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "案件处理提交成功");
	}

	/**
	 * 18.专项检查现场处理提交
	 * 
	 * @param checkNum
	 *            检查编号
	 * @param assistPersonId
	 *            协办人ID
	 * @param checkMode
	 *            检查类型
	 * @param checkModeId
	 *            检查类型ID
	 * @param checkeDate
	 *            检查时间
	 * @param checkObjectType
	 *            检查对象类型 个人：1 公司：2
	 * @param checkedUserName
	 *            姓名
	 * @param checkedUserId
	 *            身份证号
	 * @param unitName
	 *            单位名称
	 * @param roadName
	 *            道路名称
	 * @param checkSituation
	 *            巡查情况
	 * @param registNum
	 *            注册号
	 * @param legalPerson
	 *            法人
	 * @param nextCaseStatus
	 *            下一步状态
	 * @param message
	 *            处理意见
	 * @param lat
	 *            经度
	 * @param lng
	 *            纬度
	 **/
	@PostMapping("/checkDeal")
	public Result<Object> checkDeal(@RequestParam(value = "checkNum", required = false) String checkNum,
			@RequestParam(value = "assistPersonId", required = false) String assistPersonId,
			@RequestParam(value = "checkMode", required = false) String checkMode,
			@RequestParam(value = "checkModeId", required = false) String checkModeId,
			@RequestParam(value = "checkeDate", required = false) String checkeDate,
			@RequestParam(value = "checkObjectType", required = false) String checkObjectType,
			@RequestParam(value = "checkedUserName", required = false) String checkedUserName,
			@RequestParam(value = "checkedUserId", required = false) String checkedUserId,
			@RequestParam(value = "unitName", required = false) String unitName,
			@RequestParam(value = "roadName", required = false) String roadName,
			@RequestParam(value = "checkSituation", required = false) String checkSituation,
			@RequestParam(value = "registNum", required = false) String registNum,
			@RequestParam(value = "legalPerson", required = false) String legalPerson,
			@RequestParam(value = "nextCaseStatus", required = false) String nextCaseStatus,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "lng", required = false) String lng,
			@RequestParam(value = "lat", required = false) String lat) {
		/*
		 * if(StringUtils.isEmpty(lng)){ //纬度为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "纬度为空.");
		 * } if(StringUtils.isEmpty(lat)){ //经度为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "经度为空.");
		 * }
		 */
		if (StringUtils.isEmpty(checkNum)) {
			// 检查编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查编号为空");
		}
		/*
		 * if ( StringUtils.isEmpty(assistPersonId)) { //协办人ID为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "协办人ID为空"); } if ( StringUtils.isEmpty(checkTypeId)) { //检查类型ID为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "检查类型ID为空"); } if ( StringUtils.isEmpty(checkeDate)) { //检查时间为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查时间为空");
		 * }
		 */
		if (StringUtils.isEmpty(nextCaseStatus)) {
			// 下一步状态为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一步状态为空");
		}
		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见为空");
		}
		/*
		 * if ( StringUtils.isEmpty(roadName)) { //道路名称为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "道路名称为空");
		 * } if ( StringUtils.isEmpty(checkSituation)) { //巡查情况为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "巡查情况为空");
		 * }
		 */
		/*
		 * if ( StringUtils.isEmpty(unitName)) { //单位名称为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "单位名称为空");
		 * }
		 */
		/*
		 * if ( StringUtils.isEmpty(checkedUserId)) { //身份证号为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "身份证号为空");
		 * }
		 */

		// 当前登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		LssuedEntity lssuedEntity = lssuedService.getLssuedByCheckNum(checkNum);
		if (lssuedEntity == null) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查任务不存在！");
		}
		CheckRecordEntity checkRecordEntity = lssuedService.getRecordBycheckId(lssuedEntity.getId());
		if (checkRecordEntity == null) {
			checkRecordEntity = new CheckRecordEntity();
			checkRecordEntity.setId(UUID.randomUUID().toString());
		}
		checkRecordEntity.setCheckId(lssuedEntity.getId());
		checkRecordEntity.setAssistPersonId(assistPersonId);
		checkRecordEntity.setCheckMode(checkMode);
		checkRecordEntity.setCheckModeId(checkModeId);
		checkRecordEntity.setCheckObjectType(checkObjectType);
		try {
			checkRecordEntity.setCheckedDate(sdf.parse(checkeDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkRecordEntity.setRoadName(roadName);
		checkRecordEntity.setCheckSituation(checkSituation);
		checkRecordEntity.setUnitName(unitName);
		checkRecordEntity.setCheckedUserId(checkedUserId);
		checkRecordEntity.setCreateBy(user.getId());
		checkRecordEntity.setCreateName(user.getName());
		checkRecordEntity.setCreateDate(new Date());
		checkRecordEntity.setLat(Double.valueOf(lat));
		checkRecordEntity.setLng(Double.valueOf(lng));
		if (StringUtils.isEmpty(checkObjectType)) {
			// 检查对象类型为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查对象类型为空");
		} else if (Integer.valueOf(checkObjectType) == 1) {
			// 个人
			if (StringUtils.isEmpty(checkedUserName)) {
				// 姓名为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "姓名为空");
			}
			checkRecordEntity.setCheckedUserName(checkedUserName);
		} else if (Integer.valueOf(checkObjectType) == 2) {
			// 公司
			/*
			 * if ( StringUtils.isEmpty(registNum)) { //注册号为空 return
			 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "注册号为空");
			 * } if ( StringUtils.isEmpty(legalPerson)) { //法人为空 return
			 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "法人为空"); }
			 */
			checkRecordEntity.setRegistNum(registNum);
			checkRecordEntity.setLegalPerson(legalPerson);
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查对象类型错误");
		}
		try {
			this.checkRecordRepository.save(checkRecordEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithMsg(ResultCode.APP_TEMP_CONTENT_NULL.getCode(), "专项记录保存失败.");
		}

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(user.getId());
		flowDTO.setBusinessId(checkNum);
		flowDTO.setComment(message);
		flowDTO.setFlowKey(CommonParameters.FlowKey.PRO_CHECK);
		flowDTO.setNextAssignee(user.getId());
		if (Integer.valueOf(nextCaseStatus) == CommonParameters.CheckDailyFlow.ZLZG) {
			// 责令整改 101
			flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.ZLZG.toString());
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.CheckDailyFlow.XZCF) {
			// 行政处罚 102
			flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.XZCF.toString());
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.CheckDailyFlow.SQCYQZ) {
			// 申请抽样取证 103
			flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.SQCYQZ.toString());
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.CheckDailyFlow.WFXWFXW) {
			// 未发现违法行为 104
			flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.WFXWFXW.toString());
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一步状态错误");
		}
		try {
			User userInfo = this.userService.getUserByPersonId(personDTO.getId());
			this.flowService.taskComplete(flowDTO);
			this.caseDealService.appSaveTaskEntity(flowDTO, true, userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithMsg(ResultCode.APP_SUBMIT_ERROR.getCode(), "专项检查现场处理提交失败.");
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "专项检查现场处理提交成功！");
	}

	/**
	 * 19.案件合议提交
	 * 
	 * @param caseId
	 *            案件编号
	 * @param nextCaseStatus
	 *            下一步状态
	 * @param message
	 *            处理意见
	 * @param nextAssignee
	 *            下一节点处理人
	 * @param dealType
	 *            重大案件依据
	 * @return
	 */
	@PostMapping("/caseMeeting")
	public Result<Object> caseMeeting(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "nextCaseStatus", required = false) String nextCaseStatus,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "nextAssignee", required = false) String nextAssignee,
			@RequestParam(value = "dealType", required = false) String dealType) {
		
		String msg = null; // 推送的消息内容
		String username = null; // 推送的用户名
		String pushType = null; // 推送的类型

		CaseEntity apiCase = this.caseRepository.findByCaseNum(caseNum);
		
		PersonDTO pDTO = this.personDao.findUserInfo(apiCase.getCaseZzfryid());


		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}

		if (StringUtils.isEmpty(nextCaseStatus)) {
			// 下一节点为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一节点为空.");
		}

		if (StringUtils.isEmpty(nextCaseStatus)) {
			// 下一节点处理人为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一节点处理人为空.");
		}

		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见人为空.");
		}

		// 当前登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(user.getId());
		flowDTO.setBusinessId(caseNum);
		flowDTO.setComment(message);
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setNextAssignee(nextAssignee);

		if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.NOT_PUNISH) {
			// 不予处罚（案件归档） 10
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.NOT_PUNISH.toString());
			username = pDTO.getCertNum();
			pushType = CommonParameters.PushMessage.CASE_TYPE_4;
			msg = "您有一条案件不予处罚，请点击查看...";
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_INFORMING) {
			// 先行告知 9
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.TO_INFORMING.toString());
			username = pDTO.getCertNum();
			pushType = CommonParameters.PushMessage.CASE_TYPE_4;
			msg = "您有一条案件通过审理，请点击查看...";
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.REVOKE_FILING) {
			// 撤销立案审核 11
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.REVOKE_FILING.toString());
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.MAJOR_CASE) {
			// 重大案件受理 13
			User userInfo = this.userService.getUserByPersonId(personDTO.getId());
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.MAJOR_CASE.toString());
			flowDTO.setDealType(dealType);
			this.caseDealService.appSaveTaskEntity(flowDTO, true, userInfo);
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "下一节点有误.");
		}
		
		try {
			this.flowService.taskComplete(flowDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithMsg(ResultCode.APP_SUBMIT_ERROR.getCode(), "提交失败.");
		}
		
		if(!StringUtils.isEmpty(msg)) {
			try {
				ApiMessagePushController.send(msg, username, pushType, caseNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "提交成功！");
	}

	/**
	 * 20.获取当前登录人所在部门的并且有法制受理权限的人员列表
	 * 
	 * @return
	 */
	@GetMapping("/getFzHaveRoleCaseByDeptId")
	public Result<List<NextOpinionDTO>> getFzHaveRoleCaseByDeptId() {
		// 当前登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFzHaveRoleCaseByDeptId(user.getDeptId());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
}
