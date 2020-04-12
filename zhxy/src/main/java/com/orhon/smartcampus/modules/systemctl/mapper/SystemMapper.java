package com.orhon.smartcampus.modules.systemctl.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SystemMapper {

	void inserts(@Param("department_id") Integer department_id,@Param("duty_id") Integer duty_id,@Param("user_id") Integer user_id);

	HashMap<String, Object> getList(@Param("department_id") Integer department_id,@Param("duty_id") Integer duty_id,@Param("user_id") Integer user_id);

	void Delete(@Param("department_id") Integer department_id,@Param("duty_id") Integer duty_id,@Param("user_id") Integer user_id,@Param("leave_at") String leave_at);

	void isLeaders(@Param("department_id") Integer department_id,@Param("duty_id") Integer duty_id,@Param("user_id") Integer user_id,@Param("is_leaders") Integer is_leaders);

	List<HashMap<String, Object>> getDepartmentsIds(@Param("user_id") Long userId);

	List<HashMap<String, Object>> getDutiesIds(@Param("user_id") Long userId);

	void insertsSoddr1(@Param("department_id") Integer department_id,@Param("duty_id") Integer duty_id);

	void delSoddr(@Param("department_id") Integer department_id,@Param("duty_id") Integer duty_id);

	void insertsSoddr(List<Object> list);

	HashMap<String, Object> getSoddrList(@Param("department_id") Integer department_id,@Param("duty_id") Integer duty_id);

	HashMap<String, Object> getSourList(@Param("user_id") Integer user_id,@Param("operation_id") Integer operation_id);

	void insertsSour(@Param("user_id") Integer user_id,@Param("operation_id") Integer operation_id);

	void delSour(@Param("user_id") Integer user_id,@Param("operation_id") Integer operation_id);

	List<HashMap<String, Object>> getOperationIds(@Param("user_id") Long user_id);

	List<HashMap<String, Object>> getUserModulesIds(@Param("school_id") Integer school_id);

	List<HashMap<String, Object>> getUserModules(@Param("school_id") Integer school_id,@Param("user_id") Long user_id);

	List<HashMap<String, Object>> getUserMenus(@Param("school_id") Integer school_id,@Param("user_id") Long user_id);

	List<HashMap<String, Object>> getUsersDepartmentOprations(@Param("user_id") Long user_id);

	List<HashMap<String , Object>> getUserRenderedMenu(@Param("module_id") Long module_id);

}
