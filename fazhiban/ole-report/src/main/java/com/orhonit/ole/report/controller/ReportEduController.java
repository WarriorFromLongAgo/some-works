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

import com.orhonit.ole.report.dto.edu.EduDTO;
import com.orhonit.ole.report.service.edu.ReportEduService;
import com.orhonit.ole.sys.dao.DictDao;

@RestController
@RequestMapping(value="/law")
public class ReportEduController {
	
	@Autowired
	private ReportEduService eduService;
	
	@Autowired
	private DictDao dictDao;


	@GetMapping("/geteEduArea")
	public Object geteEduArea() {
		Map<String, Object> data = new HashMap<>();
		List<String> area = new ArrayList<String>();
		List<String> num = new ArrayList<String>();
		List<EduDTO> areaDetails = eduService.getArea();
		for(EduDTO item:areaDetails) {
			area.add(item.getAreaName());
			num.add(item.getCount());
		}
		data.put("area", area);
		data.put("num", num);
		return data;
	}
	
	@GetMapping("/GetEdu")
	public Object GetEdu(@RequestParam(value="areaName",required = false) String areaName) {
		Map<String, Object> data = new HashMap<>();
		List<Object> series = new ArrayList<>();
        List<Object> legend = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
		params.put("name", areaName);
		List<EduDTO> list = eduService.getAreaPost(params);
		for(EduDTO item:list) {
			if(item.getEdu()!=null) {
				Map<String,Object> map = new HashMap<>();
				String deptName = dictDao.getDescByValue("1705",item.getEdu());
				if(deptName!=null) {
				map.put("name",  deptName);
				map.put("value", item.getCount());
				}
				series.add(map);
				legend.add(deptName);
				
			}
		}

		//设置返回数据
        data.put("series",series);
        data.put("legend",legend);
        data.put("title", areaName);
		return data;
		
	}
	
}
