package com.orhonit.ole.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.server.model.LrlAtt;
import com.orhonit.ole.server.model.LrlDetAtt;
import com.orhonit.ole.server.model.LtcAtt;

@Mapper
public interface LrlDetAttDao {

	@Insert("insert into ole_base_law_item(item_code, law_id, parent_id, item_content, item_name,item_des,item_explain,remarks,publish_date,effect_date,create_by,create_date,create_name) "
			+ "values(#{item_code}, #{law_id}, #{parent_id}, #{item_content}, #{item_name}, #{item_des},#{item_explain},#{remarks},#{publish_date},#{effect_date},#{create_by},#{create_date},#{create_name})")
	int save(LrlDetAtt lrlAtt);

	int count(@Param("params") Map<String, Object> params);

	List<LrlDetAtt> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	@Delete("delete from ole_base_law_item where id = #{id}")
	int delete(Long id);
	
	@Select("select * from ole_base_law_item t where t.id = #{id}")
	LrlDetAtt getDetLrl(@Param("id") String id);
	
	@Update("update ole_base_law_item t set t.item_name = #{lrlAtt.item_name},t.law_id = #{lrlAtt.law_id},t.parent_id = #{lrlAtt.parent_id},t.item_content = #{lrlAtt.item_content},"
			+ "t.item_des = #{lrlAtt.item_des},t.item_explain = #{lrlAtt.item_explain},t.remarks = #{lrlAtt.remarks},t.publish_date = #{lrlAtt.publish_date},t.effect_date = #{lrlAtt.effect_date} ,t.update_by = #{lrlAtt.update_by},t.update_date = #{lrlAtt.update_date},t.update_name = #{lrlAtt.update_name} where t.id = #{lrlAtt.id}")
	void updateLrl(@Param("lrlAtt") LrlDetAtt lrlAtt);
	
	@Select("select n.* from ole_base_law_item t inner join ole_base_law_category n on t.law_id=n.id where t.law_id = #{id}")
	List<LrlAtt> getPDLtc(@Param("id") String id);
}
