package com.orhonit.ole.server.dao;

import com.orhonit.ole.server.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


@Mapper
public interface LarDao {

	@Insert("insert into ole_base_potence(id,code, name, parent_id, duty,limit_time,is_effect,create_name,create_by,create_date,pro_type,level,duty_ref,approval_req,acc_basis,remarks,del_flag,process_id) values(#{id},#{code}, "
			+ "#{name}, "
			+ "#{parent_id}, #{duty},#{limit_time}, #{is_effect},#{create_name},#{create_by},#{create_date},#{pro_type}, #{level},#{duty_ref},#{approval_req},#{acc_basis},#{remarks},#{del_flag},#{process_id})")
	int save(LarAtt larAtt);
	
	@Insert("insert into ole_base_potence_hi(potence_id,code, name, parent_id, duty,limit_time,is_effect,create_name,create_by,create_date,pro_type,level,duty_ref,approval_req,acc_basis,remarks,is_deal,del_flag,process_id) values(#{potence_id},#{code}, "
			+ "#{name}, "
			+ "#{parent_id}, #{duty},#{limit_time}, #{is_effect},#{create_name},#{create_by},#{create_date},#{pro_type}, #{level},#{duty_ref},#{approval_req},#{acc_basis},#{remarks},0,#{del_flag},#{process_id})")
	int saveHi(LarAttHi larAtthi);

	@Insert("insert into ole_base_potence_law_map(right_duty_id, wz_catalog_name,wz_item,wz_catalog_id, wz_content, fz_catalog_name,fz_item,fz_catalog_id, fz_content, is_effect,create_name,create_date,create_by,update_by,update_name,update_date) "
			+ "values(#{right_duty_id},#{wz_catalog_name},#{wz_item}, #{wz_catalog_id}, #{wz_content},#{fz_catalog_name},#{fz_item}, "
			+ "#{fz_catalog_id}, #{fz_content}, #{is_effect},#{create_name},#{create_date},#{create_by}, #{update_by},#{update_name}, #{update_date})")
	int saveCon(LarConAtt larAtt);

	int count(@Param("params") Map<String, Object> params);

	List<LarAtt> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	@Select("SELECT * FROM ole_base_potence a  ORDER BY a.create_date DESC")
	List<LarAtt> listSerializerNum();
	
	@Delete("delete from ole_base_potence where id = #{id}")
	int delete(String id);
	
	@Delete("delete from ole_base_potence_law_map where id = #{id}")
	int deleteCon(Long id);

    List<LarAtt> sameConn(@Param("right_duty_id") String right_duty_id, @Param("wz_catalog_id") String wz_catalog_id, @Param("wz_content") String wz_content, @Param("fz_catalog_id") String fz_catalog_id, @Param("fz_content") String fz_content);
	
	List<LarAtt> getLtcs(@Param("name")String name);
	
	@Select("select * from ole_base_potence t where t.id = #{id}")
	LarAtt getLar(@Param("id") String id);
	
	@Select("select * from ole_base_potence_law_map t where t.right_duty_id = #{id}")
	List<LarConAtt> getConLar(@Param("id") String id);
	
	@Select("select count(1) from ole_base_potence_law_map t where t.right_duty_id = #{params.id}")
	int conncount(@Param("params") Map<String, Object> params);
	
	@Select("select * from ole_base_potence_law_map t where t.id = #{id}")
	LarConAtt getLarconn(@Param("id") String id);
	
	@Update("update ole_base_potence t set t.name = #{larAtt.name},t.process_id = #{larAtt.process_id},t.parent_id = #{larAtt.parent_id},t.duty = #{larAtt.duty},t.limit_time = #{larAtt.limit_time},"
			+ "t.is_effect = #{larAtt.is_effect},t.update_name = #{larAtt.update_name},t.update_by = #{larAtt.update_by},t.update_date = #{larAtt.update_date},t.level = #{larAtt.level},t.duty_ref = #{larAtt.duty_ref},t.approval_req = #{larAtt.approval_req},t.acc_basis = #{larAtt.acc_basis},t.pro_type = #{larAtt.pro_type},t.remarks = #{larAtt.remarks},t.del_flag = #{larAtt.del_flag} where t.id = #{larAtt.id}")
	void updateLar(@Param("larAtt") LarAtt larAtt);
	
	int larcount(@Param("params") Map<String, Object> params);
	
	@Select("select DISTINCT t.code,t.name from ole_base_law_category t inner join ole_base_potence_law_map n on t.id=n.fz_catalog_id where n.right_duty_id=#{params.id}")
	List<LrlAtt> larlists(@Param("params") Map<String, Object> params);
	
	@Select("select DISTINCT t.code,t.name from ole_base_law_category t inner join ole_base_potence_law_map n on t.id=n.wz_catalog_id where n.right_duty_id=#{params.id}")
	List<LrlAtt> comlists(@Param("params") Map<String, Object> params);
	
	@Select("select DISTINCT t.code,t.name from ole_base_law_category t inner join ole_base_potence_law_map n on t.id=n.wz_catalog_id where n.right_duty_id=#{params.id}")
	int comcount(@Param("params") Map<String, Object> params);
	
	@Select("select * from ole_base_law_category where is_effect!='0'")
	List<LrlAtt> getLaws();
	
	@Select("select * from ole_base_law_item")
	List<LrlDetAtt> getLawsItem();
	
	@Select("select * from ole_base_potence t where t.parent_id = #{id}")
	List<LarAtt> getPLar(@Param("id") String id);
	
	@Select("select n.* from ole_base_potence_law_map t inner join ole_base_law_category n on t.wz_catalog_id=n.id or t.fz_catalog_id=n.id  where t.right_duty_id = #{id}")
	List<LrlAtt> getlLar(@Param("id") String id);
	
	@Select("select n.* from ole_base_potence_law_map t inner join ole_base_law_item n on t.wz_catalog_id=n.id or t.fz_catalog_id=n.id  where t.right_duty_id = #{id}")
	List<LrlDetAtt> getilLar(@Param("id") String id);
	
	@Select("select n.* from ole_base_potence_dept t inner join ole_base_dept n on t.dept_id=n.id where t.potence_id = #{id}")
	List<LtcAtt> getPDLtc(@Param("id") String id);
}
