package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreGoOutEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 外出登记表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-05-10 13:48:31
 */
@Mapper
public interface CoreGoOutDao extends BaseMapper<CoreGoOutEntity> {
	
}
