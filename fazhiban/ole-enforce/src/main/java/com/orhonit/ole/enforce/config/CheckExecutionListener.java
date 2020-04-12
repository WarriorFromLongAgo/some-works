package com.orhonit.ole.enforce.config;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckExecutionListener implements ExecutionListener {

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
			
			log.info("ProcessDefinitionId : {}", execution.getProcessDefinitionId());
			if ( execution.getProcessDefinitionId().startsWith("dayCheck")) {
				CheckDailyService checkDailyService = SpringContext.getBean(CheckDailyService.class);
				checkDailyService.updateCheckStatusByCheckNum(execution.getProcessBusinessKey(), CommonParameters.CheckStatus.FINISHED.toString());
			} else if(execution.getProcessDefinitionId().startsWith("proCheck")){
				LssuedService lussedService = SpringContext.getBean(LssuedService.class);
				lussedService.updateCaseStatusByCaseNum(execution.getProcessBusinessKey(), CommonParameters.CheckStatus.FINISHED);
			}
			
		}
	}

}
