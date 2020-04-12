package com.orhonit.ole.tts.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 在线执法启动-加载数据
 * @author ebusu
 *
 */
@Component
@Slf4j(topic="startup")
public class TTSStartupRunner implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		// 配置文件相关
		System.out.println("=======================加载yml配置参数===================");
		log.info("TTS-监督预警系统启动成功.");
		System.out.println("=======================加载完毕==========================");
	}
}
