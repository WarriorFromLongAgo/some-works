package com.orhonit.ole.report.service.dept.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportDeptDao;
import com.orhonit.ole.report.dao.cases.ReportCaseDeptDao;
import com.orhonit.ole.report.dao.dept.ReportDeptPersonDao;
import com.orhonit.ole.report.dto.DeptDTO;
import com.orhonit.ole.report.dto.cases.DeptCaseCountDTO;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonAvgDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonDistDTO;
import com.orhonit.ole.report.dto.ps.AreaDeptDTO;
import com.orhonit.ole.report.service.dept.ReportDeptService;

@Service
public class ReportDeptServiceimpl implements ReportDeptService {
	
	@Autowired
	private ReportDeptDao deptDao;
	
	@Autowired
	private ReportCaseDeptDao ReportCaseDeptDao;
	
	@Autowired
	private ReportDeptPersonDao reportDeptPersonDao;

	@Override
	public List<AreaDeptDTO> areaDeptList() {
		return deptDao.areaDeptList();
	
	}

	@Override
	public List<AreaDeptDTO> areaDeptProList(Map<String, Object> params) {
		return deptDao.areaDeptProList(params);
	}

	@Override
	public List<DeptCaseCountDTO> DeptCaseCount(String year,String areaId) {
		//查询所有部门的列表
		List<DeptCaseCountDTO> dept = ReportCaseDeptDao.findAllDept(areaId);
		//查询各部门的案件数量
		List<DeptCaseCountDTO> case1 = ReportCaseDeptDao.findDeptAndCase(year,areaId);
		//将部门名称和数量做整合
		for(DeptCaseCountDTO d:dept) {
			for(DeptCaseCountDTO c:case1) {
				if(d.getName().equals(c.getName())) {
					d.setValue(c.getValue());
					break;
				}else {
					d.setValue("0");
				}
			}
		}
		Collections.sort(dept, new Comparator<DeptCaseCountDTO>() {  
            public int compare(DeptCaseCountDTO o1, DeptCaseCountDTO o2) {  
                // 按照学生的年龄进行降序排列  
                if (Integer.valueOf(o1.getValue())> Integer.valueOf(o2.getValue())){  
                    return -1;  
                }  
                if (Integer.valueOf(o1.getValue())== Integer.valueOf(o2.getValue())){    
                    return 0;  
                }  
                return 1;  
            }  
        });  
		return dept;
	}

	@Override
	public List<DeptPersonAvgDTO> findDeptList(String areaId) {
		return reportDeptPersonDao.findDeptList(areaId);
	}

	@Override
	public List<DeptPersonAvgDTO> findDeptPersonCount(String areaId) {
		return reportDeptPersonDao.findDeptPersonCount(areaId);
	}

	@Override
	public List<DeptPersonAvgDTO> findDeptCaseCount(String year,String areaId) {
		return reportDeptPersonDao.findDeptCaseCount(year,areaId);
	}

	@Override
	public List<DeptPersonDistDTO> findDeptPersonByLawType(String lawType) {
		return reportDeptPersonDao.findDeptPersonByLawType(lawType);
	}

	@Override
	public List<BaseChartDTO> findPersonLawType() {
		return reportDeptPersonDao.findPersonLawType();
	}

	@Override
	public DeptDTO findDeptById(String id) {
		return deptDao.getDeptById(id);
	}

	@Override
	public String getDeptAndChildById(String id) {
		return deptDao.getDeptAndChildById(id);
	}

	@Override
	public List<Map<String, Object>> getDeptHaveCase(Map<String, Object> params) {
		return deptDao.getDeptHaveCase(params);
	}



}
