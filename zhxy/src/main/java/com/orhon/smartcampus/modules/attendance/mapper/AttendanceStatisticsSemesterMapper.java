package com.orhon.smartcampus.modules.attendance.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceStatisticsSemester;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceStatisticsSemesterMapper extends BaseMapper<AttendanceStatisticsSemester> {

    AttendanceStatisticsSemester getAttendanceData(String user_id, Integer semester_id);
}
