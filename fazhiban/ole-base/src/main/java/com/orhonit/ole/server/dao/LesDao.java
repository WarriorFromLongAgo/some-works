package com.orhonit.ole.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.server.model.Lesection;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.sys.model.Area;


@Mapper
public interface LesDao {

	@Insert("insert into ole_base_area(code, name,level,parent_id,name_en,sort,is_effect,create_name,create_by,create_date) values"
			+ "(#{code}, #{name}, #{level}, #{parent_id},#{name_en}, #{sort}, #{is_effect},#{create_name},#{create_by},#{create_date})")
	int save(Lesection lepeson);

	int count(@Param("params") Map<String, Object> params);

	List<Lesection> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	@Update("update ole_base_area set del_flag = 1 where id = #{id}")
	int delete(Long id);
	
	@Select("select * from ole_base_area t where t.id = #{id}")
	Lesection getLes(@Param("id") String id);
	
	@Select("select * from ole_base_area")
	List<Lesection> getLtcs();
	
	List<Lesection> getAreaTree(@Param("params") Map<String, Object> params);
	
	@Update("update ole_base_area t set t.name = #{lesection.name},t.parent_id = #{lesection.parent_id},t.name_en = #{lesection.name_en},t.level = #{lesection.level},"
			+ "t.is_effect = #{lesection.is_effect},t.sort = #{lesection.sort},t.update_name = #{lesection.update_name},t.update_by = #{lesection.update_by},t.update_date = #{lesection.update_date} where t.id = #{lesection.id}")
	void updateLes(@Param("lesection") Lesection lesection);
	
	@Select("select * from ole_base_area t where t.is_effect ='1' and t.del_flag = '0' order by t.sort")
	List<Lesection> areaAll();
	
	
	@Select("select * from ole_base_area t where t.parent_id = #{id}")
	List<Lesection> areaAllP(@Param("id") String id);
	
	@Select("select * from ole_base_dept t where t.area_id = #{id}")
	List<LtcAtt> areaAllPd(@Param("id") String id);
	
}
