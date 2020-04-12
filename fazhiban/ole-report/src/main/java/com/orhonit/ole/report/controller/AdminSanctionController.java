package com.orhonit.ole.report.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.cases.AreaCaseCountDTO;
import com.orhonit.ole.report.dto.cases.DeptCaseCountDTO;
import com.orhonit.ole.report.service.area.ReportAreaService;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 行政处罚
 * @author Jwen
 *
 */
@RestController
@RequestMapping("/AdminSanction")
public class AdminSanctionController {
	
	@Autowired
	private ReportDeptService reportDeptService;
	
	@Autowired
	private ReportAreaService reportAreaService;
	
	/**
	 * AdminPunDept  部门行政处罚案件统计
	 * @return
	 */
	@GetMapping("/AdminPunDept")
	public Object AdminPunDept(@RequestParam(value = "year",defaultValue = "")String year) {
		if("".equals(year)) {
			Calendar now = Calendar.getInstance();  
			year = now.get(Calendar.YEAR)+"";
		}
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		List<DeptCaseCountDTO> deptCaseCount = reportDeptService.DeptCaseCount(year,areaId);
		return deptCaseCount;
	}

	/**
	 * AreaAdminDept  区县行政处罚案件统计
	 * @return
	 */
	@GetMapping("/AreaAdminDept")
	public Object AreaAdminDept(@RequestParam(value = "year",defaultValue = "")String year) {
		if("".equals(year)) {
			Calendar now = Calendar.getInstance();  
			year = now.get(Calendar.YEAR)+"";
		}
		List<AreaCaseCountDTO> areaAndCaseCount = reportAreaService.findAreaAndCaseCount(year);
		return areaAndCaseCount;
	}
}
