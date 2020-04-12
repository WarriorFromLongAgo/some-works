package com.orhonit.ole.server.dao;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.server.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LtcAttDao {

	@Insert("insert into ole_base_dept(id,code,name_spell,address,legal_person,level,dept_property,parent_id,sort,law_type,tel,area_id,lawarea,"
			+ "if_effect,is_ps,del_flag,create_by,create_name,create_date,update_by,update_name,"
			+ "update_date,name,mgl_name,mgl_address,mgl_legal_person,mgl_create_name,"
			+ "mgl_update_name,short_name,mgl_short_name,mgl_lawarea) values(#{id},#{code}, #{name_spell},#{address}, #{legal_person}, #{level}, #{dept_property}, "
			+ "#{parent_id},#{sort}, #{law_type}, #{tel}, #{area_id}, #{lawarea}, #{if_effect},  #{is_ps},#{del_flag}, "
			+ "#{create_by},#{create_name}, now(), #{update_by}, "
			+ "#{update_name}, now(), #{name}, #{mgl_name}, #{mgl_address}, #{mgl_legal_person}, #{mgl_create_name}, #{mgl_update_name}, #{short_name},#{mgl_short_name},#{mgl_lawarea})")
	void save(LtcAtt ltcAtt);
	
	@Insert("insert into ole_base_dept_hi(dept_id,code,name_spell,address,legal_person,level,dept_property,parent_id,sort,law_type,tel,area_id,lawarea,"
			+ "if_effect,is_ps,del_flag,create_by,create_name,create_date,update_by,update_name,"
			+ "update_date,name,mgl_name,mgl_address,mgl_legal_person,mgl_create_name,"
			+ "mgl_update_name,short_name,mgl_short_name,is_deal,mgl_lawarea) values(#{dept_id},#{code}, #{name_spell},#{address}, #{legal_person}, #{level}, #{dept_property}, "
			+ "#{parent_id},#{sort}, #{law_type}, #{tel}, #{area_id}, #{lawarea}, #{if_effect},  #{is_ps},#{del_flag}, "
			+ "#{create_by},#{create_name}, now(), #{update_by}, "
			+ "#{update_name}, now(), #{name}, #{mgl_name}, #{mgl_address}, #{mgl_legal_person}, #{mgl_create_name}, #{mgl_update_name}, #{short_name},#{mgl_short_name},0,#{mgl_lawarea})")
	void saveHi(LtcAttHi ltcAttHi);
	
	@Insert("insert into ole_base_potence_dept(potence_id, dept_id,dept_id_agent, is_effect,effect_date,invalid_date,create_name,create_by,create_date,update_name,update_by,update_date) values(#{potence_id}, #{dept_id},#{dept_id_agent}, #{is_effect}, #{effect_date},"
			+ " #{invalid_date},#{create_name}, #{create_by}, #{create_date},#{update_name}, #{update_by}, #{update_date})")
	void saveConn(LtcConnAtt ltcAtt);

	int count(@Param("params") Map<String, Object> params);
		//统计执法主体数量
	@Select("select count(DISTINCT id) from ole_base_dept where del_flag = 0")
	int countzfzt();
	//统计执法人员数量
	@Select("SELECT COUNT(DISTINCT id) FROM ole_base_dept_person where del_flag = 0")
	int countzfry();
	//统计权责数量
	@Select("SELECT COUNT(DISTINCT id) FROM ole_base_potence where del_flag = 0")
	int countqzsl();
	//统计法律条数
	@Select("SELECT COUNT(DISTINCT id) FROM ole_base_law_category where del_flag = 0")
	int countflts();
	//执法主体分布情况------报表
	@Select("SELECT a.name,a.mgl_name , COUNT(b.id) AS cnumber FROM  ole_base_area a LEFT JOIN  ole_base_dept b ON  a.id = b.area_id WHERE a.parent_id <> 0 and a.del_flag = 0   GROUP BY a.name")
	List<ReportIndex> getZfzt();
	//执法人员分布情况------报表
	@Select("SELECT a.name,a.mgl_name ,COUNT(c.dept_id) AS cnumber FROM ole_base_area a LEFT JOIN ole_base_dept b ON a.id = b.area_id LEFT JOIN ole_base_dept_person c  ON b.id = c.dept_id WHERE a.parent_id <> 0 and a.del_flag = 0  GROUP BY a.name")
	List<ReportIndex> getZfry();






	@Select("select * from ole_base_dept where if_effect='1' and del_flag='0'")
	List<LtcAtt> getLtcs();
	
	List<LtcAtt> getDeptTree(@Param("params") Map<String, Object> params,@Param("id")String id);
	
	@Select("select * from ole_base_dept where area_id = #{id} and if_effect='1'")
	List<LtcAtt> getLtcss(String id);
	
	
	@Select("select bpd.*,bp.name  potence_name,bd.name dept_id_name from ole_base_potence_dept bpd LEFT JOIN ole_base_potence bp on bpd.potence_id=bp.id LEFT JOIN ole_base_dept bd on bpd.dept_id=bd.id  where dept_id = #{params.id}   limit #{params.start}, #{params.length}")
	List<LtcConnAtt> getConnLtcs(@Param("params") Map<String, Object> params);
	
	@Select("select bpd.*,bp.name  potence_name,bd.name dept_id_name from ole_base_potence_dept bpd LEFT JOIN ole_base_potence bp on bpd.potence_id=bp.id LEFT JOIN ole_base_dept bd on bpd.dept_id=bd.id  where dept_id = #{params.id} ")
	List<LtcConnAtt> getPotencesByDeptId(@Param("params") Map<String, Object> params);
	
	@Select("select count(1) from ole_base_potence_dept where dept_id = #{params.id}")
	int conncount(@Param("params") Map<String, Object> params);
	
	@Select("select * from ole_base_potence_dept where id = #{id}")
	LtcConnAtt getConnLtcss(String id);
	
	List<LtcAtt> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	List<LtcAtt> sameConn(@Param("potence_id") String potence_id,@Param("dept_id") String dept_id,@Param("dept_id_agent") String dept_id_agent);
	
	@Select("select * from ole_base_potence_dept t where t.dept_id = #{params.id}")
	List<LtcConnAtt> connlists(@Param("params") Map<String, Object> params);
	
	@Delete("delete from ole_base_dept where id = #{id}")
	int delete(String id);
	
	@Delete("delete from ole_base_potence_dept where id = #{id}")
	int deleteConn(String id);
	
	@Delete("delete from ole_base_potence_dept where id = #{id}")
	int deleteCon(String id);
	
	@Select("select * from ole_base_dept t where t.id = #{id}")
	LtcAtt getLtc(@Param("id") String id);
	
	@Update("update ole_base_dept t set t.name = #{lawAtt.name},t.address = #{lawAtt.address},t.legal_person = #{lawAtt.legal_person},"
			+ "t.law_type = #{lawAtt.law_type},t.tel = #{lawAtt.tel},t.name_spell = #{lawAtt.name_spell},t.update_name = #{lawAtt.update_name},t.update_by = #{lawAtt.update_by},t.update_date = #{lawAtt.update_date},"
			+ "t.if_effect = #{lawAtt.if_effect},t.is_ps = #{lawAtt.is_ps},t.lawarea = #{lawAtt.lawarea},t.short_name = #{lawAtt.short_name},t.level = #{lawAtt.level},t.dept_property = #{lawAtt.dept_property} ,"
			+ "t.sort = #{lawAtt.sort} ,t.area_id = #{lawAtt.area_id} ,t.parent_id = #{lawAtt.parent_id},t.del_flag = #{lawAtt.del_flag},t.mgl_name=#{lawAtt.mgl_name},t.mgl_address=#{lawAtt.mgl_address},t.mgl_legal_person=#{lawAtt.mgl_legal_person},"
			+ "t.mgl_short_name=#{lawAtt.mgl_short_name},t.mgl_lawarea=#{lawAtt.mgl_lawarea} where t.id = #{lawAtt.id}")
	void updateLtc(@Param("lawAtt") LtcAtt lawAtt);
	
	@Select("select t.* from ole_base_potence t inner join ole_base_potence_dept n on t.id=n.potence_id where n.dept_id=#{params.id}")
	List<LarAtt> larlists(@Param("params") Map<String, Object> params);
	
	@Select("select DISTINCT t.code,t.name from ole_base_dept t inner join ole_base_potence_dept n on t.id=n.dept_id_agent where n.dept_id=#{params.id}")
	List<LtcAtt> comlists(@Param("params") Map<String, Object> params);
	

	@Select("select * from ole_base_dept")
	List<LtcAtt> getAllLtcs();
	
	@Select("select * from ole_base_potence")
	List<LarAtt> getAllLars();
	
	@Select("select * from ole_base_dept t where t.parent_id = #{id} and t.del_flag='1' and t.if_effect='0'")
	List<LtcAtt> getPLtc(@Param("id") String id);
	
	@Select("select * from ole_base_dept_person t where t.dept_id = #{id} and t.del_flag='1' and t.if_effect='0' ")
	List<Lepeson> getDLtc(@Param("id") String id);
	
	@Select("select n.* from ole_base_potence_dept t inner join ole_base_potence n on t.potence_id=n.id where t.dept_id = #{id} and n.del_flag='1' and n.is_effect='0' ")
	List<LarAtt> getPDLtc(@Param("id") String id);
}
