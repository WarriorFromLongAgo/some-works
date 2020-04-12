package com.orhonit.modules.generator.service;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.EjSchedulingFileEntity;

import java.util.Map;

/**
 * 调度附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
public interface EjSchedulingFileService extends IService<EjSchedulingFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

