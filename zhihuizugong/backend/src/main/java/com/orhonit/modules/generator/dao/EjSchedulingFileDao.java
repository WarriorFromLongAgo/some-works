package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.EjSchedulingFileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 调度附件表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Mapper
public interface EjSchedulingFileDao extends BaseMapper<EjSchedulingFileEntity> {
	
}
