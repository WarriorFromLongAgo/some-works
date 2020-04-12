package com.orhon.smartcampus.modules.moral.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.DataValues;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface DataValuesMapper extends BaseMapper<DataValues> {
    void student_statics(@Param("month_id") Integer month_id, @Param("week_id") Integer week_id);
    Integer selects_grade(@Param("user_id") Integer user_id,@Param("semester_id") Integer semester_id);
    Integer selects_school(@Param("user_id") Integer user_id,@Param("semester_id") Integer semester_id);
    Integer selects_grade_month(@Param("user_id") Integer user_id,@Param("start_times") String start_times,@Param("end_times") String end_times);
    Integer selects_school_month(@Param("user_id") Integer user_id,@Param("start_times") String start_times,@Param("end_times") String end_times);
    Integer moral_base(@Param("semester_id") Integer semester_id,@Param("school_id") Integer school_id);
    String student_name(@Param("user_id") Integer user_id);
    String class_name(@Param("eclass_id") Integer eclass_id);
    List<HashMap<String, Object>> getDateValue(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getDateValuepage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);
    Object getOneDateValue(Integer id);
}
