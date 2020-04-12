package com.orhonit.ole.tts.service.caseinfo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.service.flow.FlowService;

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
