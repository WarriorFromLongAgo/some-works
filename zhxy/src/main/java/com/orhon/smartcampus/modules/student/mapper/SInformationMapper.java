package com.orhon.smartcampus.modules.student.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.utils.PageDto;

/**
 * <p>
 * 学生基础信息表 Mapper 接口
 * </p>
 *
 * @author bao
 */
@Mapper
public interface SInformationMapper extends BaseMapper<SInformation> {

	List<Map<String, Object>> getManagementTj(@Param("school_id") String school_id);

	List<Map<String, Object>> getManagementjTj(@Param("school_id") String school_id);

	List<Map<String, Object>> getManagementrxTj(@Param("string") String string,@Param("school_id") String school_id);

	List<Map<String, Object>> getManagementnvTj(@Param("school_id") String school_id);

	List<Map<String, Object>> getManagementnldTj(@Param("school_id") String school_id);

	List<Map<String, Object>> getManagementmzTj(@Param("school_id") String school_id);

	List<Map<String, Object>> getManagementmgznvTj(@Param("string") String string,@Param("school_id")  String school_id);

	List<SInformation> getStudentByLearninfo(@Param("maps") HashMap<String, Object> maps,@Param("map") Map student_name);

	List<SInformation> getEclassByStudent(@Param("maps") HashMap<Object, Object> maps,@Param("map") Map student_name);

	List<SInformation> getStudentByLearninfopage(@Param("maps") HashMap<String, Object> maps,@Param("dto")  PageDto dto,@Param("map") Map student_name);

    List<HashMap<String, Object>> PageListStudent(@Param("maps") HashMap<String, Object> map);

	Integer StudentCount(@Param("maps") HashMap<String, Object> map);

}
