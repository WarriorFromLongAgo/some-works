package com.orhonit.ole.report.controller;

import com.orhonit.ole.report.dto.cases.DeptWranDTO;
import com.orhonit.ole.report.service.cases.ReportDeptWranInfoService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件异常统计
 * @author Jwen
 *
 */
@RestController
@RequestMapping("/basewraninfo")
@Slf4j
public class BaseWranInfoController {

	@Autowired
	private ReportDeptWranInfoService eprortDeptWranInfoService;
	
	@GetMapping("/warnType")
	public Object warnType() {
		Map<String,Object> result = new HashMap<>();
		List<Map<String,Object>> wranCount = eprortDeptWranInfoService.findBaseWranCount();
		List<Object> xAxis = new ArrayList<>();
		List<Object> series = new ArrayList<>();
		for (Map<String, Object> map : wranCount) {
			xAxis.add(map.get("name"));
			series.add(map.get("value"));
		}
		
		result.put("xAxis",xAxis);
		result.put("series",series);
		
		return result;
	}
	
	@GetMapping("/warnTypeDetail")
	public Object warnTypeDetail(@RequestParam(value="warnType",required = false) String warnType) {
		HashMap<String, List<String>> warnTypeMap = new HashMap<String, List<String>>() {
			{
				put("执法人员预警", new ArrayList<String>() {
					{
						add("48");//增
						add("49");//删
						add("50");//改
					}
				});
				put("执法主体预警", new ArrayList<String>() {
					{
						add("51");//增
						add("52");//改
						add("53");//删
					}
				});
				put("权责清单告警", new ArrayList<String>() {
					{
						add("54");//增
						add("55");//改
						add("56");//删
					}
				});
			}
		};
		Map<String,Object> result = new HashMap<>();
		List<Map<String,Object>> warnTypeDetail = eprortDeptWranInfoService.findBaseWranDetail(warnTypeMap.get(warnType).toString().substring(1,warnTypeMap.get(warnType).toString().length()-1));
		result.put("warnTypeDetail", warnTypeDetail);
		return result;
	}
}
