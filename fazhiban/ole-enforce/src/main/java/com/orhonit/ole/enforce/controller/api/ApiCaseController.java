package com.orhonit.ole.enforce.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.JaveUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.common.utils.SysUtil;
import com.orhonit.ole.enforce.config.UploadConfig;
import com.orhonit.ole.enforce.dao.LssuedDao;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.DeptPersonDTO;
import com.orhonit.ole.enforce.dto.LssuedDTO;
import com.orhonit.ole.enforce.dto.LssuedDetailInfoDTO;
import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.TaskDTO;
import com.orhonit.ole.enforce.dto.YujDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseDocDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseInfoDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseListDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseTaskDTO;
import com.orhonit.ole.enforce.dto.api.ApiCheckTaskDTO;
import com.orhonit.ole.enforce.dto.api.ApiDailyCheckDTO;
import com.orhonit.ole.enforce.entity.AttachFileEntity;
import com.orhonit.ole.enforce.entity.CaseCommentEntity;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.CheckRecordEntity;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.entity.PartyInfoEntity;
import com.orhonit.ole.enforce.repository.CaseRepository;
import com.orhonit.ole.enforce.repository.CheckRecordRepository;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.repository.PartyInfoRepository;
import com.orhonit.ole.enforce.service.caseComment.CaseCommentService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.caseinfo.impl.FlowThreadService;
import com.orhonit.ole.enforce.service.checkQuery.CheckQueryService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.enforce.service.yuj.YujService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.dto.ReviewRecordDTO;
import com.orhonit.ole.sys.dto.ReviewRecordItemDTO;
import com.orhonit.ole.sys.model.ReviewItemEntity;
import com.orhonit.ole.sys.model.ReviewRecordItemEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.ReviewService;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 办案相关控制器 1. 案件或检查列表 2. 案件或检查详情 3. 案件文书查询 4. 案件上报-提交 5. 案件模糊查询 6. 案件统计-领导端 8.
 * 下达通知列表 9. 案件查询列表 12. 专项下达通知---暂存 13. 日常检查暂存、编辑 14-17. 待办事项 18. 获取案件附件 19.
 * 案件受理获取检查标题 20. 案卷评查提交 21. 案卷评查查询 22. 案卷评查内容查询
 * 
 * @author ebusu
 */

@RestController
@RequestMapping("/api/case")
public class ApiCaseController {

	@Autowired
	private CaseService caseService;

	@Autowired
	private CheckDailyService checkDailyService;

	@Autowired
	private LssuedService lssuedService;

	@Autowired
	private DocContentService docContentService;

	@Autowired
	private FlowThreadService flowThreadService;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private CheckQueryService checkQueryService;

	@Autowired
	private UserService userService;

	@Autowired
	private UploadConfig uploadConfig;

	@Autowired
	private FlowService flowService;

	@Autowired
	private CaseCommentService caseCommentService;

	@Autowired
	private YujService yujService;

	@Autowired
	private PartyInfoRepository partyInfoRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private LssuedDao lssuedDao;

	@Autowired
	private ReviewService reviewService;

	@Value("${files.punishBill}")
	private String filesPath;

	@Value("${upload.serverUrl}")
	private String serverUrl;

	@Autowired
	private DocContentRepository docContentRepository;

	/**
	 * 1.app 案件或检查列表接口
	 * 
	 * @param mode
	 *            类型：案件；专项检查；日常检查
	 * @param status
	 *            案件（或检查）的状态
	 * @param startNo
	 *            开始页
	 * @param endNo
	 *            结束页
	 * @param queryDate
	 *            查询时间 今天：1，近7天：2，近30天：3，近180天：4，近365天:5
	 * @param caseName
	 *            案件名称
	 * @return 案件或检查列表
	 */
	@GetMapping("/list")
	public Result<Object> getCaseList(@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "startNo", required = false) String startNo,
			@RequestParam(value = "endNo", required = false) String endNo,
			@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "flowType", required = false) String flowType) {

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO pDTO = this.personDao.findUserInfo(personDTO.getId());

		Map<String, Object> params = new HashMap<>();

		params.put("startNo", Integer.valueOf(startNo));
		params.put("endNo", Integer.valueOf(endNo));
		params.put("status", status);
		params.put("personId", personDTO.getId());
		params.put("deptId", pDTO.getDeptId());
		if (!StringUtils.isEmpty(queryDate) && !queryDate.equals("null")) {
			params.put("queryDate", Integer.valueOf(queryDate));
		}

		List<ApiCaseListDTO> apiCaseListDTO = new ArrayList<ApiCaseListDTO>();
		List<YujDTO> yuj = new ArrayList<YujDTO>();
		Map<String, Object> resultMap = new HashMap<>();
		String caseNum = "";

		if (StringUtils.isEmpty(mode)) {
			// 参数为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "mode is null");

		} else if (mode.equals(CommonParameters.FlowType.CASE) || mode == CommonParameters.FlowType.CASE) {
			// 案件
			if (!StringUtils.isEmpty(caseName) && !caseName.equals("null")) {
				params.put("caseName", caseName);
			}
			if (!StringUtils.isEmpty(flowType) && !flowType.equals("null")) {
				params.put("flowType", flowType);
			}

			apiCaseListDTO = this.caseService.findCaseList(params);

			for (int i = 0; i < apiCaseListDTO.size(); i++) {
				caseNum = apiCaseListDTO.get(i).getCaseNum();
				yuj = this.yujService.getWarnListByCaseNum(caseNum);
				if (yuj.size() > 0) {
					apiCaseListDTO.get(i).setIsYuj("1");
				} else {
					apiCaseListDTO.get(i).setIsYuj("2");
				}
			}

			if (apiCaseListDTO.size() == 0) {
				// 数据为空
				resultMap.put("apiCaseListDTO", "无数据！");
				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

			}

			resultMap.put("apiCaseListDTO", apiCaseListDTO);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

		} else if (mode.equals(CommonParameters.FlowType.DAILY_CHECK)
				|| mode == CommonParameters.FlowType.DAILY_CHECK) {
			// 日常检查
			if (!StringUtils.isEmpty(caseName) && !caseName.equals("null")) {
				params.put("checkTitle", caseName);
			}

			apiCaseListDTO = this.checkDailyService.findCheckDailyList(params);

			for (int i = 0; i < apiCaseListDTO.size(); i++) {
				caseNum = apiCaseListDTO.get(i).getCaseNum();
				yuj = this.yujService.getWarnListByCaseNum(caseNum);
				if (yuj.size() > 0) {
					apiCaseListDTO.get(i).setIsYuj("1");
				} else {
					apiCaseListDTO.get(i).setIsYuj("2");
				}
			}

			if (apiCaseListDTO.size() == 0) {
				// 数据为空
				resultMap.put("apiCaseListDTO", "无数据！");
				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

			}

			resultMap.put("apiCaseListDTO", apiCaseListDTO);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

		} else if (mode.equals(CommonParameters.FlowType.SPECIAL_CHECK)
				|| mode == CommonParameters.FlowType.SPECIAL_CHECK) {
			// 专项检查
			if (!StringUtils.isEmpty(caseName) && !caseName.equals("null")) {
				params.put("checkTitle", caseName);
			}
			apiCaseListDTO = this.lssuedService.findLssuedList(params);

			for (int i = 0; i < apiCaseListDTO.size(); i++) {
				caseNum = apiCaseListDTO.get(i).getCaseNum();
				yuj = this.yujService.getWarnListByCaseNum(caseNum);
				if (yuj.size() > 0) {
					apiCaseListDTO.get(i).setIsYuj("1");
				} else {
					apiCaseListDTO.get(i).setIsYuj("2");
				}
			}

			if (apiCaseListDTO.size() == 0) {
				// 数据为空
				resultMap.put("apiCaseListDTO", "无数据！");
				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
			}

			resultMap.put("apiCaseListDTO", apiCaseListDTO);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

		} else {
			// 参数错误
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "mode is error");

		}
	}

	/**
	 * 2.app 案件或检查详情接口
	 * 
	 * @param caseNum
	 *            案件（或检查）caseNum
	 * @param type
	 *            类型：日常检查5;专项检查4;案件6;
	 * @return 案件或检查详情
	 */
	@GetMapping("/info")
	public Result<Object> getCaseInfo(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "type", required = false) String type) {

		Map<String, Object> params = new HashMap<>();
		params.put("caseNum", caseNum);
		params.put("type", type);

		ApiCaseInfoDTO apiCaseInfoDTO = new ApiCaseInfoDTO();
		Map<String, Object> resultMap = new HashMap<>();

		if (StringUtils.isEmpty(caseNum) || StringUtils.isEmpty(type)) {
			// 参数为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "caseNum or type is null");

		} else if (type == CommonParameters.FlowType.SPECIAL_CHECK
				|| type.equals(CommonParameters.FlowType.SPECIAL_CHECK)) {
			// 专项检查
			LssuedDetailInfoDTO lssuedDetailInfoDTO = this.lssuedService.findCaseInfo(params);

			if (lssuedDetailInfoDTO == null) {
				// 返回数据为空
				resultMap.put("lssuedDetailInfoDTO", "无数据！");
				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

			}

			lssuedDetailInfoDTO.setFlowType(CommonParameters.FlowType.SPECIAL_CHECK);
			resultMap.put("apiCaseInfoDTO", lssuedDetailInfoDTO);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

		} else if (type == CommonParameters.FlowType.DAILY_CHECK
				|| type.equals(CommonParameters.FlowType.DAILY_CHECK)) {
			// 日常检查
			ApiDailyCheckDTO apiDailyCheckDTO = this.checkDailyService.findCaseInfo(params);

			if (apiDailyCheckDTO == null) {
				// 返回数据为空
				resultMap.put("apiDailyCheckDTO", "无数据！");

				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
			}

			apiDailyCheckDTO.setFlowType(CommonParameters.FlowType.DAILY_CHECK);
			resultMap.put("apiCaseInfoDTO", apiDailyCheckDTO);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

		} else if (type == CommonParameters.FlowType.CASE || type.equals(CommonParameters.FlowType.CASE)) {
			// 案件
			apiCaseInfoDTO = this.caseService.findCaseInfo(params);

			if (apiCaseInfoDTO == null) {
				// 返回数据为空
				resultMap.put("apiCaseInfoDTO", "无数据！");
				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

			}

			String filePath = serverUrl + "file/" + filesPath.split("/")[filesPath.split("/").length - 1]
					+ apiCaseInfoDTO.getPunishBill();
			apiCaseInfoDTO.setPunishBill(filePath);

			resultMap.put("apiCaseInfoDTO", apiCaseInfoDTO);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

		} else {
			// 参数错误
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "id or type is error");

		}

	}

	/**
	 * 3.app 案件文书查询
	 * 
	 * @param caseNum
	 *            案件（或检查）编号;
	 * @param code
	 *            文书编号;
	 * @return 案件文书查询
	 */
	@GetMapping("/doc")
	public Result<Object> getDoc(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "code", required = false) String code) {

		if (StringUtils.isEmpty(caseNum)) {
			// 案件（或检查）编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "caseNum is null!");
		}

		if (StringUtils.isEmpty(code)) {
			// 文书编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "code is null!");
		}

		Map<String, Object> params = new HashMap<>();
		params.put("caseNum", caseNum);
		params.put("code", code);

		ApiCaseDocDTO apiCaseDocDTO = new ApiCaseDocDTO();
		apiCaseDocDTO = this.docContentService.findDocInfo(params);

		Map<String, Object> resultMap = new HashMap<>();

		if (apiCaseDocDTO == null) {
			resultMap.put("apiCaseDocDTO", "无数据！");
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
		}

		resultMap.put("apiCaseDocDTO", apiCaseDocDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

	}

	/**
	 * 4.app 案件上报
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param caseName
	 *            案件名称
	 * @param caseAddress
	 *            案发地址
	 * @param caseSource
	 *            案源
	 * @param caseApplyDate
	 *            收件时间
	 * @param caseHandler
	 *            经办人
	 * @param caseZzfryId
	 *            主执法人id
	 * @param caseZzfryname
	 *            主执法人
	 * @param caseFzfryId
	 *            协办人id
	 * @param caseFzfryname
	 *            协办人
	 * @param briefCaseContent
	 *            简要案件内容
	 * @param caseTime
	 *            案发时间
	 * @param caseType
	 *            案件类型
	 * @param message
	 *            处理意见
	 * @param ifZp
	 *            是否执行：是 指派给自己，否:上报
	 * @param lat
	 *            经度
	 * @param lng
	 *            纬度
	 * @param checkId
	 *            检查ID
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
	 * @return 案件信息
	 */
	@PostMapping("/add")
	public Result<Object> addCase(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "caseAddress", required = false) String caseAddress,
			@RequestParam(value = "caseSource", required = false) String caseSource,
			@RequestParam(value = "caseApplyDate", required = false) String caseApplyDate,
			@RequestParam(value = "caseZpdate", required = false) String caseZpdate,
			@RequestParam(value = "caseHandler", required = false) String caseHandler,
			@RequestParam(value = "caseZzfryname", required = false) String caseZzfryname,
			@RequestParam(value = "caseZzfryId", required = false) String caseZzfryId,
			@RequestParam(value = "caseFzfryname", required = false) String caseFzfryname,
			@RequestParam(value = "caseFzfryId", required = false) String caseFzfryId,
			@RequestParam(value = "briefCaseContent", required = false) String briefCaseContent,
			@RequestParam(value = "caseTime", required = false) String caseTime,
			@RequestParam(value = "caseType", required = false) String caseType,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "ifZp", required = false) String ifZp,
			@RequestParam(value = "lng", required = false) String lng,
			@RequestParam(value = "lat", required = false) String lat,
			@RequestParam(value = "checkId", required = false) String checkId,

			@RequestParam(value = "caseStatus", required = false) Integer caseStatus,
			@RequestParam(value = "partyType", required = false) String partyType,
			// 个人
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "sex", required = false) String sex,
			@RequestParam(value = "age", required = false) String age,
			@RequestParam(value = "idCard", required = false) String idCard,
			@RequestParam(value = "tel", required = false) String tel,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "postCode", required = false) String postCode,
			// 公司
			@RequestParam(value = "legalName", required = false) String legalName,
			@RequestParam(value = "orgIdCard", required = false) String orgIdCard,
			@RequestParam(value = "unitName", required = false) String unitName,
			@RequestParam(value = "unitAddress", required = false) String unitAddress,
			@RequestParam(value = "orgCode", required = false) String orgCode) {
		if (StringUtils.isEmpty(lng)) {
			// 纬度为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "纬度为空.");
		}
		if (StringUtils.isEmpty(lat)) {
			// 经度为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "经度为空.");
		}
		if (StringUtils.isEmpty(caseAddress)) {
			// 案发地址为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "caseAddress is null!");
		}

		// 当前登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		CaseEntity caseEntity = new CaseEntity();
		caseEntity.setCaseName(caseName);
		caseEntity.setCaseAddress(caseAddress);
		caseEntity.setCaseSource(caseSource);
		if (caseSource.equals("5") || caseSource.equals("6")) {
			caseEntity.setCheckId(checkId);
			if (caseSource.equals("5")) {
				// 专项检查
				this.lssuedService.updateIsRelateLssued(checkId);
			}
			if (caseSource.equals("6")) {
				// 日常检查
				this.checkDailyService.updateIsRelateCheckDaily(checkId);
			}
			// add by liuzhih start 20180706
			// 如果是来自专项或日常检查则复制对应检查勘验笔录
			// 一般 849b8d5d-a112-42b6-8e6a-cdde385c3c05
			if (StringUtils.isEmpty(caseNum)) {
				if (caseEntity.getId() == null || caseEntity.getId().equals("")) {
					caseEntity.setId(UUID.randomUUID().toString());
				}
				if (caseEntity.getCaseSource().equals("5")) {
					// 专项 b8678bbb-831c-4d6d-9276-fd403ddc3ceb
					List<DocContentEntity> docContentEntitys = docContentRepository
							.findByTemplateIdAndCaseId("b8678bbb-831c-4d6d-9276-fd403ddc3ceb", caseEntity.getCheckId());
					DocContentEntity docContentEntity1 = new DocContentEntity();
					for (DocContentEntity docContentEntity : docContentEntitys) {
						if (docContentEntity != null) {
							docContentEntity1.setCreateBy(user.getCertNum());
							docContentEntity1.setCreateDate(new Date());
							docContentEntity1.setCreateName(user.getName());
							docContentEntity1.setTemplateId("849b8d5d-a112-42b6-8e6a-cdde385c3c05");
							docContentEntity1.setCaseId(caseEntity.getId());
							docContentEntity1.setFlowStatus(CommonParameters.CaseStatus.AJLA);
							docContentEntity1.setId(UUID.randomUUID().toString());
							docContentEntity1.setValue(docContentEntity.getValue());
							docContentRepository.save(docContentEntity1);
						}
					}
				} else if (caseEntity.getCaseSource().equals("6")) {
					// 日常 90e31509-4c45-4341-b2d6-4dfb798ba671
					List<DocContentEntity> docContentEntitys = docContentRepository
							.findByTemplateIdAndCaseId("90e31509-4c45-4341-b2d6-4dfb798ba671", caseEntity.getCheckId());
					DocContentEntity docContentEntity1 = new DocContentEntity();
					for (DocContentEntity docContentEntity : docContentEntitys) {
						if (docContentEntity != null) {
							docContentEntity1.setCreateBy(user.getCertNum());
							docContentEntity1.setCreateDate(new Date());
							docContentEntity1.setCreateName(user.getName());
							docContentEntity1.setTemplateId("849b8d5d-a112-42b6-8e6a-cdde385c3c05");
							docContentEntity1.setCaseId(caseEntity.getId());
							docContentEntity1.setFlowStatus(CommonParameters.CaseStatus.AJLA);
							docContentEntity1.setId(UUID.randomUUID().toString());
							docContentEntity1.setValue(docContentEntity.getValue());
							docContentRepository.save(docContentEntity1);
						}
					}
				}
			}
			// add by liuzhih end 20180706
		}
		caseEntity.setCaseType(caseType);
		caseEntity.setDataSource(CommonParameters.DataSource.APP);
		if (!StringUtils.isEmpty(lat)) {
			caseEntity.setLat(Double.valueOf(lat));
		}
		if (!StringUtils.isEmpty(lng)) {
			caseEntity.setLng(Double.valueOf(lng));
		}
		if (!StringUtils.isEmpty(caseApplyDate)) {
			try {
				caseEntity.setCaseApplyDate(sdf.parse(caseApplyDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!StringUtils.isEmpty(caseZpdate)) {
			try {
				caseEntity.setCaseZpdate(sdf.parse(caseZpdate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		caseEntity.setCaseHandler(caseHandler);
		caseEntity.setBriefCaseContent(briefCaseContent);
		if (Integer.valueOf(ifZp) == CommonParameters.CaseType.CASE_REPORT) {
			// 上报案件
			caseEntity.setExeType(CommonParameters.CaseType.CASE_REPORT.toString());
			caseEntity.setCaseStatus(CommonParameters.CheckStatus.CASE_ADMISS);
			this.caseService.add(caseEntity);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "案件上报成功！");
		} else if (Integer.valueOf(ifZp) == CommonParameters.CaseType.CASE_IMPLEMENT) {
			// 执行案件

			// 指派人
			caseEntity.setCaseZpr(user.getId());
			// 指派时间
			//caseEntity.setCaseZpdate(new Date());
			caseEntity.setCaseZzfryid(caseZzfryId);
			caseEntity.setCaseZzfryname(caseZzfryname);
			caseEntity.setCaseFzfryname(caseFzfryname);
			caseEntity.setCaseFzfryid(caseFzfryId);
			if (!StringUtils.isEmpty(caseTime)) {
				try {
					caseEntity.setCaseTime(sdf.parse(caseTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			caseEntity.setCaseStatus(caseStatus);
			if (caseStatus.equals(CommonParameters.CaseStatus.XCCL)) {
				caseEntity.setFlowType(CommonParameters.FlowType.SIMPLE_CASE);
			} else if (caseStatus.equals(CommonParameters.CaseStatus.AJLA)) {
				caseEntity.setFlowType(CommonParameters.FlowType.NORMAL_CASE);
			}
			caseEntity.setExeType(CommonParameters.CaseType.CASE_IMPLEMENT.toString());

			CaseEntity caseE = new CaseEntity();

			if (StringUtils.isEmpty(caseNum)) {
				// 新增
				caseE = this.caseService.add(caseEntity);
			} else {
				// 修改
				CaseEntity apiCase = this.caseRepository.findByCaseNum(caseNum);
				BeanUtils.copyProperties(apiCase, caseEntity);
				caseEntity.setCaseStatus(caseStatus);
				caseEntity.setId(apiCase.getId());
				caseEntity.setCaseNum(caseNum);
				caseEntity.setDeptId(user.getDeptId());
				caseEntity.setUpdateBy(user.getCertNum());
				caseEntity.setUpdateDate(new Date());
				caseEntity.setUpdateName(user.getName());
				caseE = this.caseRepository.save(caseEntity);
			}

			PartyInfoEntity partyInfo = this.partyInfoRepository.findByCaseId(caseE.getId());

			if (partyInfo == null) {
				// 新增
				partyInfo = new PartyInfoEntity();
				partyInfo.setId(UUID.randomUUID().toString());
				partyInfo.setCaseId(caseE.getId());
				partyInfo.setCreateBy(user.getCertNum());
				partyInfo.setCreateDate(new Date());
				partyInfo.setCreateName(user.getName());
			} else {
				partyInfo.setUpdateBy(user.getCertNum());
				partyInfo.setUpdateDate(new Date());
				partyInfo.setUpdateName(user.getName());

			}

			PartyInfoEntity partyInfoEntity = new PartyInfoEntity();
			if (StringUtils.isEmpty(partyType)) {
				// 当事人类型为空
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "当事人类型为空.");
			} else if (Integer.valueOf(partyType) == CommonParameters.PartyType.PERSON) {
				// 个人
				if (StringUtils.isEmpty(name)) {
					// 姓名为空
					return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "姓名为空.");
				}
				partyInfo.setName(name);
				if (!StringUtils.isEmpty(age)) {
					partyInfo.setAge(Integer.valueOf(age));
				}
				if (!StringUtils.isEmpty(sex)) {
					partyInfo.setSex(Integer.valueOf(sex));
				}
				partyInfo.setIdCard(idCard);
				partyInfo.setTel(tel);
				partyInfo.setAddress(address);
				partyInfo.setPostCode(postCode);
				partyInfo.setType(Integer.valueOf(partyType));
				partyInfoEntity = this.partyInfoRepository.save(partyInfo);

			} else if (Integer.valueOf(partyType) == CommonParameters.PartyType.COMPANY) {
				partyInfo.setLegalName(legalName);
				partyInfo.setOrgIdCard(orgIdCard);
				partyInfo.setUnitName(unitName);
				partyInfo.setOrgCode(orgCode);
				partyInfo.setUnitAddress(unitAddress);
				partyInfo.setType(Integer.valueOf(partyType));
				partyInfoEntity = this.partyInfoRepository.save(partyInfo);
			} else {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "personType is error.");
			}

			if (caseEntity.getCaseStatus().intValue() == CommonParameters.CaseStatus.AJLA
					|| caseEntity.getCaseStatus().intValue() == CommonParameters.CaseStatus.XCCL) {
				// 下一节点处理人
				PersonDTO next = this.personDao.findUserInfo(caseZzfryId);

				FlowDTO flowDTO = new FlowDTO();
				flowDTO.setAssignee(user.getId());
				flowDTO.setBusinessId(caseE.getCaseNum());
				flowDTO.setComment(message);
				flowDTO.setNextAssignee(next.getId());
				flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
				if (caseStatus == CommonParameters.CaseStatus.XCCL) {
					flowDTO.setHandleMode(CommonParameters.SimpleFlow.LOCATE_PRO.toString());
				} else {
					flowDTO.setHandleMode(CommonParameters.SimpleFlow.PLACE_CASE.toString());
				}
				this.flowThreadService.setFlowDTO(flowDTO);
				this.flowThreadService.setFlowType(1);
				Thread ft = new Thread(this.flowThreadService);
				ft.start();
				return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "案件执行成功！");
			}
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "案件暂存成功！");
		} else {
			// ifZp参数错误
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "ifZp is error!");
		}
	}

	/**
	 * 5.案件模糊查询
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param caseName
	 *            案件名称
	 * @param zzfryName
	 *            主执法人员姓名
	 * @param start
	 *            开始位置
	 * @param length
	 *            长度
	 */
	@GetMapping("/caseLike")
	public Result<Object> caseLike(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "zzfryName", required = false) String zzfryName,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", caseNum);
		params.put("caseName", caseName);
		params.put("zzfryName", zzfryName);

		if (StringUtils.isEmpty(start)) {
			// 开始位置
			start = "0";
		}

		if (StringUtils.isEmpty(length)) {
			// 展示数量
			length = "10";
		}

		List<CaseListDTO> caseList = this.caseService.getCaseList(params, Integer.valueOf(start),
				Integer.valueOf(length));
		Map<String, Object> resultMap = new HashMap<>();

		if (caseList.size() == 0) {
			resultMap.put("caseList", "无数据！");
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
		}

		resultMap.put("caseList", caseList);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

	}

	/**
	 * 6. 案件统计-领导端
	 */
	@GetMapping("/leader/casecount")
	public Result<Object> leanderCaseCount() {

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();

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

		String caseStatusList = "";

		for (int i = 0; i < roleId.size(); i++) {
			if (roleId.get(i) == CommonParameters.Role.APPROVE && roleId.get(i) == CommonParameters.Role.OPINION) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
						ResultCode.APP_ROLE_ERROR.getMsg());
			}
			if (roleId.get(i) == CommonParameters.Role.APPROVE) {
				caseStatusList = "审批";
			} else if (roleId.get(i) == CommonParameters.Role.OPINION) {
				caseStatusList = "审核";
			}
		}

		if (StringUtils.isEmpty(caseStatusList)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
					ResultCode.APP_ROLE_ERROR.getMsg());
		}

		Map<String, Integer> resultMap = new HashMap<>();
		int approveCount = this.caseService.getLeaderCaseCount(personAppDTO.get(0), caseStatusList);

		resultMap.put("approveCount", approveCount);

		Map<String, Object> caseParams = new HashMap<>();
		caseParams.put("deptId", personAppDTO.get(0).getDeptId());
		Integer caseCount = this.caseService.getQueryCasecount(caseParams);

		resultMap.put("caseCount", caseCount);

		Map<String, Object> checkParams = new HashMap<>();
		checkParams.put("createBy", personAppDTO.get(0).getCertNum());
		Integer checkCount = this.checkQueryService.getCount(checkParams);

		resultMap.put("checkCount", checkCount);

		/*
		 * Map<String, Object> yuJparams = new HashMap<String, Object>();
		 * yuJparams.put("deptId", personAppDTO.get(0).getDeptId()); int count =
		 * yujDao.countBydeptId(yuJparams); resultMap.put("yujCount", count);
		 */

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

	}

	/**
	 * 8.下达通知列表
	 *
	 *
	 **/

	@GetMapping("/leader/notifications")
	public Result<Object> leaderNotify(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "checkTitle", required = false) String checkTitle,
			@RequestParam(value = "checkStatus", required = false) String checkStatus,
			@RequestParam(value = "queryDate", required = false) String queryDate) {

		if (!StringUtils.isEmpty(start) && !StringUtils.isEmpty(length)) {
			try {
				Integer.valueOf(start);
				Integer.valueOf(length);
			} catch (NumberFormatException e) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
			}
		} else {
			start = "0";
			length = "10";
		}

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();

		List<PersonAppDTO> personAppDTO = this.personDao.getPersonRole(personDTO.getId());

		List<String> roleId = new ArrayList<String>();
		for (int i = 0; i < personAppDTO.size(); i++) {
			if (personAppDTO.get(i).getRoleId() == null) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
						ResultCode.APP_ROLE_ERROR.getMsg());
			} else {
				roleId.add(personAppDTO.get(i).getRoleId().toString());
			}
		}
		if (roleId.contains(CommonParameters.Role.APPROVE.toString())
				&& roleId.contains(CommonParameters.Role.OPINION.toString())) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
					ResultCode.APP_ROLE_ERROR.getMsg());
		}

		Map<String, Object> map = new HashMap<>();
		map.put("createBy", personAppDTO.get(0).getCertNum());
		if (!StringUtils.isEmpty(checkTitle) && !checkTitle.equals("null")) {
			map.put("checkTitle", checkTitle);
		}
		if (!StringUtils.isEmpty(checkStatus) && !checkStatus.equals("null")) {
			map.put("status", checkStatus);
		}
		if (!StringUtils.isEmpty(queryDate) && !queryDate.equals("null")) {
			map.put("queryDate", Integer.valueOf(queryDate));
		}
		List<LssuedEntity> lssuedEntityList = this.checkQueryService.getCaseListCopy(map, Integer.valueOf(start),
				Integer.valueOf(length));

		String personName = "";

		for (int i = 0; i < lssuedEntityList.size(); i++) {
			String person[] = lssuedEntityList.get(i).getPersonId().split(",");
			if (person.length > 1) {
				for (int k = 0; k < person.length; k++) {
					User user = this.userService.getUserByPersonId(person[k]);
					personName += user.getNickname() + ",";
				}
				lssuedEntityList.get(i).setPersonName(personName.substring(0, personName.length() - 1));
			}
		}

		Map<String, List<LssuedEntity>> resultMap = new HashMap<>();
		resultMap.put("lssuedEntityList", lssuedEntityList);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}

	/**
	 * 9.案件查询列表
	 *
	 *
	 **/

	@GetMapping("/leader/caseList")
	public Result<Object> leaderCaseList(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "caseStatus", required = false) String caseStatus,
			@RequestParam(value = "queryDate", required = false) String queryDate) {
		try {
			Integer.valueOf(start);
			Integer.valueOf(length);
		} catch (NumberFormatException e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
		}

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();

		List<PersonAppDTO> list = this.personDao.getPersonRole(personDTO.getId());
		List<String> roleId = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRoleId() == null) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
						ResultCode.APP_ROLE_ERROR.getMsg());
			} else {
				roleId.add(list.get(i).getRoleId().toString());
			}
		}

		if (roleId.contains(CommonParameters.Role.APPROVE.toString())
				&& roleId.contains(CommonParameters.Role.OPINION.toString())) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(),
					ResultCode.APP_ROLE_ERROR.getMsg());
		}

		Map<String, Object> caseParams = new HashMap<>();
		caseParams.put("deptId", list.get(0).getDeptId());
		caseParams.put("typeValue", "1002");
		caseParams.put("caseName", caseName);
		caseParams.put("caseStatus", caseStatus);
		if (!StringUtils.isEmpty(queryDate) && !queryDate.equals("null")) {
			caseParams.put("queryDate", Integer.valueOf(queryDate));
		}
		List<CaseListDTO> listDTOS = this.caseService.getQueryCaseList(caseParams, Integer.valueOf(start),
				Integer.valueOf(length));
		Map<String, List<CaseListDTO>> resultMap = new HashMap<>();
		resultMap.put("listDTOS", listDTOS);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

	}

	/**
	 * 12.app 专项下达通知---暂存
	 * 
	 * @param checkNum
	 *            检查编号
	 * @param status
	 *            检查状态
	 * @param checkTitle
	 *            检查标题
	 * @param checkObject
	 *            检查对象
	 * @param startDate
	 *            检查开始时间
	 * @param endDate
	 *            检查结束时间
	 * @param deptId
	 *            检查部门
	 * @param person
	 *            执法人员
	 * @param checkBasis
	 *            检查依据
	 * @param checkWay
	 *            检查方式
	 * @param checkContent
	 *            检查内容
	 * @return 下达专项信息
	 */
	@PostMapping("/saveCheck")
	public Result<Object> saveLssued(@RequestParam(value = "checkNum", required = false) String checkNum,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "checkTitle", required = false) String checkTitle,
			@RequestParam(value = "checkObject", required = false) String checkObject,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "deptId", required = false) String deptId,
			@RequestParam(value = "person", required = false) String person,
			@RequestParam(value = "checkBasis", required = false) String checkBasis,
			@RequestParam(value = "checkWay", required = false) String checkWay,
			@RequestParam(value = "checkContent", required = false) String checkContent) {
		/*
		 * if(StringUtils.isEmpty(checkTitle)){ //检查标题为空 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "checkTitle is null!"); }
		 * 
		 * if(StringUtils.isEmpty(checkObject)){ //检查对象 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "checkObject is null!"); }
		 * 
		 * if(StringUtils.isEmpty(startDate)){ //检查开始时间 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "startDate is null!"); }
		 * 
		 * if(StringUtils.isEmpty(endDate)){ //检查结束时间 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "endDate is null!"); }
		 * 
		 * if(StringUtils.isEmpty(deptId)){ //检查部门 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "dept is null!"); }
		 * 
		 * if(StringUtils.isEmpty(person)){ //执法人员 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "person is null!"); } if(StringUtils.isEmpty(checkBasis)){ //检查依据 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "checkBasis is null!"); }
		 * 
		 * if(StringUtils.isEmpty(checkWay)){ //检查方式 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "checkWay is null!"); }
		 * 
		 * if(StringUtils.isEmpty(checkContent)){ //检查内容 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "checkContent is null!"); }
		 */
		if (status.equals(CommonParameters.CheckStatus.CHECK_ZC.toString())) {
			LssuedEntity lssuedEntity = new LssuedEntity();
			lssuedEntity.setCheckTitle(checkTitle);
			lssuedEntity.setCheckObject(checkObject);
			SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				if (!StringUtils.isEmpty(startDate)) {
					lssuedEntity.setStartDate(dates.parse(startDate));
				}
				if (!StringUtils.isEmpty(endDate)) {
					lssuedEntity.setEndDate(dates.parse(endDate));
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
			lssuedEntity.setStatus(status);
			lssuedEntity.setDeptId(deptId);
			lssuedEntity.setPersonId(person);
			lssuedEntity.setCheckBasis(checkBasis);
			lssuedEntity.setCheckWay(checkWay);
			lssuedEntity.setCheckContent(checkContent);
			lssuedEntity.setComment("专项检查下达通知暂存");
			lssuedEntity.setCheckNum(checkNum);
			this.lssuedService.appTemsave(lssuedEntity);
		} else {
			if (!StringUtils.isEmpty(checkNum)) {
				lssuedDao.deleteByCheckNum(checkNum);
			}

			String[] personIds = person.split(",");
			for (int i = 0; i < personIds.length; i++) {
				LssuedEntity lssuedEntity = new LssuedEntity();
				lssuedEntity.setCheckTitle(checkTitle);
				lssuedEntity.setCheckObject(checkObject);
				SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					if (!StringUtils.isEmpty(startDate)) {
						lssuedEntity.setStartDate(dates.parse(startDate));
					}
					if (!StringUtils.isEmpty(endDate)) {
						lssuedEntity.setEndDate(dates.parse(endDate));
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
				lssuedEntity.setStatus(status);
				lssuedEntity.setDeptId(deptId);
				lssuedEntity.setPersonId(personIds[i]);
				lssuedEntity.setCheckBasis(checkBasis);
				lssuedEntity.setCheckWay(checkWay);
				lssuedEntity.setCheckContent(checkContent);
				lssuedEntity.setComment("专项检查下达通知");
				this.lssuedService.appSave(lssuedEntity);
				PersonDTO personDTO = personDao.findPersonInfo(personIds[i]);
				try {
					ApiMessagePushController.send("您有一条专项任务，请点击查看...", personDTO.getCertNum(), CommonParameters.PushMessage.CASE_TYPE_3,
							lssuedEntity.getCheckNum());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return ResultUtil.success();
	}

	/**
	 * 13.日常检查暂存、编辑
	 * 
	 * @param id
	 * @param checkTitle
	 * @param checkMode
	 * @param checkModeId
	 * @param roadName
	 * @param checkedDate
	 * @param checkObjectType
	 * @param assistPersonId
	 * @param checkedUserName
	 * @param checkedUserId
	 * @param registNum
	 * @param legalPerson
	 * @param checkSituation
	 * @param unitName
	 * @param lat
	 *            经度
	 * @param lng
	 *            纬度
	 * @return
	 */
	@PostMapping("/saveDailyCheck")
	public Result<Object> saveDailyCheck(@RequestParam(value = "checkNum", required = false) String checkNum,
			@RequestParam(value = "checkTitle", required = false) String checkTitle,
			@RequestParam(value = "checkMode", required = false) String checkMode,
			@RequestParam(value = "checkModeId", required = false) String checkModeId,
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
		if (StringUtils.isEmpty(lng)) {
			// 纬度为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "纬度为空.");
		}
		if (StringUtils.isEmpty(lat)) {
			// 经度为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "经度为空.");
		}
		if (StringUtils.isEmpty(checkTitle)) {
			// 检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查标题不能为空");
		}

		if (StringUtils.isEmpty(checkMode)) {
			// 检查类型
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "检查类型不能为空");
		}

		/*
		 * if(StringUtils.isEmpty(roadName)){ //检查开始时间 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "街道名称不能为空"); }
		 * 
		 * if(StringUtils.isEmpty(checkedDate)){ //检查结束时间 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "检查时间不能为空"); }
		 * 
		 * if(StringUtils.isEmpty(checkObjectType)){ //检查部门 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "检查对象类型不能为空"); }
		 * 
		 * if(StringUtils.isEmpty(assistPersonId)){ //执法人员 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "协办人员不能为空"); } if(StringUtils.isEmpty(checkSituation)){ //检查依据 return
		 * ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(),
		 * "检查情况不能为空"); }
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();

		User user = this.userService.getUserByPersonId(personDTO.getId());

		CheckDailyEntity checkEntity = new CheckDailyEntity();
		checkEntity.setCheckNum(checkNum);
		checkEntity.setCheckTitle(checkTitle);
		checkEntity.setCheckMode(checkMode);
		checkEntity.setCheckModeId(checkModeId);
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

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "下达通知成功！");
	}

	/**
	 * 14.待办事项
	 * 
	 * @param currentPage
	 *            页数
	 */
	@GetMapping("/backlog")
	public Result<Object> getUserTask(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length) {
		if (!StringUtils.isEmpty(start) && !StringUtils.isEmpty(length)) {
			try {
				Integer.valueOf(start);
				Integer.valueOf(length);
			} catch (NumberFormatException e) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
			}
		} else {
			start = "0";
			length = "10";
		}

		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		// 获取当前登录用户信息
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		List<TaskDTO> taskDTOs = this.flowService.getUserTask(user.getId().toString(), Integer.valueOf(start),
				Integer.valueOf(length));

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, taskDTOs);
	}

	/**
	 * 15.待办事项--案件列表
	 * 
	 * @param start
	 *            开始位置
	 * @param length
	 *            长度
	 * @param caseName
	 *            案件名称
	 */
	@GetMapping("/getCaseTask")
	public Result<Object> getCaseTask(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "caseStatus", required = false) String caseStatus,
			@RequestParam(value = "flowType", required = false) String flowType) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(queryDate) && !queryDate.equals("null")) {
			try {
				Integer.valueOf(queryDate);
			} catch (NumberFormatException e) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
			}
			params.put("queryDate", Integer.valueOf(queryDate));
		}
		try {
			Integer.valueOf(start);
			Integer.valueOf(length);

		} catch (NumberFormatException e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
		}
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		// 获取当前登录用户信息
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		params.put("caseStatus", caseStatus);
		params.put("caseName", caseName);
		params.put("userId", user.getId().toString());
		params.put("flowType", flowType);

		List<ApiCaseTaskDTO> list = this.flowService.getCaseTask(params, Integer.valueOf(start),
				Integer.valueOf(length));

		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String caseNum = "";

		for (int i = 0; i < list.size(); i++) {
			caseNum = list.get(i).getCaseNum();
			yuj = this.yujService.getWarnListByCaseNum(caseNum);
			if (yuj.size() > 0) {
				list.get(i).setIsYuj("1");
			} else {
				list.get(i).setIsYuj("2");
			}
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}

	/**
	 * 16.待办事项--日常检查列表
	 * 
	 * @param start
	 *            开始位置
	 * @param length
	 *            长度
	 * @param checkTitle
	 *            检查标题
	 */
	@GetMapping("/getCheckDailyTask")
	public Result<Object> getCheckDailyTask(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "checkTitle", required = false) String checkTitle,
			@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "checkStatus", required = false) String checkStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(queryDate) && !queryDate.equals("null")) {
			try {
				Integer.valueOf(queryDate);
			} catch (NumberFormatException e) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
			}
			params.put("queryDate", Integer.valueOf(queryDate));
		}
		try {
			Integer.valueOf(start);
			Integer.valueOf(length);

		} catch (NumberFormatException e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
		}
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		// 获取当前登录用户信息
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		params.put("checkStatus", checkStatus);
		params.put("checkTitle", checkTitle);
		params.put("userId", user.getId().toString());

		List<ApiCheckTaskDTO> taskDTOs = this.flowService.getCheckDailyTask(params, Integer.valueOf(start),
				Integer.valueOf(length));

		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String checkNum = "";

		for (int i = 0; i < taskDTOs.size(); i++) {
			checkNum = taskDTOs.get(i).getCheckNum();
			yuj = this.yujService.getWarnListByCaseNum(checkNum);
			if (yuj.size() > 0) {
				taskDTOs.get(i).setIsYuj("1");
			} else {
				taskDTOs.get(i).setIsYuj("2");
			}
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, taskDTOs);
	}

	/**
	 * 17.待办事项--专项检查列表
	 * 
	 * @param start
	 *            开始位置
	 * @param length
	 *            长度
	 * @param checkTitle
	 *            检查标题
	 */
	@GetMapping("/getCheckTask")
	public Result<Object> getCheckTask(@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "checkTitle", required = false) String checkTitle,
			@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "checkStatus", required = false) String checkStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(queryDate) && !queryDate.equals("null")) {
			try {
				Integer.valueOf(queryDate);
			} catch (NumberFormatException e) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
			}
			params.put("queryDate", Integer.valueOf(queryDate));
		}
		try {
			Integer.valueOf(start);
			Integer.valueOf(length);

		} catch (NumberFormatException e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), "参数错误，必须传整型");
		}

		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		// 获取当前登录用户信息
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		params.put("checkStatus", checkStatus);
		params.put("checkTitle", checkTitle);
		params.put("userId", user.getId().toString());

		List<ApiCheckTaskDTO> taskDTOs = this.flowService.getCheckTask(params, Integer.valueOf(start),
				Integer.valueOf(length));

		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String checkNum = "";

		for (int i = 0; i < taskDTOs.size(); i++) {
			checkNum = taskDTOs.get(i).getCheckNum();
			yuj = this.yujService.getWarnListByCaseNum(checkNum);
			if (yuj.size() > 0) {
				taskDTOs.get(i).setIsYuj("1");
			} else {
				taskDTOs.get(i).setIsYuj("2");
			}
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, taskDTOs);
	}

	/**
	 * 18.获取案件附件 附件接口调试,按照小魏的意思把给他提供一个可直接访问的http链接
	 * http://47.100.82.50:8080/enforce/pages/attachFile/ca34b10a-812b-4dc7-995e-496b8c4e1ae0.mp4
	 * 看能不能正常播放 联调时需要把shiro验证去掉 如果可以的话,我们需要把上传附件的方法改一下,把附件都放到一个服务下面,可以直接访问
	 * 
	 * @param checkNum
	 * @return
	 */
	@SuppressWarnings("null")
	@GetMapping("/attach/list")
	public Result<Object> attachList(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "mode", required = false) String mode, HttpServletRequest request) {
		if (StringUtils.isEmpty(caseNum)) {
			// 检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}

		List<AttachFileEntity> attachFiles = null;

		String path = request.getContextPath();

		String baseUrl = uploadConfig.getServerUrl() + "file/";
		// request.getScheme() +
		// "://" +
		// request.getServerName() +
		// "192.168.1.128" +
		// ":" +
		// request.getServerPort() +
		// path +
		// "/";

		if (StringUtils.isEmpty(mode)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型不能为空");
		}

		if (mode.equals(CommonParameters.FlowType.DAILY_CHECK)) {
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);

			if (checkDailyEntity == null) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "日常检查编号错误");
			}

			attachFiles = this.caseService.caseFileList(checkDailyEntity.getId(), baseUrl);
		}

		else if (mode.equals(CommonParameters.FlowType.SPECIAL_CHECK)) {
			LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);

			if (lssuedEntity == null) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "专项检查编号错误");
			}

			attachFiles = this.caseService.caseFileList(lssuedEntity.getId(), baseUrl);
		}

		else if (mode.equals(CommonParameters.FlowType.CASE)) {

			CaseEntity caseEntity = this.caseService.getCaseByCaseNum(caseNum);

			if (caseEntity == null) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号错误");
			}

			attachFiles = this.caseService.caseFileList(caseEntity.getId(), baseUrl);

		}

		else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型错误");
		}
		String localpath = uploadConfig.getOtherPath();
		if (SysUtil.sysIsWin()) {
			localpath = uploadConfig.getWinPath();
		}

		List<Object> res = new ArrayList<>();
		for (AttachFileEntity atta : attachFiles) {
			if (atta.getFileType().equals("mp4")) {
				Map temp = JaveUtil.getMp4Info(localpath + atta.getFilePath().replaceFirst(baseUrl, ""));
				temp.putAll(new BeanMap(atta));
				res.add(temp);
			} else if (atta.getFileType().equals("mp3")) {
				Map temp = JaveUtil.getMp3Info(localpath + atta.getFilePath().replaceFirst(baseUrl, ""));
				temp.putAll(new BeanMap(atta));
				res.add(temp);
			} else {
				res.add(atta);
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, res);
	}

	/**
	 * 19. 案件受理获取检查标题
	 * 
	 * @param mode
	 *            类型 日常：6；专项：5.
	 */
	@GetMapping("/getCaseSourse")
	public Result<Object> getCaseSourse(@RequestParam(value = "mode", required = false) String mode) {
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		if (mode.equals(CommonParameters.CaseSourceCheckType.PRO)) {
			// 专项检查
			List<LssuedDTO> lssuedDTOs = this.lssuedService.getCaseSourceCheck(user.getDeptId());
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, lssuedDTOs);
		} else if (mode.equals(CommonParameters.CaseSourceCheckType.DAILY)) {
			// 日常检查
			List<CheckDailyDTO> checkDailyDTOs = this.checkDailyService.getCaseSourceCheck(user.getDeptId());
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkDailyDTOs);
		} else {
			return ResultUtil.toResponseWithData(ResultCode.BUSI_ERROR, "参数错误.");
		}
	}

	/**
	 * 20. 案卷评查提交
	 * 
	 * @param caseId
	 *            案件ID
	 * @param comment
	 *            评查结果
	 */
	@PostMapping("/caseComment")
	public Result<Object> caseComment(@RequestParam(value = "caseId", required = false) String caseId,
			@RequestParam(value = "comment", required = false) String comment) {
		if (StringUtils.isEmpty(caseId)) {
			// 案件ID
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件ID不能为空");
		}
		if (StringUtils.isEmpty(comment)) {
			// 评查结果为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "评查结果不能为空");
		}
		// 获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());

		CaseCommentEntity caseCommentEntity = new CaseCommentEntity();
		caseCommentEntity.setCaseId(caseId);
		caseCommentEntity.setComment(comment);
		caseCommentEntity.setCreateDate(new Date());
		caseCommentEntity.setCreateBy(user.getCertNum());
		caseCommentEntity.setCreateName(user.getName());
		try {
			this.caseCommentService.save(caseCommentEntity);

		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithData(ResultCode.BUSI_ERROR, "案卷评查提交成功失败！");
		}

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "案卷评查提交成功！");
	}

	/**
	 * 21. 案卷评查查询
	 * 
	 * @param caseId
	 *            案件ID
	 */
	@GetMapping(value = "/caseComment/list")
	public Result<Object> list(@RequestParam(value = "caseId", required = false) String caseId) {
		if (StringUtils.isEmpty(caseId)) {
			// 案件ID
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件ID不能为空");
		}
		List<CaseCommentEntity> caseComment = this.caseCommentService.getListByCaseId(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseComment);
	}

	/**
	 * 22. 案卷评查内容查询
	 */
	@GetMapping(value = "/reviewContent")
	public Result<Object> reviewContent(@RequestParam(value = "parentId", required = false) Integer parentId) {
		Map<String, Object> params = new HashMap<>();
		params.put("delFlag", "0");
		System.out.println("parentId:" + parentId);
		params.put("parent", parentId);
		List<ReviewItemEntity> list = this.reviewService.getList(params, null, null);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}

	/**
	 * 23. 案卷评查保存\修改
	 * 
	 * @param caseId
	 *            案件ID
	 * @param remark
	 *            备注
	 * @param itemIdsStr
	 *            条目ID
	 * @param status
	 *            状态 1暂存 2提交
	 * @param score
	 *            得分
	 */
	@PostMapping(value = "/reviewSave")
	public Result<Object> reviewSave(@RequestParam(value = "caseId", required = false) String caseId,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "itemIdsStr", required = false) String itemIdsStr,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "score", required = false) Integer score) {
		ReviewRecordDTO reviewRecordDTO = new ReviewRecordDTO();
		if (StrUtil.isNotEmpty(itemIdsStr)) {
			List<ReviewRecordItemDTO> itemIds = JSONObject.parseArray(itemIdsStr, ReviewRecordItemDTO.class);
			reviewRecordDTO.setItemIds(itemIds);
		}
		reviewRecordDTO.setCaseId(caseId);
		reviewRecordDTO.setRemark(remark);
		reviewRecordDTO.setStatus(status);
		if (StrUtil.isNotEmpty(score)) {
			reviewRecordDTO.setScore(100 - score);
		}
		reviewService.saveReviewRecordByapp(reviewRecordDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "评查成功");
	}

	/**
	 * 24. 根据案件编号查询已评查项目详情查询
	 * 
	 * @param caseId
	 */
	@GetMapping(value = "/getReviewComment")
	public Result<Object> getReviewComment(@RequestParam(value = "caseId", required = false) String caseId) {
		List<ReviewRecordItemEntity> list = this.reviewService.getAppReviewRecordItemIdByCaseId(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}

	/**
	 * 25. 案卷评查审核审批
	 * 
	 * @param caseId
	 */
	@GetMapping(value = "/submitReviewRecord")
	public Result<Object> submitReviewRecord(@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "recordId", required = false) String recordId,
			@RequestParam(value = "oneComment", required = false) String oneComment,
			@RequestParam(value = "twoComment", required = false) String twoComment) {
		if (StringUtils.isEmpty(status)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "评查记录状态不能为空");
		}
		if (StringUtils.isEmpty(recordId)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "评查记录ID不能为空");
		}
		ReviewRecordDTO reviewRecordDTO = new ReviewRecordDTO();
		reviewRecordDTO.setStatus(status);
		reviewRecordDTO.setId(Integer.valueOf(recordId));
		reviewRecordDTO.setOneComment(oneComment);
		reviewRecordDTO.setTwoComment(twoComment);
		this.reviewService.submitReview(reviewRecordDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "提交成功");
	}

	/**
	 * 26.app 评查记录列表分页查询
	 * 
	 * @param status
	 *            状态
	 * @param startNo
	 *            开始页
	 * @param endNo
	 *            结束页
	 * @param caseNum
	 *            案件编号
	 * @param caseName
	 *            案件名称
	 * @return 评查记录列表
	 */
	@GetMapping("/reviewRecordList")
	public Result<Object> getReviewRecordwList(@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "startNo", required = false) String startNo,
			@RequestParam(value = "endNo", required = false) String endNo,
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "caseNum", required = false) String caseNum) {

		Map<String, Object> params = new HashMap<>();
		if (StrUtil.isNotEmpty(status)) {
			params.put("status", status);
		}
		if (StrUtil.isNotEmpty(caseName)) {
			params.put("caseName", caseName);
		}
		if (StrUtil.isNotEmpty(caseNum)) {
			params.put("caseNum", caseNum);
		}

		if (StringUtils.isEmpty(startNo)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "startNo不能为空");
		}

		if (StringUtils.isEmpty(endNo)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "endNo不能为空");
		}
		List<ReviewRecordDTO> lists = reviewService.getReviewRecordList(params, Integer.valueOf(startNo),
				Integer.valueOf(endNo));
		Map<String, Object> resultMap = new HashMap<>();
		if (lists.size() == 0) {
			// 数据为空
			resultMap.put("apiReviewRecordList", "无数据！");
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
		}

		resultMap.put("apiReviewRecordList", lists);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);

	}

	/**
	 * 27. 根据评查记录项目id删除
	 * 
	 * @param caseId
	 */
	@PostMapping(value = "/deleteReviewRecordItem")
	public Result<Object> deleteReviewRecordItemIdById(@RequestParam(value = "id", required = false) String id) {
		this.reviewService.deleteReviewRecordItemIdById(id);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "操作成功");
	}

}
