package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreWorkDynamicEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工作队动态表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-05-10 16:46:14
 */
@Mapper
public interface CoreWorkDynamicDao extends BaseMapper<CoreWorkDynamicEntity> {
    void insertDynamic(CoreWorkDynamicEntity coreWorkDynamic);

    @Delete("delete from core_work_dynamic where dynamic_id = #{dynamicId}")
    void deleteByDynamicId(String dynamicId);

    @Delete("delete from core_work_dynamic where serve_id = #{serveId}")
    void deleteByServeId(String serveId);
}
