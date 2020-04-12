package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.OverseeFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 领导督办附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
public interface OverseeFileService extends IService<OverseeFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<OverseeFileEntity> wordList(String overseeId);

	List<OverseeFileEntity> imageList(String overseeId);

	List<OverseeFileEntity> audioList(String overseeId);

	List<OverseeFileEntity> videoList(String overseeId);
}

