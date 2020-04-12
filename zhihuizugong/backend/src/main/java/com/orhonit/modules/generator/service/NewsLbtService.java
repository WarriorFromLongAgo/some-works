package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.NewsLbtEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zjw
 * @email sunlightcs@gmail.com
 * @date 2019-01-14 11:09:47
 */
public interface NewsLbtService extends IService<NewsLbtEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<NewsLbtEntity> getALLlist();
}

