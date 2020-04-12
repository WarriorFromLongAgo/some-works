package com.orhonit.ole.enforce.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "flowModule")
@Data
public class FlowConfig {

	private String ip;
	
	private String port;
	
	private String modulePath;
	
	private List<Map<String, String>> api;
	
}


