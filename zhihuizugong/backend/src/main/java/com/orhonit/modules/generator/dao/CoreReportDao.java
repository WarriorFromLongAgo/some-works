package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreReportEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 每季报告表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
@Mapper
public interface CoreReportDao extends BaseMapper<CoreReportEntity> {
    void insertReport(CoreReportEntity coreReport);

    @Delete("delete from core_report where report_id = #{reportId}")
    void deleteByReportId(String reportId);
}
