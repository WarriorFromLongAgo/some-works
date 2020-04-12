package com.orhon.smartcampus.modules.teacher.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教职工表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface TInformationMapper extends BaseMapper<TInformation> {

    public HashMap selectTeacherInfoById(Long teacherId);

    List<TInformation> getTeacherInfomation(@Param("maps") HashMap<String, Object> maps, @Param("map") Map teacher_name);

    List<TInformation> getTeacherInfomationpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto, @Param("map") Map teacher_name);

    public List<HashMap<String, Object>> getEclassIds(@Param("user_id") String user_id);

    public HashMap<String, Object> getSexCount(@Param("id") String id);

    public List<HashMap<String, Object>> getNationCount(@Param("id") String id);

    String getTeacherName(String user_id);

    List<HashMap<String, Object>> PageListTeacher(@Param("maps") HashMap<String, Object> map);

    HashMap<String, Object> teacherDetails(@Param("id") Integer id);
}
