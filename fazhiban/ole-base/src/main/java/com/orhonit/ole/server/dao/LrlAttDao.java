package com.orhonit.ole.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.server.model.LarAtt;
import com.orhonit.ole.server.model.LrlAtt;
import com.orhonit.ole.server.model.LrlDetAtt;
import com.orhonit.ole.server.model.LtcAtt;

@Mapper
public interface LrlAttDao {

	@Insert("insert into ole_base_law_category(code, name, name_en, pub_dept, is_effect,item_type,create_name,create_by,create_date,publish_date,effect_level,effect_level_id,act_date,effect_date,source_href) values(#{code}, #{name}, #{name_en},"
			+ " #{pub_dept}, #{is_effect}, #{item_type},#{create_name},#{create_by},#{create_date},#{publish_date},#{effect_level},#{effect_level_id},#{act_date},#{effect_date},#{source_href})")
	int save(LrlAtt lrlAtt);

	int count(@Param("params") Map<String, Object> params);

	List<LrlAtt> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	@Select("SELECT * FROM ole_base_law_category a ORDER BY a.publish_date DESC")
	List<LrlAtt> listSerializerNum();
	
	@Delete("delete from ole_base_law_category where id = #{id}")
	int delete(Long id);
	
	@Delete("delete from ole_base_potence_law_map where id = #{id}")
	int deleteConn(Long id);
	
	@Select("select * from ole_base_law_category t where t.id = #{id}")
	LrlAtt getLrl(@Param("id") String id);
	
	@Select("select * from ole_base_law_item t where t.law_id = #{id}")
	List<LrlDetAtt> getDettLrl(@Param("id") String id);
	
	@Select("select * from ole_base_dept t where t.id = #{id}")
	LtcAtt getltc(@Param("id") String id);
	
	@Update("update ole_base_law_category t set t.name = #{lrlAtt.name},t.name_en = #{lrlAtt.name_en},t.pub_dept = #{lrlAtt.pub_dept},t.is_effect = #{lrlAtt.is_effect},"
			+ "t.item_type = #{lrlAtt.item_type},t.effect_level = #{lrlAtt.effect_level},t.effect_level_id = #{lrlAtt.effect_level_id},t.update_name = #{lrlAtt.update_name},"
			+ "t.update_by = #{lrlAtt.update_by},t.update_date = #{lrlAtt.update_date},t.effect_level = #{lrlAtt.effect_level},t.effect_date = #{lrlAtt.effect_date},t.source_href = #{lrlAtt.source_href} where t.id = #{lrlAtt.id}")
	void updateLrl(@Param("lrlAtt") LrlAtt lrlAtt);
	
	List<LrlAtt> lawCateGoryByParam(@Param("name")String name);
	
	@Select("select * from ole_base_dept")
	List<LtcAtt> getLtcs();
	
	@Select("select * from ole_base_law_item t order by t.id")
	List<LrlDetAtt> lrlDetAll();
	
	@Select("select * from ole_base_law_item")
	List<LrlDetAtt> getLawsItem();
	
	@Select("select n.* from ole_base_potence_law_map t inner join ole_base_potence n on t.right_duty_id=n.id where t.wz_catalog_id = #{id} or t.fz_catalog_id = #{id}")
	List<LarAtt> getPDLtc(@Param("id") String id);
}
