package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 考勤组时间数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceGroupTimeMapper extends BaseMapper<AttendanceGroupTime> {

    AttendanceGroupTime checkAttendanceGroupId(Integer id);

    boolean insertAttendanceGroupTime(Integer school_id, Integer semester_id, @Param("maps") HashMap<String, Object> maps, Long user_id);

    boolean updateAttendanceGroupTime(@Param("maps") HashMap<String, Object> maps, Long user_id);

    /**
     * 查找用户考勤时间段ID
     *
     * @param attendanceGroupId
     * @param authTime
     * @return
     */
    AttendanceGroupTime selectAttendanceGroupTimeId(Integer attendanceGroupId, String authTime);
}
