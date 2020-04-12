package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreAppraisalFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 评比附件表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-26 17:20:44
 */
public interface CoreAppraisalFileService extends IService<CoreAppraisalFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CoreAppraisalFileEntity> getByAppraisalId(String appraisalId);
}

