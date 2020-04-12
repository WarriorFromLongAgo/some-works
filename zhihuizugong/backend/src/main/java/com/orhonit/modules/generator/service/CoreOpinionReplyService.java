package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreOpinionReplyEntity;

import java.util.Map;

/**
 * 
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 16:10:56
 */
public interface CoreOpinionReplyService extends IService<CoreOpinionReplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

