package com.orhonit.ole.enforce;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.orhonit.ole.report.entity.DocTemplateEntity;
import com.orhonit.ole.report.repository.DocTemplateRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DocTemplateTest {
	
	@Autowired
	private DocTemplateRepository docTemplateRepository;

	@Test
	public void saveDocTemplate() {
		DocTemplateEntity entity = new DocTemplateEntity();
		entity.setId(UUID.randomUUID().toString());
		entity.setContent("test");
		entity.setCreateDate(new Date());
		entity.setCreateName("testCreateName");
		entity.setCreateBy("create by ....");
		entity.setIsEffect(1);
		entity.setName("汉字测试");
		entity.setUpdateBy("我更新了");
		entity.setUpdateDate(new Date());
		entity.setUpdateName("更新人的姓名");
		entity = docTemplateRepository.save(entity);
		log.info("新增记录成功,id={}",entity.getId());
	}
}
