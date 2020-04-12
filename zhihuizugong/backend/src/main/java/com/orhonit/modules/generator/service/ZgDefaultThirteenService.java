package com.orhonit.modules.generator.service;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgDefaultThirteenEntity;

import java.util.Map;

/**
 * 个人画像十二边型默认值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 11:25:16
 */
public interface ZgDefaultThirteenService extends IService<ZgDefaultThirteenEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ZgDefaultThirteenEntity zgDefaultThirteen);
}

