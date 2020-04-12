package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgNearlyThreeYearsEntity;

import java.util.List;
import java.util.Map;

/**
 * 近三年考核情况,奖惩
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-19 16:50:11
 */
public interface ZgNearlyThreeYearsService extends IService<ZgNearlyThreeYearsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(List<ZgNearlyThreeYearsEntity> entityList);
}

