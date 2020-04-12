package com.orhonit.ole.report.service.basics.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.basics.ReportBasicsDao;
import com.orhonit.ole.report.dao.check.ReportOnlyCheckDao;
import com.orhonit.ole.report.dto.basics.ReportBasicsDTO;
import com.orhonit.ole.report.dto.check.ReportMonthlyDTO;
import com.orhonit.ole.report.service.basics.ReportBsicsService;




@Service
public class ReportBsicsServiceImpl implements ReportBsicsService {

	@Autowired
	private ReportBasicsDao reportBasicsDao;
	
	@Autowired
	private ReportOnlyCheckDao reportOnlyCheckDao;
	
	@Override
	public List<Object> getCheckedItem() {
		//获取数据
		List<ReportBasicsDTO> day = reportBasicsDao.getCheckedDayItem();
		//封装数据
		List<Object> list = new ArrayList<>();
		for(ReportBasicsDTO rb:day) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", rb.getName());
			map.put("value", rb.getValue());
			list.add(map);
		}
		return list;
	}

	
	//获取检查总数和各类型的检查的数量
	@Override
	public List<Object> getChecked(String areaId) {
		
		//获取数据
		List<ReportMonthlyDTO> Day = reportOnlyCheckDao.findDayAndSpecialCount(areaId);//日常检查
		List<ReportMonthlyDTO> Special = reportOnlyCheckDao.findCheckAndSpecialcount(areaId);//专项检查
		//封装数据
		List<Object> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
//		List<Object> specialValue = new ArrayList<>();
//		List<Object> dayValue = new ArrayList<>();
		Integer specialValue = 0;
		Integer dayValue = 0;
		Integer count = 0;
		for(ReportMonthlyDTO str : Day) {
			if(str.getCount() != 0) {
				dayValue = dayValue + str.getCount();
			}
		}
		for(ReportMonthlyDTO str : Special) {
			
			if(str.getCount() != 0) {
				specialValue = specialValue + str.getCount();
			}
			
		}
		count = specialValue + dayValue;
		map.put("SpecialValue", specialValue);
		map.put("DayValue", dayValue);
		map.put("count", count);
		list.add(map);
		return list;
		
	}
	
	
}
