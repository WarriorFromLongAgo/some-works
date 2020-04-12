package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.EjSchedulingRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 调度记录表(完成情况/督办情况)
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Mapper
public interface EjSchedulingRecordDao extends BaseMapper<EjSchedulingRecordEntity> {
	
}
