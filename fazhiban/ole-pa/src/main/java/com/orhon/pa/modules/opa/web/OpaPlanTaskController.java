/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.orhon.pa.common.config.Global;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.utils.DateUtils;
import com.orhon.pa.common.utils.FileUtils;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.common.utils.StringUtils;
import com.orhon.pa.common.web.BaseController;
import com.orhon.pa.modules.opa.dao.OpaTaskAppealDao;
import com.orhon.pa.modules.opa.entity.OpaPlan;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.opa.entity.OpaTaskAppeal;
import com.orhon.pa.modules.opa.service.OpaPlanService;
import com.orhon.pa.modules.opa.service.OpaPlanTaskService;
import com.orhon.pa.modules.opa.service.OpaTaskAppealService;
import com.orhon.pa.modules.sys.entity.Office;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 计划任务模块Controller
 * 
 * @author Shawn
 * @version 2017-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/opa/opaPlanTask")
public class OpaPlanTaskController extends BaseController {

	@Autowired
	private OpaPlanTaskService opaPlanTaskService;

	@Autowired
	private OpaPlanService opaPlanService;
	
	@Autowired
	private OpaTaskAppealService opaTaskAppealService;
	
    @Autowired
    private OpaTaskAppealDao opaTaskAppealDao;

	@ModelAttribute
	public OpaPlanTask get(@RequestParam(required = false) String id) {
		OpaPlanTask entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = opaPlanTaskService.get(id);
		}
		if (entity == null) {
			entity = new OpaPlanTask();
		}
		return entity;
	}
   /**
    * 计划指标填报  查询
    * @param opaPlanTask
    * @param request
    * @param response
    * @param model
    * @return
    */
	@RequestMapping(value = { "index" })
	public String index(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaPlanTask.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			//TODO LIUZH
			Integer pageNum = opaPlanTask.getPageNo();
			//opaPlanTask.setAuditorOfficeId(UserUtils.getUser().getOffice().getId());
			if(opaPlanTask.getPageNo() == null){
				opaPlanTask.setPageNo(0);
			}else{
				opaPlanTask.setPageNo((opaPlanTask.getPageNo()-1)*10);
			}
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			for (OpaPlanTask opt : list) {
				//子任务
				opt.setHasCommonChild(opaPlanTaskService.hasCommonChild(opt));
			}
			model.addAttribute("list", list);
			model.addAttribute("pageNum",pageNum);
		}
		return "modules/opa/opaPlanTaskIndex";
	}
	
	
   /**
    * 计划添加完提交
    * @param opaPlanTask
    * @param model
    * @param request
    * @param response
    * @param redirectAttributes
    * @return
    */
	@RequestMapping(value = "apply")
	public String apply(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("待审核", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		opaPlanTaskService.applyAuditPass(opaPlanTask);
		addMessage(redirectAttributes, "考核计划指标提交成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}
	
	
/**
 * 评分标准
 * @param opaPlanTask
 * @param model
 * @param request
 * @param response
 * @param redirectAttributes
 * @return
 */
	@RequestMapping(value = "inputValue")
	public String inputValue(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("已填报", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "评分标准填报成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}

	@RequestMapping(value = "inputCount")
	public String inputCount(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("已填报", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "数值标准填报成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}

	@RequestMapping(value = "addTask")
	public String addTask(OpaPlanTask opaPlanTask, HttpServletRequest request, Model model) {

		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_plan_status", ""));

		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);

		String parentId = request.getParameter("parentId");
		OpaPlanTask parent = opaPlanTaskService.get(parentId);
		boolean ifParent = true;
		Double parentValue = 0D;
		Double childSum = 0D;
		Double restValue = 0D;
		Double thisValue = 0D;
		if (parent != null) {
			parentValue = parent.getValue();
			parentValue = parentValue == null ? 0 : parentValue;
			childSum = opaPlanTaskService.getChildSum(parent);
			childSum = childSum == null ? 0 : childSum;
			thisValue = opaPlanTask.getValue();
			thisValue = thisValue == null ? 0 : thisValue;
			restValue = parentValue - childSum + thisValue;
			ifParent = false;
		}

		//考核对象类型
		opaPlanTask.setObjectType(parent.getObjectType());
		
		opaPlanTask.setItemParentId(parent.getItemId());
		opaPlanTask.setItemParentName(parent.getName());
		opaPlanTask.setPlanId(parent.getPlanId());
		opaPlanTask.setAuditorId(parent.getAuditorId());
		opaPlanTask.setOffice(parent.getOffice());
		opaPlanTask.setIfNum(DictUtils.getDictValue("非数值", "opa_item_Num_type", ""));
		model.addAttribute("ifParent", ifParent);
		model.addAttribute("parentValue", parentValue);
		model.addAttribute("restValue", restValue);
		model.addAttribute("opaPlanTask", opaPlanTask);
		model.addAttribute("parentId", parentId);
		return "modules/opa/opaPlanTaskForm";
	}

	@RequestMapping(value = "save")
	public String save(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		String parentId = request.getParameter("parentId");
		OpaPlanTask parent = opaPlanTaskService.get(parentId);
		opaPlanTask.setIsNewRecord(true);
		opaPlanTask.setId(IdGen.uuid());
		opaPlanTask.setCode(opaPlanTask.getId());
		opaPlanTask.setItemId(opaPlanTask.getId());
		opaPlanTask.setItemParentId(parent.getItemId());
		opaPlanTask.setItemParentIds(parent.getItemParentIds() + parent.getItemId() + ",");
		opaPlanTask.setItemParentName(parent.getName());
		opaPlanTask.setPlanId(parent.getPlanId());
		opaPlanTask.setAuditorId(parent.getAuditorId());
		opaPlanTask.setOffice(parent.getOffice());
		opaPlanTask.setLevel(parent.getLevel() + 1);
		//TODO LIUZH
		opaPlanTask.setObjectType(parent.getObjectType());
		opaPlanTask.setMethod(DictUtils.getDictValue("手工考核", "opa_item_method", ""));
		//opaPlanTask.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
		opaPlanTask.setType(DictUtils.getDictValue("个性指标", "opa_item_type", ""));
		opaPlanTask.setStatus(DictUtils.getDictValue("已填报", "opa_planTask_status", ""));
		if (!beanValidator(model, opaPlanTask)) {
			return addTask(opaPlanTask, request, model);
		}
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "保存计划任务成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}

	@RequestMapping(value = "delete")
	public String delete(OpaPlanTask opaPlanTask, RedirectAttributes redirectAttributes) {
		opaPlanTaskService.delete(opaPlanTask);
		addMessage(redirectAttributes, "删除计划任务成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}
  
	/**
	 * 计划指标审核查询
	 * @param opaPlanTask
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "audit/index" })
	public String audtiIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaPlanTaskAuditIndex";
	}
    
	/**
	 * 计划指标提交到审核者 
	 * @param opaPlanTask
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "audit/pass")
	public String auditPass(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("待执行", "opa_planTask_status", ""));
		opaPlanTaskService.applyAuditPass1(opaPlanTask);
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "审核计划任务成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/audit/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}

	@RequestMapping(value = "audit/return")
	public String auditReturn(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("已退回", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "退回计划任务成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/audit/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}
    
	/**
	 *  考核资料上传查询
	 * @param opaPlanTask
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "excute/index" })
	public String excuteIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		OpaPlan oPt = opaPlanService.get(opaPlanTask.getPlanId());
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL",OpaPlanTask.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			//opaPlanTask.setOffice(UserUtils.getUser().getOffice());
			 //opaPlanTask.setMethod(DictUtils.getDictValue("手工考核","opa_item_method", ""));
			if("2".equals(oPt.getObjectType())){
				opaPlanTask.setPeopleId(UserUtils.getUserPersonId());
				opaPlanTask.setObjectType("2");
			}else if("1".equals(oPt.getObjectType())){
//				opaPlanTask.set
				opaPlanTask.setObjectType("1");
			}
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaPlanTaskExcuteIndex";
	}

	
	/**
	 * 考核资料上传  状态:待评分    上传资料
	 * @param opaPlanTask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "inputScore")
	public String inputScore(OpaPlanTask opaPlanTask, Model model) {
		model.addAttribute("opaPlanTask", opaPlanTask);
		return "modules/opa/opaPlanTaskExcuteInputScore";
	}
  
	/**
	 * 添加子任务时 上传资料(任务自评) 状态为待考核
	 * 
	 * @param file
	 * @param opaPlanTask
	 * @param request
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */		
	@RequestMapping("/uploadFile")
	public String fileUpload(@RequestParam("file") MultipartFile file, OpaPlanTask opaPlanTask,
			HttpServletRequest request,RedirectAttributes redirectAttributes, Model model) {
		// 获得原始文件名
		String fileName = file.getOriginalFilename();
		//获取文件后缀
		String sufFix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		System.out.println("原始文件名:" + fileName);
		System.out.println("原始文件后缀名:" + sufFix);
        //临时路径
		String basePath = Global.getConfig("upload.attachedBasePath");
		StringBuffer fileNameSB = new StringBuffer();
		StringBuffer filePathSB = new StringBuffer();
		String attachedPath = new String();
		String path = new String();
		if (!file.isEmpty()) {
			try {
				if (!Global.getConfig("upload.fileType").contains(sufFix)) {
					addMessage(model, "文件上传格式不正确，多个文件请压缩后上传");
					return "modules/opa/opaPlanTaskExcuteInputScore";
				}
			OpaPlan opaPlan = opaPlanService.get(opaPlanTask.getPlanId());
				String planName = opaPlan.getName();
				//信息录入
				//String officeName = UserUtils.getUser().getOffice().getName();
				String officeName = UserUtils.getUser().getName();
				path = filePathSB.append(basePath).append(DateUtils.getYear()).append("/").append(planName).append("/")
						.append(officeName).append("/").toString();
				fileName = fileNameSB.append(opaPlanTask.getName()).append(".").append(sufFix).toString();
				attachedPath = filePathSB.append(fileName).toString();
				File dir = new File(path, fileName);
				if (!dir.exists())
					dir.mkdirs();
				// MultipartFile自带的解析方法
				file.transferTo(dir);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			addMessage(model, "请选择文件");
			return "modules/opa/opaPlanTaskExcuteInputScore";
		}
		System.out.println("上传文件到:" + attachedPath);
		opaPlanTask.setId(request.getParameter("id"));
		opaPlanTask.setAttachedPath(attachedPath);
		opaPlanTaskService.save(opaPlanTask);
		model.addAttribute("opaPlanTask", opaPlanTask);
		addMessage(model, "文件上传成功");
		return "modules/opa/opaPlanTaskExcuteInputScore";
	}

	/***
	 * 实现文件下载
	 * 
	 * @throws IOException
	 ***/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(OpaPlanTask opaPlanTask, HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {
		File file = new File(opaPlanTask.getAttachedPath());
		String fileName = new String(file.getName().getBytes("utf-8"),"iso8859-1");
		// 用来封装响应头信息
		HttpHeaders responseHeaders = new HttpHeaders();
		// 下载的附件的类型
		responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 下载的附件的名称
		responseHeaders.setContentDispositionFormData("attachment", fileName);

		/**
		 * arg1:需要响应到客户端的数据 arg2:设置本次请求的响应头 arg3:响应到客户端的状态码
		 ***/
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), responseHeaders, HttpStatus.CREATED);
	}
   
	
	/**
	 * 计划指标审核通过后 
	 * @param opaPlanTask
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "saveInputScore")
	public String saveInputScore(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("待考核", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "上传完成");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/inputScore?id=" + opaPlanTask.getId()
				+ "&repage";
	}
    
	/**
	 * 考核打分查询
	 * @param opaPlanTask
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "excute/audit/index" })
	public String excuteAuditIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			/*opaPlanTask.setAuditorId(UserUtils.getUser().getId());*/
			 opaPlanTask.setMethod(DictUtils.getDictValue("手工考核", "opa_item_method", ""));
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaPlanTaskExcuteAuditIndex";
	}
   
	/**
	 * 上传完资料  打分
	 * @param opaPlanTask
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "saveInputScoreAudit")
	public String saveInputScoreAudit(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("已通过", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "评分完成");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/inputScore/audit?id=" + opaPlanTask.getId()
				+ "&repage";
	}
	
	@RequestMapping(value = "excute/audit/pass")
	public String excuteAuditPass(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("已通过", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "考核任务审核成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/excute/audit/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}

	@RequestMapping(value = "excute/audit/return")
	public String excuteAuditReturn(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("未通过", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);  
		addMessage(redirectAttributes, "考核任务退回成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/excute/audit/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}
	/*
	 * 上传完资料打分
	 */
	@RequestMapping(value = "excute/audit/inputScore")
	public String saveInputAuditScore(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String reason = request.getParameter("reason");
		String inputScore = request.getParameter("inputScore");
		String remark = opaPlanTask.getRemarks();
		StringBuffer allReason = new StringBuffer();
		if(StringUtils.isNoneEmpty(remark)){
			allReason.append("\n").append(reason);
		}else{
			allReason.append(reason);
		}
		OpaTaskAppeal appeal = new OpaTaskAppeal();
		opaPlanTask.setScore(Double.valueOf(inputScore));
		opaPlanTask.setRemarks(allReason.toString());
		opaPlanTask.setStatus(DictUtils.getDictValue("已通过", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		opaTaskAppealService.appealupdate(opaPlanTask);
		opaTaskAppealService.updatestatus(appeal);
		addMessage(redirectAttributes, "考核任务打分成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/excute/audit/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}
	
	@RequestMapping(value = "view")
	public String view(OpaPlanTask opaPlanTask, HttpServletRequest request, Model model) {
		OpaPlanTask parent = new OpaPlanTask();
		parent = opaPlanTaskService.findParentTask(opaPlanTask);
		if(parent !=null){
			opaPlanTask.setItemParentName(parent.getName());
		}
		model.addAttribute("opaPlanTask", opaPlanTask);
		return "modules/opa/opaPlanTaskView";
	}

	@RequestMapping(value = { "list", "" })
	public String list(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaPlanTask> page = opaPlanTaskService.findPage(new Page<OpaPlanTask>(request, response), opaPlanTask);
		model.addAttribute("page", page);
		return "modules/opa/opaPlanTaskList";
	}

	@RequestMapping(value = "form")
	public String form(OpaPlanTask opaPlanTask, Model model) {
		model.addAttribute("opaPlanTask", opaPlanTask);
		return "modules/opa/opaPlanTaskForm";
	}

	@ResponseBody
	@RequestMapping(value = "getRestValue")
	public Double getRestValue(HttpServletRequest request, String extId, HttpServletResponse response) {
		String planId = request.getParameter("planId");
		String itemParentId = request.getParameter("itemParentId");
		String officeId = request.getParameter("officeId");
		Office office = new Office();
		office.setId(officeId);
		OpaPlanTask parent = new OpaPlanTask();
		parent.setPlanId(planId);
		parent.setOffice(office);
		parent.setItemParentId(itemParentId);
		parent = opaPlanTaskService.findParentTask(parent);
		Double childSum = opaPlanTaskService.getChildSum(parent);
		Double sum = parent.getValue();
		Double restValue = (sum == null ? 0 : sum) - (childSum == null ? 0 : childSum);
		return restValue;
	}
	
	@ResponseBody
	@RequestMapping(value = "getPages")
	public int getPages(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("指标填报中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaPlanTask.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			//TODO LIUZH
			//opaPlanTask.setAuditorOfficeId(UserUtils.getUser().getOffice().getId());
			int items = opaPlanTaskService.findListCount(opaPlanTask);
			System.out.println(items);
			return items/10 + 1;
		}
		return 0;
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletRequest request, String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		OpaPlanTask opt = new OpaPlanTask();
		opt.setPlanId(request.getParameter("planId"));
		String type = request.getParameter("type");
		if (type != null && StringUtils.isNoneBlank(type)) {
			if (StringUtils.equals(type, "1")) {
				opt.setOffice(UserUtils.getUser().getOffice());
			}
		}
		// opt.setAuditorId(UserUtils.getUser().getOffice().getId());
		List<OpaPlanTask> list = opaPlanTaskService.findList(opt);
		for (int i = 0; i < list.size(); i++) {
			OpaPlanTask e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId())
					&& e.getItemParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getItemId());
				map.put("pId", e.getItemParentId());
				map.put("name", e.getName());
				map.put("planTaskId", e.getId());
				mapList.add(map);
			}
		}
		return mapList;
	}

	/* add by lxy start */
	@RequestMapping(value = { "monitor/index" })
	public String monitorIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			opaPlanTask.setOffice(UserUtils.getUser().getOffice());
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			for (OpaPlanTask opt : list) {
				opt.setHasCommonChild(opaPlanTaskService.hasCommonChild(opt));
			}
			model.addAttribute("list", list);
		}
		return "modules/opa/opaPlanTaskMonitorIndex";
	}
	/* add by lxy end */
	
	/* add by lxy start */
	@RequestMapping(value = {"monitor/list"})
	public String monitorList(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaPlanTask> page = opaPlanTaskService.findPage(new Page<OpaPlanTask>(request, response), opaPlanTask); 
		model.addAttribute("page", page);
		return "modules/opa/opaPlanTaskMonitorList";
	}
	/* add by lxy end */
	
	/* add by lxy start */
	@RequestMapping(value = { "monitor/dept/index" })
	public String monitorDeptIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaSchemeItem.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())) {
			opaPlanTask.setOffice(UserUtils.getUser().getOffice());
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			for (OpaPlanTask opt : list) {
				opt.setHasCommonChild(opaPlanTaskService.hasCommonChild(opt));
			}
			model.addAttribute("list", list);
		}
		return "modules/opa/opaPlanTaskMonitorDeptIndex";
	}
	/* add by lxy end */
	
	/* add by lxy start */
	@RequestMapping(value = {"monitor/dept/view"})
	public String monitorDeptList(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpaPlanTask> page = opaPlanTaskService.findPage(new Page<OpaPlanTask>(request, response), opaPlanTask); 
		model.addAttribute("page", page);
		return "modules/opa/opaPlanTaskMonitorDeptView";
	}
	/* add by lxy end */

	/* add by lxy start */
	@RequestMapping(value = "bonus/inputScore")
	public String bonusInputScore(OpaPlanTask opaPlanTask, Model model) {
		model.addAttribute("opaPlanTask", opaPlanTask);
		return "modules/opa/opaPlanTaskBonusInputScore";
	}
	
	/* add by lxy end */
	
	/* add by lxy start 
	 * 
	 * 绩效反馈   加减分
	 * */
	@RequestMapping(value = { "bonus/index" })
	public String bonusIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		//statusList.add(DictUtils.getDictValue("已通过", "opa_planTask_status", ""));
		statusList.add(DictUtils.getDictValue("执行中", "opa_plan_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaPlanTask.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanTaskService.findListByStatus(param);
		model.addAttribute("planList", planList);
		/*if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())&&opaPlanTask.getName().equals("加减分项")) {*/
		if (StringUtils.isNoneBlank(opaPlanTask.getPlanId())){
			//opaPlanTask.setOffice(UserUtils.getUser().getOffice());
			 //opaPlanTask.setMethod(DictUtils.getDictValue("手工考核","opa_item_method", ""));
			opaPlanTask.setStatus(DictUtils.getDictValue("申请中","opa_planTask_status",""));
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaPlanTaskBonusIndex";
	}
	
	@RequestMapping(value = "bonus/saveInputScore")
	public String bonusSaveInputScore(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("已通过", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "评分填报成功");
		return "redirect:"+Global.getAdminPath()+"/opa/opaPlanTask/bonus/inputScore?&repage";
	}
	
	@RequestMapping(value = "bonus/audit/pass")
	public String bonusAuditPass(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("申请已通过", "opa_planTask_status", ""));
		opaPlanTaskService.applyAuditPass(opaPlanTask);
		addMessage(redirectAttributes, "审核计划任务成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/bonus/audit/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}

	@RequestMapping(value = "bonus/audit/return")
	public String bonusAuditReturn(OpaPlanTask opaPlanTask, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		opaPlanTask.setStatus(DictUtils.getDictValue("申请已退回", "opa_planTask_status", ""));
		opaPlanTaskService.save(opaPlanTask);
		addMessage(redirectAttributes, "退回计划任务成功");
		return "redirect:" + Global.getAdminPath() + "/opa/opaPlanTask/audit/index?planId=" + opaPlanTask.getPlanId()
				+ "&repage";
	}
	/* add by lxy end */
	
	
	//加减分
	/* add by lxy start */
	@RequestMapping(value = {"bonus/audit/index"})
	public String auditIndex(OpaPlanTask opaPlanTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		statusList.add(DictUtils.getDictValue("申请中", "opa_planTask_status", ""));
		param.put("statusList", statusList);
		param.put("DEL_FLAG_NORMAL", OpaPlanTask.DEL_FLAG_NORMAL);
		List<Status> planList = opaPlanService.findListByStatus(param);
		model.addAttribute("planList", planList);
		if(StringUtils.isNoneBlank(opaPlanTask.getPlanId())){
			opaPlanTask.setAuditorId(UserUtils.getUser().getId());
//			opaPlanTask.setMethod(DictUtils.getDictValue("手工考核", "opa_item_method", ""));
			List<OpaPlanTask> list = opaPlanTaskService.findList(opaPlanTask);
			model.addAttribute("list", list);
		}
		return "modules/opa/opaPlanTaskBonusAuditIndex";
	}
	
	/* add by lxy end */
}