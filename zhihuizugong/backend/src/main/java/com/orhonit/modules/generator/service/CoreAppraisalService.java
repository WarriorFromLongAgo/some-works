package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreAppraisalEntity;

import java.util.Map;

/**
 * 每季评比表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
public interface CoreAppraisalService extends IService<CoreAppraisalEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(CoreAppraisalEntity coreAppraisal);

    void deleteByAppraisalId(String appraisalId);
}

