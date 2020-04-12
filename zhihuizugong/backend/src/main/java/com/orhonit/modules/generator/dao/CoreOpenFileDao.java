package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreOpenFileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 党务公开附件表
 * 
 * @author xiaobai
 * @date 2019-05-22 17:55:44
 */
@Mapper
public interface CoreOpenFileDao extends BaseMapper<CoreOpenFileEntity> {

    @Select("select * from core_open_file where open_id = #{openId}")
    List<CoreOpenFileEntity> getById(String openId);

    @Delete("DELETE FROM `core_open_file` WHERE open_id = #{openId}")
    void deleteByOpenId(String openId);

}
