package com.orhonit.ole.enforce.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.DeptDTO;
import com.orhonit.ole.enforce.dto.LssuedDetailInfoDTO;
import com.orhonit.ole.enforce.dto.YujDTO;
import com.orhonit.ole.enforce.dto.api.ApiCheckTypeDTO;
import com.orhonit.ole.enforce.dto.api.ApiDailyCheckDTO;
import com.orhonit.ole.enforce.dto.api.ApiYujDTO;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.dto.ps.PsCheckDTO;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.dept.DeptService;
import com.orhonit.ole.enforce.service.yuj.YujService;
import com.orhonit.ole.enforce.utils.PageList;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;

/**
 * 手机监督平台
 *
 */
@RestController
@RequestMapping("/api/zxzf")
public class ApiDealControll {

	@Autowired
	CaseService caseService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private UserService userService;
	@Autowired
	private YujService yujService;
	
	/**
	 * 查询日常检查统计
	 * @return
	 */
	@GetMapping("/daily")
	public Result<List<PsCaseDTO>> daily(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		Map<String, Object> params = new HashMap<>();
		if("2".equals(dept.getLaw_type())){
			params.put("areaId", dept.getArea_id());
		} else {
			params.put("deptId", deptId);// 后加的
		}
		
		params.put("checkObjectType", CommonParameters.DictType.PARTY_TYPE.toString());
		params.put("checkStutas", CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
		psCaseDTO = this.caseService.LdDailyNum(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询日常检查统计分区域
	 * @return
	 */
	@GetMapping("/dailyByDept")
	public Result<List<PsCaseDTO>> dailyByDept(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.LdDailyNumByDept(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.LdDailyNum(params);
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询日常检查统计分部门
	 * @return
	 */
	@GetMapping("/dailyByDeptAll")
	public Result<List<PsCaseDTO>> dailyByDeptAll(@RequestParam(value="areaId",required = false) String areaId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		psCaseDTO = this.caseService.dailyByDeptAll(params);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 查询专项检查统计
	 * @return
	 */
	@GetMapping("/special")
	public Result<List<PsCaseDTO>> special(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		Map<String, Object> params = new HashMap<>();
		if("2".equals(dept.getLaw_type())){
			params.put("areaId", dept.getArea_id());
		} else {
			params.put("deptId", deptId);
		}
		params.put("checkMode", CommonParameters.DictType.CHECK_STATUS.toString());
		psCaseDTO = this.caseService.LdSpecialNum(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	public Result<List<PsCaseDTO>> specialsss(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		Map<String, Object> params = new HashMap<>();
//		if(!"2".equals(dept.getLaw_type())){
			params.put("deptId", deptId);
//		}
		params.put("checkMode", CommonParameters.DictType.CHECK_STATUS.toString());
		psCaseDTO = this.caseService.LdSpecialNum(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	
	
	/**
	 * 查询本年所有案件统计
	 * @return
	 */
	@GetMapping("/caseAll")
	public Result<List<PsCaseDTO>> caseAll(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		Map<String, Object> params = new HashMap<>();
		if("2".equals(dept.getLaw_type())){
			params.put("areaId", dept.getArea_id());
		} else {
			params.put("deptId", deptId);// 后加的
		}
		params.put("checkMode", CommonParameters.DictType.CASE_STATUS.toString());
		params.put("checkObjectType", CommonParameters.DictType.CASE_RES.toString());
		psCaseDTO = this.caseService.caseAll(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询本年案件统计分区域
	 * @return
	 */
	@GetMapping("/caseAllByArea")
	public Result<List<PsCaseDTO>> caseAllByArea(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.caseAllByArea(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.caseAll(params);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询本年案件统计分部门
	 * @return
	 */
	@GetMapping("/caseAllByAreaAll")
	public Result<List<PsCaseDTO>> caseAllByAreaAll(@RequestParam(value="areaId",required = false) String areaId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		psCaseDTO = this.caseService.caseAllByAreaAll(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	@GetMapping("/caseAllList")
	public Result<PageList<PsCaseDTO>> caseAllList(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="1",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName,
			@RequestParam(value="queryDate", required = false) String queryDate,
			@RequestParam(value="caseStatus", required = false) String caseStatus) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String caseNum="";
		caseDTO.setPageSize(20);
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setCaseName(caseName);
		caseDTO.setQueryDate(queryDate);
		caseDTO.setCaseStatus(caseStatus);
		caseDTO.setCheckMode(CommonParameters.DictType.CASE_STATUS.toString());
		caseDTO.setCheckObjectType(CommonParameters.DictType.CASE_RES.toString());
		if("2".equals(dept.getLaw_type())){
			caseDTO.setAreaId(dept.getArea_id());
		}else{
			caseDTO.setDeptId(deptId);
		}
		psCaseDTO = this.caseService.caseAllListMap(caseDTO);
		if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0) {
			for(int i = 0;i<psCaseDTO.getDatas().size();i++){
				caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
				yuj = this.yujService.getWarnListByCaseNum(caseNum);
				if(yuj.size()>0){
					psCaseDTO.getDatas().get(i).setIsYuj("1");
				}else{
					psCaseDTO.getDatas().get(i).setIsYuj("2");
				}
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	@GetMapping("/caseAllLists")
	public Result<PageList<CaseListDTO>> caseAllLists(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="1",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName,
			@RequestParam(value="queryDate", required = false) String queryDate,
			@RequestParam(value="caseStatus", required = false) String caseStatus) {
		PsCaseDTO caseDTO = new PsCaseDTO();
		Map<String, Object> params = new HashMap<String, Object>();
		currentPage = currentPage < 1 ? 1 : currentPage;
		Integer pageSize = 20;
		
		params.put("deptId", deptId);
		params.put("caseName", caseName);
		params.put("queryDate", queryDate);
		params.put("caseStatus", caseStatus);
		params.put("typeValue", "1002");
		params.put("currentPage", currentPage);
		params.put("pageSize", pageSize);
		params.put("checkMode", CommonParameters.DictType.CASE_STATUS.toString());
		params.put("checkObjectType", CommonParameters.DictType.CASE_RES.toString());
//		Integer pageCount = caseService.getQueryCasecount(params);
//		PageList<PsCaseDTO> pageList = caseService.getQueryCaseListApp(params, currentPage, pageSize);
		PageList<CaseListDTO> pageList = caseService.getQueryCaseListApp(params);
		
//		PageList<PsCaseDTO> pageList = new PageList<PsCaseDTO>();
		
//		Integer pageCount = caseDao.caseAllListNumMap(paramMap);//得到总条数
//        List<CaseListDTO> list = caseDao.caseAllListMap(paramMap);
        
        
//        list.forEach((item) -> BeanUtils.copyProperties(item, message.add(psCaseDTO)))
        
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, pageList);
		
	}
	
	/**
	 * Api根据登录人的部门查询本年案件列表
	 * @return
	 */
	@GetMapping("/caseAllListss")
	public Result<PageList<PsCaseDTO>> caseAllListss(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="1",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName,
			@RequestParam(value="queryDate", required = false) String queryDate,
			@RequestParam(value="caseStatus", required = false) String caseStatus) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String caseNum="";
		
		if(!StringUtils.isEmpty(queryDate)&&!queryDate.equals("null")){
			caseDTO.setQueryDate(queryDate);
        }
		if(!StringUtils.isEmpty(caseStatus)&&!caseStatus.equals("null")){
			caseDTO.setCaseStatus(caseStatus);
        }
		caseDTO.setPageSize(20);
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setCaseName(caseName);
		caseDTO.setCheckMode(CommonParameters.DictType.CASE_STATUS.toString());
		caseDTO.setCheckObjectType(CommonParameters.DictType.CASE_RES.toString());
//		if(!"2".equals(dept.getLaw_type())){
			if(!StringUtils.isEmpty(deptId)&&!deptId.equals("null")){
				caseDTO.setDeptId(deptId);
	        }
//		}
		psCaseDTO = this.caseService.caseAllListMap(caseDTO);
		if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0){
			for(int i = 0;i<psCaseDTO.getDatas().size();i++){
				caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
				yuj = this.yujService.getWarnListByCaseNum(caseNum);
				if(yuj.size()>0){
					psCaseDTO.getDatas().get(i).setIsYuj("1");
				}else{
					psCaseDTO.getDatas().get(i).setIsYuj("2");
				}
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询专项检查统计分区域
	 * @return
	 */
	@GetMapping("/specialByDept")
	public Result<List<PsCaseDTO>> specialByDept(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.LdSpecialNumByDept(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.LdSpecialNum(params);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	
	/**
	 * 查询专项检查统计分部门
	 * @return
	 */
	@GetMapping("/specialByDeptAll")
	public Result<List<PsCaseDTO>> specialByDeptAll(@RequestParam(value="areaId",required = false) String areaId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		psCaseDTO = this.caseService.specialByDeptAll(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * Api根据登录人的部门查询日常检查列表
	 * @return
	 */
	@GetMapping("/ApiSelectDailyBydeptId")
	public Result<PageList<PsCheckDTO>> ApiSelectDailyBydeptId(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="checkTitle",defaultValue="",required=false) String checkTitle,
			//@RequestParam(value="queryDate", required = false) String queryDate,
			@RequestParam(value="checkMode", required = false) String checkMode) {
		PsCheckDTO caseDTO =new PsCheckDTO();
		PageList<PsCheckDTO> psCaseDTO = new PageList<PsCheckDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		
		if(!StringUtils.isEmpty(checkMode)&&!checkMode.equals("null")){
			caseDTO.setCheckMode(checkMode);
        }
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setCheckTitle(checkTitle);
		caseDTO.setPageSize(20);
		if("2".equals(dept.getLaw_type())){
			caseDTO.setAreaId(dept.getArea_id());
		} else {
			caseDTO.setDeptId(deptId);
		}
		caseDTO.setCheckStutas(CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
//		caseDTO.setCheckMode(CommonParameters.DictType.CHECK_MODE.toString());
		caseDTO.setCheckObjectType(CommonParameters.DictType.PARTY_TYPE.toString());
		psCaseDTO = this.caseService.ApiSelectDailyBydeptId(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	public Result<PageList<PsCheckDTO>> ApiSelectDailyBydeptIdsss(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="checkTitle",defaultValue="",required=false) String checkTitle,
			@RequestParam(value="queryDate", required = false) String queryDate,
			@RequestParam(value="checkMode1", required = false) String checkMode1) {
		PsCheckDTO caseDTO =new PsCheckDTO();
		PageList<PsCheckDTO> psCaseDTO = new PageList<PsCheckDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		
		if(!StringUtils.isEmpty(queryDate)&&!queryDate.equals("null")){
			caseDTO.setQueryDate(queryDate);
        }
		if(!StringUtils.isEmpty(checkMode1)&&!checkMode1.equals("null")){
			caseDTO.setCheckMode1(checkMode1);
        }
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setCheckTitle(checkTitle);
		caseDTO.setPageSize(20);
		caseDTO.setCheckStutas(CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
		caseDTO.setCheckMode(CommonParameters.DictType.CHECK_MODE.toString());
		caseDTO.setCheckObjectType(CommonParameters.DictType.PARTY_TYPE.toString());
//		if(!"2".equals(dept.getLaw_type())){
			caseDTO.setDeptId(deptId);
//		}
		psCaseDTO = this.caseService.ApiSelectDailyBydeptId(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * Api根据登录人的部门查询专项检查列表
	 * @return
	 */
	@GetMapping("/selectSpecialBydeptId")
	public Result<PageList<PsCheckDTO>> selectSpecialBydeptId(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="checkTitle",required = false) String checkTitle,
			@RequestParam(value="checkMode", required = false) String checkMode) {
		PsCheckDTO caseDTO =new PsCheckDTO();
		PageList<PsCheckDTO> psCaseDTO = new PageList<PsCheckDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		
		if(!StringUtils.isEmpty(checkMode)&&!checkMode.equals("null")){
			caseDTO.setCheckMode(checkMode);
        }
		caseDTO.setPageSize(20);
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setCheckTitle(checkTitle);
		caseDTO.setCheckMode(checkMode);
		if("2".equals(dept.getLaw_type())){
			caseDTO.setAreaId(dept.getArea_id());
		} else {
			caseDTO.setDeptId(deptId);
		}
		psCaseDTO = this.caseService.ApiSelectSpecialBydeptId(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	public Result<PageList<PsCheckDTO>> selectSpecialBydeptIdsss(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="checkTitle",required = false) String checkTitle,
			@RequestParam(value="queryDate", required = false) String queryDate,
			@RequestParam(value="checkMode", required = false) String checkMode) {
		PsCheckDTO caseDTO =new PsCheckDTO();
		PageList<PsCheckDTO> psCaseDTO = new PageList<PsCheckDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		
		if(!StringUtils.isEmpty(queryDate)&&!queryDate.equals("null")){
			caseDTO.setQueryDate(queryDate);
        }
		if(!StringUtils.isEmpty(checkMode)&&!checkMode.equals("null")){
			caseDTO.setCheckMode(checkMode);
        }
		caseDTO.setPageSize(20);
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setCheckTitle(checkTitle);
		caseDTO.setCheckStutas(CommonParameters.DictType.CHECK_STATUS.toString());
//		if(!"2".equals(dept.getLaw_type())){
			caseDTO.setDeptId(deptId);
//		}
		psCaseDTO = this.caseService.ApiSelectSpecialBydeptIdMap(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询一般流程案件统计
	 * @return
	 */
	@GetMapping("/generalCase")
	public Result<List<PsCaseDTO>> generalCase(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.generalCaseNum(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.generalCaseNum(params);
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询简易流程案件统计
	 * @return
	 */
	@GetMapping("/simpleCase")
	public Result<List<PsCaseDTO>> simpleCase(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.simpleCaseNum(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.simpleCaseNum(params);
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	
	/**
	 * 查询一般案件统计分区域
	 * @return
	 */
	@GetMapping("/generalCaseByDept")
	public Result<List<PsCaseDTO>> generalCaseByDept(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.generalCaseNumByDept(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.generalCaseNum(params);
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询一般案件统计分部门
	 * @return
	 */
	@GetMapping("/generalCaseDept")
	public Result<List<PsCaseDTO>> generalCaseDept(@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="type",required = false) String type) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			params.put("areaId", areaId);
			params.put("type", type);
			psCaseDTO = this.caseService.generalCaseDept(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("areaId", areaId);
			params.put("type", type);
			params.put("deptId", dept.getId());
			psCaseDTO = this.caseService.generalCaseDept(params);
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询简易案件统计分区域
	 * @return
	 */
	@GetMapping("/simpleCaseByDept")
	public Result<List<PsCaseDTO>> simpleCaseByDept(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.simpleCaseNumByDept(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.simpleCaseNum(params);
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * Api根据登录人的部门查询一般简易案件最新列表
	 * @return
	 */
	@GetMapping("/selectGeneralSimple")
	public Result<PageList<PsCaseDTO>> selectGeneralSimple(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
		if("2".equals(dept.getLaw_type())){
			caseDTO.setPageSize(20);
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setCaseName(caseName);
			psCaseDTO = this.caseService.selectGeneralSimple(caseDTO);
		}else{
			caseDTO.setDeptId(deptId);
			caseDTO.setPageSize(20);
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setCaseName(caseName);
			psCaseDTO = this.caseService.selectGeneralSimple(caseDTO);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * Api根据登录人的部门查询简易案件列表
	 * @return
	 */
	@GetMapping("/selectSimpleCaseList")
	public Result<PageList<PsCaseDTO>> selectSimpleCaseList(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String caseNum="";
		if("2".equals(dept.getLaw_type())){
			caseDTO.setAreaId(areaId);
			caseDTO.setPageSize(20);
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setCaseName(caseName);
			psCaseDTO = this.caseService.selectSimpleCaseList(caseDTO);
			if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0) {
				for(int i = 0;i<psCaseDTO.getDatas().size();i++){
					caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
					yuj = this.yujService.getWarnListByCaseNum(caseNum);
					if(yuj.size()>0){
						psCaseDTO.getDatas().get(i).setIsYuj("1");
					}else{
						psCaseDTO.getDatas().get(i).setIsYuj("2");
					}
				}
			}
		}else{
			caseDTO.setDeptId(deptId);
			caseDTO.setPageSize(20);
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setCaseName(caseName);
			psCaseDTO = this.caseService.selectSimpleCaseList(caseDTO);
			if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0) {
				for(int i = 0;i<psCaseDTO.getDatas().size();i++){
					caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
					yuj = this.yujService.getWarnListByCaseNum(caseNum);
					if(yuj.size()>0){
						psCaseDTO.getDatas().get(i).setIsYuj("1");
					}else{
						psCaseDTO.getDatas().get(i).setIsYuj("2");
					}
				}
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * Api根据登录人的部门查询一般案件列表
	 * @return
	 */
	@GetMapping("/selectGeneralCaseList")
	public Result<PageList<PsCaseDTO>> selectGeneralCaseList(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String caseNum="";
		if("2".equals(dept.getLaw_type())){
			caseDTO.setAreaId(areaId);
			caseDTO.setPageSize(20);
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setCaseName(caseName);
			psCaseDTO = this.caseService.selectGeneralCaseList(caseDTO);
			if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0) {
				for(int i = 0;i<psCaseDTO.getDatas().size();i++){
					caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
					yuj = this.yujService.getWarnListByCaseNum(caseNum);
					if(yuj.size()>0){
						psCaseDTO.getDatas().get(i).setIsYuj("1");
					}else{
						psCaseDTO.getDatas().get(i).setIsYuj("2");
					}
				}
			}
		}else{
			caseDTO.setDeptId(deptId);
			caseDTO.setPageSize(20);
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setCaseName(caseName);
			psCaseDTO = this.caseService.selectGeneralCaseList(caseDTO);
			if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0) {
				for(int i = 0;i<psCaseDTO.getDatas().size();i++){
					caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
					yuj = this.yujService.getWarnListByCaseNum(caseNum);
					if(yuj.size()>0){
						psCaseDTO.getDatas().get(i).setIsYuj("1");
					}else{
						psCaseDTO.getDatas().get(i).setIsYuj("2");
					}
				}
			}
			
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询重大案件统计分区域
	 * @return
	 */
	@GetMapping("/SeriousCaseByDept")
	public Result<List<PsCaseDTO>> SeriousCaseByDept(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.SeriousCaseByDept(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.SeriousCaseNum(params);
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * Api根据登录人的部门查询重大案件列表
	 * @return
	 */
	@GetMapping("/selectSeriousCaseList")
	public Result<PageList<PsCaseDTO>> selectSeriousCaseList(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String caseNum="";
		caseDTO.setPageSize(20);
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setCaseName(caseName);
		caseDTO.setCheckMode(CommonParameters.DictType.CASE_STATUS.toString());
		caseDTO.setCheckObjectType(CommonParameters.DictType.CASE_RES.toString());
		if("2".equals(dept.getLaw_type())){
			caseDTO.setAreaId(dept.getArea_id());
		} else {
			caseDTO.setDeptId(deptId);
		}
			
		psCaseDTO = this.caseService.selectSeriousCaseList(caseDTO);
		if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0) {
			for(int i = 0;i<psCaseDTO.getDatas().size();i++){
				caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
				yuj = this.yujService.getWarnListByCaseNum(caseNum);
				if(yuj.size()>0){
					psCaseDTO.getDatas().get(i).setIsYuj("1");
				}else{
					psCaseDTO.getDatas().get(i).setIsYuj("2");
				}
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	public Result<PageList<PsCaseDTO>> selectSeriousCaseListsss(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
		List<YujDTO> yuj = new ArrayList<YujDTO>();
		String caseNum="";
		
		caseDTO.setCurrentPage(currentPage < 1 ? 1 : currentPage);
		caseDTO.setPageSize(20);
		caseDTO.setCaseName(caseName);
		caseDTO.setCheckMode(CommonParameters.DictType.CASE_STATUS.toString());
		caseDTO.setCheckObjectType(CommonParameters.DictType.CASE_RES.toString());
		if("2".equals(dept.getLaw_type())){
			caseDTO.setAreaId(dept.getArea_id());
		}else{
			caseDTO.setDeptId(deptId);
		}
		psCaseDTO = this.caseService.selectSeriousCaseList(caseDTO);
		if(null != psCaseDTO && null != psCaseDTO.getDatas() && psCaseDTO.getDatas().size() > 0) {
			for(int i = 0;i<psCaseDTO.getDatas().size();i++){
				caseNum = psCaseDTO.getDatas().get(i).getCaseNum();
				yuj = this.yujService.getWarnListByCaseNum(caseNum);
				if(yuj.size()>0){
					psCaseDTO.getDatas().get(i).setIsYuj("1");
				}else{
					psCaseDTO.getDatas().get(i).setIsYuj("2");
				}
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	
	/**
	 * Api 
	 * 案件详情
	 * @param caseId 案件ID
	 * @return
	 */
	@GetMapping("/getCaseByCaseId")
	public Result<PsCaseDTO> getCaseByCaseId(
			@RequestParam(value="caseId",defaultValue="",required=false) String caseId
			){
		
		Map<String, Object> caseIdMp = new HashMap<>();
		caseIdMp.put("caseId", caseId);
		
		PsCaseDTO caseList = this.caseService.getCaseByCaseId(caseIdMp);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseList);
		
	}
	/**
	 * Api 
	 * 日常流程详情
	 * @param checkId 案件ID
	 * @return
	 */
	@GetMapping("/getDailyByCaseId")
	public Result<ApiDailyCheckDTO> getDailyByCaseId(
			@RequestParam(value="checkId",defaultValue="",required=false) String checkId,
			@RequestParam(value="checkNum",defaultValue="",required=false) String checkNum){
		
		Map<String, Object> caseIdMp = new HashMap<>();
		caseIdMp.put("checkId", checkId);
		caseIdMp.put("checkNum", checkNum);
		ApiDailyCheckDTO caseList = this.caseService.getDailyByCaseId(caseIdMp);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseList);
		
	}
	/**
	 * Api 
	 * 专项流程详情
	 * @param checkId 案件ID
	 * @return
	 */
	@GetMapping("/getSpecialByCaseId")
	public Result<LssuedDetailInfoDTO> getSpecialByCaseId(
			@RequestParam(value="checkId",defaultValue="",required=false) String checkId,
			@RequestParam(value="checkNum",defaultValue="",required=false) String checkNum){
		
		Map<String, Object> caseIdMp = new HashMap<>();
		caseIdMp.put("checkId", checkId);
		caseIdMp.put("checkNum", checkNum);
		LssuedDetailInfoDTO caseList = this.caseService.getSpecialByCaseId(caseIdMp);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseList);
		
	}
	/**
	 * 案卷评查各区域统计已结案案件
	 * @return
	 */
	@GetMapping("/caseCaseByArea")
	public Result<List<PsCaseDTO>> caseCaseByArea(@RequestParam(value="deptId",required = false) String deptId) {
		List<PsCaseDTO> psCaseDTO = new ArrayList<PsCaseDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.caseCaseNumByArea(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.caseCaseNumByDept(params);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * Api案卷评查根据登录人的部门查询已结案案件列表
	 * deptId:部门编号
	 * currentPage：当前页
	 * caseName：案件名称
	 * isReview：是否评查过 0：未评查；1：已评查；
	 * @return
	 */
	@GetMapping("/selectCasecaseList")
	public Result<PageList<PsCaseDTO>> selectCasecaseList(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="caseName",required = false) String caseName,
			@RequestParam(value="isReview",required = false) String isReview) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		PageList<PsCaseDTO> psCaseDTO = new PageList<PsCaseDTO>();
			caseDTO.setDeptId(deptId);
			caseDTO.setPageSize(20);
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setCaseName(caseName);
			caseDTO.setIsReview(isReview);
			psCaseDTO = this.caseService.selectCasecaseList(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 获取案件受理数量
	 * @return
	 */
	@GetMapping("/getCaseSLCount")
	public Result<Object> getCaseSLCount(){
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		Map<String,Object> params = new HashMap<>();
		if(dept != null && dept.getDept_property() == 3 && dept.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", caseService.execFunction(user.getDept_id()));
		}
		params.put("status", "12");
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseService.getCaseCountAreaByStatus(params));
	}
	
	/**
	 * 获取案件结案数量
	 * @return
	 */
	@GetMapping("/getCaseJACount")
	public Result<Object> getCaseJACount(){
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		Map<String,Object> params = new HashMap<>();
		if(dept != null && dept.getDept_property() == 3 && dept.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", caseService.execFunction(user.getDept_id()));
		}
		params.put("status", "90");
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseService.getCaseCountAreaByStatus(params));
	}
	
	/**
	 * 获取案件立案数量
	 * @return
	 */
	@GetMapping("/getCaseLACount")
	public Result<Object> getCaseLACount(){
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		Map<String,Object> params = new HashMap<>();
		if(dept != null && dept.getDept_property() == 3 && dept.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", caseService.execFunction(user.getDept_id()));
		}
		params.put("status", "21");
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseService.getCaseCountAreaByStatus(params));
	}
	
	/**
	 * 按区划统计预警数量
	 * @return
	 */
	@GetMapping("/getWarnCountForArea")
	public Result<Object> getWarnCountForArea(){
		Map<String,Object> params = new HashMap<>();
		params.put("", "");
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseService.getWarnCountForArea(params));
	}
	
	/**
	 * 按区划统计专项检查数量
	 * @return
	 */
	@GetMapping("/getCheckCountForArea")
	public Result<Object> getCheckCountForArea(){
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		Map<String,Object> params = new HashMap<>();
		if(dept != null && dept.getDept_property() == 3 && dept.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", caseService.execFunction(user.getDept_id()));
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseService.getCheckCountForArea(params));
	}
	
	/**
	 * 按区划统计日常检查数量
	 * @return
	 */
	@GetMapping("/getCheckDailyCountForArea")
	public Result<Object> getCheckDailyCountForArea(){
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		Map<String,Object> params = new HashMap<>();
		if(dept != null && dept.getDept_property() == 3 && dept.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", caseService.execFunction(user.getDept_id()));
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseService.getCheckDailyCountForArea(params));
	}
	
	/**
	 * 按部门统计案件处理情况
	 * @return
	 */
	@GetMapping("/getCaseDealCount")
	public Result<Object> getCaseDealCount(){
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		List<Map<String,Object>> res = new ArrayList<>();
		Map<String,Object> params = new HashMap<>();
		Map<String,Object> temp = new HashMap<>();
		
		if(dept != null && dept.getDept_property() == 3 && dept.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
				params.put("areaId", user.getArea_id());
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", caseService.execFunction(user.getDept_id()));
		}
		
		
		
		params.put("caseStatus", "90");
		params.put("deptId", user.getDept_id());
		res.add(new HashMap<String,Object>(){{
			put("name", "结案");
			put("value",caseService.getCaseCountByStatus(params));
		}});
		params.put("dealMode",7);
		res.add(new HashMap<String,Object>(){{
			put("name", "立案");
			put("value",caseService.getCaseCountByDeal(params));
		}});
		params.put("dealMode",2);
		res.add(new HashMap<String,Object>(){{
			put("name", "案件受理");
			put("value",caseService.getCaseCountByDeal(params));
		}});
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, res);
	}
	
	/**
	 * 按部门统计检查类型
	 * @return
	 */
	@GetMapping("/getCheckTypeCount")
	public Result<Object> getCheckTypeCount(){
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		List<Map<String,Object>> res = new ArrayList<>();
		Map<String,Object> params = new HashMap<>();
		Map<String,Object> temp = new HashMap<>();
		if(dept != null && dept.getDept_property() == 3 && dept.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
				params.put("areaId", user.getArea_id());
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", caseService.execFunction(user.getDept_id()));
		}
		
		params.put("deptId", user.getDept_id());
		res.add(new HashMap<String,Object>(){{
			put("name", "专项检查");
			put("value",caseService.getCheckCountByDeptId(params));
		}});
		res.add(new HashMap<String,Object>(){{
			put("name", "日常检查");
			put("value",caseService.getCheckDailyCountByDeptId(params));
		}});
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, res);
	}
	
	/**
	 * 本年所有预警统计
	 * @return
	 */
	@GetMapping("/yujCount")
	public Result<List<ApiYujDTO>> yujCount(@RequestParam(value="deptId",required = false) String deptId) {
		List<ApiYujDTO> psCaseDTO = new ArrayList<ApiYujDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		Map<String, Object> params = new HashMap<>();
		if("2".equals(dept.getLaw_type())){
			params.put("areaId", dept.getArea_id());
		} else {
			params.put("deptId", deptId);// 后加的			
		}
		psCaseDTO = this.caseService.yujCount(params);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 查询预警统计分区域
	 * @return
	 */
	@GetMapping("/yujCountByArea")
	public Result<List<ApiYujDTO>> yujCountByArea(@RequestParam(value="deptId",required = false) String deptId) {
		List<ApiYujDTO> psCaseDTO = new ArrayList<ApiYujDTO>();
		DeptDTO dept = this.deptService.findDeptInfoById(deptId);
		if("2".equals(dept.getLaw_type())){
			Map<String, Object> params = new HashMap<>();
			psCaseDTO = this.caseService.yujCountByArea(params);
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("deptId", deptId);
			psCaseDTO = this.caseService.yujCount(params);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 查询预警统计分部门
	 * @return
	 */
	@GetMapping("/yujCountByAreaAll")
	public Result<List<ApiYujDTO>> yujCountByAreaAll(@RequestParam(value="areaId",required = false) String areaId) {
		List<ApiYujDTO> psCaseDTO = new ArrayList<ApiYujDTO>();
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		psCaseDTO = this.caseService.yujCountByAreaAll(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	
	/**
	 * 检查状态查询
	 * @return
	 */
	@GetMapping("/checkStatusByDeptId")
	public Result<List<ApiCheckTypeDTO>> checkStatusByDeptId(@RequestParam(value="deptId",required = false) String deptId) {
		List<ApiCheckTypeDTO> list = new ArrayList<ApiCheckTypeDTO>();
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		list = this.caseService.checkStatusByDeptId(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
	
}
