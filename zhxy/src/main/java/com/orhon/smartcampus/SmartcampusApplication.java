package com.orhon.smartcampus;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan({"com.alibaba.fastjson.support.spring", "com.orhon.smartcampus"})
@EnableTransactionManagement
public class SmartcampusApplication {


    public static void main(String[] args) {
        SpringApplication.run(SmartcampusApplication.class, args);
    }
}
