package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 考勤时间段数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceGroupDateMapper extends BaseMapper<AttendanceGroupDate> {
    Boolean insertAttendanceGroupDate(@Param("maps") HashMap<String, Object> maps, Long user_id);

    Boolean updateAttendanceGroupDate(@Param("maps") HashMap<String, Object> maps, Long user_id);
}
