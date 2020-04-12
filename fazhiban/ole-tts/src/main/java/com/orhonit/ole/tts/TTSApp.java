package com.orhonit.ole.tts;

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
public class TTSApp extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TTSApp.class, args);
	}
	
	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(TTSApp.class);  
    }  

}
