package com.orhonit.ole.tts.service.flow.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.tts.config.ThreadLocalVariables;
import com.orhonit.ole.tts.dao.FlowDao;
import com.orhonit.ole.tts.dto.FlowCommentDTO;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.FlowTaskCommentDTO;
import com.orhonit.ole.tts.dto.NextOpinionDTO;
import com.orhonit.ole.tts.dto.TaskDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseDealDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseTaskDTO;
import com.orhonit.ole.tts.dto.api.ApiCheckTaskDTO;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.entity.PartyInfoEntity;
import com.orhonit.ole.tts.repository.PartyInfoRepository;
import com.orhonit.ole.tts.service.flow.FlowService;

@Service
public class FlowServiceImpl implements FlowService {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private FlowDao flowDao;
	
	@Autowired
	private PartyInfoRepository partyInfoRepository;

	@Override
	public String startFlowByKey(FlowDTO flowDTO) {
		
		String nextAssignee = flowDTO.getNextAssignee();
		flowDTO.setNextAssignee(flowDTO.getAssignee());
		
		ThreadLocalVariables.setFlowDTO(flowDTO);
		
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(flowDTO.getFlowKey(),flowDTO.getBusinessId());
		
		System.out.println("启动的案件办理流程数量 : " + runtimeService.createProcessInstanceQuery()
						.processInstanceBusinessKey(flowDTO.getBusinessId()).count());
		
		String pid = processInstance.getId();
		
		String taskId = taskService.createTaskQuery()
				.processInstanceId(pid)
				.taskAssignee(flowDTO.getAssignee())
				.singleResult()
				.getId();
		flowDTO.setNextAssignee(nextAssignee);
		ThreadLocalVariables.setFlowDTO(flowDTO);
		Authentication.setAuthenticatedUserId(flowDTO.getAssignee());
		taskService.addComment(taskId, pid, flowDTO.getComment());
		// 如果流程启动的时候就需要选择分支的话，需要传handleMode
		Map<String, Object>  variables = new HashMap<>();
		if ( !StringUtils.isEmpty( flowDTO.getHandleMode())) {
			variables.put("handleMode", flowDTO.getHandleMode() );
			taskService.complete(taskId, variables);
		} else {
			taskService.complete(taskId);
		}
		return pid;
	}

	@Override
	@Transactional
	public void taskComplete(String pid, String createName, Map<String, Object> variables, String comment) {
		String taskId = taskService.createTaskQuery()
			.processInstanceId(pid)
			.taskAssignee(createName)
			.singleResult()
			.getId();
		Authentication.setAuthenticatedUserId(createName);
		taskService.addComment(taskId, pid, comment);
		taskService.complete(taskId, variables);
	}

	@Override
	public List<TaskDTO> getUserTask(String assignee, Integer currentPage , Integer pageSize) {
		return flowDao.getUserTask(assignee, currentPage, pageSize);
	}

	@Override
	public String getProcessInstanceIdByKeyAndBusinessId(String key, String businessId) {
		
		String pid = "";
		ProcessInstance processInstance =  this.runtimeService.createProcessInstanceQuery()
			.processInstanceBusinessKey(businessId, key)
			.singleResult();
		if (processInstance == null) {
			pid = this.historyService.createHistoricProcessInstanceQuery()
					.processInstanceBusinessKey(businessId)
					.processDefinitionKey(key)
					.singleResult()
					.getId();
		} else {
			pid = processInstance.getId();
		}
		
		return pid;
	}

	@Override
	public List<FlowTaskCommentDTO> getCommemntByProcessInstanceId(String pid) {
		List<FlowTaskCommentDTO> flowTaskCommentDTOs = new ArrayList<>();
//		List<Comment> comments = taskService.getProcessInstanceComments(pid);
//		if ( comments != null ) {
//			comments.forEach(item->{  
//				FlowTaskCommentDTO dto = new FlowTaskCommentDTO();
//				dto.setComment(item.getFullMessage());
//				dto.setProcessInstanceId(item.getProcessInstanceId());
//				dto.setTaskId(item.getTaskId());
//				HistoricTaskInstance hti = this.processEngine.getHistoryService().createHistoricTaskInstanceQuery()
//						.taskId(item.getTaskId())
//						.taskAssignee(item.getUserId())
//						.singleResult();
//				dto.setTaskName(hti.getName());
//				dto.setTime(item.getTime());
//				dto.setUser(item.getUserId());
//				flowTaskCommentDTOs.add(dto);
//			});
//		}
		
		
//		return flowTaskCommentDTOs;
		
		return this.flowDao.getHisCommentByProcInstId(pid);
	}

	@Override
	public void taskComplete(FlowDTO flowDTO) {
		ThreadLocalVariables.setFlowDTO(flowDTO);
		String pid = this.getProcessInstanceIdByKeyAndBusinessId(flowDTO.getFlowKey(), flowDTO.getBusinessId());
		String taskId = taskService.createTaskQuery()
				.processInstanceId(pid)
				.taskAssignee(flowDTO.getAssignee())
				.singleResult()
				.getId();
		Authentication.setAuthenticatedUserId(flowDTO.getAssignee());
		taskService.addComment(taskId, pid, flowDTO.getComment());
		Map<String,Object> variables = new HashMap<String,Object>();
		if ( !StringUtils.isEmpty( flowDTO.getHandleMode())) {
			variables.put("handleMode", flowDTO.getHandleMode() );
		}
		
		/**
		 * 测试， 两分钟之后自动执行
		 */
//		Date date = new Date();
//		Calendar ca = Calendar.getInstance();
//		ca.setTime(date);
//		ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 2);
//		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
//        String timeDate = DateFormatUtils.format(ca.getTime(), pattern); 
		
		
		if ( !StringUtils.isEmpty( flowDTO.getTimeDate())) {
			Date date = new Date();
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) + Integer.valueOf(flowDTO.getTimeDate()));
			String pattern = "yyyy-MM-dd'T'HH:mm:ss";
			String timeDate = DateFormatUtils.format(ca.getTime(), pattern); 
			variables.put("hearingDate", timeDate );
		}
		
		taskService.complete(taskId, variables);
		ThreadLocalVariables.removeFlowDTO();
	}

	@Override
	public List<NextOpinionDTO> getHaveRoleOpinionByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.OPINION);
	}

	@Override
	public List<NextOpinionDTO> getApproveUserByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.APPROVE);
	}
	
	@Override
	public List<NextOpinionDTO> getFzHaveRoleCaseByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.FzCASE);
	}
	
	@Override
	public List<NextOpinionDTO> getFzHaveRoleOpinionByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.FzOPINION);
	}

	@Override
	public List<NextOpinionDTO> getFzApproveUserByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.FzApprove);
	}
	
	@Override
	public List<NextOpinionDTO> getFyReviewVerificationByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.FyOPINION);
	}

	@Override
	public List<NextOpinionDTO> getFyReconsiderationByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, CommonParameters.Role.FyApprove);
	}
	
	@Override
	public List<NextOpinionDTO> getFyContractorByDeptIdArea(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptIdForFZB(deptId,  CommonParameters.Role.FYCB);
	}
	
	@Override
	public List<NextOpinionDTO> getFySectionChiefByDeptIdArea(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptIdForFZB(deptId,  CommonParameters.Role.FYKZ);
	}
	
	@Override
	public List<NextOpinionDTO> getFyDirectorByDeptIdArea(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptIdForFZB(deptId,  CommonParameters.Role.FYZR);
	}
	
	@Override
	public List<NextOpinionDTO> getPersonByDeptId(String deptId) {
		return this.flowDao.getHaveRoleOpinionByDeptId(deptId, null);
	}
	
	@Override
	public PartyInfoEntity getPartyByCaseId(String caseId) {
		return this.partyInfoRepository.findByCaseId(caseId);
	}
	
	@Override
	public ProcessDefinition getProcessDefinitionByTaskId(String taskId) {
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)
				.singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId)
				.singleResult();
		return pd;
	}

	@Override
	public Map<String, Object> getCoordinateByTaskId(String taskId) {

		Map<String, Object> map = new HashMap<String,Object>();

		Task task = taskService.createTaskQuery()//
					.taskId(taskId)
					.singleResult();

		String processDefinitionId = task.getProcessDefinitionId();

		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);

		String processInstanceId = task.getProcessInstanceId();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId)
					.singleResult();
		
		String activityId = pi.getActivityId();
		
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}

	@Override
	public InputStream getResourceStream(String deployId, String imageName) {
		return this.repositoryService.getResourceAsStream(deployId, imageName);
	}

	@Override
	public String getTaskIdByCaseNum(String caseNum) {
		return this.flowDao.findTaskIdBycaseNum(caseNum);
	}

	@Override
	public List<FlowCommentDTO> getCommentByBusikeyAndTaskName(String busikey, String taskName) {
		return this.flowDao.getCommentByBusikeyAndTaskName(busikey, taskName);
	}

	@Override
	public InputStream getCoordinateAndFlowSeqByTaskId(String taskId,String key,String businessId) {
		Boolean taskFlag = false;
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = null;
		String executionId = null;
		if(task == null){
			ProcessInstance processInstance =  this.runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(businessId, key)
				.singleResult();
			if (processInstance == null) {
				HistoricProcessInstance historicProcessInstance = this.historyService.createHistoricProcessInstanceQuery()
						.processInstanceBusinessKey(businessId)
						.processDefinitionKey(key)
						.singleResult();
				processDefinitionId = historicProcessInstance
						.getProcessDefinitionId();
				executionId = historicProcessInstance.getId();
			} else {
				processDefinitionId = processInstance.getProcessDefinitionId();
				executionId = processInstance.getId();
			}
		}else{
			processDefinitionId = task.getProcessDefinitionId();
			executionId = task.getExecutionId();
			taskFlag = true;
		}
		
		List<String> activeActivityIds = null;
		
		if ( taskFlag ) {
			activeActivityIds = this.runtimeService.getActiveActivityIds(executionId);
		} else {
			List<HistoricActivityInstance> activityInstances = this.historyService.createHistoricActivityInstanceQuery().executionId(executionId).list();
			if ( activityInstances != null && activityInstances.size() > 0 ) {
				activeActivityIds = new ArrayList<>();
				for ( HistoricActivityInstance hai : activityInstances) {
					activeActivityIds.add(hai.getActivityId());
				}
			}
		}
		
		BpmnModel bpmnModel = this.repositoryService.getBpmnModel(processDefinitionId);
		
		DefaultProcessDiagramGenerator gen = new DefaultProcessDiagramGenerator();
		
		// 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）  
		List<HistoricActivityInstance> historicActivityInstances = this.historyService  
	            .createHistoricActivityInstanceQuery().executionId(executionId)  
	            .orderByHistoricActivityInstanceStartTime().asc().list(); 
		
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) this.repositoryService).getDeployedProcessDefinition(processDefinitionId);
		
		List<String> highLightedFlows = this.getHighLightedFlows(processDefinitionEntity, historicActivityInstances);
		
		// gen.generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows, activityFontName, labelFontName, annotationFontName, customClassLoader, scaleFactor);
		
		return gen.generateDiagram(bpmnModel, "png", activeActivityIds,highLightedFlows,"宋体","宋体","宋体",null,1.0);
		
	}
	
	private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId  
	    for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历  
	        ActivityImpl activityImpl = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i)  
	                        .getActivityId());// 得 到节点定义的详细信息  
	        List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点  
	        if ((i + 1) >= historicActivityInstances.size()) {  
	            break;  
	        }  
	        ActivityImpl sameActivityImpl1 = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i + 1)  
	                        .getActivityId());// 将后面第一个节点放在时间相同节点的集合里  
	        sameStartTimeNodes.add(sameActivityImpl1);  
	        for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {  
	            HistoricActivityInstance activityImpl1 = historicActivityInstances  
	                    .get(j);// 后续第一个节点  
	            HistoricActivityInstance activityImpl2 = historicActivityInstances  
	                    .get(j + 1);// 后续第二个节点  
	            if (activityImpl1.getStartTime().equals(  
	                    activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存  
	                ActivityImpl sameActivityImpl2 = processDefinitionEntity  
	                        .findActivity(activityImpl2.getActivityId());  
	                sameStartTimeNodes.add(sameActivityImpl2);  
	            } else {// 有不相同跳出循环  
	                break;  
	            }  
	        }  
	        List<PvmTransition> pvmTransitions = activityImpl  
	                .getOutgoingTransitions();// 取出节点的所有出去的线  
	        for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历  
	            ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition  
	                    .getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示  
	            if (sameStartTimeNodes.contains(pvmActivityImpl)) {  
	                highFlows.add(pvmTransition.getId());  
	            }  
	        }  
	    }  
	    return highFlows;  
	}

	@Override
	public List<NextOpinionDTO> getTzslReconsiderationByDeptId(String dept_id) {
		return this.flowDao.getHaveRoleOpinionByDeptId(dept_id, CommonParameters.Role.TZSL);
	}

	@Override
	public List<ApiCaseTaskDTO> getCaseTask(String flowType,String caseStatus,String caseName, String assignee,Integer queryDate, Integer start, Integer length) {		
		return this.flowDao.getCaseTask(flowType,caseStatus,caseName, assignee,queryDate, start, length);
	}

	@Override
	public List<ApiCheckTaskDTO> getCheckDailyTask(String checkStatus,String checkTitle, String assignee,Integer queryDate, Integer start, Integer length) {
		return this.flowDao.getCheckDailyTask(checkStatus,checkTitle, assignee,queryDate, start, length);
	}

	@Override
	public List<ApiCheckTaskDTO> getCheckTask(String checkStatus,String checkTitle, String assignee, Integer queryDate,Integer start, Integer length) {
		return this.flowDao.getCheckTask(checkStatus,checkTitle, assignee, queryDate,start, length);
	}

	@Override
	public List<LssuedEntity> getPcCheckTask(Map<String, Object> params, Integer start, Integer length) {
		return this.flowDao.getPcCheckTask(params , start, length);
	}

	@Override
	public int getPcCheckcount(Map<String, Object> params) {
		return this.flowDao.getPcCheckcount(params);
	}
	
	@Override
	public List<Map<String, String>> getPerson(String deptId) {
		return flowDao.getPersonByDeptId(deptId);
	}

}
