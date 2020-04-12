package com.orhonit.modules.generator.service;


import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgWorkScheduleEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-05 17:52:46
 */
public interface ZgWorkScheduleService extends IService<ZgWorkScheduleEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void save(ZgWorkScheduleEntity zgWorkSchedule);
}

