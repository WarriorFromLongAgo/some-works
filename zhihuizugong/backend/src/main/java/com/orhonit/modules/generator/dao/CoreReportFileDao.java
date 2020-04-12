package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreReportFileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 党务公开附件表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
@Mapper
public interface CoreReportFileDao extends BaseMapper<CoreReportFileEntity> {

    @Select("select * from core_report_file where report_id = #{reportId}")
    List<CoreReportFileEntity> getById(String reportId);

    @Delete("DELETE FROM `core_report_file` WHERE report_id = #{reportId}")
    void removeById(String reportId);

}
