package com.orhonit.ole.warn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * 启动类
 * 监督预警
 * @author liuzh
 * 
 */
@SpringBootApplication(scanBasePackages="com.orhonit")
public class WarnApp {

	public static void main(String[] args) {
		SpringApplication.run(WarnApp.class, args);
	}

}
