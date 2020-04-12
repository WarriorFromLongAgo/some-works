package com.orhon.smartcampus.modules.student.service;

import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.utils.PageDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orhon.smartcampus.framework.service.BaseService;


/**
 * <p>
 * 学生基础信息表 服务类
 * </p>
 *
 * @author bao
 */
public interface SIInformationService extends BaseService<SInformation> {

	List<Map<String, Object>> getManagementTj(String school_id);

	List<Map<String, Object>> getManagementjTj(String school_id);

	List<Map<String, Object>> getManagementrxTj(String string, String school_id);

	List<Map<String, Object>> getManagementnvTj(String school_id);

	List<Map<String, Object>> getManagementnldTj(String school_id);

	List<Map<String, Object>> getManagementmzTj(String school_id);

	List<Map<String, Object>> getManagementmgznvTj(String string, String school_id);

	List<SInformation> getStudentByLearninfo(HashMap<String, Object> maps, PageDto dto, Map student_name);

	List<SInformation> getStudentByLearninfo(HashMap<String, Object> maps,  Map student_name);
	
	List<SInformation> getEclassByStudent(HashMap<Object, Object> maps, Map mapTypes);

	List<HashMap<String, Object>> PageListStudent(HashMap<String, Object> map);

	Integer StudentCount(HashMap<String, Object> map);

}
