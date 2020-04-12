package com.orhonit.ole.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.cases.DeptWranDTO;
import com.orhonit.ole.report.service.cases.ReportDeptWranInfoService;
import com.orhonit.ole.sys.service.SysDeptService;

import lombok.extern.slf4j.Slf4j;

/**
 * 案件异常统计
 * @author Jwen
 *
 */
@RestController
@RequestMapping("/deptwraninfo")
@Slf4j
public class DeptWranInfoController {

	@Autowired
	private ReportDeptWranInfoService eprortDeptWranInfoService;
	
	@Autowired
	private SysDeptService sysDeptService;

	@GetMapping("/dept")
	public Object AdminPunDept() {
		List<DeptWranDTO> wranCount = eprortDeptWranInfoService.findDeptWranCount(sysDeptService.getDepts());
		return wranCount;
	}


	@GetMapping("/area")
	public Object AreaAdminDept() {
		Map<String,Object> result = new HashMap<>();
		List<DeptWranDTO> wranCount = eprortDeptWranInfoService.findDeptWranCount(sysDeptService.getDepts());
		result.put("dept",wranCount);
		return result;
	}
}
