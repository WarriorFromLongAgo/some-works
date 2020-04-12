package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreWorkDynamicFileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工作队动态附件表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
@Mapper
public interface CoreWorkDynamicFileDao extends BaseMapper<CoreWorkDynamicFileEntity> {

    @Delete("DELETE FROM `core_work_dynamic_file` WHERE dynamic_id = #{dynamicId}")
    void removeById(String dynamicId);
}
