package com.orhonit.ole.enforce.controller.ps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.ps.PsCaseDTO;
import com.orhonit.ole.enforce.dto.ps.PsCheckDTO;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.utils.PageList;

/**
 *公示系统
 *公示案件查询控制器
 * 1.根据区域ID和deptId查询案件列表
 * 2. 
 * 3. 
 * @author ebusu
 */
@RestController
@RequestMapping("/ps/case")
public class PsCaseController {
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private DocTemplateService docTemplateService;

	@Autowired
	private DocContentService docContentService ;
	
	/**
	 * ps 根据区域ID和deptId查询案件列表
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/tjCaseList")
	public Result<PageList<PsCaseDTO>> tjCaseList(
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="deptId", required = false) String deptId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		if(currentPage != 0 & pageSize != 0){
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setPageSize(pageSize);
			if(deptId != "" & deptId != null){
				caseDTO.setDeptId(deptId);
			}
		}else{
			if(deptId != "" & deptId != null){
				caseDTO.setDeptId(deptId);
			}
			caseDTO.setCurrentPage(1);
			caseDTO.setPageSize(20);
		}
		PageList<PsCaseDTO> casePage = this.caseService.tjCaseList(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, casePage);
	}
	
	/**
	 * ps 根据区域ID和deptId查询案件列表
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/tjPcCaseList")
	public Result<PageList<PsCaseDTO>> tjPcCaseList(
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="deptId", required = false) String deptId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		if(currentPage != 0 & pageSize != 0){
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setPageSize(pageSize);
			if(areaId != "" & areaId != null){
				caseDTO.setAreaId(areaId);
			}
		}else{
			if(areaId != "" & areaId != null){
				caseDTO.setAreaId(areaId);
			}
			caseDTO.setCurrentPage(1);
			caseDTO.setPageSize(20);
		}
		PageList<PsCaseDTO> casePage = this.caseService.tjPcCaseList(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, casePage);
	}
	
	/**
	 * ps 各辖区案件数量统计
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/psCaseList")
	public Result<List<PsCaseDTO>> psCaseList() {
		
		List<PsCaseDTO> psCaseDTO = this.caseService.psCaseList();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * PC各辖区案件数量统计
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/psPcCaseList")
	public Result<List<PsCaseDTO>> psPcCaseList() {
		
		List<PsCaseDTO> psCaseDTO = this.caseService.psPcCaseList();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * ps 各辖区案件数量统计
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/psCase")
	public Result<Object> psCase(@RequestParam(value="caseId",required = false) String caseId) {
		Map<String, Object> params = new HashMap<>();
		params.put("caseId", caseId);
		PsCaseDTO psCaseDTO = this.caseService.psCase(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,psCaseDTO);
	}
	/**
	 * ps 根据案件名称，当事人姓名、电话、身份证查询案件
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/selectCase")
	public Result<PageList<PsCaseDTO>> selectCase(@RequestParam(value="caseName",required = false) String caseName,
			@RequestParam(value="personName",required = false) String personName,
			@RequestParam(value="personNum",required = false) String personNum,
			@RequestParam(value="personTel",required = false) String personTel,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		if(currentPage != 0 & pageSize != 0){
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setPageSize(pageSize);
			caseDTO.setCaseName(caseName);
			caseDTO.setPersonName(personName);
			caseDTO.setPersonNum(personNum);
			caseDTO.setPersonTel(personTel);
		}else{
			caseDTO.setCurrentPage(1);
			caseDTO.setPageSize(20);
			caseDTO.setCaseName(caseName);
			caseDTO.setPersonName(personName);
			caseDTO.setPersonNum(personNum);
			caseDTO.setPersonTel(personTel);
		}
		PageList<PsCaseDTO> psCaseDTO = this.caseService.selectCase(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * ps 手机端查询各地区案件列表
	 * @param paramMap
	 * @return
	 */
	@GetMapping("/apiCaseByAreaId")
	public Result<Object> apiCaseByAreaId(@RequestParam(value="areaId",required = false) String areaId) {
		Map<String, Object> params = new HashMap<>();
		params.put("areaId", areaId);
		List<PsCaseDTO> psCaseDTO = this.caseService.apiCaseByAreaId(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,psCaseDTO);
	}
	
	/**
	 * 查询各部门案件统计
	 * @return
	 */
	@GetMapping("/selectCaseList")
	public Result<List<PsCaseDTO>> selectCaseList(@RequestParam(value="deptName",required = false) String deptName) {
		Map<String, Object> params = new HashMap<>();
		params.put("deptName", deptName);
		List<PsCaseDTO> psCaseDTO = this.caseService.selectCaseList(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 根据主体ID查询案件列表
	 * @return
	 */
	@GetMapping("/selectCaseListBydeptId")
	public Result<PageList<PsCaseDTO>> selectCaseListBydeptId(
	@RequestParam(value="deptId",required = false) String deptId,
	@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
	@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize) {
		PsCaseDTO caseDTO =new PsCaseDTO();
		if(currentPage != 0 & pageSize != 0){
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setPageSize(pageSize);
			caseDTO.setDeptId(deptId);
		}else{
			caseDTO.setCurrentPage(1);
			caseDTO.setPageSize(20);
			caseDTO.setDeptId(deptId);
		}
		PageList<PsCaseDTO> psCaseDTO = this.caseService.tjCaseList(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 查询各部门日常检查统计
	 * @return
	 */
	@GetMapping("/selectDailyCaseList")
	public Result<List<PsCaseDTO>> selectDailyCaseList(@RequestParam(value="deptName",required = false) String deptName) {
		Map<String, Object> params = new HashMap<>();
		if("".equals(deptName)){
			params.put("deptName", "");
		}else{
			params.put("deptName", deptName);
		}
		List<PsCaseDTO> psCaseDTO = this.caseService.selectDailyCaseList(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 查询各地区日常/专项检查统计PC
	 * @return
	 */
	@GetMapping("/checkList")
	public Result<List<Object>> checkList() {
		List<PsCaseDTO> psCaseDTO = this.caseService.dailyList();
		List<PsCaseDTO> CaseDTO = this.caseService.specialList();
		List<Object> list = new ArrayList<Object>();
		list.add(psCaseDTO);
		list.add(CaseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
	/**
	 * 查询各地区日常检查统计
	 * @return
	 */
	@GetMapping("/dailyList")
	public Result<List<PsCaseDTO>> dailyList() {
		List<PsCaseDTO> psCaseDTO = this.caseService.dailyList();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 查询各地区专项检查统计
	 * @return
	 */
	@GetMapping("/specialList")
	public Result<List<PsCaseDTO>> specialList() {
		List<PsCaseDTO> psCaseDTO = this.caseService.specialList();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 查询各部门专项检查统计
	 * @return
	 */
	@GetMapping("/selectSpecialCaseList")
	public Result<List<PsCaseDTO>> selectSpecialCaseList(@RequestParam(value="deptName",required = false) String deptName) {
		Map<String, Object> params = new HashMap<>();
		if("".equals(deptName)){
			params.put("deptName", "");
		}else{
			params.put("deptName", deptName);
		}
		
		List<PsCaseDTO> psCaseDTO = this.caseService.selectSpecialCaseList(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 根据deptId查询日常检查列表
	 * @return
	 */
	@GetMapping("/selectDailyBydeptId")
	public Result<PageList<PsCheckDTO>> selectDailyBydeptId(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize) {
		
		PsCheckDTO caseDTO =new PsCheckDTO();
		if(currentPage != 0 & pageSize != 0){
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setPageSize(pageSize);
			caseDTO.setDeptId(deptId);
			caseDTO.setAreaId(areaId);
			caseDTO.setCheckStutas(CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
			caseDTO.setCheckMode(CommonParameters.DictType.CHECK_MODE.toString());
			caseDTO.setCheckObjectType(CommonParameters.DictType.PARTY_TYPE.toString());
		}else{
			caseDTO.setCurrentPage(1);
			caseDTO.setPageSize(20);
			caseDTO.setDeptId(deptId);
			caseDTO.setAreaId(areaId);
			caseDTO.setCheckStutas(CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
			caseDTO.setCheckMode(CommonParameters.DictType.CHECK_MODE.toString());
			caseDTO.setCheckObjectType(CommonParameters.DictType.PARTY_TYPE.toString());
		}
		PageList<PsCheckDTO> psCaseDTO = this.caseService.selectDailyBydeptId(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 根据deptId查询专项检查列表
	 * @return
	 */
	@GetMapping("/selectSpecialBydeptId")
	public Result<PageList<PsCheckDTO>> selectSpecialBydeptId(@RequestParam(value="deptId",required = false) String deptId,
			@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
			@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize) {
		PsCheckDTO caseDTO =new PsCheckDTO();
		if(currentPage != 0 & pageSize != 0){
			caseDTO.setCurrentPage(currentPage);
			caseDTO.setPageSize(pageSize);
			caseDTO.setDeptId(deptId);
			caseDTO.setAreaId(areaId);
		}else{
			caseDTO.setCurrentPage(1);
			caseDTO.setPageSize(20);
			caseDTO.setDeptId(deptId);
			caseDTO.setAreaId(areaId);
		}
		PageList<PsCheckDTO> psCaseDTO = this.caseService.selectSpecialBydeptId(caseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, psCaseDTO);
	}
	/**
	 * 查询各部门日常/专项检查统计
	 * @return
	 */
	@GetMapping("/check")
	public Result<List<Object>> check(@RequestParam(value="deptName",required = false) String deptName) {
		Map<String, Object> params = new HashMap<>();
		if("".equals(deptName)){
			params.put("deptName", "");
		}else{
			params.put("deptName", deptName);
		}
		List<PsCaseDTO> psCaseDTO = this.caseService.selectDailyCaseList(params);
		List<PsCaseDTO> CaseDTO = this.caseService.selectSpecialCaseList(params);
		List<Object> list = new ArrayList<Object>();
		list.add(psCaseDTO);
		list.add(CaseDTO);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
	/**
	 * 获取文书内容,将模板和数据(Json)绑定并返回html到前台
	 * @return
	 */
//	@GetMapping("/detail")
//	public Result<Object> getFinalContent(@RequestParam("templateId") String templateId, 
//			@RequestParam("caseId") String caseId) {
//		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);
//
//		// 获取模板内容
//		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
//		
//		// 获取文书内容
//		String htmlContent = this.docContentService.getHtmlContent(templateId, caseId, document);
//		
//		Map<String, String> map = new HashMap<>();
//		// 返回到前台
//		map.put("htmlContent", htmlContent);
//		
//		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
//	}
}
