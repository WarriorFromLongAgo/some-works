package com.orhonit.ole.enforce.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.sys.service.VersionService;
/**
 * app版本控制器
 * @author wuyz
 *
 */
@RestController
@RequestMapping("/api/version")
public class ApiVersionController {
	
	@Autowired
	VersionService versionService;
	
	/**
	 * 进行最新版本比对
	 * @param versionCode
	 * @param versionName
	 * @return
	 */
    @GetMapping
    public Object version(){
    	return versionService.getNewVersion();
    }
    
}
