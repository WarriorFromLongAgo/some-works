package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreAppraisalEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 每季评比表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
@Mapper
public interface CoreAppraisalDao extends BaseMapper<CoreAppraisalEntity> {
    void save(CoreAppraisalEntity coreAppraisal);

    @Delete("delete from core_appraisal where appraisal_id = #{appraisalId}")
    void deleteByAppraisalId(String appraisalId);
}
