package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupTime;

import java.util.HashMap;

/**
 * <p>
 * 考勤组时间数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceGroupTimeService extends BaseService<AttendanceGroupTime> {

    Boolean checkAttendanceGroupId(Integer attendance_group_id);

    String insertAttendanceGroupTime(HashMap<String, Object> maps);

    String updateAttendanceGroupTime(HashMap<String, Object> maps);

    /**
     * 返回用户当前考勤时间段考勤时间ID
     *
     * @param maps
     * @param attendanceGroupId
     * @return
     */
    AttendanceGroupTime selectAttendanceGroupTimeId(HashMap<String, Object> maps, Integer attendanceGroupId);
}
