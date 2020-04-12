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

import com.orhonit.ole.report.dto.ps.AreaDeptDTO;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.sys.dao.DictDao;

/**
 * 执法主体
 * @author Jwen
 * 2018-1-6 17:33
 */
@RestController
@RequestMapping("/lawsub")
public class LawSubController {
		
	
	@Autowired
	private ReportDeptService reportDeptService;
	
	@Autowired
	private DictDao dictDao;
	
	
	@GetMapping("/getAreaAndDept")
	public Object getAreaAndDept() {
		//封装数据
		Map<String, Object> map = new HashMap<String,Object>();
		List<String> area = new ArrayList<String>();
		List<String> dept = new ArrayList<String>();
		//查询对应数据
		List<AreaDeptDTO> deptlist = reportDeptService.areaDeptList();
		//筛选符合条件的数据到map
		for(AreaDeptDTO depts:deptlist) {
			area.add(depts.getAreaName());
			dept.add(depts.getCount());
		}
		//拼装map
		map.put("e_area", area);
		map.put("e_dept", dept);
		return map;
	}
	
	
	@GetMapping("/getDeptAndProp")
	public Object getDeptAndProp(@RequestParam(value="areaName",required = false) String areaName) {
		//包装参数
        Map<String,Object> data = new HashMap<>();
       //包装data
        List<Object> series = new ArrayList<>();
        List<Object> legend = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();

		//查询区域内主体数量
		params.put("areaName", areaName);
		List<AreaDeptDTO> dept = reportDeptService.areaDeptProList(params);
		for(AreaDeptDTO d:dept) {
			if(d.getDeptProperty()!=null) {
				Map<String,Object> map = new HashMap<>();
				String deptName = dictDao.getDescByValue("1707",	 d.getDeptProperty());
				map.put("name",  deptName);
				map.put("value", d.getCount());
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
