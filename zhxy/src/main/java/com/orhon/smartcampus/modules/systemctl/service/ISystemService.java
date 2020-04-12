package com.orhon.smartcampus.modules.systemctl.service;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestBody;

import com.orhon.smartcampus.utils.R;

/**
 * 服务类
 * @author tsutsumiiwamine
 *
 */
public interface ISystemService{

	R inserts(HashMap<String, Object> maps);

	boolean getList(Integer department_id, Integer duty_id, Integer user_id);

	R Delete(HashMap<String, Object> maps);

	R isLeaders(HashMap<String, Object> maps);

	R getUserDepartments();

	R getUserDuties();
	
	R getUserOperations();

	void insertsSoddr(Integer department_id, Integer duty_id);

	R delSoddr(HashMap<String, Object> maps);

	R insertsSoddr(HashMap<String, Object> maps);

	boolean getSoddrList(Integer department_id, Integer duty_id);

	boolean getSourList(Integer user_id, Integer operation_id);

	R insertsSour(HashMap<String, Object> maps);

	R delSour(HashMap<String, Object> maps);

	R insertsSoddr1(HashMap<String, Object> maps);

	R getUserModules();

	R getUserMenus();

	R getUsersDepartmentOprations();

	R getModeuleMenus(HashMap<String, Object> maps);

	R getModeuleWidgets(HashMap<String, Object> maps);

	R getMenusAndWidgets(HashMap<String, Object> maps);

	R getRenderMenu(Long moudle_id);


}
