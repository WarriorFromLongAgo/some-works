package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.post.PostPersonnelDTO;
import com.orhonit.ole.report.service.lawp.ReportLawPersonService;

@RequestMapping("/post")
@RestController
public class PostPersonnelController {
	
	@Autowired
	private ReportLawPersonService reportLawPersonService;
	
	@GetMapping("/getAllAreaPostPersonnel")
	public Object getAllAreaPostPersonnel() {
		//获取数据
		List<PostPersonnelDTO> data = reportLawPersonService.getAllAreaPostPersonnel();
		List<Object> series = new ArrayList<>();
		List<Object> xAxis = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for(PostPersonnelDTO str : data) {
			series.add(str.getCount());
			xAxis.add(str.getAreaName());
		}
		map.put("series", series);
		map.put("xAxis", xAxis);
		return map;
	}
	
	@GetMapping(value = "getPostPersonnel")
	public Object getPostPersonnel(@RequestParam(value="AreaName",required = false) String AreaName) {
		Map<String, Object> params = new HashMap<>();
		params.put("AreaName", AreaName);
		List<PostPersonnelDTO> data = reportLawPersonService.getPostPersonnel(params);
		List<PostPersonnelDTO> deptData = reportLawPersonService.getPostDeptCount(params);
		List<Object> series = new ArrayList<>();
		List<Object> legend = new ArrayList<>();
		List<Object> deptLegend = new ArrayList<>();
		List<Object> deptSeries = new ArrayList<>();
		Map<String, Object> dataMap = new HashMap<>();
		for(PostPersonnelDTO str : data) {
			Map<String, Object> map = new HashMap<>();
			legend.add(str.getEnumDesc() + "类");
			
			map.put("name", str.getEnumDesc());
			map.put("value", str.getCount());
			series.add(map);
		}
		for(PostPersonnelDTO dept : deptData) {
			Map<String,Object> map = new HashMap<>();
			deptLegend.add(dept.getEnumDesc() + "类");
			
			map.put("name", dept.getEnumDesc());
			map.put("value", dept.getCount());
			deptSeries.add(map);
		}
		
		dataMap.put("series", series);
		dataMap.put("legend", legend);
		dataMap.put("deptLegend", deptLegend);
		dataMap.put("deptSeries", deptSeries);
		return dataMap;
	}
}
