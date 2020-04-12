/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orhon.pa.common.persistence.BaseEntity;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.service.OpaItemService;
import com.orhon.pa.modules.opa.service.OpaPlanService;
import com.orhon.pa.modules.opa.service.OpaSummaryService;
import com.orhon.pa.modules.sys.utils.DictUtils;

/**
 * 汇总模块Controller
 * @author Shawn
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaSummary")
public class OpaSummaryController extends BaseController {

	@Autowired
	private OpaItemService opaItemService;
	@Autowired
	private OpaPlanService opaPlanService;
	@Autowired
	private OpaSummaryService opaSummaryService;
	
	@ModelAttribute
	public OpaItem get(@RequestParam(required=false) String id) {
		OpaItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaItemService.get(id);
		}
		if (entity == null){
			entity = new OpaItem();
		}
		return entity;
	}
	
	@RequestMapping(value = {"all/index"})
	public String allIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("已完成", "opa_plan_status", ""));
		//statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", BaseEntity.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		Map<String, Object> headAndData= new HashMap<String, Object>();
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			headAndData = opaSummaryService.getHeadAndDataMap(opaPlanTask.getPlanId());
			model.addAttribute("headAndData", headAndData);
		}
		return "modules/opa/opaSummaryAllIndex";
	}
	
	@RequestMapping(value = {"office/index"})
	public String officeIndex(OpaItem opaItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpaItem> list = opaItemService.findList(opaItem); 
		model.addAttribute("list", list);
		return "modules/opa/opaAllSummary";
	}
}