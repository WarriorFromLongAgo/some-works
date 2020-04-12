package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreWorkCrewEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 工作队全队人员
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-10 10:11:20
 */
@Mapper
public interface CoreWorkCrewDao extends BaseMapper<CoreWorkCrewEntity> {
    @Select("select * from core_work_crew where serve_id = #{serveId}")
    List<CoreWorkCrewEntity> getByServeId(String serveId);

    @Delete("delete from core_work_crew where serve_id = #{serveId}")
    void deleteByServeId(String serveId);
}
