package com.orhon.smartcampus.modules.activiti;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.utils.HttpUtil;
import com.orhon.smartcampus.utils.IPInfoUtil;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * 流程控制器
 */
@RestController
@RequestMapping("/workflow")
public class ModelerController{

	private static final Logger logger = LoggerFactory.getLogger(ModelerController.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired 
	private ProcessEngine processEngine;

	@Autowired 
	private InfoService infoService;


	/**
	 * 查询模型列表
	 * @return
	 */
	 @RequestMapping("modelerList")
	 public R modelerList(PageDto pageDto) {
		 List<Model> list = repositoryService.createModelQuery().listPage(pageDto.getStart(), pageDto.getLimit());
		 ArrayList<HashMap<String,Object>> list2 = new ArrayList<>();
		 list.forEach(item->{
			 HashMap<String , Object> ret = new HashMap<>();
			 ret.put("id" , item.getId());
			 ret.put("name" , item.getName());
			 ret.put("key" , item.getKey());
			 ret.put("category" , item.getCategory());
			 ret.put("tenantId" , item.getTenantId());
			 ret.put("createtime" , item.getCreateTime());
			 ret.put("lastupdatetime" , item.getLastUpdateTime());
			 ret.put("version" , item.getVersion());
			 list2.add(ret);
		 });
		 return R.ok().put("list", list2);
	 }

	 /**
	  * 流程部署列表
	  * @param pageDto
	  * @return
	  */
	 @RequestMapping("/deploymentList")
	 public R deploymentList(PageDto pageDto){
		 List<Deployment> list = repositoryService.createDeploymentQuery().listPage(pageDto.getStart() , pageDto.getLimit());
		 ArrayList<HashMap<String,Object>> list2 = new ArrayList<>();
		 list.forEach(item->{
			 HashMap<String , Object> ret = new HashMap<>();
			 ret.put("id" , item.getId());
			 ret.put("name" , item.getName());
			 ret.put("key" , item.getKey());
			 ret.put("deploymentTime" , item.getDeploymentTime());
			 ret.put("category" , item.getCategory());
			 ret.put("tenantId" , item.getTenantId());
			 list2.add(ret);
		 });
		 return R.ok().put("list" , list2);
	 }

	 @RequestMapping("/processInstanceList")
	 public R processIntantceList(PageDto pageDto){
		 List<ProcessInstance> p_list = runtimeService.createProcessInstanceQuery().listPage(pageDto.getStart() , pageDto.getLimit());
		 ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		 p_list.forEach(item -> {
			 HashMap<String, Object> ret = new HashMap<>();
			 ret.put("id" , item.getId());
			 ret.put("deploymentId" , item.getDeploymentId());
			 ret.put("name" , item.getName());
			 ret.put("processDefinitionId" , item.getProcessDefinitionId());
			 ret.put("startTime" , item.getStartTime());
			 ret.put("processDefinitionKey" , item.getProcessDefinitionKey());
			 ret.put("startUserId" , item.getStartUserId());
			 ret.put("activitiId" , item.getActivityId());
			 ret.put("processDefinitionName" , item.getProcessDefinitionName());
			 list.add(ret);
		 });
		 return R.ok().put("list" ,list);
	 }


	 /**
	  * 创建模型
	  * @param response
	  * @param productId 决策配置ID
	  * @throws IOException
	  */
	 @RequestMapping("/create")
	 public void create(HttpServletResponse response,String name,String key) throws IOException {
		 logger.info("创建模型入参name：{},key:{}",name,key);
		 Model model = repositoryService.newModel();
		 ObjectNode modelNode = objectMapper.createObjectNode();
		 modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		 modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "");
		 modelNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		 model.setName(name);
		 model.setKey(key);
		 model.setMetaInfo(modelNode.toString());
		 repositoryService.saveModel(model);
		 createObjectNode(model.getId());
		 response.sendRedirect("/static/activiti/modeler.html?modelId="+ model.getId());
		 logger.info("创建模型结束，返回模型ID：{}",model.getId());
	 }

	 /**
	  * 创建模型时完善ModelEditorSource
	  * @param modelId
	  */
	 @SuppressWarnings("deprecation")
	 private void createObjectNode(String modelId){
		 logger.info("创建模型完善ModelEditorSource入参模型ID：{}",modelId);
		 ObjectNode editorNode = objectMapper.createObjectNode();
		 editorNode.put("id", "canvas");
		 editorNode.put("resourceId", "canvas");
		 ObjectNode stencilSetNode = objectMapper.createObjectNode();
		 stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
		 editorNode.put("stencilset", stencilSetNode);
		 try {
			 repositoryService.addModelEditorSource(modelId,editorNode.toString().getBytes("utf-8"));
		 } catch (Exception e) {
			 logger.info("创建模型时完善ModelEditorSource服务异常：{}",e);
		 }
		 logger.info("创建模型完善ModelEditorSource结束");
	 }

	 /**
	  * 发布流程
	  * @param modelId 模型ID
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping("/deploy")
	 public Object publish(String modelId){
		 logger.info("流程部署入参modelId：{}",modelId);
		 Map<String, String> map = new HashMap<String, String>();
		 try {
			 Model modelData = repositoryService.getModel(modelId);
			 byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
			 if (bytes == null) {
				 logger.info("部署ID:{}的模型数据为空，请先设计流程并成功保存，再进行发布",modelId);
				 map.put("code", "FAILURE");
				 return map;
			 }
			 JsonNode modelNode = new ObjectMapper().readTree(bytes);
			 BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			 Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addBpmnModel(modelData.getKey()+".bpmn20.xml", model).deploy();
			 modelData.setDeploymentId(deployment.getId());
			 repositoryService.saveModel(modelData);
			 map.put("code", "SUCCESS");
		 } catch (Exception e) {
			 logger.info("部署modelId:{}模型服务异常：{}",modelId,e);
			 map.put("code", "FAILURE");
		 }
		 logger.info("流程部署出参map：{}",map);
		 return map;
	 }

	 /**
	  * 撤销流程定义
	  * @param modelId 模型ID
	  * @param result
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping("/revokeDeploy")
	 public Object revokePublish(String modelId){
		 logger.info("撤销发布流程入参modelId：{}",modelId);
		 Map<String, String> map = new HashMap<String, String>();
		 Model modelData = repositoryService.getModel(modelId);
		 if(null != modelData){
			 try {
				 /**
				  * 参数不加true:为普通删除，如果当前规则下有正在执行的流程，则抛异常 
				  * 参数加true:为级联删除,会删除和当前规则相关的所有信息，包括历史 
				  */
				 repositoryService.deleteDeployment(modelData.getDeploymentId(),true);
				 map.put("code", "SUCCESS");
			 } catch (Exception e) {
				 logger.error("撤销已部署流程服务异常：{}",e);
				 map.put("code", "FAILURE");
			 }
		 }
		 logger.info("撤销发布流程出参map：{}",map);
		 return map;
	 }

	 /**
	  * 删除流程实例
	  * @param modelId 模型ID
	  * @param result
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping("/delete")
	 public Object deleteProcessInstance(String modelId){
		 logger.info("删除流程实例入参modelId：{}",modelId);
		 Map<String, String> map = new HashMap<String, String>();
		 Model modelData = repositoryService.getModel(modelId);
		 if(null != modelData){
			 try {
				 ProcessInstance pi = runtimeService.createProcessInstanceQuery().processDefinitionKey(modelData.getKey()).singleResult();
				 if(null != pi) {
					 runtimeService.deleteProcessInstance(pi.getId(), "");
					 historyService.deleteHistoricProcessInstance(pi.getId());
				 }
				 map.put("code", "SUCCESS");
			 } catch (Exception e) {
				 logger.error("删除流程实例服务异常：{}",e);
				 map.put("code", "FAILURE");
			 }
		 }
		 logger.info("删除流程实例出参map：{}",map);
		 return map;
	 }

	 /**
	  * 查询全部历史记录
	  */
	 @GetMapping(value="/queryHistoricInstance")
	 @ResponseBody
	 public R queryHistoricInstance(PageDto dto) {
		 List<HistoricProcessInstance> Historylist = processEngine.getHistoryService()
				 .createHistoricProcessInstanceQuery()
				 .orderByProcessInstanceStartTime().asc()//排序
				 .listPage(dto.getStart(), dto.getLimit());

		 ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		 Historylist.forEach(item -> {
			 HashMap<String, Object> ret = new HashMap<>();
			 ret.put("id" , item.getId());
			 ret.put("deploymentId" , item.getDeploymentId());
			 ret.put("name" , item.getName());
			 ret.put("processDefinitionId" , item.getProcessDefinitionId());
			 ret.put("startTime" , item.getStartTime());
			 ret.put("processDefinitionKey" , item.getProcessDefinitionKey());
			 ret.put("startUserId" , item.getStartUserId());
			 ret.put("processDefinitionName" , item.getProcessDefinitionName());
			 list.add(ret);
		 });
		 return R.ok().put("data", list);
	 }
	 /**
	  * 查询某个人历史审批过的任务
	  */
	 @GetMapping(value="/findHistoryUserTask")
	 @ResponseBody
	 public R findHistoryUserTask(String taskAssignee,PageDto pageDto){
		 List<HistoricTaskInstance> Historylist = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
				 .createHistoricTaskInstanceQuery()//创建历史任务实例查询
				 .taskAssignee(taskAssignee)//指定历史任务的办理人
				 .listPage(pageDto.getStart(), pageDto.getLimit());

		 ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		 Historylist.forEach(item -> {
			 HashMap<String, Object> ret = new HashMap<>();
			 ret.put("id" , item.getId());
			 ret.put("name" , item.getName());
			 ret.put("processDefinitionId" , item.getProcessDefinitionId());
			 ret.put("startTime" , item.getStartTime());
			 list.add(ret);
		 });

		 return R.ok().put("data", list);
	 }
	 /**
	  * 流程id查询 某个流程历史记录
	  */
	 @GetMapping(value="/findHistoryIDTask")
	 @ResponseBody
	 public R findHistoryIDTask(String processInstanceId,PageDto dto){
		 List<HistoricTaskInstance> Historylist = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
				 .createHistoricTaskInstanceQuery()//创建历史任务实例查询
				 .processInstanceId(processInstanceId)//
				 .orderByHistoricTaskInstanceStartTime().asc()
				 .listPage(dto.getStart(), dto.getLimit());

		 ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		 Historylist.forEach(item -> {
			 HashMap<String, Object> ret = new HashMap<>();
			 ret.put("id" , item.getId());
			 ret.put("name" , item.getName());
			 ret.put("processDefinitionId" , item.getProcessDefinitionId());
			 ret.put("startTime" , item.getStartTime());
			 list.add(ret);
		 });

		 return R.ok().put("data", list);
	 }
	 /**
	  * 查询流程状态（判断流程正在执行，还是结束）
	  * @param processInstanceId
	  * @return
	  */
	 @GetMapping(value="/isProcessEnd")
	 @ResponseBody
	 public R isProcessEnd(String processInstanceId){
		 //去正在执行的任务表查询
		 ProcessInstance pi = processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象
				 .createProcessInstanceQuery()//创建流程实例查询
				 .processInstanceId(processInstanceId)//使用流程实例ID查询
				 .singleResult();
		 if(pi==null){
			 System.out.println("该流程实例走完");
			 return R.ok().put("msg", "该流程实例走完");
		 }
		 else{
			 System.out.println("该流程实例还没走完");
			 return R.ok().put("msg", "该流程实例还没走完");
		 }
	 }

	 /**
	  * 查询某个人的代办任务
	  */
	 @GetMapping(value="/findMyTaskListByUser")
	 @ResponseBody
	 public R findMyTaskListByUser(PageDto pageDto){
		 HashMap currentUser = infoService.getCurrentUser();
		 if(currentUser!=null) {
			 Users users =  (Users) currentUser.get("user");
			 List<Task> assigneeList = processEngine.getTaskService()
					 .createTaskQuery()//指定个人任务查询
					 .taskAssignee(users.getUsername())
					 .listPage(pageDto.getStart(), pageDto.getLimit());
			 JSONArray jsonArray = new JSONArray();
			 for (Task task : assigneeList) {
				 JSONObject jo = new JSONObject();
				 jo.put("ProcessInstanceId", task.getProcessInstanceId());
				 jo.put("Id", task.getId());
				 jo.put("Assignee", task.getAssignee());
				 jo.put("Name", task.getName());
				 jo.put("CreateTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()));
				 jsonArray.add(jo);
			 }
			 return R.ok().put("data", jsonArray);
		 }
		 return R.ok().put("data", "");

	 }



}
