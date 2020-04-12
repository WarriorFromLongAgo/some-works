package com.orhonit.ole.tts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "upload")
@Component
@Data
public class UploadConfig {
	
	private String winPath;
	
	private String otherPath;
	
	private String serverUrl;
	
	private String winCaseCatalogPath;
	
	private String otherCaseCatalogPath;
	
	private String winTemp;
	
	private String otherTemp;
}
