package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dao.dept.ReportDeptPersonDao;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonAvgDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonDistDTO;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 执法人员分布图
 * @author Jwen
 * @time 2018-1-23
 */
@RestController
@RequestMapping("LawPersonDist")
public class LawPersonDistController {
	
	@Autowired
	private ReportDeptService reportDeptService;

	@GetMapping("bar")
	public Object Bar() {
		Map<String, Object> result = new HashMap<>();
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		List<String> name = new ArrayList<>();
		List<String> value1 = new ArrayList<>();
		List<String> value2 = new ArrayList<>();
		List<DeptPersonAvgDTO> dept = reportDeptService.findDeptList(areaId);
		
		
		String lawType = "1";
		List<DeptPersonDistDTO> list = reportDeptService.findDeptPersonByLawType(lawType);
		for(int i  = 0 ; i < dept.size(); i ++) {
			String deptName = dept.get(i).getName();
			dept.get(i).setValue("0");
			for(int n = 0 ; n < list.size(); n ++) {
				if(deptName.equals(list.get(n).getName())) {
					String value = list.get(n).getValue();
					dept.get(i).setValue(value);
				}
			}
		}
		
		for(int i  = 0 ; i < dept.size(); i ++) {
			name.add(dept.get(i).getName());
			value1.add(dept.get(i).getValue());
		}
		
		
		lawType = "2";
		List<DeptPersonDistDTO> list2 = reportDeptService.findDeptPersonByLawType(lawType);
		for(int i  = 0 ; i < dept.size(); i ++) {
			String deptName2 = dept.get(i).getName();
			dept.get(i).setValue("0");
			for(int n = 0 ; n < list2.size(); n ++) {
				if(deptName2.equals(list2.get(n).getName())) {
					
					String value = list2.get(n).getValue();
					dept.get(i).setValue(value);
				}
			}
		}
		
		for(int i  = 0 ; i < dept.size(); i ++) {
			value2.add(dept.get(i).getValue());
		}
		
		List<BaseChartDTO> personLawType = reportDeptService.findPersonLawType();
		
		
		result.put("name", name);
		result.put("value1",value1);
		result.put("value2",value2);
		result.put("personLawType", personLawType);
		
		return result;
	}
}
