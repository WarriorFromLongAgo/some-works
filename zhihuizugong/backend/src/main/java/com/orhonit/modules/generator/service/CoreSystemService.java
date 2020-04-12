package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreSystemEntity;

import java.util.List;
import java.util.Map;

/**
 * 党建制度表
 *
 * @author xiaobai
 * @date 2019-05-18 11:14:27
 */
public interface CoreSystemService extends IService<CoreSystemEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CoreSystemEntity> queryPage1();

}

