package com.orhonit.ole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.orhonit")
public class CimApplication{

	public static void main(String[] args) {
		SpringApplication.run(CimApplication.class, args);
	}
}
