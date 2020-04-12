package com.orhon.smartcampus.modules.attendance.service;

import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.attendance.entity.AttendanceGroupDate;

import java.util.HashMap;

/**
 * <p>
 * 考勤时间段数据表 服务类
 * </p>
 *
 * @author Orhon
 */
public interface IAttendanceGroupDateService extends BaseService<AttendanceGroupDate> {
    String insertAttendanceGroupDate(HashMap<String, Object> maps);

    String updateAttendanceGroupDate(HashMap<String, Object> maps);
}
