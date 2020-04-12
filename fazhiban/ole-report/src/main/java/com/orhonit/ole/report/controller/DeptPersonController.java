package com.orhonit.ole.report.controller;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.dept.DeptPersonAvgDTO;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;


/**
 *  各部门人均执法量
 * @author Jwen
 * @time 2018-1-22
 */
@RestController
@RequestMapping("/deptperson")
@Slf4j
public class DeptPersonController {

	@Autowired
	private ReportDeptService reportDeptService;

	/**
	 * 统计各部门人均执法量
	 * @return
	 */
	@GetMapping("/deptpersonlawcount")
	public Object data(@RequestParam(value = "year", required = false, defaultValue = "") String year) {
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		
		Map<String, Object> result = new HashMap<>();
		String[] lenged = { "人均执法数量" };
		
		//查询所有部门列表
		List<DeptPersonAvgDTO> dept = reportDeptService.findDeptList(areaId);
		//查询各部门人员数
		List<DeptPersonAvgDTO> personCount = reportDeptService.findDeptPersonCount(areaId);
		//查询各部门执法案件量
		List<DeptPersonAvgDTO> caseCount = reportDeptService.findDeptCaseCount(year,areaId);
		
		
		//整合人员数量和案件数量到caseCOunt对象中
		for(int i = 0 ; i < caseCount.size() ; i ++) {
			String name = caseCount.get(i).getName();//案件部门名称
			for(int x = 0 ; x < personCount.size();x ++) {
				if(name.equals(personCount.get(x).getName())) {//人数部门和案件部门相同
					int p = Integer.parseInt(personCount.get(x).getValue());
					int c = Integer.parseInt(caseCount.get(i).getValue());
					if(p == 0 ) {
						caseCount.get(i).setValue("0");
					}else {
						DecimalFormat df=new DecimalFormat("0.00");
						String b = df.format((float)c/p);
						caseCount.get(i).setValue(b+"");
					}
				}
			}
			
			
		}
		for(int c = 0 ; c < caseCount.size();c++) {
			//将caseCount对象中的平均值放入到dept对象中
			for(int o = 0 ; o  < dept.size();o ++) {
				if(caseCount.get(c).getName().equals(dept.get(o).getName())) {
					dept.get(o).setValue(caseCount.get(c).getValue());
					break;
				}
			}
		}
		
		for(int i = 0 ; i<dept.size();i++) {
			if(dept.get(i).getValue()== null) {
				dept.get(i).setValue("0");
			}
		}
		
		Collections.sort(dept, new Comparator<DeptPersonAvgDTO>() {  
            public int compare(DeptPersonAvgDTO o1, DeptPersonAvgDTO o2) {  
            	if(o1 == null && o2 == null) {  
            	    return 0;  
            	}  
            	if(o1 == null) {  
            	    return -1;  
            	}  
            	if(o2 == null) {  
            	    return 1;  
            	}  
                if (Float.valueOf(o1.getValue())> Float.valueOf(o2.getValue())){  
                    return -1;  
                }  
                if (Float.valueOf(o1.getValue())< Float.valueOf(o2.getValue())){    
                    return 1;  
                }  
                return 0;  
            }
        });  
		
		result.put("lenged", lenged);
		result.put("dept",dept);
		return result;
	}
}
