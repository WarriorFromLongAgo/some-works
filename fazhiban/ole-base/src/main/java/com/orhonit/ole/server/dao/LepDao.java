package com.orhonit.ole.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.server.model.Lepeson;
import com.orhonit.ole.server.model.LepesonHi;
import com.orhonit.ole.server.model.LtcAtt;


@Mapper
public interface LepDao {

	@Insert("insert into ole_base_dept_person(id,code,name, sex,nation,tel,political,edu,card_num,create_name,create_by,create_date,dept_id,cert_num,lawarea,birthday,"
			+ "if_effect,duty,cert_type,cert_category,cert_auth,cert_time,cert_term,law_type,picture,other_date,del_flag,mgl_name) values(#{id},#{code},"
			+ " #{name}, #{sex}, #{nation},#{tel}, #{political}, #{edu}, #{card_num},#{create_name},#{create_by},#{create_date},#{dept_id},#{cert_num},#{lawarea},"
			+ "#{birthday},#{if_effect},#{duty},#{cert_type},#{cert_category},#{cert_auth},#{cert_time},#{cert_term},#{law_type},#{picture},#{other_date},0,#{ltcAtt.mgl_name})")
	int save(Lepeson lepeson);
	
	@Insert("insert into ole_base_dept_person_hi(person_id,code,name, sex,nation,tel,political,edu,card_num,create_name,create_by,create_date,dept_id,cert_num,lawarea,birthday,"
			+ "if_effect,duty,cert_type,cert_auth,cert_time,cert_term,law_type,picture,is_deal,del_flag,update_date,update_by,update_name) values(#{person_id},#{code},"
			+ " #{name}, #{sex}, #{nation},#{tel}, #{political}, #{edu}, #{card_num},#{create_name},#{create_by},#{create_date},#{dept_id},#{cert_num},#{lawarea},"
			+ "#{birthday},#{if_effect},#{duty},#{cert_type},#{cert_auth},#{cert_time},#{cert_term},#{law_type},#{picture},#{is_deal},#{del_flag},#{update_date},#{update_by},#{update_name})")
	int saveHi(LepesonHi lepesonHi);

	int count(@Param("params") Map<String, Object> params);

	List<Lepeson> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	@Update("update ole_base_dept_person set del_flag = 1 where id = #{id}")
	int delete(String  id);
	
	String execFunction(@Param("functionName") String functionName, @Param("deptId") String deptId);
	
	@Select("select * from ole_base_dept")
	List<LtcAtt> getLtcs();
	
	@Select("select * from ole_base_dept_person t where t.id = #{id}")
	Lepeson getLep(@Param("id") String  id);
	
	@Update("update ole_base_dept_person t set t.name = #{ltcAtt.name},t.sex = #{ltcAtt.sex},t.nation = #{ltcAtt.nation},t.tel = #{ltcAtt.tel},t.political = #{ltcAtt.political} ,"
			+ "t.tel = #{ltcAtt.tel},t.card_num = #{ltcAtt.card_num},t.if_effect = #{ltcAtt.if_effect},t.duty = #{ltcAtt.duty},t.update_name = #{ltcAtt.update_name},t.cert_num = #{ltcAtt.cert_num},t.birthday = #{ltcAtt.birthday},t.lawarea = #{ltcAtt.lawarea},t.update_by = #{ltcAtt.update_by},t.update_date = #{ltcAtt.update_date},"
			+ "t.dept_id = #{ltcAtt.dept_id},t.law_type = #{ltcAtt.law_type},t.cert_term = #{ltcAtt.cert_term},t.edu = #{ltcAtt.edu},t.cert_time = #{ltcAtt.cert_time},t.cert_auth = #{ltcAtt.cert_auth},t.cert_type = #{ltcAtt.cert_type},t.cert_category = #{ltcAtt.cert_category},t.picture = #{ltcAtt.picture},t.other_date = #{ltcAtt.other_date},t.del_flag = #{ltcAtt.del_flag},t.mgl_name=#{ltcAtt.mgl_name} where t.id = #{ltcAtt.id}")
	void updateLep(@Param("ltcAtt") Lepeson ltcAtt);
}
