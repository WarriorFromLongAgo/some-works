package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgDefaultScoreDeptEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;

import java.util.Map;

/**
 * 组工画像科室默认值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-19 14:43:39
 */
public interface ZgDefaultScoreDeptService extends IService<ZgDefaultScoreDeptEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ZgDefaultScoreDeptEntity zgDefaultScoreDept);

    TaDepartmentMemberEntity findLowerName(Integer lowerId);
}

