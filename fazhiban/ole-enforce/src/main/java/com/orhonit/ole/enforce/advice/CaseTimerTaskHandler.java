package com.orhonit.ole.enforce.advice;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.identity.Authentication;

import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.PartyInfoEntity;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.party.PartyInfoService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;

public class CaseTimerTaskHandler implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		
		String businessKey = delegateExecution.getProcessBusinessKey();
		
		CaseService caseService = SpringContext.getBean(CaseService.class);
		
		CaseEntity caseEntity = caseService.getCaseByCaseNum(businessKey);
		
		UserService userService = SpringContext.getBean(UserService.class);
		
		User zzfryUser = userService.getUserByPersonId(caseEntity.getCaseZzfryid());
		
		PartyInfoService partyInfoService = SpringContext.getBean(PartyInfoService.class);
		
		PartyInfoEntity partyInfoEntity = partyInfoService.findByCaseId(caseEntity.getId());
		
		FlowDTO flowDTO = new FlowDTO();
		
		flowDTO.setNextAssignee(zzfryUser.getId().toString());
		
		flowDTO.setAssignee(partyInfoEntity.getId());
		
		String pid = delegateExecution.getProcessInstanceId();
		
		TaskService taskService = SpringContext.getBean(TaskService.class);
		
		String taskId = taskService.createTaskQuery()
				.processInstanceId(pid)
				.taskAssignee(flowDTO.getAssignee())
				.singleResult()
				.getId();
		
		Authentication.setAuthenticatedUserId(flowDTO.getAssignee());
		
		taskService.addComment(taskId, pid, "由于当事人在指定期限内未提交申请，系统自动跳转到下一节点。");
		
		ThreadLocalVariables.setFlowDTO(flowDTO);
	}

}
