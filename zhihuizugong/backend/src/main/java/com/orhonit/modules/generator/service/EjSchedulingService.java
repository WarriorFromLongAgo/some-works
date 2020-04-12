package com.orhonit.modules.generator.service;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.EjSchedulingEntity;

import java.util.Map;

/**
 * 调度主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
public interface EjSchedulingService extends IService<EjSchedulingEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(EjSchedulingEntity ejScheduling);

    EjSchedulingEntity selectSchedulingInfo(String id);

    EjSchedulingEntity selectschedulingTaskInfo(String id,Long userId);

    void updateStatus(String schedulingId);
}

