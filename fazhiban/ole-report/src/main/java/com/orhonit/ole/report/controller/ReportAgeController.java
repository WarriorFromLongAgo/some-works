package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.age.AgeDTO;
import com.orhonit.ole.report.dto.political.PoliticalDTO;
import com.orhonit.ole.report.service.age.ReportAgeService;
import com.orhonit.ole.report.service.political.ReportPoliticalService;

@RestController
@RequestMapping(value="/law")
public class ReportAgeController {
	
	@Autowired
	private ReportAgeService ageService;


	@GetMapping("/reportage")
	public Object Reportage() {
		Map<String, Object> data = new HashMap<>();
		List<String> area = new ArrayList<String>();
		List<String> num = new ArrayList<String>();
		List<AgeDTO> areaDetails = ageService.getBrithday();
		for(AgeDTO item:areaDetails) {
			if(item.getAge()!=null) {
			area.add(item.getAge());
			num.add(item.getCount());
			}
		}
		data.put("area", area);
		data.put("num", num);
		return data;
	}
	
}
