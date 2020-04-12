package com.orhonit.ole.report.service.area.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportAreaDao;
import com.orhonit.ole.report.dao.area.ReportAreaCaseDao;
import com.orhonit.ole.report.dao.cases.ReportAreaAdminCaseDao;
import com.orhonit.ole.report.dao.dept.ReportDeptPersonDao;
import com.orhonit.ole.report.dto.AreaDTO;
import com.orhonit.ole.report.dto.area.ReportAreaDTO;
import com.orhonit.ole.report.dto.cases.AreaCaseCountDTO;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonAvgDTO;
import com.orhonit.ole.report.service.area.ReportAreaService;

import lombok.extern.slf4j.Slf4j;


/**
 * author:田俊文
 * time:2018-1-13 
 * desc:区域地图服务类
 */
@Service
@Slf4j
public class ReportAreaServiceImpl implements ReportAreaService {
	
	@Autowired
	private ReportAreaDao reportAreaDao;
	
	@Autowired
	private ReportAreaCaseDao reportAreaCaseDao;
	
	@Autowired
	private ReportDeptPersonDao reportDeptPersonDao;

	@Autowired
	private ReportAreaAdminCaseDao reportAreaAdminCaseDao;
	@Override
	public List<AreaDTO> findArea() {
		return reportAreaDao.findArea();
	}
	
	@Override
	public List<AreaDTO> findAreaSize() {
		return reportAreaDao.findAreaSize();
	}

	@Override
	public List<ReportAreaDTO> findAreaCaseCount(Map<String, Object> params) {
		return reportAreaCaseDao.findAreaCaseCount(params);
	}

	@Override
	public List<ReportAreaDTO> findAreaLowPersonCount(Map<String, Object> params) {
		return reportAreaCaseDao.findAreaLowPersonCount(params);
	}

	@Override
	public List<ReportAreaDTO> findReconsiderationCount(Map<String, Object> params) {
		return reportAreaCaseDao.findReconsiderationCount(params);
	}

	@Override
	public List<AreaCaseCountDTO> findAreaAndCaseCount(String year) {
		//查询区域列表
		List<AreaCaseCountDTO> areaList = reportAreaAdminCaseDao.findAreaList();
		//查询区域参数
		List<AreaCaseCountDTO> case1 = reportAreaAdminCaseDao.findAreaAndCase(year);
		
		for(AreaCaseCountDTO d:areaList) {
			for(AreaCaseCountDTO c:case1) {
				if(d.getName().equals(c.getName())) {
					d.setValue(c.getValue());
					break;
				}else {
					d.setValue("0");
				}
			}
		}
		return areaList;
	}

	@Override
	public List<DeptPersonAvgDTO> findAVGPersonCaseCount(String year,String areaId) {
		//查询部门列表
		List<DeptPersonAvgDTO> dept = reportDeptPersonDao.findDeptList(areaId);
		//查询部门人员数量
		List<DeptPersonAvgDTO> personCount = reportDeptPersonDao.findDeptPersonCount(areaId);
		//查询部门处理案件数
		List<DeptPersonAvgDTO> caseCount = reportDeptPersonDao.findDeptCaseCount(year,areaId);
		//求平均数和构造返回对象
		for(int i = 0 ; i < caseCount.size() ; i ++) {
			String name = caseCount.get(i).getName();
			for(int x = 0 ; x < personCount.size();x ++) {
				if(name == personCount.get(x).getName()) {
					int p = Integer.parseInt(personCount.get(i).getValue());
					int c = Integer.parseInt(caseCount.get(i).getValue());
					if(p == 0 ) {
						caseCount.get(i).setValue("0");
					}else {
						int b = c/p;
						System.out.println(b);
						caseCount.get(i).setValue(b+"");
					}
				}
			}
			//设置对象的默认值
			for(int o = 0 ; o  < dept.size();o ++) {
				if(name == dept.get(i).getName()) {
					dept.get(i).setValue(caseCount.get(i).getValue());
				}else {
					dept.get(i).setValue("0");
				}
			}
		}
		return dept;
	}

	@Override
	public String[][] findCaseCountByAreaName( String year) {
		//查询区域列表
		List<AreaCaseCountDTO> areaList = reportAreaAdminCaseDao.findAreaList();
		String[][] data = new String[10][12];
		for(int i = 0 ; i < 10; i ++) {
			for(int n = 0; n < 12 ; n ++) {
				data[i][n]="0";
			}
		}
		for(int item = 0 ; item < areaList.size() ; item ++) {
			List<BaseChartDTO> yearData= reportAreaCaseDao.findCaseCountByAreaName(areaList.get(item).getName(), year);
			for(int x = 0 ; x < yearData.size() ; x ++) {
				if(yearData.get(x).getValue() != null) {
					data[item][x] = yearData.get(x).getValue();
				}
				
			}
		}
		return data;
	}



	

}
