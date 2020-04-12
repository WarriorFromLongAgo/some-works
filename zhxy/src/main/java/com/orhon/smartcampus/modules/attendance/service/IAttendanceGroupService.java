package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroup;
import com.orhon.smartcampus.framework.service.BaseService;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>
 * 考勤组数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceGroupService extends BaseService<AttendanceGroup> {

    Boolean insertAttendanceGroup(AttendanceGroup attendanceGroup);

    String deleteAttendanceGroup(HashMap<String, Object> maps);

    Boolean updateAttendanceGroup(AttendanceGroup attendanceGroup);

    Integer AttendanceGroup(HashMap<String, Object> maps);
}
