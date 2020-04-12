package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.entity.SmsEntity;

@Mapper
public interface SmsDao {
	
	/**
	 * 获取执法主体列表
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<SmsEntity> getSmsByCertNum(@Param("params") Map<String, Object> params);
	
}
