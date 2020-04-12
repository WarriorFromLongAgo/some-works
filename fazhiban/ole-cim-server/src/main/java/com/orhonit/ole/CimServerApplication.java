package com.orhonit.ole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages="com.orhonit")
public class CimServerApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CimServerApplication.class, args);
	}
	
	
	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(CimServerApplication.class);  
    }  
}
