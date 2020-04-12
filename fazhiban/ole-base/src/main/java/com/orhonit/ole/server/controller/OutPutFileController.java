package com.orhonit.ole.server.controller;

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
import com.orhonit.ole.server.dao.LarDao;
import com.orhonit.ole.server.dao.LepDao;
import com.orhonit.ole.server.dao.LrlAttDao;
import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.LarAtt;
import com.orhonit.ole.server.model.Lepeson;
import com.orhonit.ole.server.model.LrlAtt;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.model.LtcConnAtt;

@RestController
@RequestMapping("/outputExcelFile")
public class OutPutFileController {
	
	
	@Autowired
	private LepDao lepDao;
	
	@Autowired
	private LtcAttDao ltcAttDao;
	
	@Autowired
	private LarDao larDao;
	
	@Autowired
	private LrlAttDao lrlAttDao;
	
	
	@GetMapping("/excel")
	public void excel(HttpServletResponse response,TableRequest request,
						@RequestParam(value="name",required=false) String name,@RequestParam(value="cardNum",required=false) String cardNum,
						@RequestParam(value="jobType",required=false) String jobType,@RequestParam(value="type",required=false) String type,
						@RequestParam(value="lawType",required=false) String lawType,@RequestParam(value="beginTime",required=false) String beginTime,
						@RequestParam(value="endTime",required=false) String endTime,@RequestParam(value="code",required=false) String code,
						@RequestParam(value="deptName",required=false) String deptName
	){
		
		//接受数据
		List<Lepeson> personList = null;
		List<LtcAtt> deptList = null;
		List<LarAtt> rightList = null;
		List<LrlAtt> lawList = null;
		//存储数据
		List<Object[]> fileList = new ArrayList<Object[]>();
		fileList.clear();
		
		//格式化日期字符串
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String[] headers=new String[5];
		if(type.equals("person")) {
			//获取搜索条件
			request.getParams().put("name", name);
			request.getParams().put("card_num", cardNum);
			request.getParams().put("job_type", jobType);
			request.getParams().put("dept_name", deptName);
			
			//根据不同状态创建表头
			headers = new String[]{"人员姓名","身份证号","执法主体","职务类型","执法证号"};
			//获取数据
			personList = lepDao.list(request.getParams(), null, null);
			
			for(Lepeson perList : personList) {
				Object[] fileDTO = new Object[5];
				fileDTO[0]=perList.getName();
				fileDTO[1]=perList.getCard_num();
				fileDTO[2]=perList.getDept_id();
				fileDTO[3]=perList.getDuty();
				fileDTO[4]=perList.getCert_num();
				fileList.add(fileDTO);
			}
			ExcelUtil.excelExport("执法人员管理表", headers, fileList, response);
			
			
		}else if(type.equals("dept")) {
			request.getParams().put("name", name);
			request.getParams().put("lawtype", lawType);
			request.getParams().put("beginTime", beginTime);
			request.getParams().put("endTime", endTime);
			//获取数据
			deptList = ltcAttDao.list(request.getParams(), null, null);
			
			headers = new String[]{"主体名称","法定代表人","执法性质","主体地址","创建时间"};
			//遍历数据
			for(LtcAtt depList : deptList) {
				Object[] fileDTO = new Object[5];
				fileDTO[0]=depList.getName();
				fileDTO[1]=depList.getLegal_person();
				fileDTO[2]=depList.getLaw_type();
				fileDTO[3]=depList.getAddress();
				if(depList.getCreate_date() != null) {
					fileDTO[4]=formatter.format(depList.getCreate_date());
				}else {
					fileDTO[4]="";
				}
				
				fileList.add(fileDTO);
			}
			ExcelUtil.excelExport("执法主体管理表", headers, fileList, response);
		}else if(type.equals("right")) {
			request.getParams().put("name", name);
			request.getParams().put("code", code);
			request.getParams().put("beginTime", beginTime);
			request.getParams().put("endTime", endTime);
			//获取数据
			rightList = larDao.list(request.getParams(), null, null);
			
			headers = new String[]{"权责代码","权责名称","责任事项","创建人","创建日期"};
			//遍历数据
			for(LarAtt righList : rightList) {
				Object[] fileDTO = new Object[5];
				fileDTO[0]=righList.getCode();
				fileDTO[1]=righList.getName();
				fileDTO[2]=righList.getDuty();
				fileDTO[3]=righList.getCreate_name();
				if(righList.getCreate_date() != null) {
					fileDTO[4]=formatter.format(righList.getCreate_date());
				}else {
					fileDTO[4]="";
				}
				
				fileList.add(fileDTO);
			}
			ExcelUtil.excelExport("权责清单管理表", headers, fileList, response);
		}else if(type.equals("Law")) {
			
			request.getParams().put("name", name);
			request.getParams().put("code", code);
			request.getParams().put("beginTime", beginTime);
			request.getParams().put("endTime", endTime);
			//获取数据
			lawList = lrlAttDao.list(request.getParams(), null, null);
			
			headers = new String[]{"编码","名称","效力级别","发布部门","发布日期"};
			//遍历数据
			for(LrlAtt laList : lawList) {
				Object[] fileDTO = new Object[5];
				fileDTO[0]=laList.getCode();
				fileDTO[1]=laList.getName();
				fileDTO[2]=laList.getEffect_level();
				fileDTO[3]=laList.getPub_dept();
				if(laList.getPublish_date() != null) {
					fileDTO[4]=formatter.format(laList.getPublish_date());
				}else {
					fileDTO[4]="";
				}
				
				fileList.add(fileDTO);
			}
			ExcelUtil.excelExport("法律法规管理表", headers, fileList, response);
		}else if(type.equals("potence")) {
			
			request.getParams().put("id", lawType);
			//获取数据
			List<LtcConnAtt> deptPotences= ltcAttDao.getPotencesByDeptId(request.getParams());
			headers = new String[]{"主体名称","权责名称","承办机构","关联时间"};
			//遍历数据
			for(LtcConnAtt deptPotence : deptPotences) {
				Object[] fileDTO = new Object[5];
				fileDTO[0]=deptPotence.getDept_id_name();
				fileDTO[1]=deptPotence.getPotence_name();
				fileDTO[2]=deptPotence.getDept_id_agent_name();
				if(deptPotence.getCreate_date() != null) {
					fileDTO[3]=formatter.format(deptPotence.getCreate_date());
				}else {
					fileDTO[3]="";
				}
				fileList.add(fileDTO);
			}
			ExcelUtil.excelExport("主体权责列表", headers, fileList, response);
		}
		
		
		

	}
}
