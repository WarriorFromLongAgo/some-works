package com.orhonit.ole.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.sys.model.Person;
import com.orhonit.ole.sys.model.User;

@Mapper
public interface UserDao  {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into ole_sys_user(username, password, salt, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime, updateTime, dept_id, person_id, area_id) values(#{username}, #{password}, #{salt}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, now(), now(), #{dept_id}, #{person_id}, #{area_id})")
	int save(User user);

	@Select("select t.*,k.name deptName from ole_sys_user t  LEFT JOIN ole_base_dept k on t.dept_id=k.id  where t.id = #{id}")
	User getById(Long id);

	@Select("select t.*,k.name deptName from ole_sys_user t  LEFT JOIN ole_base_dept k on t.dept_id=k.id  where t.username = #{username}")
	User getUser(@Param("username") String username);

	@Update("update ole_sys_user t set t.status = #{status} where t.id = #{id}")
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);

	@Update("update ole_sys_user t set t.password = #{password} where t.id = #{id}")
	int changePassword(@Param("id") Long id, @Param("password") String password);

	Integer count(@Param("params") Map<String, Object> params);

	List<User> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);

	@Delete("delete from ole_sys_role_user where userId = #{userId}")
	int deleteUserRole(Long userId);

	int saveUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

	int update(User user);

	@Select("select * from ole_sys_user t where t.username = #{username} and t.password = #{password}")
	User getfindByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
	
	@Delete("delete from ole_sys_user where id = #{id}")
	int delete(Long id);
	
	@Select("select * from ole_sys_user t where t.person_id = #{personId}")
	User getUserByPersonId(@Param("personId") String personId);
	
	@Select("select picture from ole_base_dept_person t where t.id = #{personId}")
	String getImgByPersonId(@Param("personId") String personId);
	
	List<Object> selRoleByPersonNum(String username);
	
	@Select("select * from ole_base_dept_person t where t.id = #{personId}")
	Person getPersonById(@Param("personId") String personId);
}
