package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreReportFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 党务公开附件表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
public interface CoreReportFileService extends IService<CoreReportFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CoreReportFileEntity> getById(String reportId);

    void removeById(String reportId);
}

