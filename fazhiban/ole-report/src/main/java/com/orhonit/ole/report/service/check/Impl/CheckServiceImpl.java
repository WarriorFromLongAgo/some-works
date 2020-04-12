package com.orhonit.ole.report.service.check.Impl;

import com.orhonit.ole.report.dao.check.CheckQualificationDao;
import com.orhonit.ole.report.dao.check.ReportOnlyCheckDao;
import com.orhonit.ole.report.dto.check.CheckDTO;
import com.orhonit.ole.report.dto.check.CheckQualificationDTO;
import com.orhonit.ole.report.dto.check.ReportMonthlyDTO;
import com.orhonit.ole.report.service.check.CheckService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService{

    @Autowired
    private ReportOnlyCheckDao reportOnlyCheckDao;
    
    @Autowired
    private CheckQualificationDao checkQualificationDao;

    @Override
    public List<CheckDTO> findOnlyCheck(String areaId) {
        return reportOnlyCheckDao.findOnlyCheck(areaId);
    }
    
    Integer count;
	@Override
	public List<Object> findDayAndSpecialCount(String areaId) {
		//获取数据
		List<ReportMonthlyDTO> Day = reportOnlyCheckDao.findDayAndSpecialCount(areaId);//日常检查
		List<ReportMonthlyDTO> Special = reportOnlyCheckDao.findCheckAndSpecialcount(areaId);//专项检查
		//封装数据
		List<Object> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<Object> specialValue = new ArrayList<>();
		List<Object> dayName = new ArrayList<>();
		List<Object> dayValue = new ArrayList<>();
		for(ReportMonthlyDTO str : Day) {
			
			dayName.add(str.getName());
			dayValue.add(str.getCount());
			
		}
		for(ReportMonthlyDTO str : Special) {
			
			specialValue.add(str.getCount());
			
		}
		map.put("SpecialValue", specialValue);
		map.put("name", dayName);
		map.put("DayValue", dayValue);
		list.add(map);
		return list;
	}

	@Override
	public List<Object> findDayAndMonthly(@Param("params") Map<String, Object> params) {
		//获取数据
		List<ReportMonthlyDTO> Day = reportOnlyCheckDao.findDayAndMonthly(params);//日常检查
		List<ReportMonthlyDTO> Special = reportOnlyCheckDao.findSpecialAndMonthly(params);//专项检查
		//封装数据
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<>();
		List<Object> series1 = new ArrayList<>();
		List<Object> series2 = new ArrayList<>();
		Integer data[] = {0,0,0,0,0,0,0,0,0,0,0,0};
		Integer data1[] = {0,0,0,0,0,0,0,0,0,0,0,0};
		
		
		for(ReportMonthlyDTO str : Day) {
			if(Integer.valueOf(str.getDataValue())>0){
				data[Integer.parseInt(str.getDataValue())-1] = str.getCount();
			}
//			data[Integer.parseInt(str.getDataValue())] = 0;
		}
		series1.add(data);
		for(ReportMonthlyDTO str : Special) {
			if(Integer.valueOf(str.getDataValue())>0){
			data1[Integer.parseInt(str.getDataValue())-1] = str.getCount();
			}
			
		}
		series2.add(data1);
		map.put("series1", series1);
		map.put("series2", series2);
		list.add(map);
		return list;
	}

	/**
	 * 1. 返回List, 泛型Object, 业务层service对外暴露的方法Object不友好, 如果其他地方想使用这个方法, 首先过来看实现, 最后强制类型转换
	 * 2. 返回List, 但是里面就放了一个Map, 是不是应该就可以返回一个Map, 要不初始化ArrayList设定长度, 默认大小为10
	 * 3. 接口入参: @param 注解不用在Service层加, mybatis的注解
	 * 4. 参数说明: ...普遍存在
	 * 5. 变量命名: List<CheckQualificationDTO> day, foreach中的str; 变量day是个集合, dayList 或者 dailyCheckInfoDTOs 一眼就知道是日常检查的集合了, 稍微注意一下
	 * 6. 临时变量, 尤其集合, 可能会导致新生代过度频繁
	 * 7. dayValue1 = dayValue1 + str.getCount(); Integer + Integer 不对
	 * 8. 为什么要声明一个count?
	 */
	//检查合格
	@Override
	public List<Object> getQualificationCount(@Param("params") Map<String, Object> params) {
		List<CheckQualificationDTO> day = checkQualificationDao.getDayQualificationCount(params);
		List<CheckQualificationDTO> special = checkQualificationDao.getSpecialQualificationCount(params);
		//
		//封装数据
		List<Object> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<Object> specialValue = new ArrayList<>();
		List<Object> dayName = new ArrayList<>();
		List<Object> dayValue = new ArrayList<>();
		Integer specialValue1 = 0;
		Integer dayValue1 = 0;
		for(CheckQualificationDTO str : day) {
			
			dayName.add(str.getName());
			dayValue.add(str.getCount());
			
			if(str.getCount() != 0) {
				dayValue1 = dayValue1 + str.getCount();
			}
			
		}
		for(CheckQualificationDTO str : special) {
			
			specialValue.add(str.getCount());
			if(str.getCount() != 0) {
				specialValue1 = specialValue1 + str.getCount();
			}
			
		}
		count = dayValue1 + specialValue1;
		map.put("SpecialValue", specialValue);
		map.put("name", dayName);
		map.put("DayValue", dayValue);
		list.add(map);
		return list;
	}

	@Override
	public List<Object> getQualificationItemCount(@Param("params") Map<String,Object> params) {
		getQualificationCount(params);
		List<CheckQualificationDTO> day = checkQualificationDao.getDayCount(params);
		List<CheckQualificationDTO> special = checkQualificationDao.getSpecialCount(params);
		//封装数据
		List<Object> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		Integer specialValue = 0;
		Integer dayValue = 0;
		Integer count1 = 0;
		String name = "";
		for(CheckQualificationDTO str : day) {
			if(str.getCount() != 0) {
				name = str.getName();
				dayValue = dayValue + str.getCount();
			}
		}
		for(CheckQualificationDTO str : special) {
			
			if(str.getCount() != 0) {
				specialValue = specialValue + str.getCount();
			}
			
		}
		count1 = specialValue + dayValue - count;
		
		map.put("Qualification",count);
		map.put("count", count1);
		map.put("name", name);
		list.add(map);
		return list;
	}
	
	
}
