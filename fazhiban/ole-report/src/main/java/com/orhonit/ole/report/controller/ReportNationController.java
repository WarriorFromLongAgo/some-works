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

import com.orhonit.ole.report.dto.nation.NationDTO;
import com.orhonit.ole.report.service.nation.ReportNationService;
import com.orhonit.ole.sys.dao.DictDao;

@RestController
@RequestMapping(value="/law")
public class ReportNationController {
	
	@Autowired
	private ReportNationService nationService;
	
	@Autowired
	private DictDao dictDao;


	@GetMapping("/geteNationArea")
	public Object geteNationArea() {
		Map<String, Object> data = new HashMap<>();
		List<String> area = new ArrayList<String>();
		List<String> num = new ArrayList<String>();
		List<NationDTO> areaDetails = nationService.getArea();
		for(NationDTO item:areaDetails) {
			area.add(item.getAreaName());
			num.add(item.getCount());
		}
		data.put("area", area);
		data.put("num", num);
		return data;
	}
	
	@GetMapping("/GetNation")
	public Object GetNation(@RequestParam(value="areaName",required = false) String areaName) {
		Map<String, Object> data = new HashMap<>();
		List<Object> series = new ArrayList<>();
        List<Object> legend = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
		params.put("name", areaName);
		List<NationDTO> list = nationService.getAreaPost(params);
		for(NationDTO item:list) {
			if(item.getNation()!=null) {
				Map<String,Object> map = new HashMap<>();
				String deptName = dictDao.getDescByValue("1701",item.getNation());
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
