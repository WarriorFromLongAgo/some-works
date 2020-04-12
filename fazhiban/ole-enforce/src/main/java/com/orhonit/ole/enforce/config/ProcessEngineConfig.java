package com.orhonit.ole.enforce.config;

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * processEngine配置类
 * 后期需要配置来优化activiti工作流
 * @author ebusu
 *
 */
public class ProcessEngineConfig {

	/*@Bean
	public StandaloneProcessEngineConfiguration setFalse(){
		StandaloneProcessEngineConfiguration  aa= new StandaloneProcessEngineConfiguration();
		aa.setJobExecutorActivate(false);
		return  aa;
	}*/
}
