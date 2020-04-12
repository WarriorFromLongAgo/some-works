/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.service.OpaItemService;
import com.orhon.pa.modules.opa.service.OpaSchemeService;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 考核方案管理模块Controller
 * @author Shawn
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaScheme")
public class OpaSchemeController extends BaseController {

	@Autowired
	private OpaSchemeService opaSchemeService;
	
	@Autowired
	private OpaItemService opaItemService;
	
	@ModelAttribute
	public OpaScheme get(@RequestParam(required=false) String id) {
		OpaScheme entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opaSchemeService.get(id);
		}
		if (entity == null){
			entity = new OpaScheme();
		}
		return entity;
	}
	

	@RequiresPermissions("pa:opaScheme:index")
	@RequestMapping(value= {"index"})
	public String index(OpaScheme opaScheme , HttpServletRequest request,HttpServletResponse response,Model model) {
		Page<OpaScheme>page = opaSchemeService.findPage(new Page<OpaScheme>(request,response),opaScheme);
		model.addAttribute("page",page);
		return"modules/opa/opaSchemeIndex";
	}

	@RequestMapping(value= {"list"})
	public String list(OpaScheme opaScheme,HttpServletRequest request,HttpServletResponse response,Model model) {
		Page<OpaScheme> page = opaSchemeService.findPage(new Page<OpaScheme>(request,response),opaScheme);
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeList";
	}

	@RequestMapping(value = "form")
	public String form(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeForm";
	}
	
	//考核方案查看
	@RequestMapping(value = "view")
	public String view(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeView";
	}
   
	//方案保存
	@RequestMapping(value = "save")
	public String save(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaScheme)){
			return form(opaScheme, model);
		}
		if (StringUtils.isBlank(opaScheme.getId())){
			opaScheme.setCode(IdGen.uuid());
		}
		OpaItem itemParent = opaItemService.get(opaScheme.getItemParent());
		opaScheme.setLevel(itemParent.getLevel());
		opaScheme.setStatus(DictUtils.getDictValue("制定中", "opa_scheme_status",""));
		opaSchemeService.save(opaScheme);
		addMessage(redirectAttributes, "考核方案保存成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
	}
	
	@RequiresPermissions("pa:opaScheme:edit")
	@RequestMapping(value = "delete")
	public String delete(OpaScheme opaScheme, RedirectAttributes redirectAttributes) {
		opaSchemeService.delete(opaScheme);
		addMessage(redirectAttributes, "删除考核方案成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
	}
	
	//上报提交考核方案
	@RequestMapping(value = "apply")
	public String apply(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opaScheme)){
			return form(opaScheme, model);
		}
		if (StringUtils.isBlank(opaScheme.getId())){
			opaScheme.setCode(IdGen.uuid());
		}
		OpaItem itemParent = opaItemService.get(opaScheme.getItemParent());
		opaScheme.setLevel(itemParent.getLevel());
		opaScheme.setStatus(DictUtils.getDictValue("待审核", "opa_scheme_status",""));
		opaSchemeService.auditPass(opaScheme);
		opaSchemeService.save(opaScheme);
		addMessage(redirectAttributes, "考核方案提交成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
		//return "modules/opa/opaSchemeAuditList";
	}
	
	//提交到审核
	@RequestMapping(value = {"audit/index"})
	public String auditIndex(OpaScheme opaScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
//	if (!beanValidator(model, opaScheme)){
//			return form(opaScheme, model);
//		}
		if (StringUtils.isBlank(opaScheme.getId())){
			opaScheme.setCode(IdGen.uuid());
		}
		opaScheme.setAuditor(UserUtils.getUser());
		opaScheme.setStatus(DictUtils.getDictValue("待审核", "opa_scheme_status", ""));
		Page<OpaScheme> page = opaSchemeService.findPage(new Page<OpaScheme>(request, response), opaScheme);
		model.addAttribute("page", page);
		//opaSchemeService.save(opaScheme);
		//addMessage(redirectAttributes,"考核方案提交成功");
		return "modules/opa/opaSchemeAuditList";
		//return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/index?repage";
	}
	
	//考核方案审核阶段
	@RequiresPermissions("pa:opaScheme:audit:view")
	@RequestMapping(value = "audit/view")
	public String auditView(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeAuditView";
	}
	//方案审核阶段
	@RequestMapping(value = "audit/pass")
	public String auditPass(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes) {
		opaScheme.setStatus(DictUtils.getDictValue("指标分配中", "opa_scheme_status",""));
		opaSchemeService.auditPass1(opaScheme);
		addMessage(redirectAttributes, "考核方案审核成功");
	//	opaSchemeService.save(opaScheme);
		//重定向，跳转到指定的链接
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/audit/index/?repage";
	}
	
	//审核退回
	@RequestMapping(value = "audit/return")
	public String auditReturn(OpaScheme opaScheme, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
		//add lxy start
		String reason = request.getParameter("reason");
		String remark = opaScheme.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		opaScheme.setRemarks(allReason.toString());
		//add lxy end
		opaScheme.setStatus(DictUtils.getDictValue("已退回", "opa_scheme_status",""));
		opaSchemeService.save(opaScheme);
		addMessage(redirectAttributes, "考核方案退回成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaScheme/audit/index/?repage";
	}
	// add by lhb start
	@RequestMapping(value = "monitor/index")
	public String monitorIndex(OpaScheme opaScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaScheme> page = opaSchemeService.findPage(new Page<OpaScheme>(request, response), opaScheme); 
		model.addAttribute("page", page);
		return "modules/opa/opaSchemeMonitorIndex";
	}
	// add by lhb end
	
	// add by lhb start
	@RequestMapping(value = "monitor/form")
	public String montiorForm(OpaScheme opaScheme, Model model) {
		model.addAttribute("opaScheme", opaScheme);
		return "modules/opa/opaSchemeMonitorForm";
	}
	// add by lhb end

}