package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.DeptDTO;
import com.orhonit.ole.report.dto.cases.ReCaseCountDTO;
import com.orhonit.ole.report.dto.cases.ReCaseNumCountDTO;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.report.service.recase.RecaseService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 各部门复议案件数统计
 * @author 田俊文
 *
 */
@RestController
@RequestMapping("/recase")
@Slf4j
public class RecaseController {

	@Autowired
	private RecaseService recaseService;

	@Autowired
	private ReportDeptService reportDeptService;

	/**
	 * 各部门复议案件数量
	 * bmfy.html
	 * @return 各部门名称及复议案件数
	 */
	@GetMapping("bmfy")
	public Object bmfy(){
		User user = UserUtil.getCurrentUser();
		String areaId = user.getArea_id();
		if(areaId.equals("15")) {
			areaId = "";
		}
		List<ReCaseCountDTO> reCaseWithDept = recaseService.findReCaseWithDept(areaId);
		return reCaseWithDept;
	}

	@GetMapping("fyaj")
	public Object fyaj(){
		//获取当前年份
		Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        
		Map<String,Object> result = new HashMap<>();
		String[] month = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
        String[] legend = {"申请复议","复议受理","复议结案"};
        //	42 申请复议
        List<ReCaseNumCountDTO> sq = recaseService.findReCaseNumByYearAndStatus("42", year);

        //	44 复议受理
        List<ReCaseNumCountDTO> sl = recaseService.findReCaseNumByYearAndStatus("44", year);

        //	50 复议结案
        List<ReCaseNumCountDTO> ja = recaseService.findReCaseNumByYearAndStatus("50", year);


        String[] v1 = new String[12];
        String[] v2 = new String[12];
        String[] v3 = new String[12];

        for(int i = 1 ; i < 13 ; i ++){
            String m = i+"";
            for(int a = 0 ; a < sq.size(); a ++){
                if(m.equals(sq.get(a).getName())){
                    String v = sq.get(a).getValue();
                    v1[i-1] = v;
                    break;
                }else {
                	v1[i-1] = "0";
                }
            }
            for(int b = 0 ; b < sl.size(); b ++){
                if(m.equals(sl.get(b).getName())){
                    String v = sl.get(b).getValue();
                    v2[i-1] = v;
                    break;
                }else {
                	v2[i-1] = "0";
                }
            }
            for(int c = 0 ; c < ja.size(); c ++){
                if(m.equals(ja.get(c).getName())){
                    String v = ja.get(c).getValue();
                    v3[i-1] = v;
                    break;
                }else {
                	 v3[i-1] = "0";
                }
            }
        }
        result.put("v1",v1);
        result.put("v2",v2);
        result.put("v3",v3);
		result.put("monthArr",month);
		result.put("leg",legend);

        List<ReCaseNumCountDTO> person = recaseService.findApplyPerson(year);
        result.put("person",person);

        List<ReCaseNumCountDTO> accCase = recaseService.findAccCase(year);
        result.put("acccase",accCase);

        List<ReCaseNumCountDTO> allAccCase = recaseService.findAllAccCase(year);
        result.put("allacccase",allAccCase);
        return  result;
	}
	
	@GetMapping("/yearCount")
	public Object deptCaseCount() {
		User user = UserUtil.getCurrentUser();
		DeptDTO deptDTO = null;
		Map<String, Object> params = new HashMap<String,Object>();
		if(!user.getUsername().equals("admin")){
			deptDTO = reportDeptService.findDeptById(user.getDept_id());
		}
		if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}else if(deptDTO != null && deptDTO.getDept_property() == 3 && deptDTO.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
				params.put("deptId", user.getDept_id());
			}
		}else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", reportDeptService.getDeptAndChildById(user.getDept_id()));
		}
		//查找复议案件
		params.put("is_fy", "is_fy");
		List<Map<String, Object>> depts = reportDeptService.getDeptHaveCase(params);
		//年份从2014至今年
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> temp = new HashMap<>();
		//暂存整理结果
		for (Map<String, Object> action : depts) {
			if(action.containsKey("deptName") && !temp.containsKey(action.get("deptName").toString())){
				List<Integer> yearArr =new ArrayList<>();
				for(int i = 2014 ;i <= year; i++){
					yearArr.add(0);
				}
				if(Integer.parseInt(action.get("year").toString())>=2014 && Integer.parseInt(action.get("year").toString())<=year){
					yearArr.set(Integer.parseInt(action.get("year").toString())-2014, Integer.parseInt(action.get("count").toString()));
					temp.put(action.get("deptName").toString(), yearArr);
				}
			}else if(action.containsKey("deptName")){
				if(Integer.parseInt(action.get("year").toString())>=2014 && Integer.parseInt(action.get("year").toString())<=year){
					@SuppressWarnings("unchecked")
					List<Integer> yearArr = (ArrayList<Integer>)temp.get(action.get("deptName").toString());
					yearArr.set(Integer.parseInt(action.get("year").toString())-2014, Integer.parseInt(action.get("count").toString()));
				}
			}
		}
		List<Object> res = new ArrayList<>();
		temp.forEach((k,v)->{
			Map<String, Object> ob = new HashMap<>();
			ob.put("name", k);
			ob.put("value", v);
			res.add(ob);
		});
		return res;
	}

}
