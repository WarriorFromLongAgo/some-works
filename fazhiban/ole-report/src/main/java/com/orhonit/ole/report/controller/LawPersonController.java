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

import com.orhonit.ole.report.dto.laswp.LawPersonDTO;
import com.orhonit.ole.report.dto.laswp.LawPersonPieDTO;
import com.orhonit.ole.report.service.lawp.ReportLawPersonService;
import com.orhonit.ole.report.service.lawp.ReportLawService;
import com.orhonit.ole.sys.dao.DictDao;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@RequestMapping(value="/lawperson")
@RestController
public class LawPersonController {
	
	@Autowired
	private ReportLawPersonService lawPersonService;
	
	@Autowired
	private DictDao dictDao;
	
	@Autowired
	private ReportLawService reportLawService;
	
	
	//柱状图
	@GetMapping(value="/getLawPersonBar")
	public Object getLawPersonBar() {
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		List<LawPersonDTO> list = lawPersonService.getLawpCount(areaId);
		
		//数据容器
		Map<String, Object> map = new HashMap<>();
		List<String> count = new ArrayList<>();
		List<String> name = new ArrayList<>();
		
		for(LawPersonDTO d : list) {
			count.add(d.getCount());
			name.add(d.getName());
		}
		map.put("name", name);
		map.put("count",count);
		return map;
	}
	
	
	@GetMapping(value="/getLawPersonPie")
	public Object getLawPersonPie(@RequestParam(value="areaName",required = false) String areaName) {
		
		//把数据封装
		Map<String, Object> params = new HashMap<>();
		params.put("name", areaName);
		List<LawPersonPieDTO> list =  lawPersonService.getLawpDCount(params);
		
		//数据容器
		Map<String, Object> map = new HashMap<>();
		List<Object> dept = new ArrayList<>();
		List<Object> count = new ArrayList<>();
		
		for(LawPersonPieDTO str : list) {
			if(str.getLawType() != null) {
				Map<String, Object> data = new HashMap<>();
				String deptName = dictDao.getDescByValue("1707",str.getLawType());
				data.put("name", deptName);
				data.put("value", str.getCount());
				count.add(data);
				dept.add(deptName);
			}
		}
		map.put("count", count);
		map.put("lename", dept);
		map.put("title", areaName);
		return map;
	}
	
	@GetMapping("getItemLawCount")
	public Object getItemLawCount() {
		return reportLawService.getLawItemCount();
	}
	
	
	
}
