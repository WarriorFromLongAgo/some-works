package com.orhon.smartcampus.modules.systemctl.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface OrgDepartmentsMapper extends BaseMapper<OrgDepartments> {
    List<HashMap<String, Object>> getDepartmentTeacher(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getDepartmentTeacherpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);
//    List<HashMap<String, Object>> departmentToGrade(@Param("list") List<HashMap> list);
//    List<HashMap<String, Object>> departmentToSubject(@Param("list") List<HashMap> list);
//    List<HashMap<String, Object>> departmentToDuties(@Param("list") List<HashMap> list);
    void departmentToGrade(List<Object> list);
    void departmentToSubject(List<Object> list);
    void departmentToDuties(List<Object> list);

}
