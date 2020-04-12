package com.orhonit.ole.report.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.cases.CaseSimpDTO;
import com.orhonit.ole.report.service.cases.ReportCaseSimpService;

/**
 * 行政处罚案件简易统计
 * @author YangWenHui
 *
 */
@RequestMapping("/lawCaseSimp")
@RestController
public class LawCaseSimpController {
	
	
	@Autowired
	private ReportCaseSimpService reportCaseService;
	
	@GetMapping("/getDataCount")
	public Object getDataCount() {
		List<CaseSimpDTO> cases = reportCaseService.getCaseCount1();
		for(CaseSimpDTO str : cases) {
			str.setCaseTime(str.getCaseTime().substring(0,4));
		}
		return cases;
	}
	
	@GetMapping("/getData")
	public Object getData(@RequestParam(value="timeYear",required = false) String timeYear) {
		Map<String, Object> params = new HashMap<>();
	
		params.put("timeYear", timeYear);
		List<CaseSimpDTO> cases = reportCaseService.getCaseCount(params);
		return cases;
	}
	
}
