package com.orhonit.ole.enforce.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.utils.ExcelUtil;
import com.orhonit.ole.enforce.dao.CaseDao;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件导出控制器
 * @author wuyz
 *
 */
@RestController
@RequestMapping("/outputFile")
@Slf4j
public class OutputFileController {
	
	@Autowired
	CaseDao caseDao;
	
	@Autowired
	CaseService caseService;
	/**
	 * excel文件导出
	 * @param request
	 * @return
	 */
	@GetMapping(value="/excel")
	public void listCase(HttpServletResponse response,TableRequest request,@RequestParam String id,@RequestParam String caseName,@RequestParam String zzfryName,@RequestParam String caseTime,@RequestParam String type) {
		//设置查询条件
		request.getParams().put("typeValue", "1002");
		request.getParams().put("id", id);
		request.getParams().put("caseName", caseName);
		request.getParams().put("zzfryName", zzfryName);
		request.getParams().put("caseTime", caseTime);
		
		//创建表头
		String[] headers=new String[]{"案件名称","案件编号","案源","案件状态","案发地址","案发时间","收件时间","指派时间","收件人","主执法人员"};
		
		//查询列表
		List<CaseListDTO> list = null;
		if(type.equals("query")){
			list = caseService.getQueryCaseList(request.getParams(), null, null);
		}else if(type.equals("list")){
			request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
			list = caseService.getCaseList(request.getParams(), null, null);
		}else{
			return;
		}
		
		//用于保存列表数据
		List<Object[]> caseDTOLists = new ArrayList<Object[]>();
		
		//格式化日期字符串
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (CaseListDTO caseListDTO : list) {
			Object[] caseDTOs = new Object[10];
			caseDTOs[0]=caseListDTO.getCaseName();
			caseDTOs[1]=caseListDTO.getCaseNum();
			caseDTOs[2]=caseListDTO.getCaseSource();
			caseDTOs[3]=caseListDTO.getCaseStatus();
			caseDTOs[4]=caseListDTO.getCaseAddress();
			caseDTOs[5]=formatter.format(caseListDTO.getCaseTime());
			caseDTOs[6]=formatter.format(caseListDTO.getCaseApplyDate());
			caseDTOs[7]=formatter.format(caseListDTO.getCaseZpDate());
			caseDTOs[8]=caseListDTO.getCaseHandler();
			caseDTOs[9]=caseListDTO.getZzfryName();
			caseDTOLists.add(caseDTOs);
		}
		ExcelUtil.excelExport("案件受理列表", headers, caseDTOLists, response);
	}
}
