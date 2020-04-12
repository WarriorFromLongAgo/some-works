package com.orhonit.ole.enforce.advice;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.util.StringUtils;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.FlowDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckProTaskHandler implements TaskListener {
	
	private LssuedService lssuedService;
	
	// private String testName;
	
	private Expression checkStatus;  
	
	private ProcessEngine processEngine;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		FlowDTO flowDTO = ThreadLocalVariables.getFlowDTO();
		
		if ( this.lssuedService == null ) {
			lssuedService = SpringContext.getBean(LssuedService.class);
		} else {
			System.out.println("lssuedService is not null.");
		}
		if ( this.processEngine == null ) {
			this.processEngine = SpringContext.getBean(ProcessEngine.class);
		}
		if ( !StringUtils.isEmpty(checkStatus.getExpressionText())
				&& Integer.valueOf(checkStatus.getExpressionText()).intValue() != CommonParameters.CheckStatus.NOTIFICATIONS) {
			String businessKey = processEngine.getRuntimeService()
					.createProcessInstanceQuery()
					.processInstanceId(delegateTask.getProcessInstanceId())
					.singleResult()
					.getBusinessKey();
			this.lssuedService.updateCaseStatusByCaseNum(businessKey, Integer.valueOf(checkStatus.getExpressionText()));
			if ( checkStatus.getExpressionText().equals(CommonParameters.CheckStatus.CASE_ADMISS.toString())) {
				this.lssuedService.updateCaseAcceptByCheckNum(businessKey, checkStatus.getExpressionText());
			}
		}
		delegateTask.setAssignee(flowDTO.getNextAssignee());
	}

}
