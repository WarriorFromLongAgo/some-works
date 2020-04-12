package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreAppraisalFileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 评比附件表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-26 17:20:44
 */
@Mapper
public interface CoreAppraisalFileDao extends BaseMapper<CoreAppraisalFileEntity> {

    @Select("select * from core_appraisal_file where appraisal_id = #{appraisalId}")
    List<CoreAppraisalFileEntity> getByAppraisalId(String appraisalId);

    @Delete("delete from core_appraisal_file where appraisal_id = #{appraisalId}")
    void deleteByAppraisalFileId(String appraisalId);

}
