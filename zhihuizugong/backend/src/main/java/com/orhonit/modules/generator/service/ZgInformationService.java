package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgInformationEntity;

import java.util.Map;

/**
 * 资讯表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-15 17:40:51
 */
public interface ZgInformationService extends IService<ZgInformationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils findAll(Map<String, Object> params);
}

