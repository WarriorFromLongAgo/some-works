package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreOpinionFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 意见附件
 *
 * @author xiaobai
 * @date 2019-05-13 14:37:50
 */
public interface CoreOpinionFileService extends IService<CoreOpinionFileEntity> {

	PageUtils queryPage(Map<String, Object> params);

	List<CoreOpinionFileEntity> getById(String opinionId);

}
