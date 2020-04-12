package com.orhonit.modules.generator.controller;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.orhonit.modules.generator.entity.WywNoteEntity;


public class Chapter6ApplicationTests {
	
	@Autowired
    private RedisTemplate redisTemplate;
	@SuppressWarnings("unchecked")
	@Test
	public void text() {
		//redis存储数据
		String key="name";
		redisTemplate.opsForValue().set(key, "yongkong");
		//获取数据
		String value=(String) redisTemplate.opsForValue().get(key);
		System.out.println("获取缓存中key为" + key + "的值为：" + value);

		WywNoteEntity t = new WywNoteEntity();
		t.setNoteTitle("yongkong");
		t.setCreateTime(new Date());
		String  wywNoteEntity= "yongkong";
		redisTemplate.opsForValue().set(wywNoteEntity, t);
		WywNoteEntity entity = (WywNoteEntity) redisTemplate.opsForValue().get(wywNoteEntity);
		System.out.println("获取缓存中KEY为"+wywNoteEntity+"的值为"+entity);
		
	}

}
