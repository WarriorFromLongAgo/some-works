package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgTaskFinishEntity;

import java.util.Map;

/**
 * 工作任务副表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:28:24
 */
public interface ZgTaskFinishService extends IService<ZgTaskFinishEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

