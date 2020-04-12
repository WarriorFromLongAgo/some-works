package com.orhonit.ole.report.service.lawp.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.law.ReportLawDao;
import com.orhonit.ole.report.dto.check.ReportLawDTO;
import com.orhonit.ole.report.service.lawp.ReportLawService;

@Service
public class ReportLawServiceImpl implements ReportLawService {
	
	@Autowired
	private ReportLawDao reportLawDao;

	@Override
	public List<Object> getLawItemCount() {
		//获取数据
		List<ReportLawDTO> count = reportLawDao.getCount();//法律总数
		List<ReportLawDTO> itemCount = reportLawDao.getLawItemCount();//各类型法律的数量
		//封装数据
		List<Object> data = new ArrayList<>();
		List<Object> itemData = new ArrayList<>();
		for(ReportLawDTO str : count) {
			Map<String, Object> map = new HashMap<>();
			
			map.put("count", str.getCount());
			data.add(map);
		}
		for(ReportLawDTO str : itemCount) {
			Map<String,Object> map = new HashMap<>();
			map.put("itemCount", str.getCount());
			map.put("enumDesc", str.getEnumDesc());
			data.add(map);
		}
		
		return data;
	}

}
