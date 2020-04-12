package com.orhonit.ole.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.service.basics.ReportBsicsService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;


@RequestMapping("basics")
@RestController
public class ReportBasicsController {
	
	@Autowired
	private ReportBsicsService reportBsicsService;
	
	@GetMapping("getCheckedItem")
	public Object getCheckedItem() {
		
		return reportBsicsService.getCheckedItem();
	}
	
	@GetMapping("getChecked")
	public Object getChecked() {
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		return reportBsicsService.getChecked(areaId);
	}
}
