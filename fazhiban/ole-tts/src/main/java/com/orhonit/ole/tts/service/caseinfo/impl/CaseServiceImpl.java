package com.orhonit.ole.tts.service.caseinfo.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.utils.FileUtil;
import com.orhonit.ole.common.utils.SysUtil;
import com.orhonit.ole.sys.dao.UserDao;
import com.orhonit.ole.sys.model.SequenceEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.SequenceRepository;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.service.SysDictService;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.config.ThreadLocalVariables;
import com.orhonit.ole.tts.config.UploadConfig;
import com.orhonit.ole.tts.dao.CaseDao;
import com.orhonit.ole.tts.dao.CheckDailyDao;
import com.orhonit.ole.tts.dao.FilingDao;
import com.orhonit.ole.tts.dao.LssuedDao;
import com.orhonit.ole.tts.dao.PersonDao;
import com.orhonit.ole.tts.dto.CaseDetailDTO;
import com.orhonit.ole.tts.dto.CaseDetailInfoDTO;
import com.orhonit.ole.tts.dto.CaseDocDTO;
import com.orhonit.ole.tts.dto.CaseInfoDTO;
import com.orhonit.ole.tts.dto.CaseListDTO;
import com.orhonit.ole.tts.dto.DeptPersonDTO;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.FlowDealDTO;
import com.orhonit.ole.tts.dto.LssuedDTO;
import com.orhonit.ole.tts.dto.NextOpinionDTO;
import com.orhonit.ole.tts.dto.PersonAppDTO;
import com.orhonit.ole.tts.dto.PersonDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseInfoDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseListDTO;
import com.orhonit.ole.tts.dto.api.ApiCheckTypeDTO;
import com.orhonit.ole.tts.dto.api.ApiYujDTO;
import com.orhonit.ole.tts.dto.ps.AreaAndPotAndProPotDTO;
import com.orhonit.ole.tts.dto.ps.AreaAndPotDTO;
import com.orhonit.ole.tts.dto.ps.LoginUserDTO;
import com.orhonit.ole.tts.dto.ps.PotenceListDTO;
import com.orhonit.ole.tts.dto.ps.PsCaseDTO;
import com.orhonit.ole.tts.dto.ps.PsCheckDTO;
import com.orhonit.ole.tts.dto.ps.RigAndPotListDTO;
import com.orhonit.ole.tts.entity.AttachFileEntity;
import com.orhonit.ole.tts.entity.CaseDealEntity;
import com.orhonit.ole.tts.entity.CaseEntity;
import com.orhonit.ole.tts.entity.CheckDailyEntity;
import com.orhonit.ole.tts.entity.DeptEntity;
import com.orhonit.ole.tts.entity.DocTemplateEntity;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.repository.AttachFileRepository;
import com.orhonit.ole.tts.repository.CaseDealRepository;
import com.orhonit.ole.tts.repository.CaseRepository;
import com.orhonit.ole.tts.repository.CheckDailyRepository;
import com.orhonit.ole.tts.repository.DeptPersonRepository;
import com.orhonit.ole.tts.repository.DeptRepository;
import com.orhonit.ole.tts.repository.DocTemplateRepository;
import com.orhonit.ole.tts.repository.LssuedRepository;
import com.orhonit.ole.tts.service.caseinfo.CaseService;
import com.orhonit.ole.tts.service.doc.DocContentService;
import com.orhonit.ole.tts.service.flow.FlowService;
import com.orhonit.ole.tts.utils.EnforceException;
import com.orhonit.ole.tts.utils.FileToPdf;
import com.orhonit.ole.tts.utils.PageList;

@Service
public class CaseServiceImpl implements CaseService{
	
	private static final String CASE_NUM_TITLE = "CF";
	
	private static final String CASE_NUM_PREFIX = "-";
	
	private static final String CASE_NUM_SEQ_NAME = "case_id_seq";
	
	private static final int CASE_NUM_PAD_NUM = 4;
	
	// 可转换的文件类型列表
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
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private SequenceRepository sequenceRepository;
	
	@Autowired
	private DeptPersonRepository deptPersonRepository;
	
	@Autowired
	private CaseDealRepository caseDealRepository;
	
	@Autowired
	private LssuedRepository lssuedRepository;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private CaseService caseService;

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private CaseDao caseDao;
	
	@Autowired
	private LssuedDao lssuedDao;
	
	@Autowired
	private CheckDailyDao checkDailyDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FlowThreadService flowThreadService;
	
	@Autowired
	private FilingDao FilingDao;
	
	@Autowired
	private CheckDailyRepository checkDailyRepository;
	
	@Autowired
	private DocTemplateRepository docTemplateRepository;
	
	@Autowired
	private DocContentService docContentService;
	
	@Autowired
	private AttachFileRepository attachFileRepository;
	
	@Autowired
	private UploadConfig uploadConfig;
	
	@Autowired
	private SysDictService sysDictService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Override
	@Transactional
	public FlowDTO save(CaseEntity caseEntity) {
		User user = UserUtil.getCurrentUser();
		caseEntity.setUpdateBy(user.getUsername());
		caseEntity.setUpdateDate(new Date());
		caseEntity.setUpdateName(user.getNickname());
		caseEntity.setCreateBy(user.getUsername());
		caseEntity.setCreateDate(new Date());
		caseEntity.setCreateName(user.getNickname());
		if(caseEntity.getId()==null || caseEntity.getId().equals("")){
			caseEntity.setId(UUID.randomUUID().toString());
		}
		// 指派人
		caseEntity.setCaseZpr(user.getId().toString());
		caseEntity.setDeptId(user.getDept_id());
		// 指派时间
		caseEntity.setCaseZpdate(new Date());
		// 案件编号,生成规则(处罚-机构-日期-4位序列  )  e.g. CF-15010001003-20171130-0003
		caseEntity.setCaseNum(this.getCaseNum());
		this.caseRepository.save(caseEntity);
		
		if ( caseEntity.getCaseSource().equals(CommonParameters.CaseSourceCheckType.DAILY)) {
			// 日常检查
			//oldIsRelate
			this.checkDailyDao.updateOldIsRelate(caseEntity.getId());
			this.lssuedDao.updateOldIsRelateLssued(caseEntity.getId());
			//newIsRelate
			CheckDailyEntity checkDailyEntity = this.checkDailyRepository.findOne(caseEntity.getCheckId());
			checkDailyEntity.setIsRelate(CommonParameters.Effect.EFFECT.toString());
			this.checkDailyRepository.save(checkDailyEntity);
			
		} else if (caseEntity.getCaseSource().equals(CommonParameters.CaseSourceCheckType.PRO)) {
			// 专项检查
			this.checkDailyDao.updateOldIsRelate(caseEntity.getId());
			this.lssuedDao.updateOldIsRelateLssued(caseEntity.getId());
			this.lssuedDao.updateNewIsRelateLssued(caseEntity.getCheckId());
		}else{
			this.checkDailyDao.updateOldIsRelate(caseEntity.getId());
			this.lssuedDao.updateOldIsRelateLssued(caseEntity.getId());
		}
		
		// 根据案件状态判断是否需要启动流程
		if ( caseEntity.getCaseStatus().intValue() == CommonParameters.CaseStatus.CBHS) {
			FlowDTO flowDTO = new FlowDTO();
			flowDTO.setAssignee(user.getId().toString());
			flowDTO.setBusinessId(caseEntity.getCaseNum());
			flowDTO.setComment(caseEntity.getComment());
			this.userDao.getUserByPersonId(caseEntity.getCaseZzfryid());
			flowDTO.setNextAssignee(this.userDao.getUserByPersonId(caseEntity.getCaseZzfryid()).getId().toString());
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
			return flowDTO;
		}
		return null;
		
	}
	
	@Override
	public void update(CaseEntity caseEntity) {
		User user = UserUtil.getCurrentUser();
		CaseEntity caseentity = this.caseRepository.findOne(caseEntity.getId());
		caseEntity.setUpdateBy(user.getUsername());
		caseEntity.setUpdateDate(new Date());
		caseEntity.setUpdateName(user.getNickname());
		caseentity.setCaseName(caseEntity.getCaseName());
		caseentity.setCaseSource(caseEntity.getCaseSource());
		caseentity.setCaseAddress(caseEntity.getCaseAddress());
		caseentity.setCaseApplyDate(caseEntity.getCaseApplyDate());
		caseentity.setCaseHandler(caseEntity.getCaseHandler());
		caseentity.setCaseType(caseEntity.getCaseType());
		caseentity.setCaseTime(caseEntity.getCaseTime());
		caseentity.setCaseZzfryid(caseEntity.getCaseZzfryid());
		caseentity.setCaseZzfryname(deptPersonRepository.findById(caseEntity.getCaseZzfryid()).getName());
		caseentity.setCaseFzfryid(caseEntity.getCaseFzfryid());
		caseentity.setCaseFzfryname(deptPersonRepository.findById(caseEntity.getCaseFzfryid()).getName());
		caseentity.setBriefCaseContent(caseEntity.getBriefCaseContent());
		caseentity.setCaseStatus(CommonParameters.CaseStatus.CBHS);
		this.caseRepository.save(caseentity);
		if ( caseEntity.getCaseStatus().intValue() == CommonParameters.CaseStatus.CBHS) {
			//启动流程
			FlowDTO flowDTO = new FlowDTO();
			flowDTO.setAssignee(caseEntity.getCreateBy());
			flowDTO.setBusinessId(caseEntity.getCaseNum());
			flowDTO.setComment(caseEntity.getComment());
			flowDTO.setNextAssignee(caseEntity.getCaseZzfryname());
			flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
			flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
			// this.flowService.startFlowByKey(flowDTO);
			this.flowThreadService.setFlowDTO(flowDTO);
			this.flowThreadService.setFlowType(1);
			Thread ft = new Thread(this.flowThreadService);
			ft.start();
		}
	}

	public Page<CaseEntity> getCaseListOld(Map<String, Object> params, Integer start, Integer length) {
		PageRequest request = new PageRequest(start/length ,length);
		
		Specification<CaseEntity> spec = new Specification<CaseEntity>() {

			@Override
			public Predicate toPredicate(Root<CaseEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				
				Predicate result = null;
				
				if ( params.get("id") != null && !"".equals(params.get("id").toString())) {
					Path<String> id = root.get("id");   
					Predicate pid = cb.equal(id, params.get("id"));
					result = cb.and(pid);
					
				}
				
				if ( params.get("caseName") != null && !"".equals(params.get("caseName").toString())) {
					Path<String> name = root.get("caseName");   
					Predicate pname = cb.like(name, "%" + params.get("caseName") + "%");
					if ( result != null) {
						result.getExpressions().add(cb.and(pname));
					} else {
						result = cb.and(pname);
					}
				}
				
				return result;
			}
			
		};  
		
		return this.caseRepository.findAll(spec, request);
	}

	@Override
	public CaseInfoDTO findOne(String id) {
		CaseEntity caseEntity = this.caseRepository.findOne(id);
		CaseInfoDTO caseInfoDTO = new CaseInfoDTO();
		BeanUtils.copyProperties(caseEntity, caseInfoDTO);
		return caseInfoDTO;
	}
	
	/**
	 * 获取案件编号
	 * CF-机构代码-日期-0001
	 * @return
	 */
	private String getCaseNum() {
		
		SequenceEntity sequenceEntity = this.sequenceRepository.findOne(CASE_NUM_SEQ_NAME);
		if ( sequenceEntity == null || sequenceEntity.getSeqDate() == null) {
			throw new EnforceException(ResultCode.BUSI_ERROR.getCode(), CASE_NUM_SEQ_NAME + ", seq_name is not exits or seq_date is null.");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar now = Calendar.getInstance();
		
		StringBuilder builder = new StringBuilder();
		
		String deptCode = this.deptRepository.findOne(UserUtil.getCurrentUser().getDept_id()).getCode();
		
		builder.append(CASE_NUM_TITLE)
			.append(CASE_NUM_PREFIX)
			.append(deptCode)
			.append(CASE_NUM_PREFIX)
			.append(sdf.format(now.getTime()))
			.append(CASE_NUM_PREFIX);
		
		Calendar seqDate = Calendar.getInstance();
		
		seqDate.setTime(sequenceEntity.getSeqDate());
		
		if ( now.get(Calendar.YEAR) == seqDate.get(Calendar.YEAR) 
				&& now.get(Calendar.MONTH) == seqDate.get(Calendar.MONTH)
				&& now.get(Calendar.DAY_OF_MONTH) == seqDate.get(Calendar.DAY_OF_MONTH)) {
			
			Integer nextVal = Integer.valueOf(sequenceEntity.getSeqValue()) + 1;
			sequenceEntity.setSeqValue(String.valueOf(nextVal));
			builder.append(
					String.format("%" + CASE_NUM_PAD_NUM +"s", sequenceEntity.getSeqValue()).replace(' ', '0'));
			
		} else {
			builder.append(
					String.format("%" + CASE_NUM_PAD_NUM +"s", 1).replace(' ', '0'));
			// 更新序列
			sequenceEntity.setSeqValue("1");
			sequenceEntity.setSeqDate(now.getTime());
		}
		this.sequenceRepository.save(sequenceEntity);
		return builder.toString();
	}
	
	/**
	 * 获取当前登录人执法主体以及下属主体下的所有执法人员
	 * @return
	 */
	@Override
	public String getDeptUserByCurrentUser() {
		StringBuilder stringBuilder = new StringBuilder();
		List<DeptPersonDTO> deptPersonDTOs = this.caseDao.getDeptUserByCurrentUser(UserUtil.getCurrentUser().getDept_id(), CommonParameters.Role.LAW_ENFORCEMENT_OFFICIALS);
		if (deptPersonDTOs != null && deptPersonDTOs.size() > 0) {
			Map<String, List<DeptPersonDTO>> tmpMap = new HashMap<>();
			for ( DeptPersonDTO deptPersonDTO : deptPersonDTOs){
				if ( tmpMap.get(deptPersonDTO.getDeptId()) == null ) {
					List<DeptPersonDTO> list = new ArrayList<>();
					list.add(deptPersonDTO);
					tmpMap.put(deptPersonDTO.getDeptId(), list);
				} else {
					tmpMap.get(deptPersonDTO.getDeptId()).add(deptPersonDTO);
				}
			}
			
			Iterator<Entry<String, List<DeptPersonDTO>>> iter = tmpMap.entrySet().iterator();
			while ( iter.hasNext() ) {
				Entry<String, List<DeptPersonDTO>> entry = iter.next();
				List<DeptPersonDTO> tmpDTOList = entry.getValue();
				if ( tmpDTOList != null && tmpDTOList.size() > 0 ) {
					stringBuilder.append("<optgroup label=\"")
						.append(tmpDTOList.get(0).getDeptName())
						.append("\">");
					stringBuilder.append("<option value=\"\">请选择</option>");
					for ( DeptPersonDTO deptPersonDTO : tmpDTOList) {
						stringBuilder.append("<option value=\"")
							.append(deptPersonDTO.getPersonId())
							.append("\">")
							.append(deptPersonDTO.getPersonName())
							.append("</option>");
					}
					stringBuilder.append("</optgroup>");
				}
			}
		}
		return stringBuilder.toString();
	}

	@Override
	public Integer getCasecount(Map<String, Object> params) {
		return this.caseDao.count(params);
	}
	
	@Override
	public Integer findCasecount(Map<String, Object> params) {
		return this.caseDao.caseCount(params);
	}
	
	@Override
	public Integer needDealCount(String id) {
		return this.caseDao.needDealCount(id);
	}
	
	@Override
	public Integer caseCountByStatus(int status) {
		return this.caseDao.caseCountByStatus(status);
	}
	
	@Override
	public Integer getQueryCasecount(Map<String, Object> params) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.caseDao.caseQuerycount(params);
	}

	@Override
	public List<CaseListDTO> getCaseList(Map<String, Object> params, Integer start, Integer length) {
		return this.caseDao.caseList(params, start, length);
	}
	
	@Override
	public List<CaseListDTO> getQueryCaseList(Map<String, Object> params, Integer start, Integer length) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.caseDao.caseQuery(params, start, length);
	}

	@Override
	public CaseDetailDTO findCaseDetail(Map<String, Object> params) {
		return this.caseDao.findCaseDetail(params);
	}

	@Override
	public void caseClosed(FlowDealDTO flowDealDTO) {
		//处理流程
		User user = UserUtil.getCurrentUser();
		Map<String, Object> variables = new HashMap<>();
		String userName=user.getNickname();
		variables.put("createUser",userName);
		String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId("case", caseRepository.findOne(flowDealDTO.getCaseId()).getCaseNum());
		flowService.taskComplete(pid,userName, variables, flowDealDTO.getDealContent());
		//修改案件状态
		caseDao.updateCaseStatus(flowDealDTO.getCaseId(),CommonParameters.CaseStatus.ANGD);
	}

	@Override
	public void updateCaseStatus(String caseId, Integer caseStatus) {
		this.caseDao.updateCaseStatus(caseId, caseStatus);
		
	}

	@Override
	public CaseDetailInfoDTO queryCaseByCaseId(String caseId, Map<String, Object> params) {
		return this.caseDao.getCaseDetailInfo(caseId, params);
	}

	@Override
	public List<CaseDocDTO> queryDocContentByCaseId(String caseId) {
		return this.caseDao.findCaseDoc(caseId);
	}

	@Override
	public void updateCaseStatusByCaseNum(String caseNum, Integer caseStatus, Integer flowType) {
		if ( flowType == null ) {
			this.caseDao.updateCaseStatusByCaseNum(caseNum, caseStatus);
		} else {
			this.caseDao.updateCaseStatusAndFlowTypeByCaseNum(caseNum, caseStatus, flowType);
		}
	}
	
	@Override
	public PageList<PsCaseDTO> tjCaseList(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.getMessageNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.tjCaseList(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCaseDTO> selectGeneralCaseList(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.getGeneralNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.selectGeneralCaseList(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCaseDTO> selectGeneralSimple(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.selectGeneralSimpleNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.selectGeneralSimple(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCaseDTO> selectSeriousCaseList(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.getSeriousNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.selectSeriousCaseList(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCaseDTO> selectCasecaseList(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.getCasecaseNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.selectCasecaseList(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCaseDTO> selectSimpleCaseList(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.getSimpleNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.selectSimpleCaseList(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCaseDTO> caseAllList(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.caseAllListNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.caseAllList(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCaseDTO> tjPcCaseList(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.getPcMessageNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.tjPcCaseList(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	 private PageList<PsCaseDTO> initPage(PageList<PsCaseDTO> page, int pageCount,
			 PsCaseDTO caseDTO) {
	        page.setTotalRecord(pageCount);
	        page.setCurrentPage(caseDTO.getCurrentPage());
	        page.setPageSize(caseDTO.getPageSize());
	        caseDTO.setStartIndexEndIndex();
	        return page;    
	    } 
	 
	@Override
	public PageList<PsCaseDTO> selectCase(PsCaseDTO caseDTO) {
		PageList<PsCaseDTO> page = new PageList<PsCaseDTO>();
        int pageCount = caseDao.getSelectNum(caseDTO);//得到总条数
        page = initPage(page, pageCount,caseDTO);
        List<PsCaseDTO> message= caseDao.selectCase(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	
	@Override
	public PageList<PsCheckDTO> selectDailyBydeptId(PsCheckDTO caseDTO) {
		PageList<PsCheckDTO> page = new PageList<PsCheckDTO>();
        int pageCount = caseDao.getDailyNum(caseDTO);//得到总条数
        page = checkPage(page, pageCount,caseDTO);
        List<PsCheckDTO> message= caseDao.selectDailyBydeptId(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCheckDTO> ApiSelectDailyBydeptId(PsCheckDTO caseDTO) {
		PageList<PsCheckDTO> page = new PageList<PsCheckDTO>();
        int pageCount = caseDao.ApiDailyNum(caseDTO);//得到总条数
        page = checkPage(page, pageCount,caseDTO);
        List<PsCheckDTO> message= caseDao.ApiSelectDailyBydeptId(caseDTO);
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCheckDTO> selectSpecialBydeptId(PsCheckDTO caseDTO) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageList<PsCheckDTO> page = new PageList<PsCheckDTO>();
        int pageCount = caseDao.getSpecialNum(caseDTO);//得到总条数
        page = checkPage(page, pageCount,caseDTO);
        List<PsCheckDTO> message= caseDao.selectSpecialBydeptId(caseDTO);
        for(PsCheckDTO psCheckDTO: message){
        	try {
				Date sDate = fmt.parse(psCheckDTO.getStartDate());
				Date eDate = fmt.parse(psCheckDTO.getEndDate());
				psCheckDTO.setStartDate(fmt.format(sDate));
				psCheckDTO.setEndDate(fmt.format(eDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	
        }
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	@Override
	public PageList<PsCheckDTO> ApiSelectSpecialBydeptId(PsCheckDTO caseDTO) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageList<PsCheckDTO> page = new PageList<PsCheckDTO>();
        int pageCount = caseDao.ApiSpecialNum(caseDTO);//得到总条数
        page = checkPage(page, pageCount,caseDTO);
        List<PsCheckDTO> message= caseDao.ApiSelectSpecialBydeptId(caseDTO);
        for(PsCheckDTO psCheckDTO: message){
        	try {
				Date sDate = fmt.parse(psCheckDTO.getStartDate());
				Date eDate = fmt.parse(psCheckDTO.getEndDate());
				psCheckDTO.setStartDate(fmt.format(sDate));
				psCheckDTO.setEndDate(fmt.format(eDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	
        }
        if (!message.isEmpty()) {
            page.setDatas(message);
        }
        return page;
	}
	private PageList<PsCheckDTO> checkPage(PageList<PsCheckDTO> page, int pageCount,
			PsCheckDTO caseDTO) {
	        page.setTotalRecord(pageCount);
	        page.setCurrentPage(caseDTO.getCurrentPage());
	        page.setPageSize(caseDTO.getPageSize());
	        caseDTO.setStartIndexEndIndex();
	        return page;    
	    }  
	@Override
	public List<PsCaseDTO> selectCaseList(Map<String, Object> params) {
		return this.caseDao.selectCaseList(params);
	}
	@Override
	public List<PsCaseDTO> selectSpecialCaseList(Map<String, Object> params) {
		return this.caseDao.selectSpecialCaseList(params);
	}
	@Override
	public List<PsCaseDTO> LdDailyNum(Map<String, Object> params) {
		return this.caseDao.LdDailyNum(params);
	}
	@Override
	public List<PsCaseDTO> generalCaseNum(Map<String, Object> params) {
		return this.caseDao.generalCaseNum(params);
	}
	@Override
	public List<PsCaseDTO> simpleCaseNum(Map<String, Object> params) {
		return this.caseDao.simpleCaseNum(params);
	}
	@Override
	public List<PsCaseDTO> SeriousCaseNum(Map<String, Object> params) {
		return this.caseDao.SeriousCaseNum(params);
	}
	@Override
	public List<PsCaseDTO> LdDailyNumByDept(Map<String, Object> params) {
		return this.caseDao.LdDailyNumByDept(params);
	}
	@Override
	public List<PsCaseDTO> dailyByDeptAll(Map<String, Object> params) {
		return this.caseDao.dailyByDeptAll(params);
	}
	@Override
	public List<PsCaseDTO> LdSpecialNum(Map<String, Object> params) {
		return this.caseDao.LdSpecialNum(params);
	}
	@Override
	public List<PsCaseDTO> caseAll(Map<String, Object> params) {
		return this.caseDao.caseAll(params);
	}
	@Override
	public List<PsCaseDTO> LdSpecialNumByDept(Map<String, Object> params) {
		return this.caseDao.LdSpecialNumByDept(params);
	}
	@Override
	public List<PsCaseDTO> specialByDeptAll(Map<String, Object> params) {
		return this.caseDao.specialByDeptAll(params);
	}
	@Override
	public List<PsCaseDTO> caseAllByArea(Map<String, Object> params) {
		return this.caseDao.caseAllByArea(params);
	}
	@Override
	public List<PsCaseDTO> caseAllByAreaAll(Map<String, Object> params) {
		return this.caseDao.caseAllByAreaAll(params);
	}
	@Override
	public List<ApiYujDTO> yujCountByArea(Map<String, Object> params) {
		return this.caseDao.yujCountByArea(params);
	}
	@Override
	public List<ApiCheckTypeDTO> checkStatusByDeptId(Map<String, Object> params) {
		return this.caseDao.checkStatusByDeptId(params);
	}
	@Override
	public List<ApiYujDTO> yujCountByAreaAll(Map<String, Object> params) {
		return this.caseDao.yujCountByAreaAll(params);
	}
	@Override
	public List<PsCaseDTO> generalCaseNumByDept(Map<String, Object> params) {
		return this.caseDao.generalCaseNumByDept(params);
	}
	@Override
	public List<PsCaseDTO> simpleCaseNumByDept(Map<String, Object> params) {
		return this.caseDao.simpleCaseNumByDept(params);
	}
	@Override
	public List<PsCaseDTO> SeriousCaseByDept(Map<String, Object> params) {
		return this.caseDao.SeriousCaseByDept(params);
	}
	@Override
	public List<PsCaseDTO> selectDailyCaseList(Map<String, Object> params) {
		return this.caseDao.selectDailyCaseList(params);
	}
	@Override
	public List<PsCaseDTO> dailyList() {
		return this.caseDao.dailyList();
	}
	@Override
	public List<PsCaseDTO> specialList() {
		return this.caseDao.specialList();
	}
	@Override
	public List<PsCaseDTO> apiCaseByAreaId(Map<String, Object> params) {
		return this.caseDao.apiCaseByAreaId(params);
	}
	@Override
	public CaseEntity getCaseByCaseNum(String caseNum) {
		return this.caseRepository.findByCaseNum(caseNum);
	}

	@Override
	public List<ApiCaseListDTO> findCaseList(Map<String, Object> params) {	
		return this.caseDao.findCaseList(params);
	}

	@Override
	public ApiCaseInfoDTO findCaseInfo(Map<String, Object> params) {
		return this.caseDao.findCaseInfo(params);
	}

	
	@Override
	public List<PsCaseDTO> psCaseList() {
		return this.caseDao.psCaseList();
	}
	@Override
	public List<PsCaseDTO> caseCaseNumByArea(Map<String, Object> params) {
		return this.caseDao.caseCaseNumByArea(params);
	}
	@Override
	public List<PsCaseDTO> caseCaseNumByDept(Map<String, Object> params) {
		return this.caseDao.caseCaseNumByDept(params);
	}
	@Override
	public List<PsCaseDTO> psPcCaseList() {
		return this.caseDao.psPcCaseList();
	}
	/**
	 * ps
	 * 权责列表查询
	 */
	@Override
	public List<PotenceListDTO> selectPotenceList(Map<String, Object> plMap) {
		
		return this.FilingDao.selectPotenceList(plMap);
	}
	/**
	 * ps
	 * 权责列表查询
	 */
	@Override
	public List<PotenceListDTO> selectPotenceListBydeptId(Map<String, Object> plMap) {
		
		return this.FilingDao.selectPotenceListBydeptId(plMap);
	}
	/**
	 * ps
	 * 各个地区权责清单统计
	 * @return
	 */
	@Override
	public List<AreaAndPotDTO> AreaAndPotSelect() {
		
		return this.FilingDao.AreaAndPotSelect();
	}
	/**
	 * ps
	 * 一个地区下的一个部门或全部部门的权责分类统计
	 * @param areaId 区域ID
	 * @param deptId 部门ID
	 * @return
	 */
	@Override
	public List<AreaAndPotAndProPotDTO> getAreaAndPotAndProPot(Map<String, Object> areaAndPotAndProPotMap) {
		return this.FilingDao.getAreaAndPotAndProPot(areaAndPotAndProPotMap);
	}

	@Override
	public CaseEntity add(CaseEntity caseEntity) {
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO pDTO =this.personDao.findPersonInfo(personDTO.getId());
		caseEntity.setDeptId(pDTO.getDeptId());
		caseEntity.setCreateBy(pDTO.getCertNum());
		caseEntity.setCreateDate(new Date());
		caseEntity.setCreateName(pDTO.getName());
		if(caseEntity.getId()==null || caseEntity.getId().equals("")){
			caseEntity.setId(UUID.randomUUID().toString());
		}
		// 案件编号,生成规则(处罚-机构-日期-4位序列  )  e.g. CF-15010001003-20171130-0003
		caseEntity.setCaseNum(this.getCaseNumAPP());
		this.caseRepository.save(caseEntity);
		return caseEntity;
		 
	}


	@Override
	public CaseEntity updatePotence(CaseEntity caseEntity) {
		CaseEntity caseentity = this.caseRepository.findOne(caseEntity.getId());
		caseentity.setLimitType(caseEntity.getLimitType());
		caseentity.setLimitValue(caseEntity.getLimitValue());
		caseentity.setPotenceId(caseEntity.getPotenceId());
		caseentity.setPotenceLawMapId(caseEntity.getPotenceLawMapId());
		caseentity.setCaseReason(caseEntity.getCaseReason());
		return this.caseRepository.save(caseentity);
	}
	
	@Override
	public CaseEntity updatePotenceAndSource(CaseEntity caseEntity) {
		CaseEntity caseentity = this.caseService.getCaseByCaseNum(caseEntity.getCaseNum());
		
		if(StringUtils.isEmpty(caseEntity.getPotenceId())){
			caseentity.setPotenceId(caseEntity.getPotenceId());
		}
		if(StringUtils.isEmpty(caseEntity.getPotenceLawMapId())){
			caseentity.setPotenceLawMapId(caseEntity.getPotenceLawMapId());
		}	
		caseentity.setCaseReason(caseEntity.getCaseReason());
		return this.caseRepository.save(caseentity);
	}



	/**
	 * ps
	 * 权责清单（条形图）
	 * @return
	 */
	@Override
	public List<RigAndPotListDTO> RigAndPotList() {
		
		return this.FilingDao.RigAndPotList();
	}
	
	
	/**
	 * ps根据案件Id查询案件详情
	 * @return
	 * */
	@Override
	public PsCaseDTO psCase(Map<String, Object> map) {
		
		return this.caseDao.psCase(map);
	}
	
	/**
	 * 获取案件编号
	 * CF-机构代码-日期-0001
	 * @return
	 */
	private String getCaseNumAPP() {
		
		SequenceEntity sequenceEntity = this.sequenceRepository.findOne(CASE_NUM_SEQ_NAME);
		if ( sequenceEntity == null || sequenceEntity.getSeqDate() == null) {
			throw new EnforceException(ResultCode.BUSI_ERROR.getCode(), CASE_NUM_SEQ_NAME + ", seq_name is not exits or seq_date is null.");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar now = Calendar.getInstance();
		
		StringBuilder builder = new StringBuilder();
		
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		
		PersonDTO pDTO =this.personDao.findPersonInfo(personDTO.getId());

		String deptCode = this.deptRepository.findOne(pDTO.getDeptId()).getCode();
		
		builder.append(CASE_NUM_TITLE)
			.append(CASE_NUM_PREFIX)
			.append(deptCode)
			.append(CASE_NUM_PREFIX)
			.append(sdf.format(now.getTime()))
			.append(CASE_NUM_PREFIX);
		
		Calendar seqDate = Calendar.getInstance();
		
		seqDate.setTime(sequenceEntity.getSeqDate());
		
		if ( now.get(Calendar.YEAR) == seqDate.get(Calendar.YEAR) 
				&& now.get(Calendar.MONTH) == seqDate.get(Calendar.MONTH)
				&& now.get(Calendar.DAY_OF_MONTH) == seqDate.get(Calendar.DAY_OF_MONTH)) {
			
			Integer nextVal = Integer.valueOf(sequenceEntity.getSeqValue()) + 1;
			sequenceEntity.setSeqValue(String.valueOf(nextVal));
			builder.append(
					String.format("%" + CASE_NUM_PAD_NUM +"s", sequenceEntity.getSeqValue()).replace(' ', '0'));
			
		} else {
			builder.append(
					String.format("%" + CASE_NUM_PAD_NUM +"s", 1).replace(' ', '0'));
			// 更新序列
			sequenceEntity.setSeqValue("1");
			sequenceEntity.setSeqDate(now.getTime());
		}
		this.sequenceRepository.save(sequenceEntity);
		return builder.toString();
	}
	/**
	 * ps根据案件Id查询案件详情
	 * @return
	 * */
	@Override
	public AreaAndPotDTO potenceDetail(Map<String, Object> map) {
		
		return this.FilingDao.potenceDetail(map);
	}

	@Override
	public Map<String, Integer> getLeaderCaseCount(PersonAppDTO personAppDTO, List<Integer> caseStatusList) {
		return this.caseDao.getLeaderCaseCount(personAppDTO, caseStatusList);
	}

	@Override
	public List<CaseListDTO> getLeaderApprove(PersonAppDTO personAppDTO, List<Integer> caseStatusList, String caseName,String start, String length) {
		return this.caseDao.getLeaderApprove(personAppDTO, caseStatusList, caseName, Integer.valueOf(start),  Integer.valueOf(length));
	}

	@Override
	public LssuedDTO findCheckName(String caseSource, String checkId) {
		if(caseSource.equals("6")){
			return this.caseDao.findCheckDailyName(checkId);
		}else if(caseSource.equals("5")){
			return this.caseDao.findLssuedName(checkId);
		}
		return null;
	}

	@Override
	public DeptPersonDTO getByCaseId(String id) {
		return this.caseDao.getByCaseId(id);
	}
	
	@Override
	public List<NextOpinionDTO> getFyReviewVerificationByDeptId(String deptId) {
		return this.caseDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.FyOPINION);
	}
	@Override
	public List<NextOpinionDTO> getTzslReconsiderationByDeptId(String dept_id) {
		return this.caseDao.getHaveRoleOpinionByDeptId(dept_id, CommonParameters.Role.TZSL);
	}
	@Override
	public LoginUserDTO login(LoginUserDTO User) {
		return this.caseDao.login(User);
	}
	@Override
	public List<CaseListDTO> getListCssb(Map<String, Object> map) {
		return this.caseDao.getListCssb(map);
	}
	@Override
	public List<CaseListDTO> getListCssbGs(Map<String, Object> map) {
		return this.caseDao.getListCssbGs(map);
	}
	@Override
	public List<CaseListDTO> getListSqfy(Map<String, Object> map) {
		return this.caseDao.getListSqfy(map);
	}
	@Override
	public List<CaseListDTO> getListSqfyGs(Map<String, Object> map) {
		return this.caseDao.getListSqfyGs(map);
	}
	@Override
	public List<CaseListDTO> getListSqtz(Map<String, Object> map) {
		return this.caseDao.getListSqtz(map);
	}
	@Override
	public List<CaseListDTO> getListSqtzGs(Map<String, Object> map) {
		return this.caseDao.getListSqtzGs(map);
	}
	
	@Override
	public void createCaseCateLog(String caseId) {
		if ( StringUtils.isEmpty(caseId)) {
			throw new EnforceException(ResultCode.INTERNAL_SERVER_ERROR);
		}
		// 该案件所有的文书
		List<CaseDocDTO> caseDocDTOs = this.caseDao.findCaseDoc(caseId);
		
		for ( CaseDocDTO caseDocDTO : caseDocDTOs) {
			String caseCatalogPath,split;
			if(SysUtil.sysIsWin()){
				caseCatalogPath = uploadConfig.getWinCaseCatalogPath();
				split = "\\";
			}else{
				caseCatalogPath = uploadConfig.getOtherCaseCatalogPath();
				split = "/";
			}
			// 创建存储目录
			new File(caseCatalogPath + caseDocDTO.getCaseNum() + split + "文书" + split).mkdirs();
			// 获取模板内容
			DocTemplateEntity docTemplateEntity = docTemplateRepository.findOne(caseDocDTO.getTemplateId());
			
			// 模板内容转换为标准html代码
			Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
			
			// 文书上需要填写内容的模块放到上面
			Map<String, String> map = new HashMap<>();
			map.put("datetime", "");
			map.put("templateName", docTemplateEntity.getName());

			String htmlContent = this.docContentService.getHtmlContent(caseDocDTO.getTemplateId(), caseId, document);
			
			htmlContent = htmlContent.replaceAll("<br>", "");
			
			document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(htmlContent));
			
			document.head().html("<style>@page{margin:0;}table,table tr th, table tr td { border:1px solid #000000;font-family:Simsun; }"
					+ "table { width:  100%; text-align: center; border-collapse: collapse;}</style>");

			FileToPdf.htmlToDoc(document.toString(), caseCatalogPath + caseDocDTO.getCaseNum() + split + "文书" + split + docTemplateEntity.getName() + ".doc");
		}
		
	}

	@Override
	public List<AttachFileEntity> caseFileList(String caseId, String baseUrl) {
		if(caseId!=null && !"".equals(caseId)){
			String path;
			if(SysUtil.sysIsWin()){
				path = uploadConfig.getWinPath();
			}else{
				path = uploadConfig.getOtherPath();
			}
			Sort sort = new Sort(Sort.Direction.DESC,"createDate");
			List<AttachFileEntity> entitys = this.attachFileRepository.findAllByCaseId(caseId,sort);
			List<AttachFileEntity> fileList = new ArrayList<>();
			//List<SysDictEntity> dict = sysDictService.getDictByTypeValue("1500");
			AttachFileEntity tmpAttachFileEntity = null;
			
			//获取文件名，文件路径，文件类型，创建日期，上传人
			for(AttachFileEntity entity : entitys) {
				tmpAttachFileEntity = new AttachFileEntity();
				tmpAttachFileEntity.setId(entity.getId());
				tmpAttachFileEntity.setCaseStatus(entity.getCaseStatus());
				tmpAttachFileEntity.setFileName(entity.getFileName() + "." + entity.getFileType());
				tmpAttachFileEntity.setFileType(entity.getFileType());
				tmpAttachFileEntity.setCreateDate(entity.getCreateDate());
				tmpAttachFileEntity.setCreateName(entity.getCreateName());
				String filePathName = null;
				if (fileTypeMap.get("doc").contains(entity.getFileType().toLowerCase())) {
					filePathName = entity.getPdfUrl() + entity.getFileNewname() + ".pdf";
					filePathName = baseUrl+filePathName.substring(path.length()).replaceAll("\\\\+", "/");
					tmpAttachFileEntity.setPdfUrl(filePathName);
				}
				filePathName = entity.getFilePath() + entity.getFileNewname() + "." + entity.getFileType();
				filePathName = baseUrl+filePathName.substring(path.length()).replaceAll("\\\\+", "/");
				tmpAttachFileEntity.setFilePath(filePathName);
				
				fileList.add(tmpAttachFileEntity);
				
			}
			return fileList;
		}
		return null;
		
	}
	
	@Override
	public void saveTaskEntity(FlowDTO flowDTO, Boolean isStart) {
		CaseDealEntity casedeal = new CaseDealEntity();
		casedeal.setIsDeal(CommonParameters.Effect.NOT_EFFECT);
		if ( flowDTO.getFlowKey().equals(CommonParameters.FlowKey.CASE)) {
			CaseEntity caseEntity = this.caseRepository.findByCaseNum(flowDTO.getBusinessId());
			if ( isStart ) {
				casedeal.setCaseStatus(CommonParameters.CaseStatus.AJSL);
			} else {
				casedeal.setCaseStatus(caseEntity.getCaseStatus());
			}
			casedeal.setCaseId(caseEntity.getId());
			casedeal.setId(UUID.randomUUID().toString());
			casedeal.setCreateBy(flowDTO.getUserNum());
			casedeal.setMglCreateName(caseEntity.getMglCreateName());
			casedeal.setCreateDate(new Date());
			casedeal.setMglDealContent(caseEntity.getMglBriefCaseContent());
			casedeal.setCaseNum(flowDTO.getBusinessId());
			casedeal.setCreateName(flowDTO.getUserName());
			if(flowDTO.getHandleMode() != null && !"".equals(flowDTO.getHandleMode())){
				casedeal.setDealMode(flowDTO.getHandleMode());
			}else if(flowDTO.getSingleMode() != null && !"".equals(flowDTO.getSingleMode())){
				casedeal.setDealMode(flowDTO.getSingleMode());
			}
			casedeal.setDealContent(flowDTO.getComment());
			if(flowDTO.getDealType() != null){
				casedeal.setDealType(Integer.valueOf(flowDTO.getDealType()).intValue());
			}
			this.caseDealRepository.save(casedeal);
		} else if( flowDTO.getFlowKey().equals(CommonParameters.FlowKey.DAY_CHECK)){
			CheckDailyEntity checkDailyEntity = this.checkDailyRepository.findByCheckNum(flowDTO.getBusinessId());
			casedeal.setCaseStatus(Integer.valueOf(checkDailyEntity.getStatus()).intValue());
			casedeal.setCaseId(checkDailyEntity.getId());
			casedeal.setId(UUID.randomUUID().toString());
			casedeal.setCreateBy(flowDTO.getUserNum());
			casedeal.setMglCreateName(checkDailyEntity.getMglCreateName());
			casedeal.setCreateDate(new Date());
			casedeal.setCaseNum(flowDTO.getBusinessId());
			casedeal.setCreateName(flowDTO.getUserName());
			if(flowDTO.getHandleMode() != null && !"".equals(flowDTO.getHandleMode())){
				casedeal.setDealMode(flowDTO.getHandleMode());
			}else if(flowDTO.getSingleMode() != null && !"".equals(flowDTO.getSingleMode())){
				casedeal.setDealMode(flowDTO.getSingleMode());
			}
			if(flowDTO.getDealType() != null){
				casedeal.setDealType(Integer.valueOf(flowDTO.getDealType()).intValue());
			}
			casedeal.setDealContent(flowDTO.getComment());
			this.caseDealRepository.save(casedeal);
			
		}else{
			LssuedEntity lssuedEntity = this.lssuedRepository.findByCheckNum(flowDTO.getBusinessId());
			casedeal.setCaseStatus(Integer.valueOf(lssuedEntity.getStatus()).intValue());
			casedeal.setCaseId(lssuedEntity.getId());
			casedeal.setId(UUID.randomUUID().toString());
			casedeal.setCreateBy(flowDTO.getUserNum());
			casedeal.setMglCreateName(lssuedEntity.getMglCreateName());
			casedeal.setCreateDate(new Date());
			casedeal.setMglDealContent(lssuedEntity.getComment());
			casedeal.setCaseNum(flowDTO.getBusinessId());
			casedeal.setCreateName(flowDTO.getUserName());
			casedeal.setDealMode(flowDTO.getHandleMode());
			casedeal.setDealContent(flowDTO.getComment());
			if(flowDTO.getDealType() != null){
				casedeal.setDealType(Integer.valueOf(flowDTO.getDealType()).intValue());
			}
			this.caseDealRepository.save(casedeal);
		}
	}
	
	@Override
	public void taskComplete(FlowDTO flowDTO) {
		ThreadLocalVariables.setFlowDTO(flowDTO);
		String pid = caseService.getProcessInstanceIdByKeyAndBusinessId(flowDTO.getFlowKey(), flowDTO.getBusinessId());
		String taskId = taskService.createTaskQuery()
				.processInstanceId(pid)
				.taskAssignee(flowDTO.getAssignee())
				.singleResult()
				.getId();
		Authentication.setAuthenticatedUserId(flowDTO.getAssignee());
		taskService.addComment(taskId, pid, flowDTO.getComment());
		Map<String,Object> variables = new HashMap<String,Object>();
		if ( !StringUtils.isEmpty( flowDTO.getHandleMode())) {
			variables.put("handleMode", flowDTO.getHandleMode() );
		}
		taskService.complete(taskId, variables);
		ThreadLocalVariables.removeFlowDTO();
	}
	
	@Override
	public String getProcessInstanceIdByKeyAndBusinessId(String key, String businessId) {
		
		String pid = "";
		ProcessInstance processInstance =  runtimeService.createProcessInstanceQuery()
			.processInstanceBusinessKey(businessId, key)
			.singleResult();
		if (processInstance == null) {
			pid = historyService.createHistoricProcessInstanceQuery()
					.processInstanceBusinessKey(businessId)
					.processDefinitionKey(key)
					.singleResult()
					.getId();
		} else {
			pid = processInstance.getId();
		}
		
		return pid;
	}

	@Override
	public void updateIsPs(CaseEntity caseEntity) {
		String id = caseEntity.getId();
		String isps= caseEntity.getIsPs();
		this.caseDao.updateCaseIsps(id,isps);
	}

	@Override
	public void caseFileCopy(String caseId) {
		if(caseId!=null && !"".equals(caseId)){
			Sort sort = new Sort(Sort.Direction.DESC,"createDate");
			List<AttachFileEntity> entitys = this.attachFileRepository.findAllByCaseId(caseId,sort);
			//没有获取到附件列表则结束
			if(entitys.size()>0){
				String caseCatalogPath;
				if(SysUtil.sysIsWin()){
					caseCatalogPath = uploadConfig.getWinCaseCatalogPath();
				}else{
					caseCatalogPath = uploadConfig.getOtherCaseCatalogPath();
				}
				
				// 创建附件保存目录
				new File(caseCatalogPath + entitys.get(0).getCaseNum() + "\\附件\\").mkdirs();
				Map<String, String> dicts = sysDictService.getDictMapByTypeValue("1500");
				// 附件拷贝
				entitys.forEach(item ->{
					// 源文件路径
					String sourceFilePath = (item.getFilePath()+item.getFileNewname()+"."+item.getFileType());
					// 目标文件路径
					String destFilePath = (caseCatalogPath + entitys.get(0).getCaseNum() + "\\附件\\" + dicts.get(item.getCaseStatus()) + "-" + item.getFileName() + "-" + item.getCreateName() + "." + item.getFileType());
					FileUtil.copyFile(new File(sourceFilePath), new File(destFilePath));
				});
			}
		}
	}

	@Override
	public CaseEntity updataCaseInfo(CaseEntity caseEntity) {
		CaseEntity caseentity = this.caseService.getCaseByCaseNum(caseEntity.getCaseNum());	
		caseentity.setLimitType(caseEntity.getLimitType());
		caseentity.setLimitValue(caseEntity.getLimitValue());
		caseentity.setPotenceId(caseEntity.getPotenceId());
		caseentity.setPotenceLawMapId(caseEntity.getPotenceLawMapId());
		caseentity.setCaseReason(caseEntity.getCaseReason());
		return this.caseRepository.save(caseentity);
	}
	@Override
	public PsCaseDTO getCaseByCaseId(Map<String, Object> caseIdMp) {
		return this.caseDao.getCaseByCaseId(caseIdMp);
	}
	@Override
	public PsCheckDTO getDailyByCaseId(Map<String, Object> caseIdMp) {
		return this.caseDao.getDailyByCaseId(caseIdMp);
	}
	@Override
	public PsCheckDTO getSpecialByCaseId(Map<String, Object> caseIdMp) {
		return this.caseDao.getSpecialByCaseId(caseIdMp);
	}

	@Override
	public String execFunction(String deptId) {
		return this.caseDao.execFunction("superviseGetBaseDeptByParentId", deptId);
	}

	@Override
	public List<Map<String, Object>> getCaseCountAreaByStatus(Map<String,Object> params) {
		return this.caseDao.getCaseCountAreaByStatus(params);
	}

	@Override
	public List<Map<String, Object>> getWarnCountForArea(Map<String, Object> params) {
		return this.caseDao.getWarnCountForArea(params);
	}

	@Override
	public List<Map<String,Object>> getCheckDailyCountForArea(Map<String, Object> params) {
		return this.caseDao.getCheckDailyCountForArea(params);
	}

	@Override
	public List<Map<String,Object>> getCheckCountForArea(Map<String, Object> params) {
		return this.caseDao.getCheckCountForArea(params);
	}

	@Override
	public String getCaseCountByStatus(Map<String, Object> params) {
		return this.caseDao.getCaseCountByStatus(params);
	}
	
	@Override
	public String getCaseCountByDeal(Map<String, Object> params) {
		return this.caseDao.getCaseCountByDeal(params);
	}
	
	@Override
	public String getCheckCountByDeptId(Map<String, Object> params) {
		return this.caseDao.getCheckCountByDeptId(params);
	}
	
	@Override
	public String getCheckDailyCountByDeptId(Map<String, Object> params) {
		return this.caseDao.getCheckDailyCountByDeptId(params);
	}
	@Override
	public List<ApiYujDTO> yujCount(Map<String, Object> params) {
		return this.caseDao.yujCount(params);
	}

	@Override
	public List<CaseInfoDTO> getCasePositionList(Map<String, Object> params) {
		return this.caseDao.getCasePositionList(params);
	}
}
