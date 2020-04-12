package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.DeptDTO;
import com.orhonit.ole.report.dto.cases.CaseWranDTO;
import com.orhonit.ole.report.service.cases.ReportCaseWranService;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 案件异常统计
 * @author Jwen
 *
 */
@RestController
@RequestMapping("/casewran")
@Slf4j
public class CaseWranController {
	
	@Autowired
	private ReportCaseWranService reportCaseWranService;
	
	@Autowired
	private ReportDeptService reportDeptService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 统计各种案件类型的数量
	 * @param year
	 * @return
	 */
	@GetMapping("/bar")
	public List<CaseWranDTO> bar() {
		List<CaseWranDTO> caseWranCount = reportCaseWranService.findCaseWranCount(sysDeptService.getDepts());
        return caseWranCount;
	}
	
	/**
	 * 按年统计部门预警信息
	 * @return
	 */
	@GetMapping("/yearCount")
	public Object deptCaseCount() {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("deptIds", sysDeptService.getDepts());
		List<Map<String, Object>> depts = reportCaseWranService.getDeptCaseWarn(params);
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
