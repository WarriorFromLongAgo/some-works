package com.orhonit.ole.enforce.advice;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.util.StringUtils;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.FlowDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaseTaskHandler implements TaskListener {
	
	private CaseService caseService;
	
	// private String testName;
	
	private Expression caseStatus;  
	
	private ProcessEngine processEngine;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		FlowDTO flowDTO = ThreadLocalVariables.getFlowDTO();
		
		if ( this.caseService == null ) {
			caseService = SpringContext.getBean(CaseService.class);
		} else {
			System.out.println("caseService is not null.");
		}
		if ( this.processEngine == null ) {
			this.processEngine = SpringContext.getBean(ProcessEngine.class);
		}
		if ( !StringUtils.isEmpty(caseStatus.getExpressionText()) 
				&& Integer.valueOf(caseStatus.getExpressionText()).intValue() != CommonParameters.CaseStatus.AJZC) {
			String businessKey = processEngine.getRuntimeService()
					.createProcessInstanceQuery()
					.processInstanceId(delegateTask.getProcessInstanceId())
					.singleResult()
					.getBusinessKey();
			this.caseService.updateCaseStatusByCaseNum(businessKey, Integer.valueOf(caseStatus.getExpressionText()), this.getFlowTypeByCaseStatus(Integer.valueOf(caseStatus.getExpressionText())));
		}
		delegateTask.setAssignee(flowDTO.getNextAssignee());
	}
	
	private Integer getFlowTypeByCaseStatus(Integer caseStatus ) {
		if ( caseStatus.intValue() == CommonParameters.CaseStatus.XCCL ) {
			return Integer.valueOf(CommonParameters.FlowType.SIMPLE_CASE);
		}
		else if ( caseStatus.intValue() == CommonParameters.CaseStatus.AJLA ) {
			return Integer.valueOf(CommonParameters.FlowType.NORMAL_CASE);
		}
		else if ( caseStatus.intValue() == CommonParameters.CaseStatus.FZSH ) {
			return Integer.valueOf(CommonParameters.FlowType.MAJOR_CASE);
		}
		return null;
	}

}
