package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceWeek;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 考勤组周设置数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceWeekMapper extends BaseMapper<AttendanceWeek> {

    Boolean insertAttendanceGroupWeek(Integer attendance_group_id, Integer week, Long user_id);


    Boolean deleteAttendanceGroupWeek(@Param("maps") HashMap<String, Object> maps);
}
