package com.orhonit.ole.enforce.config;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;

public class CaseExecutionListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String eventName = execution.getEventName();
		if ("start".equals(eventName)) {
			System.out.println("start=========");
		} else if ("end".equals(eventName)) {
			System.out.println("end=========");
			CaseService caseService = SpringContext.getBean(CaseService.class);
			caseService.updateCaseStatusByCaseNum(execution.getProcessBusinessKey(), CommonParameters.CaseStatus.ANGD,null);
			// 生成卷宗目录
			
		}
	}

}
