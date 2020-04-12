package com.orhonit.ole.enforce.controller.shareapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.JaveUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.common.utils.SysUtil;
import com.orhonit.ole.enforce.config.UploadConfig;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dto.CaseInfoDTO;
import com.orhonit.ole.enforce.dto.PerVerifyInfoDTO;
import com.orhonit.ole.enforce.dto.shareapi.ApiDocFlowDTO;
import com.orhonit.ole.enforce.entity.AttachFileEntity;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.entity.PartyInfoEntity;
import com.orhonit.ole.enforce.exception.EnforceException;
import com.orhonit.ole.enforce.repository.AttachFileRepository;
import com.orhonit.ole.enforce.repository.CaseRepository;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.repository.PartyInfoRepository;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.enforce.service.casedeal.CaseDealService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.caseinfo.impl.FlowThreadService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.docflow.DocFlowService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.enforce.service.perverify.PerVerifyService;
import com.orhonit.ole.enforce.utils.FileToPdf;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 案件信息同步
 * 
 * @author ebusu
 */
@RestController("shareApiCaseController")
@RequestMapping("/shareapi/case")
public class ShareApiCaseController {

	@Autowired
	private CaseService caseService;

	@Autowired
	private CheckDailyService checkDailyService;

	@Autowired
	private LssuedService lssuedService;

	@Autowired
	private FlowThreadService flowThreadService;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private PartyInfoRepository partyInfoRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private DocFlowService docFlowService;

	@Autowired
	private DocContentService docContentService;

	@Autowired
	private UserService userService;

	@Autowired
	private DocTemplateService docTemplateService;

	@Autowired
	private UploadConfig uploadConfig;

	@Autowired
	private AttachFileRepository attachFileRepository;

	@Autowired
	private CaseDealService caseDealService;

	@Autowired
	private FlowService flowService;

	@Value("${files.punishBill}")
	private String filesPath;

	@Value("${upload.serverUrl}")
	private String serverUrl;

	@Autowired
	private DocContentRepository docContentRepository;
	
	@Autowired
	private PerVerifyService perVerifyService;

	// 可转换的文件类型列表
	@SuppressWarnings("serial")
	HashMap<String, List<String>> fileTypeMap = new HashMap<String, List<String>>() {
		{
			put("doc", new ArrayList<String>() {
				{
					add("doc");
					add("docx");
					add("txt");
					add("xls");
					add("xlsx");
				}
			});
			put("img", new ArrayList<String>() {
				{
					add("jpg");
					add("jpeg");
					add("png");
					add("gif");
					add("bmp");
				}
			});
			put("video", new ArrayList<String>() {
				{
					add("mp4");
				}
			});
		}
	};

	/**
	 * 案件保存接口
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
	@Transactional
	public Result<Object> addCase(
			@RequestParam(value = "caseName", required = false) String caseName,
			@RequestParam(value = "caseSource", required = false) String caseSource,
			@RequestParam(value = "caseApplyDate", required = false) String caseApplyDate,
			@RequestParam(value = "caseAddress", required = false) String caseAddress,
			@RequestParam(value = "caseType", required = false) String caseType,
			@RequestParam(value = "caseZpdate", required = false) String caseZpdate,
			@RequestParam(value = "caseTime", required = false) String caseTime,
			@RequestParam(value = "caseHandler", required = false) String caseHandler,  // 经办人
			@RequestParam(value = "caseZzfryId", required = false) String caseZzfryId,
			@RequestParam(value = "caseFzfryId", required = false) String caseFzfryId,
			@RequestParam(value = "briefCaseContent", required = false) String briefCaseContent, // 简要内容
			@RequestParam(value = "handleMode", required = false) String handleMode, // 处理方式
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "lng", required = false) String lng,
			@RequestParam(value = "lat", required = false) String lat,

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
		if (StringUtils.isEmpty(caseName)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件名称不能为空.");
		}
		if (StringUtils.isEmpty(caseSource)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案源不能为空.");
		}
		if (StringUtils.isEmpty(caseAddress)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案发地址不能为空.");
		}
		if (StringUtils.isEmpty(caseApplyDate)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "收件时间不能为空.");
		}
		if (StringUtils.isEmpty(caseType)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "执法类别不能为空.");
		}
		
		if (StringUtils.isEmpty(caseZpdate)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "指派时间不能为空.");
		}
		
		if (StringUtils.isEmpty(caseTime)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案发时间不能为空.");
		}
		
		if (StringUtils.isEmpty(caseZzfryId)) {
			// 主执法人员编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "主执法人员编号为空.");
		}
		if (StringUtils.isEmpty(caseFzfryId)) {
			// 副执法人员编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "副执法人员编号为空!");
		}
		
		if (StringUtils.isEmpty(handleMode)) {
			// 副执法人员编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理方式不能为空!");
		}
		
		if (StringUtils.isEmpty(message)) {
			// 副执法人员编号
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "备注信息不能为空!");
		}
		
		if (StringUtils.isEmpty(partyType)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "当事人类型不能为空.");
		}
		
		if ( partyType.equals("1") ) {
			if ( StringUtils.isEmpty(name) ) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "当事人姓名不能为空.");
			}
		}
		
		if ( partyType.equals("2") ) {
			if ( StringUtils.isEmpty(unitName) ) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "单位名称不能为空.");
			}
		}
		
		// 当前登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		CaseInfoDTO caseInfoDTO = new CaseInfoDTO();
		PerVerifyInfoDTO perVerifyInfoDTO = new PerVerifyInfoDTO();
		
		caseInfoDTO.setId(UUID.randomUUID().toString());
		caseInfoDTO.setCaseNum(this.caseService.getCaseNumAPP());
		perVerifyInfoDTO.setCaseId(caseInfoDTO.getId());
		
		caseInfoDTO.setCaseName(caseName);
		caseInfoDTO.setCaseSource(caseSource);
		caseInfoDTO.setCaseHandler(caseHandler);
		caseInfoDTO.setCaseAddress(caseAddress);
		caseInfoDTO.setCaseType(caseType);
		
		caseInfoDTO.setBriefCaseContent(briefCaseContent);
		caseInfoDTO.setCaseZpr(user.getId().toString());
		try {
			caseInfoDTO.setCaseApplyDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(caseApplyDate));
			caseInfoDTO.setCaseTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(caseTime));
			caseInfoDTO.setCaseZpdate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(caseZpdate));
		} catch (ParseException e) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "日期格式不正确.");
		}
		caseInfoDTO.setCaseStatus(CommonParameters.CaseStatus.CBHS);
		caseInfoDTO.setCaseZzfryid(caseZzfryId);
		User zzfryUser = this.userService.getUserByPersonId(caseZzfryId);
		if ( zzfryUser == null ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "主执法人员编号错误.");
		}
		caseInfoDTO.setCaseZzfryname(zzfryUser.getNickname());
		
		String fzfryids[] = caseFzfryId.split(",");
		
		String fzfryName = "";
		String fzfryId = "";
		for(int i = 0;i<fzfryids.length;i++){
			User tuser = this.userService.getUserByPersonId(fzfryids[i].trim());
			if ( tuser == null ) {
				return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "副执法人员编号错误.");
			}
			if(!StringUtils.isEmpty(tuser.getNickname())){
				fzfryName += tuser.getNickname()+",";
			}
			fzfryId += fzfryids[i].trim()+",";
		}
		caseInfoDTO.setCaseFzfryid(fzfryId.substring(0, fzfryId.length()-1));
		caseInfoDTO.setCaseFzfryname(fzfryName.substring(0, fzfryName.length()-1));
		caseInfoDTO.setDealType(Integer.valueOf(handleMode));
		caseInfoDTO.setComment(message);
		perVerifyInfoDTO.setComment(message);
		perVerifyInfoDTO.setDealType(Integer.valueOf(handleMode));
		CaseEntity caseEntity = new CaseEntity();
		BeanUtils.copyProperties(caseInfoDTO, caseEntity);
		FlowDTO flowDTO = this.caseService.saveShare(caseEntity, user);
		if ( flowDTO != null ){
			flowDTO.setHandleMode(caseInfoDTO.getDealType().toString());
			this.flowThreadService.setFlowDTO(flowDTO);
			this.flowThreadService.setFlowType(1);
			Thread ft = new Thread(this.flowThreadService);
			ft.start();
			this.caseDealService.appSaveTaskEntity(flowDTO, true, user);
		}
		
		perVerifyInfoDTO.setType(Integer.valueOf(partyType));
		if ( !StringUtils.isEmpty(age)) {
			perVerifyInfoDTO.setAge(new Integer(age));
		}
		if ( !StringUtils.isEmpty(sex)) {
			perVerifyInfoDTO.setSex(new Integer(sex));
		}
		
		
		perVerifyInfoDTO.setName(name);
		perVerifyInfoDTO.setUnitName(unitName);
		perVerifyInfoDTO.setUnitAddress(unitAddress);
		perVerifyInfoDTO.setTel(tel);
		perVerifyInfoDTO.setIdCard(idCard);
		perVerifyInfoDTO.setPostCode(postCode);
		perVerifyInfoDTO.setAddress(address);
		perVerifyInfoDTO.setOrgCode(orgCode);
		perVerifyInfoDTO.setLegalName(legalName);
		perVerifyInfoDTO.setOrgIdCard(orgIdCard);
		
		PartyInfoEntity partyInfoEntity = this.partyInfoRepository.findByCaseId(perVerifyInfoDTO.getCaseId());

		if ( partyInfoEntity == null ) {
			// 新增
			partyInfoEntity = new PartyInfoEntity();
			BeanUtils.copyProperties(perVerifyInfoDTO, partyInfoEntity);
			partyInfoEntity.setCreateBy(user.getUsername());
			partyInfoEntity.setCreateName(user.getNickname());
			partyInfoEntity.setCreateDate(new Date());
			partyInfoEntity.setId(UUID.randomUUID().toString());
			this.partyInfoRepository.save(partyInfoEntity);
		} else {
			// 更新
			partyInfoEntity.setAddress(perVerifyInfoDTO.getAddress());
			partyInfoEntity.setAge(perVerifyInfoDTO.getAge());
			partyInfoEntity.setIdCard(perVerifyInfoDTO.getIdCard());
			partyInfoEntity.setLegalName(perVerifyInfoDTO.getLegalName());
			partyInfoEntity.setName(perVerifyInfoDTO.getName());
			partyInfoEntity.setOrgCode(perVerifyInfoDTO.getOrgCode());
			partyInfoEntity.setOrgIdCard(perVerifyInfoDTO.getOrgIdCard());
			partyInfoEntity.setPostCode(perVerifyInfoDTO.getPostCode());
			partyInfoEntity.setSex(perVerifyInfoDTO.getSex());
			partyInfoEntity.setTel(perVerifyInfoDTO.getTel());
			partyInfoEntity.setType(perVerifyInfoDTO.getType());
			partyInfoEntity.setUnitAddress(perVerifyInfoDTO.getUnitAddress());
			partyInfoEntity.setUnitName(perVerifyInfoDTO.getUnitName());
			partyInfoEntity.setUpdateBy(user.getUsername());
			partyInfoEntity.setUpdateName(user.getNickname());
			partyInfoEntity.setUpdateDate(new Date());
			this.partyInfoRepository.save(partyInfoEntity);
		}
		
		return ResultUtil.toResponse(ResultCode.SUCCESS);

	}

	/**
	 * 文书模版查询接口
	 * 
	 * @param flowNode
	 * @param flowType
	 * @return
	 */
	@GetMapping("/getDocList")
	public Result<Object> getDocList(@RequestParam(value = "flowNode", required = false) String flowNode,
			@RequestParam(value = "flowType", required = false) String flowType) {
		if (StringUtils.isEmpty(flowType)) {
			// 流程类型不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "流程类型为空.");
		}

		if (StringUtils.isEmpty(flowNode)) {
			// 案件或检查状态不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件或检查状态为空.");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("flowNode", flowNode);
		params.put("flowType", flowType);
		List<ApiDocFlowDTO> docFlowDTOs = docFlowService.getApiDocFlows(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, docFlowDTOs);
	}

	/**
	 * 文书提交
	 */
	@PostMapping("/saveDocContent")
	public Result<Object> saveDocContent(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "tempCode", required = false) String tempCode,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "contentId", required = false) String contentId) {

		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空.");
		}

		if (StringUtils.isEmpty(tempCode)) {
			// 模板编号不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "模板编号为空.");
		}

		if (StringUtils.isEmpty(content)) {
			// 文书内容不能为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "文书内容为空.");
		}

		String caseId = "";
		DocContentEntity docContentEntity = new DocContentEntity();
		CaseEntity caseEntity = this.caseService.getCaseByCaseNum(caseNum);
		if (caseEntity == null) {
			// 不是案件，判断是否是日常检查
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
			if (checkDailyEntity != null) {
				caseId = checkDailyEntity.getId();
				docContentEntity.setFlowStatus(Integer.valueOf(checkDailyEntity.getStatus()));
			}
		} else {
			caseId = caseEntity.getId();
			docContentEntity.setFlowStatus(caseEntity.getCaseStatus());
		}

		if ("".equals(caseId)) {
			// 案件或日常检查不存在
			return ResultUtil.toResponse(ResultCode.APP_CASE_NOT_EXIST);
		}

		DocTemplateEntity docTemplateEntity = this.docTemplateService.getTemplateByTemplateCode(tempCode);
		if (docTemplateEntity == null) {
			// 模板不存在
			return ResultUtil.toResponse(ResultCode.APP_TEMP_NOT_EXIST);
		}
		// 获取当前线程中的执法人员编号
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();

		User user = this.userService.getUserByPersonId(personDTO.getId());
		DocContentEntity docContentEntityInfo = null;
		if (StrUtil.isNotEmpty(contentId)) {
			docContentEntityInfo = docContentRepository.findOne(contentId);
		}
		if (docContentEntityInfo == null) {
			// 保存文书内容
			docContentEntity.setId(UUID.randomUUID().toString());
			docContentEntity.setCreateBy(user.getUsername());
			docContentEntity.setCreateDate(new Date());
			docContentEntity.setCreateName(user.getNickname());
			docContentEntity.setCaseId(caseId);
			docContentEntity.setTemplateId(docTemplateEntity.getId());
			docContentEntity.setValue(content);
			this.docContentService.saveAppContent(docContentEntity);
		} else {
			// 修改文书内容
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

	/**
	 * 上传附件
	 * 
	 * @param file
	 * @param request
	 */

	@PostMapping(value = "/upload")
	public Result<Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// 创建fileEntity
		AttachFileEntity attachFileEntity = new AttachFileEntity();
		attachFileEntity.setId(UUID.randomUUID().toString());
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		attachFileEntity.setCreateName(user.getNickname());
		attachFileEntity.setCreateBy(user.getUsername());

		// 案件编号
		String caseNum = request.getParameter("caseNum");
		String caseId = request.getParameter("caseId");
		String caseStatus = request.getParameter("caseStatus");
		attachFileEntity.setCaseNum(caseNum);
		attachFileEntity.setCaseId(caseId);
		attachFileEntity.setCaseStatus(caseStatus);
		// 文件类型
		String[] temp = file.getOriginalFilename().split("\\.");
		String fileType = temp[temp.length - 1];
		attachFileEntity.setFileType(fileType);
		// 原文件名
		String fileName = file.getOriginalFilename().substring(0,
				file.getOriginalFilename().length() - fileType.length() - 1);
		attachFileEntity.setFileName(fileName);
		attachFileEntity.setFileTitle(fileName);
		// 新文件名
		String fileLocalName = System.currentTimeMillis() + "";
		attachFileEntity.setFileNewname(fileLocalName);
		// 生成日期
		Date date = new Date();
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dformat.format(date);
		attachFileEntity.setCreateDate(date);
		String filePath, pdfPath;
		if (SysUtil.sysIsWin()) {
			// 文件保存路径
			filePath = uploadConfig.getWinPath() + "upload\\\\" + dateStr + "\\" + caseNum + "\\";
			// pdf文件保存路径
			pdfPath = uploadConfig.getWinPath() + "pdf\\" + dateStr + "\\" + caseNum + "\\";
		} else {
			// 文件保存路径
			filePath = uploadConfig.getOtherPath() + "upload/" + dateStr + "/" + caseNum + "/";
			// pdf文件保存路径
			pdfPath = uploadConfig.getOtherPath() + "pdf/" + dateStr + "/" + caseNum + "/";
		}
		attachFileEntity.setFilePath(filePath);
		try {
			// 创建目录
			File dir = new File(filePath);
			dir.mkdirs();

			// 获取文件输入流
			InputStream in = file.getInputStream();

			// 创建文件并保存
			File f = new File(filePath + fileLocalName + "." + fileType);
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			in.close();

			// 如果上传的文件类型为：doc/docx/txt/xls/xslx则转换为PDF
			if (fileTypeMap.get("doc").contains(fileType.toLowerCase())) {
				// 创建目录
				File pdfdir = new File(pdfPath);
				pdfdir.mkdirs();

				attachFileEntity.setPdfUrl(pdfPath);

				switch (fileType.toLowerCase()) {
				case "doc":
				case "docx":
					FileToPdf.DocToPdf(filePath + fileLocalName + "." + fileType, pdfPath + fileLocalName + ".pdf");
					break;
				case "txt":
					FileToPdf.TxtToPdf(filePath + fileLocalName + "." + fileType, pdfPath + fileLocalName + ".pdf");
					break;
				case "xls":
				case "xlsx":
					FileToPdf.XlsxToPdf(filePath + fileLocalName + "." + fileType, pdfPath + fileLocalName + ".pdf");
					break;
				default:
					break;
				}
			} else if (fileTypeMap.get("video").contains(fileType.toLowerCase())) {
				// 如果上传的文件类型为：MP4生成缩略图
				// 创建目录
				switch (fileType.toLowerCase()) {
				case "mp4":
					JaveUtil.saveSmallImg(filePath + fileLocalName + "." + fileType);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithMsg(ResultCode.APP_SAVE_ERROR.getCode(), "文件上传失败！");
		}
		// 保存上传信息到数据库
		attachFileRepository.save(attachFileEntity);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "文件上传成功！");
	}

	/**
	 * 流程提交接口（无附加内容）
	 * 
	 * @param caseNum
	 *            案件编号
	 * @param nextAssigneeId
	 *            下一节点处理人
	 * @param message
	 *            处理意见
	 **/
	@PostMapping("/flowSubmit")
	@Transactional
	public Result<Object> flowForward(@RequestParam(value = "caseNum", required = false) String caseNum,
			@RequestParam(value = "nextAssigneeId", required = false) String nextAssigneeId,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "flowType", required = false) String flowType,
			@RequestParam(value = "handleMode", required = false) String handleMode) {
		String nextAssignee = "";
		// 获取当前登录人ID

		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());

		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号为空");
		}
		if (StringUtils.isEmpty(nextAssigneeId)) {
			nextAssignee = user.getId().toString();
		} else {
			nextAssignee = nextAssigneeId;
		}

		if (StringUtils.isEmpty(message)) {
			// 处理意见为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理意见不能为空");
		}

		if (StringUtils.isEmpty(flowType)) {
			// 流程类型
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "流程类型不能为空");
		}

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(user.getId().toString());
		flowDTO.setBusinessId(caseNum);
		flowDTO.setComment(message);
		flowDTO.setFlowKey(flowType);
		flowDTO.setNextAssignee(nextAssignee);
		if (StrUtil.isNotEmpty(handleMode)) {
			flowDTO.setHandleMode(handleMode);
		}
		this.flowService.taskComplete(flowDTO);

		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "提交成功");
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
	 * @param handleMode
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
			@RequestParam(value = "handleMode", required = false) String handleMode,
			@RequestParam(value = "message", required = false) String message) {

		if (StringUtils.isEmpty(caseNum)) {
			// 案件编号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}

		if (StringUtils.isEmpty(handleMode)) {
			// 下一步案件状态
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "处理方式不能为空");
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

		if (Integer.valueOf(handleMode) == CommonParameters.SimpleFlow.TO_PUNISH) {

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
		} else if (Integer.valueOf(handleMode) == CommonParameters.SimpleFlow.TO_WARNING) {

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
		} else if (Integer.valueOf(nextCaseStatus) == CommonParameters.SimpleFlow.TO_INFORMING) {
			// 先行告知 9
			flowDTO.setHandleMode(CommonParameters.SimpleFlow.TO_INFORMING.toString());
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
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "提交成功！");
	}

}
