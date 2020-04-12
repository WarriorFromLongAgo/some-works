package com.orhonit.ole.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.sys.model.SysDictEntity;

@Mapper
public interface DictDao  {

	/**
	 * 根据字典大类和字典小类获取字典名称
	 * @param typeValue
	 * @param enumValue
	 * @return
	 */
	@Select("select enum_desc as  enumDesc from ole_sys_dict t where t.type_value = #{typeValue}  and enum_value=#{enumValue}")
	String getDescByValue(@Param("typeValue") String typeValue, @Param("enumValue") String enumValue);
	
	
	/**
	 * 分页查询字典列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<SysDictEntity> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	/**
	 * 根据条件查询字典表总数
	 * @param params
	 * @return
	 */
	Integer count(@Param("params") Map<String, Object> params);

}
