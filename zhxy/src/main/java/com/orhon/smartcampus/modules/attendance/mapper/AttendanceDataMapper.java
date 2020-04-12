package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceData;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 考勤数据表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceDataMapper extends BaseMapper<AttendanceData> {

    AttendanceData selectAttendnceData(String auth_time, Integer attendanceGroupId, Integer attendanceGroupTimeId, Long user_id);
}
