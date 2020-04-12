package com.orhonit.modules.generator.service;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.EjSchedulingPeopleEntity;

import java.util.Map;

/**
 * 调度参会人
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
public interface EjSchedulingPeopleService extends IService<EjSchedulingPeopleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateTaskClaim(EjSchedulingPeopleEntity ejSchedulingPeople);

    EjSchedulingPeopleEntity selectPeopleInfo(Integer id);
}

