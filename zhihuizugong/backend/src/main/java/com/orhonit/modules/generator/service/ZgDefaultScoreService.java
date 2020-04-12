package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgDefaultScoreEntity;

import java.util.Map;

/**
 * 组工画像人员默认值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-18 15:58:12
 */
public interface ZgDefaultScoreService extends IService<ZgDefaultScoreEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ZgDefaultScoreEntity zgDefaultScore);
}

