package com.orhonit.modules.generator.service;



import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;

/**
 * 领导点评
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 11:33:57
 */
public interface ZgRemarkService extends IService<ZgRemarkEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void save(ZgRemarkEntity zgRemark);

}

