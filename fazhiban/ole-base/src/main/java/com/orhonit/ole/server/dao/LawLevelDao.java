package com.orhonit.ole.server.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.server.model.LawLevel;
import com.orhonit.ole.server.model.Lesection;



@Mapper
public interface LawLevelDao {

	int count(@Param("params") Map<String, Object> params);
	
	String getNameById(@Param("id")String id);
	
	String getMglNameById(@Param("id")String id);
	
	List<LawLevel> list(@Param("params") Map<String, Object> params,@Param("start") Integer start, @Param("length") Integer length );

	List<LawLevel> getAllLawLevel();
	
	@Insert("insert into ole_base_law_level (id,name,parent_id,name_en,sort,if_effect,del_flag,create_by,create_name,create_date) values (#{id},"
			+ "#{name},#{parentId},#{nameEn},#{sort},#{ifEffect},#{delFlag},#{createBy},#{createName},#{createDate})")
	void save(LawLevel lawLevel);
	
	@Update("update ole_base_law_level set del_flag = '1' where id=#{id}")
	void updateDel(String id);
	
	LawLevel getInfoById (@Param("id")String id);
	
	@Update("update ole_base_law_level set name = #{name},name_en=#{nameEn},sort = #{sort},if_effect = #{ifEffect},parent_id = #{parentId} "
			+ "where id=#{id}")
	void update(LawLevel lawLevel);
}
