package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.power.PowerAndRespDTO;
import com.orhonit.ole.report.service.power.PowerAndRespService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@RestController
@RequestMapping("/power")
public class PowerAndRespController {
	
	@Autowired
	private PowerAndRespService power;
	
	//获取所有职权数量
	@GetMapping(value="getTotal")
	public Object getTotal() {
		
		List<PowerAndRespDTO> list = power.getCount();
		return list;
	}

	//获取每种执法性质的执法数量
	@GetMapping(value="getClassiFication")
	public Object getClassiFication() {
        List<PowerAndRespDTO> list = power.getClassFica();
        //封装数据
		Map<String, Object> obj = new HashMap<>();
		List<Object> data = new ArrayList<>();
		List<String> legend = new ArrayList<>();
		for(PowerAndRespDTO str : list) {
			Map<String,Object> map = new HashMap<>();
			
			map.put("name", str.getEnumDesc());
			map.put("value", str.getCount());
			legend.add(str.getEnumDesc());
			data.add(map);
		}
		
		obj.put("data", data);
		obj.put("legned", legend);
		return obj;
	}
	
	@GetMapping("getDepartmentCount")
	public Object getDepartmentCount() {
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		//获取数据
		List<PowerAndRespDTO> list = power.getDepartCount(areaId);
		//封装数据
		Map<String, Object> map = new HashMap<>();
		List<Object> series = new ArrayList<>();
		List<Object> xAxis = new ArrayList<>();
		for(PowerAndRespDTO str : list) {
			series.add(str.getCount());
			xAxis.add(str.getEnumDesc());
		}
		map.put("series", series);
		map.put("xAxis", xAxis);
		
		return map;
	}
	
	/**
	 * 日常检查
	 * @return
	 */
	@GetMapping("getDayInspection")
	public Object getDayInspection() {
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		List<PowerAndRespDTO> list = power.getDayInspection(areaId);
		
		Map<String, Object> map = new HashMap<>();
		List<Object> series = new ArrayList<>();
		List<Object> xAxis = new ArrayList<>();
		for(PowerAndRespDTO str : list) {
			series.add(str.getCount());
			xAxis.add(str.getName());
		}
		map.put("series", series);
		map.put("xAxis", xAxis);
		return map;
	}
	
	
	public int getYear() {
		//获取当前年份
		int year;
		Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        return year;
	}
}
