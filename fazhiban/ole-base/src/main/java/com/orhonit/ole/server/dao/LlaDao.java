package com.orhonit.ole.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.server.model.LrlAtt;


@Mapper
public interface LlaDao {

	@Insert("insert into ole_base_dept(code, name_spell, id, address, legal_person,level,dept_property,parent_id,sort,law_type,tel,area_id,lawarea,if_effect,del_flag,create_by,create_name,create_date,update_by,update_name,update_date,name,mgl_name,mgl_address,mgl_legal_person,mgl_create_name,"
			+ "mgl_update_name) values(#{user.code}, #{module}, #{flag}, #{remark}, now(),#{user.id}, #{module}, #{flag}, #{remark}, now(),#{user.id}, #{module}, #{flag}, #{remark}, now())")
	int save(LrlAtt lepeson);

	int count(@Param("params") Map<String, Object> params);

	List<LrlAtt> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
}
