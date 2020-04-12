package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreSystemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 党建制度表
 * 
 * @author xiaobai
 * @date 2019-05-18 11:14:27
 */
@Mapper
public interface CoreSystemDao extends BaseMapper<CoreSystemEntity> {

    @Select("select * from core_system")
    List<CoreSystemEntity> select();
}
