package com.orhonit.modules.generator.service;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.EjSchedulingRecordEntity;

import java.util.Map;

/**
 * 调度记录表(完成情况/督办情况)
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
public interface EjSchedulingRecordService extends IService<EjSchedulingRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

