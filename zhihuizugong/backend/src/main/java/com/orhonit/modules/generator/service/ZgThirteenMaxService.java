package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgThirteenMaxEntity;

import java.util.Map;

/**
 * 个人十三边型画像最高值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 15:30:46
 */
public interface ZgThirteenMaxService extends IService<ZgThirteenMaxEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ZgThirteenMaxEntity zgThirteenMax);
}

