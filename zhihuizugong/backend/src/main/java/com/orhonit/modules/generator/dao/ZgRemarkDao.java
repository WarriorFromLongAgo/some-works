package com.orhonit.modules.generator.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;

/**
 * 领导点评
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 11:33:57
 */
@Mapper
public interface ZgRemarkDao extends BaseMapper<ZgRemarkEntity> {

	void save(ZgRemarkEntity zgRemark);
	
}
