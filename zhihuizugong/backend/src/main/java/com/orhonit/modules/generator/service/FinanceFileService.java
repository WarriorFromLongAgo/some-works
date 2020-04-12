package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.FinanceFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 财务管理附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:00
 */
public interface FinanceFileService extends IService<FinanceFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<FinanceFileEntity> wordList(String financeId);

	List<FinanceFileEntity> imageList(String financeId);

	List<FinanceFileEntity> audioList(String financeId);

	List<FinanceFileEntity> videoList(String financeId);

	List<FinanceFileEntity> allList(String financeId);
}

