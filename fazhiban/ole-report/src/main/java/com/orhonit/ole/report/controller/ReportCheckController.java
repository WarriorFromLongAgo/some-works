package com.orhonit.ole.report.controller;


import com.orhonit.ole.report.dto.check.CheckDTO;
import com.orhonit.ole.report.service.check.CheckService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkChart")
public class ReportCheckController {

    @Autowired
    private CheckService checkService;

    @GetMapping("bar")
    public Object bar(){
    	User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
        List<CheckDTO> check = checkService.findOnlyCheck(areaId);
        return check;
    }
    
    //各个部门得检查总量
    @GetMapping(value="getDayAndSpecial")
    public Object getDayAndSpecial() {
    	User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
    	List<Object> list = checkService.findDayAndSpecialCount(areaId);
    	return list;
    }
    
    //某个部门的1到12月的每月的统计总量
    @GetMapping(value="getMonthly")
    public Object getMonthly(@RequestParam(value="AreaName",required = false) String AreaName) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("AreaName",AreaName);
    	List<Object> list = checkService.findDayAndMonthly(params);
    	return list;
    }
    
    //检查合格率
    @GetMapping("getQualification")
    public Object getQualification() {
    	Map<String, Object> params = new HashMap<>();
    	return checkService.getQualificationCount(params);
    }
    
    //合格率对比
    @GetMapping("getQualificationCount")
    public Object getQualificationCount(@RequestParam(value = "AreaName",required = false) String AreaName) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("AreaName", AreaName);
    	return checkService.getQualificationItemCount(params);
    }
}
