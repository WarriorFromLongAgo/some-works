package com.orhonit.ole.report.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.service.complaint.ComplainStatisticsService;

@RestController
@RequestMapping("/complaint")
public class ReportComplaintController {
	
	@Autowired
	ComplainStatisticsService complainStatisticsService;

	/**
	 * 获得近五年投诉量
	 * @return
	 */
    @GetMapping("/recentYears")
    public Object recentYears(){
    	List<Integer> res = new ArrayList<>();
    	Calendar date = Calendar.getInstance();
    	for(int i = date.get(Calendar.YEAR) - 4;i<=date.get(Calendar.YEAR);i++){
    		res.add(complainStatisticsService.getComplainCountByYear(i));
    	}
        return res;
    }
    
    /**
     * 根据年份获取当年每月投诉量
     * @param year
     * @return
     */
    @GetMapping("/monthly")
    public Object monthly(@RequestParam(value="year",required = false) int year) {
    	List<Integer> res = new ArrayList<>();
    	for(int i=1;i<=12;i++){
    		res.add(complainStatisticsService.getComplainCountByYearMonth(year, i));
    	}
        return res;
    }
    
}
