package com.orhonit.ole.report.controller;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.cases.CaseHotMapDTO;
import com.orhonit.ole.report.service.cases.ReportCaseHotMapService;

/**
 * 
 * @author Jwen
 * 案件发生热点图
 */
@RestController
@RequestMapping("/casehotmap")
public class CaseHotMapController {
	
	@Autowired
	private ReportCaseHotMapService reportCaseHotMapService;
	
	@GetMapping("/ymhotmap")
	public Object YMhotMap() {
		//data
		Map<String, Object> data = new HashMap<>();
		//月份
		List<String> month = new ArrayList<>();
		//年份
		List<String> yearList = new ArrayList<>();
		List<String> monthList = new ArrayList<>();
		Set<String>yearSet=new HashSet<String>();

		List<Object> series = new ArrayList<>();
		
		List<CaseHotMapDTO> findDailyCase = reportCaseHotMapService.findDailyCase();
		
		for(int m = 1; m <=12 ; m ++) {
			DecimalFormat df = new DecimalFormat("00");  
			monthList.add(df.format(m));
			month.add(m+"月");
		}
		//构造年份信息
		for(CaseHotMapDTO cases:findDailyCase) {
			String y =  cases.getCaseTime().substring(0, 4);
			yearSet.add(y);
		}
		yearList.addAll(yearSet);
		Collections.sort(yearList);
		
		for(CaseHotMapDTO cases:findDailyCase) {
			Map<String, Object> xMap = new HashMap<>();
			xMap.put("x", yearList.indexOf(cases.getCaseTime().substring(0, 4)));
			xMap.put("y", monthList.indexOf(cases.getCaseTime().substring(5,7)));
			xMap.put("code", cases.getCount());
			//System.out.println("快速查找"+yearList.indexOf(y));			
			series.add(xMap);
		}
		
		data.put("month", month);
		data.put("year",yearList);
		data.put("series", series);
		
		return data;
	}
	

}
