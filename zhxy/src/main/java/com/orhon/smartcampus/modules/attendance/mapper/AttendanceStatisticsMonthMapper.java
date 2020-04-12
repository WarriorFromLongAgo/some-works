package com.orhon.smartcampus.modules.attendance.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.attendance.entity.AttendanceStatisticsMonth;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AttendanceStatisticsMonthMapper extends BaseMapper<AttendanceStatisticsMonth> {

    AttendanceStatisticsMonth getAttendanceData(String user_id, String month);
}
