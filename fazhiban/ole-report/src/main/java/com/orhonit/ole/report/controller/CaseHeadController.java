package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.DeptDTO;
import com.orhonit.ole.report.dto.cases.CaseHeadChartDTO;
import com.orhonit.ole.report.dto.cases.CaseHeadDTO;
import com.orhonit.ole.report.service.cases.ReportCaseHeadService;
import com.orhonit.ole.report.service.cases.ReportCaseHotMapService;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 *  案件处理结果统计
 * @author Jwen
 * @time 2018-1-17
 */
@RestController
@RequestMapping("/casehead")
public class CaseHeadController {

	@Autowired
	private ReportCaseHeadService ReportCaseHeadService;

	@Autowired
	private ReportCaseHotMapService ReportCaseHotMapService;
	
	@Autowired
	private ReportDeptService reportDeptService;

	@GetMapping("/bar")
	public Object caseheadBar() {
		Map<String, Object> result = new HashMap<>();

		String[] lenged = { "受理案件量", "立案案件量", "结案案件量" };
		// 查询年份
		List<String> yearList = ReportCaseHotMapService.findCaseYearSort();
		// 构建年份数据表
		List<CaseHeadChartDTO> BarData = new ArrayList<>();
		for (int i = 0; i < yearList.size(); i++) {
			CaseHeadChartDTO BarChart = new CaseHeadChartDTO(yearList.get(i), "0", "0", "0");
			BarData.add(BarChart);
		}

		// 查询不同的案件数量
		String caseStatus =null;// 全部案件
		List<CaseHeadDTO> countList = ReportCaseHeadService.findCaseByCaseStatus(caseStatus);
		// 填充数据
		for (CaseHeadChartDTO data : BarData) {
			for (CaseHeadDTO value : countList) {
				if (value.getYear().equals(data.getName())) {
					data.setValue1(value.getCount());
				}
			}
		}
		// 填充案件数量
		caseStatus = "23";//立案
		List<CaseHeadDTO> countList2 = ReportCaseHeadService.findCaseByCaseStatus1(caseStatus);
		// 填充数据
		for (CaseHeadChartDTO data : BarData) {
			for (CaseHeadDTO value : countList2) {
				if (value.getYear().equals(data.getName())) {
					data.setValue2(value.getCount());
				}
			}
		}

		caseStatus = "14,36";//结案案件
		List<CaseHeadDTO> countList3 = ReportCaseHeadService.findCaseByCaseStatus1(caseStatus);
		// 填充数据
		for (CaseHeadChartDTO data : BarData) {
			for (CaseHeadDTO value : countList3) {
				if (value.getYear().equals(data.getName())) {
					data.setValue3(value.getCount());
				}
			}
		}

		result.put("BarData", BarData);
		result.put("lenged", lenged);

		return result;
	}

	@GetMapping("/line")
	public Object caseheadLine(@RequestParam(value = "year" , defaultValue = "")String year) {
		if("".equals(year) || year == null) {
			Calendar cal = Calendar.getInstance();
			year = cal.get(Calendar.YEAR)+"";
		}
		Map<String, Object> result = new HashMap<>();
		String[] month = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
		String[] lenged = { "受理案件量", "立案案件量", "结案案件量" };
		// 构建年份数据表
		List<CaseHeadChartDTO> LineData = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			CaseHeadChartDTO LineChart = new CaseHeadChartDTO(i + "", "0", "0", "0");
			LineData.add(LineChart);
		}
		// 查询不同的案件数量
		String status = null;// 全部案件
		List<CaseHeadDTO> countList = ReportCaseHeadService.findCaseByCaseStatusAndYear(status, year);
		// 填充数据
		for (CaseHeadChartDTO data : LineData) {
			for (CaseHeadDTO value : countList) {
				if (value.getMonth().equals(data.getName())) {
					data.setValue1(value.getCount());
				}
			}
		}
		
		status = "23";// 立案案件
		List<CaseHeadDTO> countList2 = ReportCaseHeadService.findCaseByCaseStatusAndYear1(status, year);
		// 填充数据
		for (CaseHeadChartDTO data : LineData) {
			for (CaseHeadDTO value : countList2) {
				if (value.getMonth().equals(data.getName())) {
					data.setValue2(value.getCount());
				}
			}
		}
		
		status = "14,36";// 结案案件  (b.case_status='14' OR b.case_status='36');
		List<CaseHeadDTO> countList3 = ReportCaseHeadService.findCaseByCaseStatusAndYear1(status, year);
		// 填充数据
		for (CaseHeadChartDTO data : LineData) {
			for (CaseHeadDTO value : countList3) {
				if (value.getMonth().equals(data.getName())) {
					data.setValue3(value.getCount());
				}
			}
		}
		
		result.put("LineData", LineData);
		result.put("lenged", lenged);
		result.put("month",month);
		return result;
	}
	
	@GetMapping("/deptCaseCount")
	public Object deptCaseCount() {
		User user = UserUtil.getCurrentUser();
		DeptDTO deptDTO = null;
		Map<String, Object> params = new HashMap<String,Object>();
		if(!user.getUsername().equals("admin")){
			deptDTO = reportDeptService.findDeptById(user.getDept_id());
		}
		if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}else if(deptDTO != null && deptDTO.getDept_property() == 3 && deptDTO.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
				params.put("deptId", user.getDept_id());
			}
		}else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", reportDeptService.getDeptAndChildById(user.getDept_id()));
		}
		//获取有案件的部门
		List<Map<String, Object>> depts = reportDeptService.getDeptHaveCase(params);
		//年份从2014至今年
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> temp = new HashMap<>();
		//暂存整理结果
		for (Map<String, Object> action : depts) {
			if(action.containsKey("deptName") && !temp.containsKey(action.get("deptName").toString())){
				List<Integer> yearArr =new ArrayList<>();
				for(int i = 2014 ;i <= year; i++){
					yearArr.add(0);
				}
				if(Integer.parseInt(action.get("year").toString())>=2014 && Integer.parseInt(action.get("year").toString())<=year){
					yearArr.set(Integer.parseInt(action.get("year").toString())-2014, Integer.parseInt(action.get("count").toString()));
					temp.put(action.get("deptName").toString(), yearArr);
				}
			}else if(action.containsKey("deptName")){
				if(Integer.parseInt(action.get("year").toString())>=2014 && Integer.parseInt(action.get("year").toString())<=year){
					@SuppressWarnings("unchecked")
					List<Integer> yearArr = (ArrayList<Integer>)temp.get(action.get("deptName").toString());
					yearArr.set(Integer.parseInt(action.get("year").toString())-2014, Integer.parseInt(action.get("count").toString()));
				}
			}
		}
		List<Object> res = new ArrayList<>();
		temp.forEach((k,v)->{
			Map<String, Object> ob = new HashMap<>();
			ob.put("name", k);
			ob.put("value", v);
			res.add(ob);
		});
		return res;
	}
}
