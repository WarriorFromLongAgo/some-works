package com.orhonit.ole.tts.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.tts.dto.CaseWranDTO;
import com.orhonit.ole.tts.service.dashboard.DashboardService;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 *首页统计信息控制器
 * @author wuyz
 *
 */
@Slf4j(topic = "indexControlLogger")
@RestController
@RequestMapping("/index")
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private SysDeptService sysDeptService;

    @RequestMapping("/countByWarnType")
    public Result<Object> countByWarnType(){
    	Map<String, Object> params = new HashMap<>();
    	params.put("deptIds", sysDeptService.getDepts());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("ssyj", dashboardService.getSsyjCount(params));
        data.put("gcyj", dashboardService.getGcyjCount(params));
        data.put("sxyj", dashboardService.getSxyjCount(params));
        data.put("jcxxyj", dashboardService.getJcxxyjCount(params));
        return ResultUtil.toResponseWithData(ResultCode.SUCCESS, data);
    }
    
    /**
	 * 统计各种案件类型的数量
	 * @param year
	 * @return
	 */
	@GetMapping("/bar")
	public List<CaseWranDTO> bar() {
        return this.dashboardService.findCaseWranCount(sysDeptService.getDepts());
	}
}
