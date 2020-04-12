package com.orhonit.ole.enforce.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "share")
@Data
public class ShareUrlConfig {

	private List<Map<String, String>> urls;
	
}


