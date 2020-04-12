package com.orhonit.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgWorkScheduleEntity;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-05 17:52:46
 */
@Mapper
public interface ZgWorkScheduleDao extends BaseMapper<ZgWorkScheduleEntity> {

	void save(ZgWorkScheduleEntity zgWorkSchedule);

	List<ZgWorkScheduleEntity> queryPage(@Param("params") Map<String, Object> params);
	List<ZgWorkScheduleEntity> queryPageCount(@Param("params") Map<String, Object> params);
	
}
