package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.dto.ps.PsLawDTO;

@Mapper
public interface LawDao {
	/**
	 * ps按照法律类型分类统计
	 * @param 
	 * 
	 * 
	 * @return
	 */
	List<PsLawDTO> lawCount();
	/**
	 * ps按照法律类型查询法律名
	 * @param 
	 * 
	 * 
	 * @return
	 */
	List<PsLawDTO> lawByItemType(@Param("params") Map<String, Object> params);
	/**
	 * 按照法律类型查询全部法律名
	 * @param 
	 * 
	 * 
	 * @return
	 */
	List<PsLawDTO> lawAllByItemType(@Param("params") Map<String, Object> params);
	/**
	 * 按照权责类型查询法律
	 * @param 
	 * 
	 * 
	 * @return
	 */
	List<PsLawDTO> lawAllByProType(@Param("params") Map<String, Object> params);
}
