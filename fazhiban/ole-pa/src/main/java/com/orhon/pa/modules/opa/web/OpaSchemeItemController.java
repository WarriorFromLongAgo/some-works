/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.opa.service.OpaSchemeItemService;
import com.orhon.pa.modules.opa.service.OpaSchemeService;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 方案指标模块Controller
 * @author Shawn
 * @version 2017-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaSchemeItem")
public class OpaSchemeItemController extends BaseController {

	@Autowired
	private OpaSchemeItemService opaSchemeItemService;
	
	@Autowired
	private OpaSchemeService opaSchemeService;
	
	@ModelAttribute
	public OpaSchemeItem get(@RequestParam(required=false) String id) {
		OpaSchemeItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaSchemeItemService.get(id);
		}
		if (entity == null){
			entity = new OpaSchemeItem();
		}
		return entity;
	}
	
	/**
	 * 指标分配模块  查询列表
	 */
	@RequestMapping(value = {"assign/index"})
	public String assignIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标分配中", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
		model.addAttribute("list", list);
	}
		return "modules/opa/opaSchemeItemAssignIndex";
	}
	@RequestMapping(value = {"list", ""})
	public String list(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaSchemeItem> page = opaSchemeItemService.findPage(new Page<OpaSchemeItem>(request, response), opaSchemeItem); 
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeItemList";
	}

	/**
	 * 方案指标明细表
	 */
	@RequestMapping(value = "assign/view")
	public String assignView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
		}
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemAssignView";
	}

	/**
	 * 指标分配 -- 打开指标分配对话框
	 */
	@RequestMapping(value = "assign/edit")
	public String assignEdit(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		String schemeId = request.getParameter("schemeId");
		opaSchemeItem.setSchemeId(schemeId);
		model.addAttribute("schemeId", schemeId);
		return "modules/opa/opaSchemeItemAssignEdit";
	}
	
	/**
	 * 方案指标分配完成
	 */
	@ResponseBody
	@RequestMapping(value = "assign/add")
	public String addAssign( HttpServletRequest request, HttpServletResponse response) {
		String msg = "ok";
		String ids = request.getParameter("ids");
		String auditorId = request.getParameter("auditorId");
		String auditorOfficeId = request.getParameter("officeId");
		Map<String,Object> param = new HashMap<String,Object>();
		String[] idss = ids.split(",");
		param.put("ids", idss);
		param.put("auditorId", auditorId);
		param.put("auditorOfficeId", auditorOfficeId);
		param.put("status", DictUtils.getDictValue("已分配", "opa_schemeItem_status", ""));
		param.put("updateBy", UserUtils.getUser().getId());
		param.put("updateDate", new Date());
		opaSchemeItemService.editAssign(param);
		return msg;
	}

	
	@ResponseBody
	@RequestMapping(value = "assign/remove")
	public String removeAssign( HttpServletRequest request, HttpServletResponse response) {
		String msg = "ok";
		String ids = request.getParameter("ids");
		Map<String,Object> param = new HashMap<String,Object>();
		String[] idss = ids.split(",");
		param.put("ids", idss);
		param.put("auditorId", "");
		param.put("auditorOfficeId", "");
		param.put("status", DictUtils.getDictValue("待分配", "opa_schemeItem_status", ""));
		param.put("updateBy", UserUtils.getUser().getId());
		param.put("updateDate", new Date());
		opaSchemeItemService.editAssign(param);
		return msg;
	}
	
	/**
	 * 指标分配 -- 上报指标分配   下发
	 */
	@RequestMapping(value = "assign/apply")
	public String assignApply(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		opaSchemeItem.setStatus(DictUtils.getDictValue("分配待审核", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		//opaSchemeItemService.assignAuditPass(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标分配成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	/**
	 * 指标分配审核模块 
	 */
	@RequestMapping(value = {"assign/audit/index"})
	public String assignAuditIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标分配中", "opa_scheme_status", ""));
//		statusList.add(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			opaSchemeItem.setAuditorId(UserUtils.getUser().getId());
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}	
		return "modules/opa/opaSchemeItemAssignAuditIndex";
	}
	
	/*
	 * 查看方案指标明细表
	 */
	@RequestMapping(value = "assign/audit/view")
	public String assignAuditView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
		}
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemAssignAuditView";
	}
	
    /**
     * 方案指标分配  审核通过 保存
     * 
     * @param opaSchemeItem
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
	@RequestMapping(value = "assign/audit/pass")
	public String assignAuditPass(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes) {
		opaSchemeItem.setStatus(DictUtils.getDictValue("待填报","opa_schemeItem_status",""));
		opaSchemeItemService.assignAuditPass(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标分配审核通过");
		opaSchemeItemService.save(opaSchemeItem);
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	
	/**
	 * 审核不通过 退回
	 * @param opaSchemeItem
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "assign/audit/return")
	public String assignAuditReturn(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		/*add lxy start*/
		String reason = request.getParameter("reason");
		String remark = opaSchemeItem.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		opaSchemeItem.setRemarks(allReason.toString());
		/*add lxy end*/
		opaSchemeItem.setStatus(DictUtils.getDictValue("分配已退回", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		addMessage(redirectAttributes, "指标分配已退回");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	
	/**
	 * lxy
	 * 指标填报审核模块 
	 */
	@RequestMapping(value = {"apply/audit/index"})
	public String applyAuditIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
//		statusList.add(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaSchemeItemApplyAuditIndex";
	}
	//lxy
	@RequestMapping(value = "apply/audit/view")
	public String applyAuditView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
		}
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyAuditView";
	}
	
	/**
	 * 填报审核通过阶段
	 * @param opaSchemeItem
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	//lxy
	@RequestMapping(value = "apply/audit/pass")
	public String applyAuditPass(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报已审核", "opa_schemeItem_status", ""));
		opaSchemeItemService.applyAuditPass2(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标填报审核通过");
		opaSchemeItemService.save(opaSchemeItem);
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	/**
	 * 填报审核失败 退回
	 * @param opaSchemeItem
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	//lxy
	@RequestMapping(value = "apply/audit/return")
	public String applyAuditReturn(OpaSchemeItem opaSchemeItem, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		/*add lxy start*/
		String reason = request.getParameter("reason");
		String remark = opaSchemeItem.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		opaSchemeItem.setRemarks(allReason.toString());
		/*add lxy end*/
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报已退回", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标填报已退回");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/audit/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	/**
	 * 填报退回 修改内容
	 * @param opaSchemeItem
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modilfy/view")
	public String applymodiflyView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		if(opaSchemeItem==null){
			opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		}
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		boolean ifParent = true;
		Double parentValue = 0D;
		Double childSum = 0D;
		Double restValue = 0D;
		Double thisValue = 0D;
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
			parentValue = parent.getValue();
			parentValue = parentValue==null?0:parentValue;
			childSum = opaSchemeItemService.getChildSum(parent);
			childSum = childSum==null?0:childSum;
			thisValue = opaSchemeItem.getValue();
			thisValue = thisValue==null?0:thisValue; 
			restValue = parentValue - childSum + thisValue;
			ifParent = false;
		}
		model.addAttribute("ifParent", ifParent);
		model.addAttribute("parentValue", parentValue);
		model.addAttribute("restValue", restValue);
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyModiflyForm";
	}
	
	/**
	 * 指标填报阶段
	 * @param opaSchemeItem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"apply/index"})
	public String applyIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {

		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaSchemeItemApplyIndex";
	}
	
	
	/**
	 * 提交      指标填报 
	 * @param opaSchemeItem
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "apply/apply")
	public String applyApply(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报待审核", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		opaSchemeItemService.applyAuditPass(opaSchemeItem);
		addMessage(redirectAttributes, "方案指标填报提交成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	/**
	 * 指标批量填报模块 
	 */
	@RequestMapping(value = {"apply/batch/index"})
	public String applyBatchIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("schemeId",request.getParameter("schemeId"));
		return "modules/opa/opaSchemeItemApplyBatchIndex";
	}
	
	@RequestMapping(value = "apply/batch/view")
	public String applyBatchView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(opaSchemeItem==null){
			opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		}
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		boolean ifParent = true;
		Double parentValue = 0D;
		Double childSum = 0D;
		Double restValue = 0D;
		Double thisValue = 0D;
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
			parentValue = parent.getValue();
			parentValue = parentValue==null?0:parentValue;
			childSum = opaSchemeItemService.getChildSum(parent);
			childSum = childSum==null?0:childSum;
			thisValue = opaSchemeItem.getValue();
			thisValue = thisValue==null?0:thisValue; 
			restValue = parentValue - childSum + thisValue;
			ifParent = false;
		}
		model.addAttribute("ifParent", ifParent);
		model.addAttribute("parentValue", parentValue);
		model.addAttribute("restValue", restValue);
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyBatchForm";
	}
	
	@RequestMapping(value = "apply/batch/save")
	public String applyBatchSave(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaSchemeItem)){
			return applyBatchView(opaSchemeItem, request, response, model);
		}
		opaSchemeItem.setStatus(DictUtils.getDictValue("已填报", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		addMessage(model, "保存方案指标成功");
		return applyBatchView(opaSchemeItem, request, response, model);
	}
	
	//指标填报明细查看
	@RequestMapping(value = "apply/view")
	public String applyView(OpaSchemeItem opaSchemeItem, HttpServletRequest request, Model model) {
		if(opaSchemeItem==null){
			opaSchemeItem = opaSchemeItemService.get(request.getParameter("id"));
		}
		OpaSchemeItem parent = new OpaSchemeItem();
		parent = opaSchemeItemService.getParentSchemeItem(opaSchemeItem);
		boolean ifParent = true;
		Double parentValue = 0D;
		Double childSum = 0D;
		Double restValue = 0D;
		Double thisValue = 0D;
		if(parent !=null){
			opaSchemeItem.setItemParentName(parent.getName());
			parentValue = parent.getValue();
			parentValue = parentValue==null?0:parentValue;
			childSum = opaSchemeItemService.getChildSum(parent);
			childSum = childSum==null?0:childSum;
			thisValue = opaSchemeItem.getValue();
			thisValue = thisValue==null?0:thisValue; 
			restValue = parentValue - childSum + thisValue;
			ifParent = false;
		}
		model.addAttribute("ifParent", ifParent);
		model.addAttribute("parentValue", parentValue);
		model.addAttribute("restValue", restValue);
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemApplyForm";
	}
	
	@RequestMapping(value = "apply/save")
	public String applySave(OpaSchemeItem opaSchemeItem, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaSchemeItem)){
			return applyView(opaSchemeItem,request, model);
		}
		opaSchemeItem.setStatus(DictUtils.getDictValue("已填报", "opa_schemeItem_status", ""));
		opaSchemeItemService.save(opaSchemeItem);
		addMessage(redirectAttributes, "保存方案指标成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/apply/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
	}
	
	//分配退回  
	@RequestMapping(value = "form")
	public String form(OpaSchemeItem opaSchemeItem, Model model) {
		model.addAttribute("opaSchemeItem", opaSchemeItem);
		return "modules/opa/opaSchemeItemModifyForm";
	}
	
	
	//指标分配退回 保存
		@RequestMapping(value = "save")
		public String save(OpaSchemeItem opaSchemeItem, Model model, RedirectAttributes redirectAttributes) {
			if (!beanValidator(model, opaSchemeItem)){
				return form(opaSchemeItem, model);
			}
			if (StringUtils.isBlank(opaSchemeItem.getId())){
				opaSchemeItem.setCode(IdGen.uuid());
			}
			OpaScheme schemeId = opaSchemeService.get(opaSchemeItem.getSchemeId());
			opaSchemeItem.setLevel(schemeId.getLevel());
			opaSchemeItem.setStatus(DictUtils.getDictValue("已分配", "opa_schemeItem_status",""));
			opaSchemeItemService.save(opaSchemeItem);
			addMessage(redirectAttributes, "指标分配修改成功");
			return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/assign/index?schemeId="+opaSchemeItem.getSchemeId()+"&repage";
		
		}
        //分配退回  删除
		/*@RequiresPermissions("pa:opaSchemeItem:assign:edit")
		@RequestMapping(value = "assigndelete")
		public String assigndelete(OpaSchemeItem opaSchemeItem, RedirectAttributes redirectAttributes) {
			opaSchemeItemService.delete(opaSchemeItem);
			addMessage(redirectAttributes, "删除指标分配成功");
			return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/?repage";
		}*/
	@RequestMapping(value = "delete")
	public String delete(OpaSchemeItem opaSchemeItem, RedirectAttributes redirectAttributes) {
		opaSchemeItemService.delete(opaSchemeItem);
		addMessage(redirectAttributes, "删除方案指标成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaSchemeItem/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData( HttpServletRequest request, String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		OpaSchemeItem osi = new OpaSchemeItem();
		osi.setSchemeId(request.getParameter("schemeId"));
		List<OpaSchemeItem> list = opaSchemeItemService.findList(osi);
		for (int i=0; i<list.size(); i++){
			OpaSchemeItem e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getItemParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getItemId());
				map.put("pId", e.getItemParentId());
				map.put("name", e.getName());
				map.put("schemeItemId", e.getId());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "TreeDataForAssign")
	public List<Map<String, Object>> TreeDataForAssign( HttpServletRequest request, String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		OpaSchemeItem osi = new OpaSchemeItem();
		osi.setSchemeId(request.getParameter("schemeId"));
		osi.setAuditorId(request.getParameter("auditorId"));
		osi.setStatus(request.getParameter("status"));
		List<OpaSchemeItem> list = opaSchemeItemService.findListForAssign(osi);
		for (int i=0; i<list.size(); i++){
			OpaSchemeItem e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getItemParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getItemId());
				map.put("pId", e.getItemParentId());
				map.put("name", e.getName());
				map.put("schemeItemId", e.getId());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * lxy
	 */
	@RequestMapping(value = {"monitor/index"})
	public String monitorIndex(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		
//		获取已审核、已发布方案列表
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		String str = "已发布";
		String str1 = "指标填报中";
		String str2 = "指标分配中";
		statusList.add(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> schemeAuditedList = opaSchemeService.findListByStatus(param);
		model.addAttribute("schemeAuditedList", schemeAuditedList);
		if(StringUtils.isNoneBlank(opaSchemeItem.getSchemeId())){
			List<OpaSchemeItem> list = opaSchemeItemService.findList(opaSchemeItem);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaSchemeItemMonitorIndex";
	}
	/* add by lxy start */
	@RequestMapping(value = {"monitor/list"})
	public String monitorList(OpaSchemeItem opaSchemeItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaSchemeItem> page = opaSchemeItemService.findPage(new Page<OpaSchemeItem>(request, response), opaSchemeItem); 
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeItemMonitorList";
	}
	/* add by lxy end */
}