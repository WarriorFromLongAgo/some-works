package com.orhonit.ole.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
 
/**
 * 启动类
 * 
 * @author caodw
 *
 *
 */
@SpringBootApplication(scanBasePackages="com.orhonit")
public class BaseApplication extends SpringBootServletInitializer {
 
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
	
	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(BaseApplication.class);  
    }  

}
