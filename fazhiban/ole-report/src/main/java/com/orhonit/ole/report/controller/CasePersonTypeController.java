package com.orhonit.ole.report.controller;

import com.orhonit.ole.report.dto.cases.CasePersonTypeDTO;
import com.orhonit.ole.report.service.cases.ReportCasePersonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处罚人员类别占比分析
 * @author Jwen
 *
 */
@RestController
@RequestMapping("/CasePersonType")
public class CasePersonTypeController {

	@Autowired
	private ReportCasePersonTypeService reportCasePersonTypeService;

	@GetMapping("bar")
	public Object bar(){
        List<CasePersonTypeDTO> count = reportCasePersonTypeService.findCasePersonTypeCount();
        return count;
	}

}
