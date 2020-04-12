package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.dto.DocFlowDTO;
import com.orhonit.ole.enforce.dto.DocFlowListDTO;
import com.orhonit.ole.enforce.dto.shareapi.ApiDocFlowDTO;

@Mapper
public interface DocFlowDao {

	/**
	 * 获取流程节点文书
	 * @param flowNode
	 * @return
	 */
	List<DocFlowDTO> getDocFlows(@Param("paramMap")Map<String, Object> paramMap);
	
	
	/**
	 * 获取模板code,name
	 * @return
	 */
	List<Map<String,String>> getListTemplate();
	
	/**
	 * 获取配置文书列表
	 * @return
	 */
	List<DocFlowListDTO> docFlowList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	/**
	 * 获取配置文书总数
	 * @return
	 */
	Integer count(@Param("params") Map<String, Object> params);


	List<ApiDocFlowDTO> getApiDocFlows(Map<String, Object> params);
	
}
