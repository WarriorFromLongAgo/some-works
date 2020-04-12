package com.orhonit.ole.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "ole.sso")
@Component
@Data
public class SsoConfig {
	
	private String tokenExpire;
	
	private String enforce;
	
	private String base;
	
	private String report;
	
	private String ps;
	
	private String pa;
	
	private String auth;
	
	private String tts;
}
