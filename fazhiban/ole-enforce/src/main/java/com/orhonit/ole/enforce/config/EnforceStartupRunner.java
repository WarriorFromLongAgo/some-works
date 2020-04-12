package com.orhonit.ole.enforce.config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringAsyncExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.orhonit.ole.common.utils.SpringContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 在线执法启动-加载数据
 * @author ebusu
 *
 */
@Component
@Slf4j(topic="startup")
public class EnforceStartupRunner implements CommandLineRunner {
	
	@Autowired
	private FlowConfig flowConfig;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	@Override
	public void run(String... args) throws Exception {

		// 配置文件相关
		System.out.println("=======================加载yml配置参数===================");
		
		log.info("流程模块：IP = {}， 端口 = {}， 上下文 = {}", flowConfig.getIp(), flowConfig.getPort(), flowConfig.getModulePath());
		if ( flowConfig.getApi() != null) {
			
			flowConfig.getApi().forEach(
					item->log.info("接口列表: {}" , item)
			);
		}
		
		
		System.out.println("=======================加载完毕==========================");
		// 数据库相关
		
		SpringAsyncExecutor sae = SpringContext.getBean(SpringAsyncExecutor.class);
		
		sae.setDefaultAsyncJobAcquireWaitTimeInMillis(60 * 60 * 1000);
		sae.setDefaultTimerJobAcquireWaitTimeInMillis(60 * 60 * 1000);
		
	}

}
