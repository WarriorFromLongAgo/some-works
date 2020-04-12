package com.orhonit.ole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.orhonit")
public class SysServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysServerApplication.class, args);
	}

}
