package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.OsExamEntity;

import java.util.Map;

/**
 * 试卷表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-07 18:11:19
 */
public interface OsExamService extends IService<OsExamEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void insertOs(OsExamEntity osExam);
	
	void deleteOsExam(String examId);
}

