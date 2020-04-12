package com.orhonit.ole.enforce.service.caseinfo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.sys.dto.FlowDTO;

import lombok.Data;

@Data
@Component
public class FlowThreadService implements Runnable{
	
	private FlowDTO flowDTO;
	
	private Integer flowType; // 1 启动流程 , 2 完成任务
	
	@Autowired
	private FlowService flowService;

	@Override
	public void run() {
		if (flowType.intValue() == 1) {
			this.flowService.startFlowByKey(this.flowDTO);
		}
		
	}

}
