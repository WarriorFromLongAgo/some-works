package com.orhonit.ole.report;

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
public class ReportApp  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ReportApp.class, args);
	}
	
	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(ReportApp.class);  
    }  

}
