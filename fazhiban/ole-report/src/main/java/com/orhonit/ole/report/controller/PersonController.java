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

import com.orhonit.ole.report.dao.ReportPersonDao;
import com.orhonit.ole.report.dto.PersonDTO;
import com.orhonit.ole.report.dto.ps.AreaDeptDTO;
import com.orhonit.ole.report.service.person.ReportPersonService;
import com.orhonit.ole.sys.dao.DictDao;

@RestController
@RequestMapping(value="/areacheckdaily")
public class PersonController {
	
	@Autowired
	private ReportPersonService reportPersonService;
	
	@Autowired
	private DictDao dictDao;
	
	@GetMapping("/AreaDailyCheck")
	public Object AreaDailyCheck() {
		Map<String, Object> data = new HashMap<>();
		List<String> area = new ArrayList<String>();
		List<String> num = new ArrayList<String>();
		List<PersonDTO> areaChecks = reportPersonService.getAreaDeptCheck();
		List<PersonDTO> areaCheckDailys = reportPersonService.getAreaDeptCheckDaily();
		Integer areaNum = areaChecks.size();
		if(areaNum != 0) {
			for(int i = 0 ; i < areaNum;i++) {
				area.add(areaChecks.get(i).getAreaName());
				num.add(Integer.valueOf(areaCheckDailys.get(i).getCount())+Integer.valueOf(areaChecks.get(i).getCount())+"");
			}
			data.put("area", area);
			data.put("num", num);
			return data;
		}else {
			return null;
		}
		
		
		/*for(PersonDTO item:areaDetails) {
			
			num.add(item.getCount());
		}*/
		
	}
	
	@GetMapping("/DeptPersonCheck")
	public Object DeptPersonCheck(@RequestParam(value="areaName",required = false) String areaName) {
		Map<String, Object> params = new HashMap<>();
		//查询区域内主体数量
		params.put("name", areaName);
		List<PersonDTO> areaDeptCheck = reportPersonService.getAreaDeptCheckByAreaName(params);
		return areaDeptCheck;
		
	}

}
