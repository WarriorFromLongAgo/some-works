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

import com.orhonit.ole.report.dto.position.PositionDTO;
import com.orhonit.ole.report.service.inspect.ReportPositionService;
import com.orhonit.ole.sys.dao.DictDao;

@RestController
@RequestMapping(value="/law")
public class PositionController {
	
	@Autowired
	private ReportPositionService positionService;
	
	@Autowired
	private DictDao dictDao;
	

	@GetMapping("/regionalClassification")
	public Object RegionalClassification() {
		Map<String, Object> data = new HashMap<>();
		List<String> area = new ArrayList<String>();
		List<String> num = new ArrayList<String>();
		List<PositionDTO> areaDetails = positionService.getArea();
		for(PositionDTO item:areaDetails) {
			area.add(item.getAreaName());
			num.add(item.getCount());
		}
		data.put("area", area);
		data.put("num", num);
		return data;
	}
	
	@GetMapping("/JobStatistics")
	public Object JobStatistics(@RequestParam(value="areaName",required = false) String areaName) {
		Map<String, Object> data = new HashMap<>();
		List<Object> series = new ArrayList<>();
        List<Object> legend = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
		params.put("name", areaName);
		List<PositionDTO> list = positionService.getAreaPost(params);
		for(PositionDTO item:list) {
			if(item.getDuty()!=null) {
				Map<String,Object> map = new HashMap<>();
				String deptName = dictDao.getDescByValue("1706",item.getDuty());
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
