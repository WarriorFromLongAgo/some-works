package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.orhonit.ole.enforce.entity.CheckTypeEntity;

@Mapper
public interface CheckTypeDao {
	//检查类型列表
	List<CheckTypeEntity> getCheckTypeList(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	//检查类型统计
	Integer  getCheckTypeCount(@Param("params") Map<String, Object> params);
	
	//检查类型保存
	@Insert("insert into ole_ef_check_type(title,sort,dept_id,create_name,create_by,create_date,is_effect,del_flag) values (#{title}, #{sort}, #{deptId}, #{createName}, #{createBy}, #{createDate}, #{isEffect},#{delFlag})")
	int save(CheckTypeEntity checkTypeEntity);

	//修改检查类型
	int update(CheckTypeEntity checkTypeEntity);
	
	//删除检查类型
	int delete(CheckTypeEntity checkTypeEntity);
	
	CheckTypeEntity finCheckTypeById(@Param("id") int id);
	//根据部门ID查找title
	List<CheckTypeEntity> checkTypeByDeotIdEntity(@Param("deptID")String deptID);
}
