package com.orhonit.ole.enforce;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.enforce.dto.CaseInfoDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.repository.CaseDealRepository;
import com.orhonit.ole.enforce.repository.CaseRepository;
import com.orhonit.ole.enforce.service.casedeal.CaseDealService;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.sys.dao.UserDao;
import com.orhonit.ole.sys.dto.FlowDTO;

import lombok.extern.slf4j.Slf4j;


/**
 * activiti流程测试
 * 
 * @author ebusu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j(topic = "flowTest")
// @Transactional
public class FlowTestNew {

	@Autowired
	ProcessEngine processEngine;

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	CaseService caseService;

	@Autowired
	CaseRepository caseRepository;

	@Autowired
	FlowService flowService;

	@Autowired
	UserDao userDao;
	
	@Autowired
	CaseDealService caseDealService;
	
	@Autowired
	CaseDealRepository caseDealRepository;

	/**
	 * 案件ID，方便测试
	 */
	private static final String CASE_ID = "83b53f44-d380-420c-b581-348282e679a9";

	/**
	 * 时间格式统一化
	 */
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 案件受理流程唯一Key
	 */
	private static final String FLOW_KEY = "case";

	/**
	 * 定时任务唯一Key
	 */
	private static final String TIMER_FLOW_KEY = "testTimer";

	/**
	 * 初步核实时可选项 1：不予处理 当前流程结束，案件更新为结案 2：一般流程 3：简易流程
	 */
	private static final String CURRENT_HANDLE_MODE = "3";

	private Map<String, Long> USER_MAP = new HashMap<>();

	/**
	 * 所有测试功能入口
	 */
	@Test
	public void testFlow() {

		/**
		 * 功能点测试
		 * 1. 部署 #完成 createDeployment(); 
		 * 2. 根据部署名称获取部署Key #完成 getDeploymentList(); 
		 * 3. 删除一个部署流程 #完成 deleteProcessDefinitionByDeploymentId(); 
		 * 4. 发起一个流程 #完成 startFlow();为了测试起来方便,需要回滚数据 
		 * 5. 流程与业务数据绑定 #完成 .. 
		 * 6. 代办事宜 #完成 .. 
		 * 7. 从案件受理步骤到初步审核#完成 
		 * 8. 详细审批记录以及批注信息
		 */

		/**
		 * 代码 
		 * this.createDeployment(); 
		 * String deployName = "案件受理"; 
		 * String key = getDeploymentList(deployName); //查看已部署的流程 
		 * System.out.println( "the key is : " + key);
		 * this.deleteProcessDefinitionByDeploymentId(deployments.get(0).getId() );
		 */
		// this.startFlow(FLOW_KEY);
		// this.getCommemntByKeyAndBusinessId(FLOW_KEY, CASE_ID);
		this.createDeployment();

		// this.startFlow(FLOW_KEY);
		// this.toTask(FLOW_KEY, CASE_ID, null);

		// this.createDeploymentTimer();
		// this.startTimerFlow(TIMER_FLOW_KEY);
	}

	/**
	 * 测试定时任务
	 */
	private void createDeploymentTimer() {
		try {

			String deployName = "timerTest";
			List<Deployment> deployments = processEngine.getRepositoryService().createDeploymentQuery()
					.deploymentName(deployName).orderByDeploymenTime().asc().list();
			if (deployments != null && deployments.size() != 0) {
				// 为了使数据看起来没那么乱
				throw new RuntimeException("deploy name : [" + deployName + "] is exits.");
			}

			String fileName = "定时触发";
			// File file = new File("classpath:processes/bpmn1tuihui.zip");
			File file = ResourceUtils.getFile("E:\\workflow\\itcast0820Project\\WebRoot\\bpmn\\timer.zip");
			/// processes/bpmn1tuihui.zip
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			processEngine.getRepositoryService().createDeployment()// 创建部署对象
					.name(fileName)// 添加部署名称
					.category("timer").addZipInputStream(zipInputStream)//
					.deploy();// 完成部署
			System.out.println("Number of process definitions=========="
					+ repositoryService.createProcessDefinitionQuery().count());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getCommemntByKeyAndBusinessId(String flowKey, String caseId) {

		CaseEntity caseEntity = this.caseRepository.findOne(caseId);

		caseId = caseEntity.getCaseNum();

		// ProcessInstance processInstance =
		// this.processEngine.getRuntimeService().createProcessInstanceQuery()
		// .processInstanceBusinessKey(caseId).singleResult();
		//
		// log.info("pinst : {}", processInstance);
		//
		// processInstance =
		// this.processEngine.getRuntimeService().createProcessInstanceQuery()
		// .processDefinitionKey(flowKey).singleResult();
		//
		// log.info("pinst : {}", processInstance);

		HistoricProcessInstance hpi = this.processEngine.getHistoryService().createHistoricProcessInstanceQuery()
				.processDefinitionKey(flowKey).processInstanceBusinessKey(caseId).singleResult();

		System.out.println("======================================================");
		System.out.println(hpi.getId());
		System.out.println(hpi.getSuperProcessInstanceId());

		this.getCommemntByProcessInstanceId(hpi.getId());
		this.getCommemntByProcessInstanceId(hpi.getSuperProcessInstanceId());

		log.info("hisotry : {}", hpi);
	}

	/**
	 * 发布流程
	 */
	public void createDeployment() {

		try {

			// String deployName = "专项检查";
			String deployName = "案件受理";
			// String deployName = "日常检查";
			List<Deployment> deployments = processEngine.getRepositoryService().createDeploymentQuery()
					.deploymentName(deployName).orderByDeploymenTime().asc().list();
			if (deployments != null && deployments.size() != 0) {
				// 为了使数据看起来没那么乱
				// throw new RuntimeException("deploy name : [" + deployName + "] is exits.");
			}

			String fileName = "案件受理";
			// String fileName = "专项检查";
			// String fileName = "日常检查";
			// File file = new File("classpath:processes/bpmn1tuihui.zip");
			File file = ResourceUtils.getFile("D:\\SJZK\\orhonworkspace\\ole-flow\\src\\com\\orhonit\\flow\\caseflow\\case.zip");
			/// processes/bpmn1tuihui.zip
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			processEngine.getRepositoryService().createDeployment()// 创建部署对象
					.name(fileName)// 添加部署名称
					// .category("proCheck")
					.category("case").addZipInputStream(zipInputStream)//
					.deploy();// 完成部署
			System.out.println("Number of process definitions=========="
					+ repositoryService.createProcessDefinitionQuery().count());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除一个已部署的流程
	 * 
	 * @param deploymentId
	 */
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/**
	 * 根据流程或者根据任务所有者查询任务列表
	 * 
	 * @param owner
	 * @param processIntaceId
	 */
	public void queryAllAndOwnTask(String owner, String processIntaceId) {
		// processInstance
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processIntaceId).orderByTaskCreateTime()
				.asc().list();
		System.out.println("所有当前流程的任务-----------------------------");

		taskList.forEach(item -> {
			log.info("任务ID : {}", item.getId());
			log.info("任务名称 : {}", item.getName());
			log.info("创建时间 : {}", SDF.format(item.getCreateTime()));
			log.info("办理人 : {}", item.getAssignee());
		});

		// taskList = taskService.createTaskQuery()
		// .orderByTaskCreateTime().asc()
		// .list();
		//
		// System.out.println("所有任务-----------------------------");
		//
		// taskList.forEach(item->{
		// log.info("任务ID : {}", item.getId());
		// log.info("任务名称 : {}", item.getName());
		// log.info("创建时间 : {}", SDF.format(item.getCreateTime()));
		// log.info("办理人 : {}", item.getAssignee());
		// });

		taskList = taskService.createTaskQuery().taskAssignee(owner).orderByTaskCreateTime().asc().list();

		System.out.println("所有自己的任务任务-----------------------------");

		taskList.forEach(item -> {
			log.info("任务ID : {}", item.getId());
			log.info("任务名称 : {}", item.getName());
			log.info("创建时间 : {}", SDF.format(item.getCreateTime()));
			log.info("办理人 : {}", item.getAssignee());

			log.info("流程实例ID : {}", item.getProcessInstanceId());
			log.info("执行对象ID : {}", item.getExecutionId());
			log.info("流程定义ID : {}", item.getProcessDefinitionId());

		});
	}

	public void getCommemntByProcessInstanceId(String processInstanceId) {

		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);

		System.out.println("查看历史审批记录以及批注信息");
		if (list != null) {
			list.forEach(item -> {
				log.info("full message : {}", item.getFullMessage());
				log.info("id : {}", item.getId());
				log.info("process instance id : {}", item.getProcessInstanceId());
				log.info("task id : {}", item.getTaskId());
				log.info("item : {}", SDF.format(item.getTime()));
				log.info("type : {}", item.getType());
				log.info("user id : {}", item.getUserId());

				HistoricTaskInstance hti = this.processEngine.getHistoryService().createHistoricTaskInstanceQuery()
						.taskId(item.getTaskId()).taskAssignee(item.getUserId()).singleResult();

				log.info("task name : {}", hti.getName());

			});
		}
	}

	/**
	 * 根据部署流程名字获取Key,中间有数据判断
	 * 
	 * @return
	 */
	public String getDeploymentList(String deployName) {
		List<Deployment> deployments = processEngine.getRepositoryService().createDeploymentQuery()
				.deploymentName(deployName).orderByDeploymenTime().asc().list();
		String deployId = "";
		if (deployments != null) {
			for (Deployment d : deployments) {
				System.out.println(d.getId() + "," + d.getName());
			}
		}
		if (deployments.size() != 1) {
			throw new RuntimeException("the deploy data is error.");
		}

		deployId = deployments.get(0).getId();

		List<ProcessDefinition> list = this.repositoryService.createProcessDefinitionQuery().deploymentId(deployId)
				.orderByProcessDefinitionVersion().asc()//
				.list();

		if (list == null || list.size() != 1) {
			throw new RuntimeException("the act_re_procdef data is error.");
		}

		return list.get(0).getKey();
	}

	/**
	 * 到指定节点的一个案件,首先必须有案件编号
	 */
	public void toTask(String key, String caseId, CaseEntity caseInfoDTO) {
		
		Random rand = new Random();

		USER_MAP.put("孙志和", Long.valueOf(20697));
		USER_MAP.put("刘四平", Long.valueOf(24002));
		USER_MAP.put("张景鹏", Long.valueOf(21939));
		
		caseId = "fa550978-8281-4f5f-ad29-bdae14633e7b";

		// 1
		// User user = new User();
		// user.setId(USER_MAP.get("孙志和"));

		caseInfoDTO = this.caseRepository.findOne(caseId);
		caseInfoDTO.setComment("1");

		String zzfryId = this.userDao.getUserByPersonId(caseInfoDTO.getCaseZzfryid()).getId().toString();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee("20697");
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment("1");
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());

		this.flowService.startFlowByKey(flowDTO);

		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment("2");
		flowDTO.setHandleMode("2");
		this.flowService.taskComplete(flowDTO);

		// 3 立案提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(USER_MAP.get("张景鹏").toString());
		flowDTO.setComment("3");
		this.flowService.taskComplete(flowDTO);

		// 4 立案审核提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(USER_MAP.get("张景鹏").toString());
		flowDTO.setNextAssignee(USER_MAP.get("刘四平").toString());
		flowDTO.setComment("4");
		this.flowService.taskComplete(flowDTO);

		// 5 立案审批提交, 选择7 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(USER_MAP.get("刘四平").toString());
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment("5");
		flowDTO.setHandleMode("7");
		this.flowService.taskComplete(flowDTO);

		// 6 调取取证提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment("6");
		this.flowService.taskComplete(flowDTO);
		
		/**
		 * 案件合一提交
		 */
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment("777");
		flowDTO.setHandleMode("9");
		this.flowService.taskComplete(flowDTO);
		
/*
		// 7 案件合意提交 选择10 不予处罚
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("10");
		this.flowService.taskComplete(flowDTO);

		// 8 申请案件处理提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 9 案件处理审核提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 10 案件处理审批提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 11 案件处理, 选择24 申请复议
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("24");
		this.flowService.taskComplete(flowDTO);

		// 12 申请复议提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 13 初步核查提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(USER_MAP.get("张景鹏").toString());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 14 初步核查审核提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(USER_MAP.get("张景鹏").toString());
		flowDTO.setNextAssignee(USER_MAP.get("刘四平").toString());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 15 初步核查审批提交 到复议受理审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(USER_MAP.get("刘四平").toString());
		flowDTO.setNextAssignee(USER_MAP.get("张景鹏").toString());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("26");
		this.flowService.taskComplete(flowDTO);

		// 16 复议受理审核提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(USER_MAP.get("张景鹏").toString());
		flowDTO.setNextAssignee(USER_MAP.get("刘四平").toString());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 17 复议受理审批提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(USER_MAP.get("刘四平").toString());
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 18 复议受理提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 19 复议审理提交 到 复议决定
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("61");
		this.flowService.taskComplete(flowDTO);

		// 20 复议决定提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 21 复议决定审核提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 22 复议决定审批提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(zzfryId);
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		this.flowService.taskComplete(flowDTO);

		// 23 复议下达通知提交 到 暂停审理审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zzfryId);
		flowDTO.setNextAssignee(USER_MAP.get("张景鹏").toString());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("64");
		this.flowService.taskComplete(flowDTO);
		*/

	}

	/**
	 * 启动一个流程
	 * 
	 * @param key
	 */
	public void startFlow(String key) {

		// 模拟案件受理流程,当案件状态变更为“已提交”时需要给主执法人员提交任务

		CaseInfoDTO caseInfoDTO = this.caseService.findOne(CASE_ID);
		// 首先自己要提交给主执法人员
		String businessId = caseInfoDTO.getCaseNum();
		Map<String, Object> variables = new HashMap<String, Object>();
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(key, businessId);
		System.out.println("==========================================" + processInstance.getBusinessKey());

		System.out.println("启动的所有流程数量 : " + runtimeService.createProcessInstanceQuery().count());

		System.out.println("启动的案件办理流程数量 : "
				+ runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessId).count());
		// 设置当前办理人
		String assingnee = "20697";
		String nextAssingee = "";
		// 查询所有任务-查询所有该流程实例的任务-查询所有指定用户的任务
		this.queryAllAndOwnTask(assingnee, processInstance.getId());

		// 从页面中点击一个案件进行提交、审核-- 传过来taskId
		String taskId = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee(assingnee)
				.singleResult().getId();

		variables = new HashMap<String, Object>();
		Authentication.setAuthenticatedUserId(assingnee);
		taskService.addComment(taskId, processInstance.getId(), "执法人员请及时处理.");

		taskService.complete(taskId, variables);

		System.out.println("当前用户完成自己的任务时，任务的流转以及代办");

		nextAssingee = "user:test, caseStatus:14";

		// 查询所有任务-查询所有该流程实例的任务-查询所有指定用户的任务
		this.queryAllAndOwnTask(nextAssingee, processInstance.getId());

		// 查看历史批注
		this.getCommemntByProcessInstanceId(processInstance.getId());

		// 张三完成自己的任务
		taskId = taskService.createTaskQuery().taskAssignee(nextAssingee).singleResult().getId();
		variables = new HashMap<String, Object>();
		variables.put("handleMode", CURRENT_HANDLE_MODE);

		Authentication.setAuthenticatedUserId(nextAssingee);
		taskService.addComment(taskId, processInstance.getId(), "这个案件不予处理.");

		taskService.complete(taskId, variables);

		System.out.println("张三把案件处理成不予处理=======================");
		this.queryAllAndOwnTask(nextAssingee, processInstance.getId());
		this.getCommemntByProcessInstanceId(processInstance.getId());
		System.out.println("启动的所有流程数量 : " + runtimeService.createProcessInstanceQuery().count());

		System.out.println("启动的案件办理流程数量 : "
				+ runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessId).count());

		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstance.getId())// 使用流程实例ID查询
				.singleResult();
		if (pi == null) {
			System.out.println("流程结束了========================================");
		} else {
			System.out.println("流程未结束========================================");
		}

	}

	/**
	 * 启动2个流程
	 * 
	 * @param key
	 */
	public void startTimerFlow(String key) {

		// 模拟案件受理流程,当案件状态变更为“已提交”时需要给主执法人员提交任务

		// 首先自己要提交给主执法人员
		String businessId = "timerTest" + System.currentTimeMillis();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("duTime", "PT60S");
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(key, businessId, variables);
		System.out.println("==========================================" + processInstance.getBusinessKey());

		System.out.println("启动的所有流程数量 : " + runtimeService.createProcessInstanceQuery().count());

		System.out.println("启动的案件办理流程数量 : "
				+ runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessId).count());
		// 设置当前办理人
		String assingnee = "1";
		String nextAssingee = "";
		// 查询所有任务-查询所有该流程实例的任务-查询所有指定用户的任务
		this.queryAllAndOwnTask(assingnee, processInstance.getId());

		/*
		 * 
		 * // 从页面中点击一个案件进行提交、审核-- 传过来taskId String taskId =
		 * taskService.createTaskQuery()
		 * .processInstanceId(processInstance.getId()) .taskAssignee(assingnee)
		 * .singleResult() .getId();
		 * 
		 * variables = new HashMap<String,Object>();
		 * Authentication.setAuthenticatedUserId(assingnee);
		 * taskService.addComment(taskId, processInstance.getId(),
		 * "执法人员请及时处理.");
		 * 
		 * taskService.complete(taskId, variables);
		 * 
		 * System.out.println("当前用户完成自己的任务时，任务的流转以及代办");
		 * 
		 * nextAssingee = "user:test, caseStatus:14";
		 * 
		 * // 查询所有任务-查询所有该流程实例的任务-查询所有指定用户的任务
		 * this.queryAllAndOwnTask(nextAssingee, processInstance.getId());
		 * 
		 * // 查看历史批注
		 * this.getCommemntByProcessInstanceId(processInstance.getId());
		 * 
		 * // 张三完成自己的任务 taskId = taskService.createTaskQuery()
		 * .taskAssignee(nextAssingee).singleResult().getId(); variables = new
		 * HashMap<String,Object>(); variables.put("handleMode",
		 * CURRENT_HANDLE_MODE );
		 * 
		 * Authentication.setAuthenticatedUserId(nextAssingee);
		 * taskService.addComment(taskId, processInstance.getId(), "这个案件不予处理.");
		 * 
		 * taskService.complete(taskId, variables);
		 * 
		 * System.out.println("张三把案件处理成不予处理=======================");
		 * this.queryAllAndOwnTask(nextAssingee, processInstance.getId());
		 * this.getCommemntByProcessInstanceId(processInstance.getId());
		 * System.out.println("启动的所有流程数量 : " +
		 * runtimeService.createProcessInstanceQuery().count());
		 * 
		 * System.out.println("启动的案件办理流程数量 : " +
		 * runtimeService.createProcessInstanceQuery()
		 * .processInstanceBusinessKey(businessId).count());
		 * 
		 * ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
		 * .processInstanceId(processInstance.getId())//使用流程实例ID查询
		 * .singleResult(); if ( pi == null ) {
		 * System.out.println("流程结束了========================================");
		 * } else {
		 * System.out.println("流程未结束========================================");
		 * }
		 */

	}
	
	/*private List<TempCase> caseNameList;
	
	private Integer caseCount;
	
	private List<Integer> years;
	
	private List<String> comments;
	
	private List<String> taskList;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void start() {
		User zfryUser = new User();
		zfryUser.setPersonId("458e174b8fc04f54ad2a52f8327d6a81");
		zfryUser.setPersonName("王永庭");
		zfryUser.setUserId("24795");
		
		User fzyryUser = new User();
		fzyryUser.setPersonId("9e6a0757eb5747f2a1e11b008e74a711");
		fzyryUser.setPersonName("于洋");
		fzyryUser.setUserId("24796");
		User shr = new User();
		shr.setPersonName("赵瑞军");
		shr.setUserId("24797");
		
		User spr = new User();
		spr.setPersonName("巴雅");
		spr.setUserId("24798");
		
		String deptId = "68e881fa6702406cb9ab3b26cdea955b";
		
		this.createCase(zfryUser, fzyryUser, shr, spr , deptId);
	}
	
	
	// @Before
	public void init() {
		log.info("初始化数据.........");
		
		years = Arrays.asList(2014, 2015, 2016, 2017, 2018);
		
		comments = Arrays.asList("正在审理", "民事案件" , "人民法院发现" , "同意" , "分的裁", "事判决中的返还", "诉讼请求赔偿范围一",
				"被害人在", "的情况", "公力救济的情况", "提起民事诉讼", "院应裁定不予受理", "裁定驳回起诉", "刑事责任的承", "主体与民事责任的", "体虽然竞合",
				"但刑事判", "对涉及的财产", "分未作处理", "或只作部分处理", "受害人通过民事诉讼", "要求刑事责任主", "返还财产或对追", "足部分承担赔偿责任",
				"根据最高人民法院", "同纠纷案件中涉及", "若干问题的规定", "有管辖权的法院应当继续审理", "刑事判决", "部分虽作出", "赃或退赔处理", "但刑事责任主体",
				"民事责任主", "不相竞合", "被害人提起民事诉讼的", "应该根据", "民事法律规范进行判断", "如果当事人之间构成民事法律关系", "除了刑事责任主体承担责任外",
				"单位或其", "人仍应承", "担民事责任", "民事案件应当继续审理", "例如行为人采用欺诈手段与被害人订立合同", "个人构成诈骗罪", "或者单位有过错的");
		
		taskList = Arrays.asList("初步核实","现场处理","结案","立案","立案审核","立案审批","调查取证","案件合议论",
				"重大案件受理","重大案件审核","法制意见审核","集体讨论","案件审核","听证告知","听证申请",
				"听证受理","听证","申请案件处理","案件处理审核","案件处理审批","案件处理","先行告知","陈述申辩", "申请陈述申辩",
				"案件结案","申请强制执行","强制执行审核","强制执行审批","强制执行","结案申请","结案审核","结案审批",
				"申请复议","初步核查","初步核查审核","初步核查审批","不予受理审核","不予受理审批","复议受理审核","复议受理审批","复议受理","复议审理",
				"复议调查取证","复议决定","调解协商","复议决定审核","复议决定审批","复议下达通知","驳回复议审核","驳回复议审批","驳回行政复议","责令恢复审理",
				"终止复议审核","终止复议审批","暂停受理审核","暂停受理审批","暂停受理","恢复审理");
		
		
		log.info("初始化案件名称,从案件公开网获取");
		this.getCaseNameByAjxxgk();
		
		this.caseCount = this.caseNameList.size();
		
		if ( this.caseNameList == null || this.caseNameList.size() == 0 || this.caseNameList.size() < 100) {
			log.info("caseNameList : {}" , this.caseNameList);
			throw new RuntimeException("案件名称List数据错误.");
		}
		
		// 测试开始之前初始化数据, 获取各个部门的所有角色以及处理人
		log.info("获取部门信息..");
		
		log.info("写死暂时..");
		
		// 初始化案件相关的数据
		
		// 初始化审批意见
		
		// 定义每个节点所生成的案件数量
	}
	
	
	public void createCase(User zf, User fz, User shr, User spr, String deptId) {
		log.info("create case test is start.");
		
		Random rand = new Random();
		TempCase tempCase = null;
		rand = new Random();
		
		Integer ajslCount = rand.nextInt(20) + 1;
		
		// 案件暂存
		for ( int i = 0 ; i < ajslCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
		}
		
		Integer cbhsCount = rand.nextInt(20) + 1;
		// 初步核实
		for ( int i = 0 ; i < cbhsCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toCbshTask(caseEntity, zf);
		}
		
		Integer xcclCount = rand.nextInt(20) + 1;
		// 现场处理
		for ( int i = 0 ; i < xcclCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toCbshXccl(caseEntity, zf);
		}
		
		Integer jygycfCount = rand.nextInt(20) + 1;
		// 简易 给与处罚
		for ( int i = 0 ; i < jygycfCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toJygycf(caseEntity, zf);
		}
		
		
		Integer jyjaCount = rand.nextInt(20) + 1;
		// 建议 结案
		for ( int i = 0 ; i < jyjaCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toJyja(caseEntity, zf);
		}
		
		Integer jygyjgCount = rand.nextInt(20) + 1;
		// 建议 给与警告
		for ( int i = 0 ; i < jygyjgCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toJygyjg(caseEntity, zf);
		}
		
		Integer jybyclCount = rand.nextInt(20) + 1;
		// 简易 不予处理
		for ( int i = 0 ; i < jybyclCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toBycl(caseEntity, zf);
		}
		
		Integer yblaCount = rand.nextInt(20) + 1;
		// 一般 立案
		for ( int i = 0 ; i < yblaCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbla(caseEntity, zf);
		}
		
		Integer yblashCount = rand.nextInt(20) + 1;
		// 一般 立案审核
		for ( int i = 0 ; i < yblashCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYblash(caseEntity, zf, shr);
		}
		
		Integer yblaspCount = rand.nextInt(20) + 1;
		// 一般 立案审批
		for ( int i = 0 ; i < yblaspCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYblasp(caseEntity, zf, shr, spr);
		}
		
		Integer ybbylaCount = rand.nextInt(20) + 1;
		// 一般 不予立案
		for ( int i = 0 ; i < ybbylaCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbbyla(caseEntity, zf, shr, spr);
		}
		
		Integer ybcxlaCount = rand.nextInt(20) + 1;
		// 一般 不予立案
		for ( int i = 0 ; i < ybcxlaCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbcxla(caseEntity, zf, shr, spr);
		}
		
		Integer ybdcqzCount = rand.nextInt(20) + 1;
		// 一般 调查取证
		for ( int i = 0 ; i < ybdcqzCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbdcqz(caseEntity, zf, shr, spr);
		}
		
		Integer ybajhyCount = rand.nextInt(20) + 1;
		// 一般 调查取证
		for ( int i = 0 ; i < ybajhyCount ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbajhy(caseEntity, zf, shr, spr);
		}
		
		Integer ybxzcxlaCount = rand.nextInt(20) + 1;
		// 一般 案件合一  选择撤销立案
		for ( int i = 0 ; i < ybxzcxlaCount	 ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbxzcxla(caseEntity, zf, shr, spr);
		}
		
		Integer ybanjdtjCount = rand.nextInt(20) + 1;
		// 一般 案件结案 提交
		for ( int i = 0 ; i < ybanjdtjCount	 ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbajjatj(caseEntity, zf, shr, spr);
		}
		
		Integer ybzdajCount = rand.nextInt(20) + 1;
		// 一般 案件结案 提交
		for ( int i = 0 ; i < ybzdajCount	 ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbzdajclbq(caseEntity, zf, shr, spr);
		}
		
		Integer ybzdajfzshCount = rand.nextInt(20) + 1;
		// 一般 案件结案 提交
		for ( int i = 0 ; i < ybzdajfzshCount	 ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbzdajfzsh(caseEntity, zf, shr, spr);
		}
		
		Integer ybqzzxCount = rand.nextInt(20) + 1;
		// 一般 案件结案 提交
		for ( int i = 0 ; i < ybqzzxCount	 ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbqzzx(caseEntity, zf, shr, spr);
		}
		
		Integer ybfyCount = rand.nextInt(20) + 1;
		// 一般 案件结案 提交
		for ( int i = 0 ; i < ybfyCount	 ; i ++ ) {
			Integer year = this.years.get(rand.nextInt(this.years.size()));
			Integer month = rand.nextInt(11);
			Integer day = rand.nextInt(28);
			
			Calendar ca = Calendar.getInstance();
			ca.set(year, month, day);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tempCase = this.caseNameList.get(rand.nextInt(this.caseCount));
			CaseEntity caseEntity = new CaseEntity();
			caseEntity.setCaseSource(String.valueOf(rand.nextInt(6)));
			caseEntity.setBriefCaseContent(tempCase.getContent());
			caseEntity.setCaseAddress("案发地址" + FlowTest.genRandomNum(8));
			caseEntity.setCaseApplyDate(ca.getTime());
			caseEntity.setCaseFzfryid(fz.getPersonId());
			caseEntity.setCaseFzfryname(fz.getPersonName());
			caseEntity.setCaseHandler("经办人" + FlowTest.genRandomNum(4));
			caseEntity.setCaseName(tempCase.getCaseName());
			caseEntity.setCaseNum("TEST-CF-" + sdf.format(ca.getTime()) + "-" + FlowTest.genRandomNum(6));
			caseEntity.setCaseReason("案由" + FlowTest.genRandomNum(8));
			caseEntity.setCaseStatus(CommonParameters.CaseStatus.AJZC);
			caseEntity.setCaseTime(ca.getTime());
			caseEntity.setCaseType("1");
			caseEntity.setCaseZpdate(ca.getTime());
			caseEntity.setCaseZpr(zf.getUserId());
			caseEntity.setCaseZzfryid(zf.getPersonId());
			caseEntity.setCaseZzfryname(zf.getPersonName());
			caseEntity.setCreateBy(zf.getPersonId());
			caseEntity.setCreateDate(ca.getTime());
			caseEntity.setCreateName(zf.getPersonName());
			caseEntity.setDeptId(deptId);
			caseEntity.setId(UUID.randomUUID().toString());
			
			this.caseRepository.save(caseEntity);
			
			this.toYbfy(caseEntity, zf, shr, spr);
		}
	}
	
	*//**
	 * 到指定节点的一个案件,首先必须有案件编号
	 *//*
	public void toCbshTask(CaseEntity caseInfoDTO, User zf) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);

	}
	
	*//**
	 * 到指定节点的一个案件,首先必须有案件编号
	 *//*
	public void toCbshXccl(CaseEntity caseInfoDTO, User zf) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择现场处理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("3");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);

	}
	
	public void toJygycf(CaseEntity caseInfoDTO, User zf) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择现场处理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("3");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 现场处理提交，给与处罚
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("5");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);

	}
	
	public void toJyja(CaseEntity caseInfoDTO, com.orhonit.ole.sys.model.User zf) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择现场处理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("3");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 现场处理提交，给与处罚 到结案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("5");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 简易结案到 案件归档
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_EASY_FILE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);

	}
	
	public void toJygyjg(CaseEntity caseInfoDTO, User zf) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择现场处理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("3");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 现场处理提交，给与警告
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("4");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);

	}
	
	public void toBycl(CaseEntity caseInfoDTO, User zf) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择现场处理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("1");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbla(CaseEntity caseInfoDTO, User zf) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYblash(CaseEntity caseInfoDTO, User zf, User shr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYblasp(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbbyla(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 不予立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("6");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbcxla(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 重新立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("8");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbdcqz(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbajhy(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 6 调查取证 提交 案件合一
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_INVESTIGATING_SEARCHING.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbxzcxla(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 6 调查取证 提交 案件合一
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_INVESTIGATING_SEARCHING.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 7 案件合一 选择 撤销立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("11");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbajjatj(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 6 调查取证 提交 案件合一
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_INVESTIGATING_SEARCHING.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 7 案件合一 选择 撤销立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("11");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 8 结案提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_COMMONLY_FILE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbzdajclbq(CaseEntity caseInfoDTO,User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 6 调查取证 提交 案件合一
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_INVESTIGATING_SEARCHING.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 7 选择 重大案件受理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("13");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 8 重大案件受理 提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_MAJOR_CASE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 9 重大案件审核 提交 选择 材料不全
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("18");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
	}
	
	public void toYbzdajfzsh(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 6 调查取证 提交 案件合一
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_INVESTIGATING_SEARCHING.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 7 选择 重大案件受理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("13");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 8 重大案件受理 提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_MAJOR_CASE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 9 重大案件审核 提交 选择 法制审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("19");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 10 法制审核提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("20");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
	}
	
	public void toYbqzzx(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 6 调查取证 提交 案件合一
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_INVESTIGATING_SEARCHING.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 7 案件合一 选择 不予处罚
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("10");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 8 申请案件受理提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CASE_HANDLE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 9 案件处理审核提交 
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CASE_HANDLE_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 案件处理审核 到 案件处理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CASE_HANDLE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 选择 申请强制执行
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("23");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 申请强制执行提交 到审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_ENFORCEMENT_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 强制执行审核提交  到审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_ENFORCEMENT_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 审批到 强制执行
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_ENFORCEMENT.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 强制执行提交 结案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CLOSED_APPLY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 结案申请提交 到审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CLOSED_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 审核 到 审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CLOSED_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 审批到 结案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CASE_CLOSED.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 结案到 归档
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_COMMONLY_FILE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
	}
	
	public void toYbfy(CaseEntity caseInfoDTO, User zf, User shr, User spr) {
		
		Random rand = new Random();

		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setAssignee(zf.getUserId().toString());
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_PRE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, true, zf);
		this.flowService.startFlowByKey(flowDTO);
		
		// 2 初步核实，选择立案
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("2");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 3 立案 - 立案审核
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(shr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 4 立案审核 - 立案审批
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(shr.getUserId());
		flowDTO.setNextAssignee(spr.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_FILING_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 5 立案审批 提交 调查取证
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(spr.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("7");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 6 调查取证 提交 案件合一
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_INVESTIGATING_SEARCHING.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 7 案件合一 选择 不予处罚
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("10");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 8 申请案件受理提交
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CASE_HANDLE_VERIFY.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 9 案件处理审核提交 
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CASE_HANDLE_APPROVE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 案件处理审核 到 案件处理
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setSingleMode(CommonParameters.SimpleFlow.TO_CASE_HANDLE.toString());
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
		// 选择 申请复议
		flowDTO = new FlowDTO();
		flowDTO.setFlowKey(CommonParameters.FlowKey.CASE);
		flowDTO.setBusinessId(caseInfoDTO.getCaseNum());
		flowDTO.setAssignee(zf.getUserId());
		flowDTO.setNextAssignee(zf.getUserId());
		flowDTO.setComment(comments.get(rand.nextInt(comments.size())));
		flowDTO.setHandleMode("24");
		this.saveTaskEntity(flowDTO, false, zf);
		this.flowService.taskComplete(flowDTO);
		
	}
	

	private void getCaseNameByAjxxgk() {
		String baseUrl = "http://www.ajxxgk.jcy.cn";
		try {
			String json = redisTemplate.opsForValue().get("case_name:temp");
			if (!StringUtils.isEmpty(json)) {
				
				List<TempCase> tempCases = JSONObject.parseArray(json, TempCase.class);

				this.caseNameList = tempCases;
			}
			if ( caseNameList == null || caseNameList.size() == 0 ) {
				this.caseNameList = new ArrayList<TempCase>();
				TempCase tempCase = null;
				for ( int i = 2 ; i <= 12 ; i ++ ) {
					String url = "http://www.ajxxgk.jcy.cn/html/gj/nmg/zdajxx/" + i + ".html";
					Document document = Jsoup.connect(url)
							.header("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
							.get();
					Elements contentElements = document.getElementById("page_0").getElementsByClass("content");
					
					for ( Element content : contentElements) {
						
						Elements names = content.select("ul").select("li").select("a[href~=^/html/2]");;
						
						for ( Element name : names ) {
							log.info("获取案件详细信息....");
							tempCase = new TempCase();
							tempCase.setCaseName(name.html());
							String contentUrl = baseUrl + name.attr("href");
							Document xxDocument = Jsoup.connect(contentUrl)
									.header("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
									.get();
							Element xxElement = xxDocument.getElementById("show_xx").select("p").first();
							tempCase.setContent(xxElement.html());
							this.caseNameList.add(tempCase);
						}
					}
					String key = "temp";
					redisTemplate.opsForValue().set("case_name:" + key, JsonUtil.toJson(JSONObject.toJSON(this.caseNameList)), 86400,
							TimeUnit.SECONDS);
				}
			}
		}catch(Exception e ) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void saveTaskEntity(FlowDTO flowDTO, Boolean isStart, User user) {
		CaseDealEntity casedeal = new CaseDealEntity();
		casedeal.setIsDeal(CommonParameters.Effect.NOT_EFFECT);
		if ( flowDTO.getFlowKey().equals(CommonParameters.FlowKey.CASE)) {
			CaseEntity caseEntity = this.caseRepository.findByCaseNum(flowDTO.getBusinessId());
			if ( isStart ) {
				casedeal.setCaseStatus(CommonParameters.CaseStatus.AJSL);
			} else {
				casedeal.setCaseStatus(caseEntity.getCaseStatus());
			}
			casedeal.setCaseId(caseEntity.getId());
			casedeal.setId(UUID.randomUUID().toString());
			casedeal.setCreateBy(user.getPersonName());
			casedeal.setMglCreateName(caseEntity.getMglCreateName());
			casedeal.setCreateDate(new Date());
			casedeal.setMglDealContent(caseEntity.getMglBriefCaseContent());
			casedeal.setCaseNum(flowDTO.getBusinessId());
			casedeal.setCreateName(user.getPersonName());
			if(flowDTO.getHandleMode() != null && !"".equals(flowDTO.getHandleMode())){
				casedeal.setDealMode(flowDTO.getHandleMode());
			}else if(flowDTO.getSingleMode() != null && !"".equals(flowDTO.getSingleMode())){
				casedeal.setDealMode(flowDTO.getSingleMode());
			}
			casedeal.setDealContent(flowDTO.getComment());
			if(flowDTO.getDealType() != null){
				casedeal.setDealType(Integer.valueOf(flowDTO.getDealType()).intValue());
			}
			this.caseDealRepository.save(casedeal);
		}
	}
	*/
	public static String genRandomNum(int length) {
		final int maxNum = 36;
		int i;
		int count = 0;
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z'};

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < length) {
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}
	
	public static void main(String[] args) throws Exception{
		
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) + 3);
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        System.out.println(DateFormatUtils.format(ca.getTime(), pattern));
	}

}
