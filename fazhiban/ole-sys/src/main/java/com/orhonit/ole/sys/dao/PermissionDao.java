package com.orhonit.ole.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orhonit.ole.sys.model.Area;
import com.orhonit.ole.sys.model.Dept;
import com.orhonit.ole.sys.model.Permission;
import com.orhonit.ole.sys.model.Person;

@Mapper
public interface PermissionDao {

	@Select("select * from ole_sys_permission t order by t.sort")
	List<Permission> listAll();

	@Select("select * from ole_sys_permission t where t.type = 1 order by t.sort")
	List<Permission> listParents();
	
	@Select("select * from ole_sys_permission t where t.type <> 2 order by t.sort")
	List<Permission> listParents1();

	@Select("select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} AND sys_id = #{sysId} order by p.sort")
	List<Permission> listByUserId(@Param("userId") Long userId, @Param("sysId") String sysId);

	@Select("select p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
	List<Permission> listByRoleId(Long roleId);

	@Select("select * from ole_sys_permission t where t.id = #{id}")
	Permission getById(Long id);

	@Insert("insert into ole_sys_permission(parentId, name, css, href, type, permission, sort,sys_id) values(#{parentId}, #{name}, #{css}, #{href}, #{type}, #{permission}, #{sort},#{sysId})")
	int save(Permission permission);

	@Update("update ole_sys_permission t set parentId = #{parentId}, name = #{name}, css = #{css}, href = #{href}, type = #{type}, permission = #{permission}, sort = #{sort},sys_id=#{sysId} where t.id = #{id}")
	int update(Permission permission);

	@Delete("delete from ole_sys_permission where id = #{id}")
	int delete(Long id);
	
	@Delete("delete from ole_sys_permission where parentId = #{id}")
	int deleteByParentId(Long id);

	@Delete("delete from ole_sys_role_permission where permissionId = #{permissionId}")
	int deleteRolePermission(Long permissionId);
	
	// 获取测试表数据开始
	@Select("select distinct p.* from ole_sys_permission p inner join ole_sys_role_permission rp on p.id = rp.permissionId inner join ole_sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} AND p.type = #{menuType} order by p.sort")
	List<Permission> listModulesByUserId(@Param("userId") Long userId, @Param("menuType") Integer menuType );
	
	@Select("SELECT DISTINCT p.* FROM ole_sys_permission p INNER JOIN ole_sys_role_permission rp ON p.id = rp.permissionId INNER JOIN ole_sys_role_user ru ON ru.roleId = rp.roleId WHERE ru.userId = #{userId} AND p.parentId IN (SELECT id FROM ole_sys_permission WHERE SYS_ID = #{moduleName})   ORDER BY p.sort;")
	List<Permission> listModuleMenuByUserId(@Param("userId") Long userId, @Param("moduleName") String moduleName);
	// 获取测试表数据结束
	
	
	//获取区划数据列表
	@Select("select * from ole_base_area t where t.is_effect = '1' and t.del_flag = '0' order by t.sort")
	List<Area> areaAll();
	
	//获取主体数据列表
	@Select("select * from ole_base_dept t where t.area_id = #{area_id} and t.if_effect = '1' and t.del_flag = '0' order by t.sort")
	List<Dept> deptAll(String area_id);
	
	//获取主体人员数据列表
	@Select("select * from ole_base_dept_person where dept_id = #{dept_id} and if_effect = '1' and del_flag = '0'")
	List<Person> personAll(String dept_id);
	
	//获取人员数据
	@Select("select * from ole_base_dept_person where id = #{id}")
	Person person(String id);
}
