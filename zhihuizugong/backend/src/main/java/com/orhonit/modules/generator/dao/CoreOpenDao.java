package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreOpenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 党务公开表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-05-18 15:14:02
 */
@Mapper
public interface CoreOpenDao extends BaseMapper<CoreOpenEntity> {

    void insertOpen(CoreOpenEntity coreOpen);

}
