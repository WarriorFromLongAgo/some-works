package com.orhonit.modules.generator.service;


import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgPortrayalEntity;

/**
 * 组工画像
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-10 10:34:45
 */
public interface ZgPortrayalService extends IService<ZgPortrayalEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void save(ZgPortrayalEntity zgPortrayal);

    void updatePortrayal(ZgPortrayalEntity zgPortrayal);
}

