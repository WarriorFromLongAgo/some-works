package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreReportEntity;

import java.util.Map;

/**
 * 每季报告表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
public interface CoreReportService extends IService<CoreReportEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void insertReport(CoreReportEntity coreReport);

    void deleteByReportId(String reportId);

}

