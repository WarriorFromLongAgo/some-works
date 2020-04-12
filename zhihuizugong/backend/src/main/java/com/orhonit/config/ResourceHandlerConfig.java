package com.orhonit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



/**
 * 
 * Title: ResourceHandlerConfig.java
 * @Description: 本地资源映射路径需要配置一下映射资源位置
 * @author YaoSC
 * @date 2019年6月22日
 */
@Configuration
public class ResourceHandlerConfig extends WebMvcConfigurerAdapter{


	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/image/**").addResourceLocations("file:E:/ftp/");
	    }

}
