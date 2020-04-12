package com.orhonit.ole.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.service.lawp.ReportLawPersonService;

@RestController
@RequestMapping(value="/enforceperson")
public class ReportEnforcePersonController {
	
	@Autowired
	private ReportLawPersonService reportLawPersonService;
	
	@GetMapping("/areaPersonCount")
	public List<BaseChartDTO> areaPersonCount(){
		List<BaseChartDTO> personCount = reportLawPersonService.findAreaEnforcePersonCount();
		return personCount;
	}
	
	@GetMapping("/ageList")
	public List<BaseChartDTO> ageList(){
		List<BaseChartDTO> personAgeList = reportLawPersonService.findEnforcePersonAge();
		return personAgeList;
	}
	
	@GetMapping("/dutyPersonCount")
	public List<BaseChartDTO> dutyPersonCount(){
		List<BaseChartDTO> dutyPersonCount = reportLawPersonService.findDutyPersonCount();
		return dutyPersonCount;
	}
	
	@GetMapping("/PersonNation")
	public List<BaseChartDTO> PersonNation(){
		List<BaseChartDTO> personNation = reportLawPersonService.findPersonNation();
		return personNation;
	}
	
	@GetMapping("/EduList")
	public List<BaseChartDTO> EduList(){
		List<BaseChartDTO> eduList = reportLawPersonService.findEduList();
		return eduList;
	}
	
	@GetMapping("/politicalList")
	public List<BaseChartDTO> politicalList(){
		List<BaseChartDTO> politicalList = reportLawPersonService.findpoliticalList();
		return politicalList;
	}
	
	
}
