package com.orhonit.ole.tts.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("testTask")
public class TestTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//定时任务只能接受一个参数；如果有多个参数，使用json数据即可
	public void test(String params){
		System.out.println("带参数的test方法，正在被执行，参数为：" + params);
	}
	
	public void test2(){
		System.out.println("不带参数的test2方法，正在被执行");
	}
}
