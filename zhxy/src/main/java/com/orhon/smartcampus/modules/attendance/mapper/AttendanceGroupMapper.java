package com.orhon.smartcampus.modules.attendance.mapper;

import com.google.gson.JsonObject;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * <p>
 * 考勤组数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceGroupMapper extends BaseMapper<AttendanceGroup> {
    Boolean insertAttendanceGroup(Integer school_id, String title, Long user_id);

    String selectAttendanceGroupId(Integer attendanceGroupId);

    Boolean deleteAttendanceGroup(Integer attendanceGroupId);

    Boolean updateAttendanceGroup(Integer id, String title, Long user_id);

    /**
     * 查询用户考勤组
     *
     * @param maps
     * @return
     */
    Integer attendanceGroup(HashMap<String, Object> maps);
}
